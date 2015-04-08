package nl.nvwoa.gillman.domain.calculation;


import nl.nvwoa.gillman.model.Bodies;
import nl.nvwoa.gillman.model.CalculatedPosition;
import nl.nvwoa.gillman.model.CalculationTypes;
import nl.nvwoa.gillman.util.AstroMath;
import nl.nvwoa.gillman.util.RangeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swisseph.SweDate;

@Component
public class SECalculator {
    @Autowired
    private SEFrontend seFrontend;

    public double[] calcAscMc(final SweDate sweDate, final double geoLon, final double geoLat) {
        double[] values = seFrontend.calcDomification(sweDate, geoLat, geoLon);
        double[] ascMc = new double[3];
        ascMc[0] = values[0];  // ascendant
        ascMc[1] = values[1];  // MC
        ascMc[2] = values[2];  // ARMC
        return ascMc;
    }

    public double calculateObliquity(final SweDate sweDate) {
        double[] epsilonAndNutation = seFrontend.calcNutationAndObliquity(sweDate);
        return epsilonAndNutation[1];
    }

    public CalculatedPosition calculatePlanet(final int index, final SweDate sweDate, final double geoLon, final double geoLat, CalculationTypes calculationType) {
        double[] eclipticPos = seFrontend.calcBodyLong(sweDate, index, geoLon, geoLat);
        double[] equatorialPos = seFrontend.calcBodyRA(sweDate, index, geoLon, geoLat);
        double[] ascMc = calcAscMc(sweDate, geoLon, geoLat);
        return createCalculatedPosition(index, sweDate, geoLon, geoLat, calculationType, eclipticPos, equatorialPos, ascMc);
    }

    private CalculatedPosition createCalculatedPosition(int index, SweDate sweDate, double geoLon, double geoLat, CalculationTypes calculationType, double[] eclipticPos, double[] equatorialPos, double[] ascMc) {
        CalculatedPosition position = new CalculatedPosition();
        position.setBodies(defineBody(index));
        position.setLongitude(eclipticPos[0]);
        position.setLatitude(eclipticPos[1]);
        position.setRightAscension(equatorialPos[0]);
        position.setDeclination(equatorialPos[1]);
        position.setRisingTime(calcRisingTime(index, sweDate, geoLon, geoLat, calculationType, position, ascMc));
        return position;
    }

    private double calcRisingTime(int index, SweDate sweDate, double geoLon, double geoLat, CalculationTypes calculationType, CalculatedPosition position, double[] ascMc) {

        if (calculationType.equals(CalculationTypes.ALTITUDE_BODY)) {
            return seFrontend.calcRisingTime(sweDate, index, geoLon, geoLat);
        } else if (calculationType.equals(CalculationTypes.ALTITUDE_ECLIPTICAL_POS)) {
            double epsilon = calculateObliquity(sweDate);
            return AstroMath.findRisingTimeForFixedPosition(sweDate, geoLon, geoLat, position, ascMc[2], epsilon);
        } else if (calculationType.equals(CalculationTypes.LONGITUDE)) {
            return RangeUtil.limitValueToRange(position.getLongitude() - ascMc[0], 0, 360);
        }
        throw new RuntimeException("Invalid calculationType : " + calculationType.getAbbreviation());
    }

    private Bodies defineBody(final int index) {
        for (Bodies body : Bodies.values()) {
            if (body.getId() == index) {
                return body;
            }
        }
        throw new IllegalArgumentException("Could not find an item in Bodies for id " + index);
    }

}

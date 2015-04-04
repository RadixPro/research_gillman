package nl.nvwoa.gillman.domain.calculation;


import nl.nvwoa.gillman.model.Bodies;
import nl.nvwoa.gillman.model.CalculatedPosition;
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
        ascMc[0] = values[0];
        ascMc[1] = values[1];
        ascMc[2] = values[2];
        return ascMc;
    }

    public double calculateObliquity(final SweDate sweDate) {
        double[] epsilonandNutation = seFrontend.calcNutationAndObliquity(sweDate);
        return epsilonandNutation[1];
    }

    public CalculatedPosition calculatePlanet(final int index, final SweDate sweDate, final double geoLon, final double geoLat) {
        CalculatedPosition position = new CalculatedPosition();
        position.setBodies(defineBody(index));
        double[] eclipticPos = seFrontend.calcBodyLong(sweDate, index, geoLon, geoLat);
        position.setLongitude(eclipticPos[0]);
        position.setLatitude(eclipticPos[1]);
        double[] equatPos = seFrontend.calcBodyRA(sweDate, index, geoLon, geoLat);
        position.setRightAscension(equatPos[0]);
        position.setDeclination(equatPos[1]);
//        double risingTime = seFrontend.calcRisingTime(sweDate, index, geoLon, geoLat);
         double[] ascMc = calcAscMc(sweDate, geoLon, geoLat);
         double epsilon = calculateObliquity(sweDate);

         double risingTime = findRisingTimeForFixedPosition(sweDate, geoLon, geoLat, position, ascMc[2], epsilon);
//         //define rising time as the distance from the ascendant.
//         double risingTime = RangeUtil.limitValueToRange(position.getLongitude() - ascMc[0], 0, 360);
        position.setRisingTime(risingTime);
        return position;
    }

    private Bodies defineBody(final int index) {
        for (Bodies body : Bodies.values()) {
            if (body.getId() == index) {
                return body;
            }
        }
        throw new IllegalArgumentException("Could not find an item in Bodies for id " + index);
    }

    private double findRisingTimeForFixedPosition(final SweDate sweDate, final double geoLon, final double geoLat, final CalculatedPosition position, final double raMC, final double epsilon) {
        double lastJulDate = sweDate.getJulDay();
        double newJulDate = lastJulDate;
        double timeIncrease = 0.01;
        double lastAltitude = AstroMath.altitudeForEquatorialPosition(geoLat, position.getDeclination(), position.getRightAscension(), raMC);
        double newAltitude;
        boolean continueSearch = true;
        while (continueSearch) {
            newJulDate = lastJulDate + timeIncrease;
            double[] ascMc = calcAscMc(new SweDate(newJulDate), geoLon, geoLat);
            newAltitude = AstroMath.altitudeForEquatorialPosition(geoLat, position.getDeclination(), position.getRightAscension(), ascMc[2]);
            if ((lastAltitude <= 0.0) && (newAltitude > 0.0)) {
                if (timeIncrease > 0.000001) { // less than 0.1 second
                    // do not change lastJulDate or lastAltitude
                    timeIncrease *= 0.1;
                } else {
                    continueSearch = false;
                }
            } else {
                lastJulDate = newJulDate;
                lastAltitude = newAltitude;
            }
        }
        return newJulDate;
    }
}

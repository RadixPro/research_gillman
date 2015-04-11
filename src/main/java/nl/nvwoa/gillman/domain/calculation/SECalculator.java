package nl.nvwoa.gillman.domain.calculation;


import nl.nvwoa.gillman.model.Bodies;
import nl.nvwoa.gillman.model.CalculatedPosition;
import nl.nvwoa.gillman.model.CalculationTypes;
import nl.nvwoa.gillman.util.RangeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swisseph.SweDate;

@Component
public class SECalculator {
    @Autowired
    private SEFrontend seFrontend;

    /**
     * Returns horizontal altitude for point specified in equatorial coordinates.
     * Used formula : sin h = sin GL * sin D + cos GL * cos D * cos HA
     * where h = altitude, GL = geographic latitude, D = declination and HA = hour angle
     * HA is defined as RAMC - RA-planet (right ascension MC minus right ascension planet)
     *
     * @param geoLat         geographic latitude (- for south, + for north)
     * @param declination    declination of te point to calculate.
     * @param rightAscension right ascension of te point to calculate.
     * @param raMC           right ascension of the MC.
     * @return altitude
     */

    public static double altitudeForEquatorialPosition(final double geoLat, final double declination, final double rightAscension, final double raMC) {
        final double hourAngle = RangeUtil.limitValueToRange(raMC - rightAscension, 0, 360);
        final double cosHourAngle = Math.cos(Math.toRadians(hourAngle));
        final double sinGeoLat = Math.sin(Math.toRadians(geoLat));
        final double cosGeoLat = Math.cos(Math.toRadians(geoLat));
        final double sinDecl = Math.sin(Math.toRadians(declination));
        final double cosDecl = Math.cos(Math.toRadians(declination));
        final double sinAltitude = (sinGeoLat * sinDecl) + (cosGeoLat * cosDecl * cosHourAngle);
        return RangeUtil.limitValueToRange(Math.toDegrees(Math.asin(sinAltitude)), -90, 90);
    }

    public double findRisingTimeForFixedPosition(final SweDate sweDate, final double geoLon, final double geoLat, final CalculatedPosition position, final double raMC, final double epsilon) {
        double lastJulDate = sweDate.getJulDay();
        double newJulDate = lastJulDate;
        double timeIncrease = 0.01;
        double lastAltitude = altitudeForEquatorialPosition(geoLat, position.getDeclination(), position.getRightAscension(), raMC);
        double newAltitude;
        boolean continueSearch = true;
        while (continueSearch) {
            newJulDate = lastJulDate + timeIncrease;
            double[] ascMc = calcAscMc(new SweDate(newJulDate), geoLon, geoLat);

            newAltitude = altitudeForEquatorialPosition(geoLat, position.getDeclination(), position.getRightAscension(), ascMc[2]);
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
            return findRisingTimeForFixedPosition(sweDate, geoLon, geoLat, position, ascMc[2], epsilon);
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

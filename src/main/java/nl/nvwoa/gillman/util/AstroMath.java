package nl.nvwoa.gillman.util;

import nl.nvwoa.gillman.model.CalculatedPosition;
import swisseph.SweDate;

public class AstroMath {
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

    /**
     * Returns Right Ascension for point on ecliptic in longitude (without latitude).
     * Used formula: RA = atan( tan L * cos E) .
     * where RA = right ascension, L = longitude, E = obliquity.
     *
     * @param longitude    eclipticalPoint point in longitude.
     * @param epsilon for obliquity.
     * @return right ascension in degrees.
     */

    public static double rightAscensionForEclipticalPoint(final double longitude, final double epsilon) {
        final double cosEps = Math.cos(Math.toRadians(epsilon));
        final double sinLong = Math.sin(Math.toRadians(longitude));
        final double cosLong = Math.cos(Math.toRadians(longitude));
        return RangeUtil.limitValueToRange(Math.toDegrees(Math.atan2(sinLong * cosEps, cosLong)), 0, 360);
    }

    public static double findRisingTimeForFixedPosition(final SweDate sweDate, final double geoLon, final double geoLat, final CalculatedPosition position, final double raMC, final double epsilon) {
        double lastJulDate = sweDate.getJulDay();
        double newJulDate = lastJulDate;
        double timeIncrease = 0.01;
        double lastAltitude = AstroMath.altitudeForEquatorialPosition(geoLat, position.getDeclination(), position.getRightAscension(), raMC);
        double newAltitude;
        boolean continueSearch = true;
        while (continueSearch) {
            newJulDate = lastJulDate + timeIncrease;
            newAltitude = AstroMath.altitudeForEquatorialPosition(geoLat, position.getDeclination(), position.getRightAscension(), raMC);
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

package nl.nvwoa.gillman.util;

public class AstroMath {
    /**
     * Returns horizontal altitude for point specified in equatorial coordinates.
     * Used formula : sin h = sin GL * sin D + cos GL * cos D * cos HA
     * where h = altitude, GL = geographic latitude, D = declination and HA = hour angle
     * HA is defined as RAMC - RA-planet (right ascension MC minus right ascension planet)
     *
     * @param geoLat         geographiclatitude (- for south, + for north)
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
     * @param epsilonvalue for obliquity.
     * @return right ascension in degrees.
     */

    public static double rightAscensionForEclipticalPoint(final double longitude, final double epsilon) {
        final double cosEps = Math.cos(Math.toRadians(epsilon));
        final double sinLong = Math.sin(Math.toRadians(longitude));
        final double cosLong = Math.cos(Math.toRadians(longitude));
        return RangeUtil.limitValueToRange(Math.toDegrees(Math.atan2(sinLong * cosEps, cosLong)), 0, 360);
    }

    /**
     * Calculates Ascensional Difference (AD).
     *
     * @param declination declination in degrees, use negative for south declination.
     * @param geoLatitude geographic latitude in degrees, use negative for south latitude.
     * @return ascensional difference
     */
    public static double ascensionalDiff(final double declination, final double geoLatitude) {
        double tanDecl = Math.tan(Math.toRadians(declination));
        double tanGL = Math.tan(Math.toRadians(geoLatitude));
        return Math.toDegrees(Math.asin(tanDecl * tanGL));
    }

    /**
     * Calculates declination.
     *
     * @param longitude longitude in degrees.
     * @param latitude  latitude in degrees, use negative for south latitude.
     * @param epsilon   epsilon in degrees
     * @return declination
     */
    public static double declination(final double longitude, final double latitude, final double epsilon) {
        double cosEps = Math.cos(Math.toRadians(epsilon));
        double sinLat = Math.sin(Math.toRadians(latitude));
        double sinEps = Math.sin(Math.toRadians(epsilon));
        double cosLat = Math.cos(Math.toRadians(latitude));
        double sinLong = Math.sin(Math.toRadians(longitude));
        return (Math.toDegrees(Math.asin((cosEps * sinLat) + (sinEps * cosLat * sinLong))));
    }
}

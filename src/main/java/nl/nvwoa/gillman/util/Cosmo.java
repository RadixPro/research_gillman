package nl.nvwoa.gillman.util;

/**
 * Cosmpographic formulae.
 */
public class Cosmo {
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
     * @param latitude latitude in degrees, use negative for south latitude.
     * @param epsilon epsilon in degrees
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

    private Cosmo() {
        // prevents instantiation.
    }
}

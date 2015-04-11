package nl.nvwoa.gillman.util;

import nl.nvwoa.gillman.domain.calculation.SECalculator;
import nl.nvwoa.gillman.model.CalculatedPosition;
import org.springframework.beans.factory.annotation.Autowired;
import swisseph.SweDate;

public class AstroMath {

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


}

package nl.nvwoa.gillman.util;

/**
 * Utility to manage the range for a value.
 * Author: Jan Kampherbeek
 */
public class RangeUtil {
    /**
     * Limit value to a a given range.
     * pre: upperLimit > lowerLimit.
     * post: lowerLimit <= value to check < upperLimit.
     *
     * @param testValue  The value to be limited to the range.
     * @param lowerLimit Lower limit of the range (inclusive).
     * @param upperLimit Upper limit of the range (exclusive).
     * @return Value limited to the defined range.
     * @throws IllegalArgumentException
     */
    public static double limitValueToRange(final double testValue,
                                           final double lowerLimit,
                                           final double upperLimit) {
        if ((upperLimit <= lowerLimit)) {
            throw new IllegalArgumentException("UpperRange: " + upperLimit + " <+ lowerLimit: " + lowerLimit);
        }
        return forceToRange(testValue, lowerLimit, upperLimit);
    }

    private static double forceLowerLimit(final double lowerLimit, final double rangeSize, final double toCheck) {
        double checkedForUpperLimit = toCheck;
        while (checkedForUpperLimit < lowerLimit) {
            checkedForUpperLimit += rangeSize;
        }
        return checkedForUpperLimit;
    }

    private static double forceToRange(final double testValue, final double lowerLimit, final double upperLimit) {
        final double rangeSize = upperLimit - lowerLimit;
        double checkedForLowerLimit = forceLowerLimit(lowerLimit, rangeSize, testValue);
        return forceUpperLimit(upperLimit, rangeSize, checkedForLowerLimit);
    }

    private static double forceUpperLimit(final double upperLimit, final double rangeSize, final double toCheck) {
        double checkedForLowerLimit = toCheck;
        while (checkedForLowerLimit >= upperLimit) {
            checkedForLowerLimit -= rangeSize;
        }
        return checkedForLowerLimit;
    }
}
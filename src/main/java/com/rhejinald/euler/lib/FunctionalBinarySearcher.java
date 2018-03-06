package com.rhejinald.euler.lib;

import java.util.function.Function;

public class FunctionalBinarySearcher {
    /**
     * Inclusive lowerBound
     * Exclusive upperBound
     * Assumed that f(lower)=true, f(upper)=false
     * <p>
     * Precisely: for some f(n), n <= x is true, n > x is false;
     * <p>
     * n_______x___________m
     * ^-true--^^--false--^.
     *
     * @return x, the highest value for which the function is still true
     */
    public static int search(int lowerBound, int upperBound, Function<Integer, Boolean> function) {
        return Math.max(lowerBound, searchHelper(lowerBound, upperBound, Integer.MIN_VALUE, function));
    }

    private static int searchHelper(int lowerBound, int upperBound, int highestTrueValue, Function<Integer, Boolean> function) {
        int pivot = lowerBound + Math.round((upperBound - lowerBound) / 2f);
        if (pivot == upperBound) {
            return highestTrueValue;
        }

        Boolean isWithinLowerSegment = function.apply(pivot);
        if (isWithinLowerSegment) {
            highestTrueValue = Math.max(highestTrueValue, pivot);
            return searchHelper(pivot, upperBound, highestTrueValue, function);
        } else {
            return searchHelper(lowerBound, pivot, highestTrueValue, function);
        }
    }

}

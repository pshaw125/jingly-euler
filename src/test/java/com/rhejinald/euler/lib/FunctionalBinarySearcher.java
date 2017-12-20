package com.rhejinald.euler.lib;

import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void testSearchOfArbitraryFunction() throws Exception {
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> val < 7)).isEqualTo(6);
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> val <= 7)).isEqualTo(7);
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> val <= 5)).isEqualTo(5);
        assertThat(FunctionalBinarySearcher.search(4, 10, val -> val <= 5)).isEqualTo(5);
        assertThat(FunctionalBinarySearcher.search(1, 20, val -> val <= 5)).isEqualTo(5);
        assertThat(FunctionalBinarySearcher.search(1, 21, val -> val <= 5)).isEqualTo(5);
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> val <= 10)).isEqualTo(9);
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> val <= 1)).isEqualTo(1);
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> true)).isEqualTo(9);
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> false)).isEqualTo(1);

    }
}

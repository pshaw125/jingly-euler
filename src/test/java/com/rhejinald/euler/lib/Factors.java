package com.rhejinald.euler.lib;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class Factors {
    /**
     * Returns all factors for ***n*** in a Set. This Set will include ***n*** as ***n*** is a factor of
     * itself.
     */
    public static Set<Long> getFactors(long subjectNumber) {
        final double squareRootOfSubject = Math.floor(Math.sqrt(subjectNumber));
        Set<Long> knownFactors = Sets.newHashSet();

        for (long divisor = 1; divisor <= squareRootOfSubject; divisor++) {
            if (knownFactors.contains(divisor)) continue;

            if (isFactor(subjectNumber, divisor)) {
                knownFactors.add(divisor);
                knownFactors.add(getDivisorResult(subjectNumber, divisor));
            }
        }
        return knownFactors;
    }

    /**
     * Returns proper divisors of ***n*** - basically all factors minus ***n***
     */
    public static Set<Long> getProperDivisors(long subjectNumber){
        Set<Long> factors = getFactors(subjectNumber);
        factors.remove(subjectNumber);
        return factors;
    }

    private static long getDivisorResult(long number, long divisor) {
        return number / divisor;
    }

    private static boolean isFactor(long number, long divisor) {
        return number == divisor
                || number >= divisor
                && number % divisor == 0;
    }

    @Test
    public void testIsFactor() throws Exception {
        assertThat(isFactor(1L, 1L)).isTrue();
        assertThat(isFactor(1L, 2L)).isFalse();
        assertThat(isFactor(10L, 1L)).isTrue();
        assertThat(isFactor(10L, 2L)).isTrue();
        assertThat(isFactor(10L, 5L)).isTrue();
        assertThat(isFactor(10L, 10L)).isTrue();
        assertThat(isFactor(10L, 9L)).isFalse();
        assertThat(isFactor(10L, 3L)).isFalse();
        assertThat(isFactor(26L, 6L)).isFalse();
        assertThat(isFactor(26L, 5L)).isFalse();
        assertThat(isFactor(26L, 4L)).isFalse();
        assertThat(isFactor(25L, 5L)).isTrue();
    }

    @Test
    public void testGetDivisorResult() throws Exception {
        assertThat(getDivisorResult(4, 2)).isEqualTo(2L);
        assertThat(getDivisorResult(10, 5)).isEqualTo(2L);
        assertThat(getDivisorResult(10, 10)).isEqualTo(1L);
        assertThat(getDivisorResult(10, 1)).isEqualTo(10L);
    }

    @Test
    public void testGetFactors() throws Exception {
        assertThat(getFactors(1)).containsOnly(1L);
        assertThat(getFactors(3)).containsOnly(1L, 3L);
        assertThat(getFactors(6)).containsOnly(1L, 2L, 3L, 6L);
        assertThat(getFactors(8)).containsOnly(1L, 2L, 4L, 8L);
        assertThat(getFactors(10)).containsOnly(1L, 2L, 5L, 10L);
        assertThat(getFactors(15)).containsOnly(1L, 3L, 5L, 15L);
        assertThat(getFactors(21)).containsOnly(1L, 3L, 7L, 21L);
        assertThat(getFactors(28)).containsOnly(1L, 2L, 4L, 7L, 14L, 28L);
    }
}

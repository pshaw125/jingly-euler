package com.rhejinald.euler.lib;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.HashSet;

import static com.rhejinald.euler.lib.Factors.*;
import static org.assertj.core.api.Assertions.assertThat;

public class FactorsTest {

    @Test
    public void testGetPrimeFactors() throws Exception {
        Primes primes = new Primes();
        primes.getPrimes(43);
        assertThat(getPrimeFactors(primes, 14)).containsExactly(2, 7);
        assertThat(getPrimeFactors(primes, 15)).containsExactly(3, 5);
        assertThat(getPrimeFactors(primes, 644)).containsExactly(2, 2, 7, 23);
        assertThat(getPrimeFactors(primes, 645)).containsExactly(3, 5, 43);
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
        assertThat(getFactors((long) 1)).containsOnly(1L);
        assertThat(getFactors((long) 3)).containsOnly(1L, 3L);
        assertThat(getFactors((long) 6)).containsOnly(1L, 2L, 3L, 6L);
        assertThat(getFactors((long) 8)).containsOnly(1L, 2L, 4L, 8L);
        assertThat(getFactors((long) 10)).containsOnly(1L, 2L, 5L, 10L);
        assertThat(getFactors((long) 15)).containsOnly(1L, 3L, 5L, 15L);
        assertThat(getFactors((long) 21)).containsOnly(1L, 3L, 7L, 21L);
        assertThat(getFactors((long) 28)).containsOnly(1L, 2L, 4L, 7L, 14L, 28L);
    }

    @Test
    public void testIsAbundantNumber() throws Exception {
        HashSet<Integer> firstSomeAbundantNumbers = Sets.newHashSet(12, 18, 20, 24, 30, 36, 40, 42, 48, 54, 56, 60, 66, 70, 72, 78, 80, 84, 88, 90, 96, 100, 102, 104, 108, 112);
        HashSet<Integer> otherNumbers = Sets.newHashSetWithExpectedSize(120);
        for (int i = 0; i < 113; i++) {
            otherNumbers.add(i);
        }
        otherNumbers.removeAll(firstSomeAbundantNumbers);
        for (Integer otherNumber : otherNumbers) {
            assertThat(isAbundantNumber(otherNumber)).isFalse();
        }
        for (Integer abundantNumber : firstSomeAbundantNumbers) {
            assertThat(isAbundantNumber(abundantNumber)).isTrue();
        }

    }
}

package com.rhejinald.euler.problems;

import com.rhejinald.euler.lib.Primes;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The primes 3, 7, 109, and 673, are quite remarkable. By taking any two primes and concatenating them in any order
 * the result will always be prime. For example, taking 7 and 109, both 7109 and 1097 are prime. The sum of these
 * four primes, 792, represents the lowest sum for a set of four primes with this property.
 * <p>
 * Find the lowest sum for a set of five primes for which any two primes concatenate to produce another prime.
 * <p>
 * ----------------------------
 */
public class Problem60Incomplete {

    private Primes primes;

    @Before
    public void setUp() {
        primes = new Primes();
    }

    @Test
    public void testPrimeConcatenationIsPrime() {
        primes.getPrimes(10000);
        assertThat(isConcatenatedPrimesStillPrime(3, 109)).isTrue();
        assertThat(isConcatenatedPrimesStillPrime(5, 109)).isFalse();
        assertThat(isConcatenatedPrimesStillPrime(3, 107)).isFalse();

    }

    private boolean isConcatenatedPrimesStillPrime(int a, int b) {
        return primes.isPrime(Integer.valueOf(String.valueOf(a) + String.valueOf(b)))
                && primes.isPrime(Integer.valueOf(String.valueOf(b) + String.valueOf(a)));
    }



}

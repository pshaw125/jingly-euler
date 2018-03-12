package com.rhejinald.euler.lib;

import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Not thread safe implementation until I have cause to do otherwise
 * <p>
 * Further reading
 * https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test
 */
public class PrimesTest {


    @Test
    public void testIsPrime() throws Exception {
        assertThat(new Primes().isPrime(1601)).isTrue();
    }

    @Test
    public void testPrimes() throws Exception {
        Primes primes = new Primes();
        assertThat(primes.getPrimes(6L)).containsOnly(2, 3, 5);
        assertThat(primes.getPrimes(6L)).containsOnly(2, 3, 5);
        assertThat(primes.getPrimes(11L)).containsOnly(2, 3, 5, 7, 11);
        assertThat(primes.getPrimes(12L)).containsOnly(2, 3, 5, 7, 11);
        assertThat(primes.getPrimes(26L)).containsOnly(2, 3, 5, 7, 11, 13, 17, 19, 23);
    }

    @Test
    public void testGetFactors() throws Exception {
        Primes primes = new Primes();
        assertThat(primes.getFactors(10)).containsOnly(2, 5);
        assertThat(primes.getFactors(15)).containsOnly(3, 5);
        assertThat(primes.getFactors(22)).containsOnly(2, 11);
        assertThat(primes.getFactors(27)).containsOnly(3);
        assertThat(primes.getFactors(30)).containsOnly(2, 3, 5);
        assertThat(primes.getFactors(1024)).containsOnly(2);
    }

    @Test
    public void testStress() throws Exception {
        Primes primes = new Primes();
        Set<Integer> primes1 = primes.getPrimes(500 * 1000 * 1000);
        assertThat(Collections.max(primes1)).isGreaterThan(100000);

    }
}

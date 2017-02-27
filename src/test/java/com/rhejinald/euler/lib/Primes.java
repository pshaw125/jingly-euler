package com.rhejinald.euler.lib;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Not thread safe implementation until I have cause to do otherwise
 * <p>
 * Further reading
 * https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test
 */
public class Primes {
    private Set<Integer> knownPrimes;
    private int highestCheckedNumber = 3;

    public Primes() {
        this.knownPrimes = Sets.newHashSet(2, 3);
    }

    /**
     * @param upperBound inclusive ceiling
     * @return primes
     */
    public Set<Integer> getPrimes(long upperBound) {
        if (upperBound > Integer.MAX_VALUE)
            throw new IllegalArgumentException("compatibility: long type not yet supported");
        getAndStorePrimesUpToAlt((int) upperBound);
        return knownPrimes.stream().filter(prime -> prime <= upperBound).collect(Collectors.toSet());
    }

    public Set<Integer> getFactors(long subject) {
        if (subject > Integer.MAX_VALUE)
            throw new IllegalArgumentException("compatibility: long type not yet supported");
        getAndStorePrimesUpToAlt((int) subject);
        return getPrimeFactorsFromKnownExisting(subject);
    }

    /**
     * A performance aware method which checks if the provided value is not prime, checked against all currently known
     * divisors. This is essentially a work around for running the seive over large number sets (100M+) which currently
     * does not have a good performance profile. This will be inaccurate if you don't call getPrimes for sqrt(value)
     * first.
     *
     * @param subject
     * @return
     */
    public boolean isAlreadyKnown(long subject) {
        for (Integer knownPrime : knownPrimes) {
            if (subject % knownPrime == 0) {
                return false;
            }
        }
        return true;
    }

    private void getAndStorePrimesUpToAlt(int upperBound) {
        if (upperBound <= highestCheckedNumber) return;

        int castUpper = (int) upperBound;
        boolean[] notPrimes = new boolean[castUpper]; //exploiting undeclared = false, for bool;
        knownPrimes.stream().filter(val -> val <= Math.sqrt(castUpper)).forEach(val -> removeMultiplesOfPrimeAlt(notPrimes, val));
        for (int i = 0; i < notPrimes.length; i++) {
            if (!notPrimes[i]) knownPrimes.add(i);
        }
        highestCheckedNumber = upperBound;
    }

    private void removeMultiplesOfPrime(ArrayList<Long> sieveNumbers, Integer prime) {
        sieveNumbers.removeAll(sieveNumbers.stream().filter(x -> (x % prime == 0)).collect(Collectors.toSet()));
    }

    private void removeMultiplesOfPrimeAlt(boolean[] sieveNumbers, int prime) {
        if (prime > Math.sqrt(Integer.MAX_VALUE)) {
            throw new IllegalArgumentException();
        }
        for (int i = 2; i <= ((double) sieveNumbers.length / prime) - 1; i++) {
            sieveNumbers[i * prime] = true;
        }
    }

    public boolean isPrime(Integer subject) {
        getAndStorePrimesUpToAlt(subject);
        return knownPrimes.contains(subject);
    }

    private HashSet<Integer> getPrimeFactorsFromKnownExisting(long subject) {
        HashSet<Integer> primeFactors = Sets.newHashSet();
        primeFactors.addAll(knownPrimes.stream()
                .filter(currentPrime -> subject % currentPrime == 0)
                .collect(Collectors.toList()));
        return primeFactors;
    }

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
    }

    @Test
    public void testGetFactors() throws Exception {
        assertThat(getFactors(10)).containsOnly(2, 5);
        assertThat(getFactors(15)).containsOnly(3, 5);
        assertThat(getFactors(22)).containsOnly(2, 11);
        assertThat(getFactors(27)).containsOnly(3);
        assertThat(getFactors(30)).containsOnly(2, 3, 5);
        assertThat(getFactors(1024)).containsOnly(2);
    }
}

package com.rhejinald.euler.lib;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Collections;
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
        if(upperBound > Integer.MAX_VALUE-1){
            throw new IllegalArgumentException("GTFO only support 2^31-2");
        }
        return getPrimes((int) upperBound);
    }

    public Set<Integer> getPrimes(int upperBound) {
        getAndStorePrimesUpTo(upperBound);
        return knownPrimes.stream().filter(prime -> prime <= upperBound).collect(Collectors.toSet());
    }

    public Set<Integer> getFactors(long subject) {
        if (subject > Integer.MAX_VALUE)
            throw new IllegalArgumentException("compatibility: long type not yet supported");
        getAndStorePrimesUpTo((int) subject);
        return getPrimeFactorsFromKnownExisting(subject);
    }

    /**
     * A performance aware method which checks if the provided value is not prime, checked against all currently known
     * divisors. This is essentially a work around for running the seive over large number sets (100M+) which currently
     * does not have a good performance profile. This will be inaccurate if you don't call getPrimes for sqrt(value)
     * first.
     */
    public boolean isAlreadyKnown(long subject) {
        for (Integer knownPrime : knownPrimes) {
            if (subject % knownPrime == 0) {
                return false;
            }
        }
        return true;
    }

    private void getAndStorePrimesUpTo(int upperBound) {
        final int upperBound1 = upperBound + 1;
        if (upperBound <= highestCheckedNumber) return;

        boolean[] notPrimes = new boolean[upperBound1]; //exploiting undeclared = false, for bool;
        notPrimes[0] = true;
        notPrimes[1] = true;
        knownPrimes.stream()
                .filter(val -> val <= Math.sqrt(upperBound1))
                .forEach(val -> removeMultiplesOfPrime(notPrimes, val));
        for (int i = Collections.max(knownPrimes)+1; i < notPrimes.length; i++) {
            boolean isValuePrime = !notPrimes[i];
            if (isValuePrime) {
                removeMultiplesOfPrime(notPrimes, i);
                knownPrimes.add(i);
            }
        }
        highestCheckedNumber = upperBound;
    }

    private void removeMultiplesOfPrime(boolean[] notPrime, int prime) {
        for (int i = 2; i < ((double) notPrime.length / prime); i++) {
            notPrime[i * prime] = true;
        }
    }

    public boolean isPrime(Integer subject) {
        getAndStorePrimesUpTo(subject);
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
        assertThat(primes.getPrimes(26L)).containsOnly(2, 3, 5, 7, 11, 13, 17, 19, 23);
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

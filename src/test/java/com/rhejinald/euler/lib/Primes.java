package com.rhejinald.euler.lib;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Not thread safe implementation until I have cause to do otherwise
 */
public class Primes {
    private Set<Long> knownPrimes;
    private Set<Long> knownNotPrimes;

    public Primes() {
        this.knownPrimes = Sets.newHashSet(2L, 3L);
        this.knownNotPrimes = Sets.newHashSet(1L);
    }

    public Set<Long> getFactors(long subject) {
        getAndStorePrimesUpTo(subject);
        return getPrimeFactorsFromKnownExisting(subject);
    }

    private Set<Long> getAndStorePrimesUpTo(long subject) {
        Long highestPrecalculatedPrime = Collections.max(knownPrimes);
        HashSet<Long> seiveNumbers = Sets.newHashSet();
        for (long i = highestPrecalculatedPrime + 1; i <= subject; i++) {
            seiveNumbers.add(i);
        }
        seiveNumbers.removeAll(knownNotPrimes);

        Iterator<Long> iterator = seiveNumbers.iterator();
        while (iterator.hasNext()) {
            Long potentialPrime = iterator.next();
            if (isPrime(potentialPrime)) {
                knownPrimes.add(potentialPrime);
            } else {
                knownNotPrimes.add(potentialPrime);
                iterator.remove();
            }
        }
        return knownPrimes;
    }

    private boolean isPrime(Long next) {
        for (Long knownPrime : knownPrimes) {
            if (next % knownPrime == 0) {
                return false;
            }
        }
        return true;
    }

    private HashSet<Long> getPrimeFactorsFromKnownExisting(long subject) {
        HashSet<Long> primeFactors = Sets.newHashSet();
        for (Long currentPrime : knownPrimes) {
            if (subject % currentPrime == 0) {
                primeFactors.add(currentPrime);
            }
        }
        return primeFactors;
    }

    @Test
    public void testPrimes() throws Exception {
        Primes primes = new Primes();
        assertThat(primes.getAndStorePrimesUpTo(6L)).containsOnly(2L, 3L, 5L);
        assertThat(primes.getAndStorePrimesUpTo(6L)).containsOnly(2L, 3L, 5L);
        assertThat(primes.getAndStorePrimesUpTo(11L)).containsOnly(2L, 3L, 5L, 7L, 11L);
        assertThat(primes.getAndStorePrimesUpTo(12L)).containsOnly(2L, 3L, 5L, 7L, 11L);
        assertThat(primes.knownNotPrimes).contains(12L);
    }

    @Test
    public void testGetFactors() throws Exception {
        assertThat(getFactors(10)).containsOnly(2L,5L);
        assertThat(getFactors(15)).containsOnly(3L,5L);
        assertThat(getFactors(22)).containsOnly(2L,11L);
        assertThat(getFactors(27)).containsOnly(3L);
        assertThat(getFactors(30)).containsOnly(2L,3L,5L);
        assertThat(getFactors(1024)).containsOnly(2L);
    }
}

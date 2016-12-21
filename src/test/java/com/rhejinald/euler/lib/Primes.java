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
 */
public class Primes {
    private Set<Long> knownPrimes;
    private Long highestCheckedNumber = 3L;

    public Primes() {
        this.knownPrimes = Sets.newHashSet(2L, 3L);
    }

    /**
     * @param upperBound inclusive ceiling
     * @return primes
     */
    public Set<Long> getPrimes(long upperBound) {
        getAndStorePrimesUpTo(upperBound);
        return Sets.newHashSet(knownPrimes);
    }

    public Set<Long> getFactors(long subject) {
        getAndStorePrimesUpTo(subject);
        return getPrimeFactorsFromKnownExisting(subject);
    }

    private void getAndStorePrimesUpTo(long primesUpTo) {
        ArrayList<Long> sieveNumbers = NumberRange.numberRangeArrayList(highestCheckedNumber + 1, primesUpTo);

        long numberToCheckUpTo = (long) Math.ceil(Math.sqrt(primesUpTo));

        for (Long knownPrime : knownPrimes) {
            removeMultiplesOfPrime(sieveNumbers, knownPrime, primesUpTo);
        }

        while (!sieveNumbers.isEmpty() && sieveNumbers.get(0) <= numberToCheckUpTo) {
            Long nextPrime = sieveNumbers.remove(0);
            knownPrimes.add(nextPrime);
            removeMultiplesOfPrime(sieveNumbers, nextPrime, primesUpTo);
        }

        knownPrimes.addAll(sieveNumbers); //once we empty, or reach sqrt(subject) then everything left is prime.

        highestCheckedNumber = primesUpTo;
    }

    private void removeMultiplesOfPrime(ArrayList<Long> sieveNumbers, Long prime, long subject) {
        Long valueBeingChecked = prime * prime;
        while (valueBeingChecked <= subject) {
            if (sieveNumbers.contains(valueBeingChecked)) {
                sieveNumbers.remove(valueBeingChecked); //not prime. Toss it out.
            }
            valueBeingChecked += prime; //next multiple
        }
    }


    public boolean isPrime(Long subject) {
        getAndStorePrimesUpTo(subject);
        return knownPrimes.contains(subject);
    }

    private HashSet<Long> getPrimeFactorsFromKnownExisting(long subject) {
        HashSet<Long> primeFactors = Sets.newHashSet();
        primeFactors.addAll(knownPrimes.stream()
                .filter(currentPrime -> subject % currentPrime == 0)
                .collect(Collectors.toList()));
        return primeFactors;
    }

    @Test
    public void testIsPrime() throws Exception {
        assertThat(new Primes().isPrime(1601L)).isTrue();
    }

    @Test
    public void testPrimes() throws Exception {
        Primes primes = new Primes();
        assertThat(primes.getPrimes(6L)).containsOnly(2L, 3L, 5L);
        assertThat(primes.getPrimes(6L)).containsOnly(2L, 3L, 5L);
        assertThat(primes.getPrimes(11L)).containsOnly(2L, 3L, 5L, 7L, 11L);
        assertThat(primes.getPrimes(12L)).containsOnly(2L, 3L, 5L, 7L, 11L);
    }

    @Test
    public void testGetFactors() throws Exception {
        assertThat(getFactors(10)).containsOnly(2L, 5L);
        assertThat(getFactors(15)).containsOnly(3L, 5L);
        assertThat(getFactors(22)).containsOnly(2L, 11L);
        assertThat(getFactors(27)).containsOnly(3L);
        assertThat(getFactors(30)).containsOnly(2L, 3L, 5L);
        assertThat(getFactors(1024)).containsOnly(2L);
    }
}

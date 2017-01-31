package com.rhejinald.euler.lib;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Not thread safe implementation until I have cause to do otherwise
 *
 * Further reading
 * https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test
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

    /**
     * A performance aware method which checks if the provided value is not prime, checked against all currently known
     * divisors. This is essentially a work around for running the seive over large number sets (100M+) which currently
     * does not have a good performance profile. This will be inaccurate if you don't call getPrimes for sqrt(value)
     * first.
     * @param subject
     * @return
     */
    public boolean isPrimeFromAlreadyFound(long subject) {
        for (Long knownPrime : knownPrimes) {
            if(subject%knownPrime==0){
                return false;
            }
        }
        return true;
    }


    private void getAndStorePrimesUpTo(long upperBound) {
        if(upperBound < highestCheckedNumber) return;

        long numberToCheckUpTo = (long) Math.ceil(Math.sqrt(upperBound));
        ArrayList<Long> sieveNumbers = NumberRange.numberRangeArrayList(highestCheckedNumber + 1, upperBound, Lists.newArrayList(2,3,5,7,13));

        for (Long knownPrime : knownPrimes) {
            removeMultiplesOfPrime(sieveNumbers, knownPrime);
        }

        while (!sieveNumbers.isEmpty() && sieveNumbers.get(0) <= numberToCheckUpTo) {
            Long nextPrime = sieveNumbers.remove(0);
            knownPrimes.add(nextPrime);
            removeMultiplesOfPrime(sieveNumbers, nextPrime);
        }

        knownPrimes.addAll(sieveNumbers); //once we empty, or reach sqrt(subject) then everything left is prime.

        highestCheckedNumber = upperBound;
    }

    private void removeMultiplesOfPrime(ArrayList<Long> sieveNumbers, Long prime) {
        sieveNumbers.removeAll(sieveNumbers.stream().filter(x -> (x%prime==0)).collect(Collectors.toSet()));
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

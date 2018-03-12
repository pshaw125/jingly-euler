package com.rhejinald.euler.lib;

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Primes {
    private Set<Integer> knownPrimes;
    private int highestCheckedNumber;
    private int highestKnownPrime;


    public Primes() {
        this.knownPrimes = Sets.newHashSet(2, 3);
        highestCheckedNumber = 3;
        highestKnownPrime = 3;
    }

    /**
     * @param upperBound inclusive ceiling
     * @return primes
     */
    public Set<Integer> getPrimes(long upperBound) {
        if (upperBound > Integer.MAX_VALUE - 1) {
            throw new IllegalArgumentException("GTFO. Primes only support 2^31-2");
        }
        return getPrimes((int) upperBound);
    }

    public Set<Integer> getPrimes(int upperBound) {
        getAndStorePrimesUpTo(upperBound);
        return knownPrimes.stream().filter(prime -> prime <= upperBound).collect(Collectors.toSet());
    }

    public Set<Integer> getFactors(long subject) {
        if (subject > Integer.MAX_VALUE) throw new IllegalArgumentException("compatibility: long type not yet supported");
        getAndStorePrimesUpTo((int) subject);
        return getPrimeFactorsFromKnownExisting(subject);
    }

    /**
     * A performance aware method which checks if the provided value is not prime, checked against all currently known
     * divisors. This is essentially a work around for running the seive over large number sets (100M+) which currently
     * does not have a good performance profile. This will be inaccurate if you don't call getPrimes for sqrt(value)
     * first.
     *
     * 3/12/18 - Is this even needed now that I've made performance suck less?
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
        if (upperBound <= highestCheckedNumber) return;
        expandKnownPrimesSize(upperBound);

        boolean[] notPrimes = new boolean[upperBound + 1]; //exploiting undeclared = false, for bool;
        notPrimes[0] = true;
        notPrimes[1] = true;
        knownPrimes.stream()
                .filter(val -> val <= Math.sqrt(upperBound + 1))
                .forEach(val -> removeMultiplesOfPrime(notPrimes, val));
        for (int i = highestKnownPrime + 1; i < notPrimes.length; i++) {
            boolean isValuePrime = !notPrimes[i];
            if (isValuePrime) {
                removeMultiplesOfPrime(notPrimes, i);
                knownPrimes.add(i);
                highestKnownPrime = i;
            }
        }
        highestCheckedNumber = upperBound;
    }

    private void expandKnownPrimesSize(int upperBound) {
        Set<Integer> primes = Sets.newHashSetWithExpectedSize(upperBound / 2);
        primes.addAll(knownPrimes);
        highestKnownPrime = Collections.max(knownPrimes); //sanity
        knownPrimes = primes;
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

}

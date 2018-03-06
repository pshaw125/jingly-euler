package com.rhejinald.euler.lib;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Factors {
    public static List<Integer> getPrimeFactors(Primes primes, long subjectNumber) {
        ArrayList<Integer> factors = Lists.newArrayList();
        long currentTotal = subjectNumber;
        for (Integer prime : primes.getPrimes((int) Math.ceil(Math.sqrt(subjectNumber)))) {
            while (isFactor(currentTotal, prime)) {
                factors.add(prime);
                currentTotal /= prime;
            }
            if (currentTotal == 1) {
                break;
            }
        }
        if(currentTotal > 1 && currentTotal < Integer.MAX_VALUE) {
            factors.add((int)currentTotal);
        }
        return factors;
    }

    static boolean isFactor(long number, long divisor) {
        return number == divisor
                || number >= divisor
                && number % divisor == 0;
    }

    /**
     * Returns all factors for ***n*** in a Set. This Set will include ***n*** as ***n*** is a factor of itself.
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

    public static boolean isAbundantNumber(int currentNumber) {
        return MathExt.sum(getProperDivisors(currentNumber)) > currentNumber;
    }

    static long getDivisorResult(long number, long divisor) {
        return number / divisor;
    }

    /**
     * Returns proper divisors of ***n*** - basically all factors minus ***n***
     */
    public static Set<Long> getProperDivisors(long subjectNumber) {
        Set<Long> factors = getFactors(subjectNumber);
        factors.remove(subjectNumber);
        return factors;
    }
}

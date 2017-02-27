package com.rhejinald.euler.problems;

import com.google.common.collect.Sets;
import com.rhejinald.euler.lib.Primes;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.rhejinald.euler.lib.Factors.getPrimeFactors;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Distinct Prime Factors
 *
 * The first two consecutive numbers to have two distinct prime factors are:
 *   14 = 2*7
 *   15 = 3*5
 *
 *   The first 3 consequtive numbers to have 3 distinct prime factors are:
 *
 *   644 = 2^2*7*23
 *   645 = 3*5*43
 *   646 = 2*17*19
 *
 *   Find first four consecutive numbers with 4 distinct prime factors each.
 *   What's the first number in that sequence?
 *======================================
 * So 2^2 is considered distinct from 2^1 on its own.
 * We'll need to post process each pull of factors so we recognise powers
 *
 * We check every 4th number, because the set we want must contain one of those. Once we find one, we can look
 * forward and backward to check if this is part of the actual set. If we find 4 or more consequtive values,
 * we have our set.
 *
 * Tried up to 100k with no joy; boosting to 10M found what I think is our value.
 * So I realize afterward: I didn't actually validate that the factors are distinct across the set; the first set
 * of 4 consq numbers with 4 distinct prime factors happened to also meet this property.
 *
 * Attempt 1: 134043 - Correct! 1.1s runtime; 42048th person to have solved this problem.
 * 134043 -> [3^1, 7^1, 13^1, 491^1]
 * 134044 -> [2^2, 23^1, 31^1, 47^1]
 * 134045 -> [5^1, 17^1, 19^1, 83^1]
 * 134046 -> [2^1, 3^2, 11^1, 677^1]
 */
public class Problem47 {

    private Primes primes;

    @Before
    public void setUp() throws Exception {
        primes = new Primes();
        primes.getPrimes(100);
    }

    @Test
    public void testProblem47() throws Exception {
        primes.getPrimes(3163);
        for (int i = 210; i < 10000000; i+=4) {
            Set<String> primeFactors = getPrimeFactorsWithPowers(getPrimeFactors(primes, i));
            if(primeFactors.size() != 4){
                continue;
            }

            int lowerBound = getLowerBound(i);
            int upperBound = getUpperBound(i);
            if(upperBound-lowerBound>=3){
                System.out.println("Chain found, starting with " + lowerBound + ", with base " + i);
                System.out.println(lowerBound + " -> " + getPrimeFactorsWithPowers(getPrimeFactors(primes, lowerBound)));
                System.out.println((lowerBound+1) + " -> " + getPrimeFactorsWithPowers(getPrimeFactors(primes, lowerBound+1)));
                System.out.println((lowerBound+2) + " -> " + getPrimeFactorsWithPowers(getPrimeFactors(primes, lowerBound+2)));
                System.out.println((lowerBound+3) + " -> " + getPrimeFactorsWithPowers(getPrimeFactors(primes, lowerBound+3)));
                break;
            }
        }
    }

    private int getUpperBound(int i) {
        for (int j = i+1; j <= i+3; j++){
            Set<String> primeFactors = getPrimeFactorsWithPowers(getPrimeFactors(primes, j));
            if(4 == primeFactors.size()){
                continue;
            }
            return j-1;
        }
        return i+3;
    }

    private int getLowerBound(int i) {
        for (int j = i-1; j >= i-3; j--) {
            Set<String> primeFactors = getPrimeFactorsWithPowers(getPrimeFactors(primes, j));
            if(4 == primeFactors.size()){
                continue;
            }
            return j+1;
        }
        return i-3;
    }

    private Set<String> getPrimeFactorsWithPowers(List<Integer> primeFactors) {
        Collections.sort(primeFactors);
        long currentValue=-1;
        long currentCount=0;
        HashSet<String> factors = Sets.newHashSet();
        for (Integer primeFactor : primeFactors) {
            if (primeFactor == currentValue) {
                currentCount++;
            } else {
                factors.add(currentValue + "^" + currentCount);
                currentValue=primeFactor;
                currentCount=1;
            }
        }
        factors.add(currentValue + "^" + currentCount);
        factors.remove("-1^0");
        return factors;
    }

    @Test
    public void testGetPrimeFactorsWithPowers() throws Exception {
        assertThat(getPrimeFactorsWithPowers(getPrimeFactors(primes, 644))).contains("2^2", "7^1", "23^1");
        assertThat(getPrimeFactorsWithPowers(getPrimeFactors(primes, 645))).contains("3^1", "5^1", "43^1");
        assertThat(getPrimeFactorsWithPowers(getPrimeFactors(primes, 646))).contains("2^1", "17^1", "19^1");
        assertThat(Sets.intersection(getPrimeFactorsWithPowers(getPrimeFactors(primes, 644)),getPrimeFactorsWithPowers(getPrimeFactors(primes, 643))).size()).isGreaterThanOrEqualTo(1);
    }
}

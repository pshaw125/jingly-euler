package com.rhejinald.euler.problems;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.rhejinald.euler.lib.Primes;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static java.util.Collections.max;

/**
 * Starting with 1 and spiralling anticlockwise in the following way, a square spiral with side length 7 is formed.
 * <p>
 * <p>
 * 37 36 35 34 33 32 31
 * 38 17 16 15 14 13 30
 * 39 18  5  4  3 12 29
 * 40 19  6  1  2 11 28
 * 41 20  7  8  9 10 27
 * 42 21 22 23 24 25 26
 * 43 44 45 46 47 48 49
 * <p>
 * It is interesting to note that the odd squares lie along the bottom right diagonal, but what is more interesting
 * is that 8 out of the 13 numbers lying along both diagonals are prime; that is, a ratio of 8/13 ≈ 62%.
 * <p>
 * If one complete new layer is wrapped around the spiral above, a square spiral with side length 9 will be formed.
 * If this process is continued, what is the side length of the square spiral for which the ratio of primes along
 * both diagonals first falls below 10%?
 * <p>
 * ==-==-==-==-==-==-==-==-==
 * Attempt 1 - 233 digits on diagonals; 23 primes on diagonals; With Ratio: 0.09871244635193133; Side length:117
 * 117: Wrong :) - I stopped adding primes after the initial gen
 *
 * So doing a bit more work and this one is a doozy. I keep OOM-ing the test JVM; The numbers are into the hundreds
 * of millions with 5000+ on each diagonal from the center outwards. The primes implementation has had some optimizations
 * and still peaks somewhere above 300,000,000 before OOM-ing.
 */
public class Problem58Incomplete {

    private static final int THREE_HUNDRED_MILLION = 300*1000*1000;

    @Test
    public void testProblem58() throws Exception {
        List<Integer> topRightDiagonal = Lists.newArrayList(1, 3);
        List<Integer> topLeftDiagonal = Lists.newArrayList(1, 5);
        List<Integer> bottomLeftDiagonal = Lists.newArrayList(1, 7);
        List<Integer> bottomRightDiagonal = Lists.newArrayList(1, 9);

        Primes primes1 = new Primes();
        Set<Integer> intersectingPrimes = Sets.newHashSet();
        primes1.getPrimes(THREE_HUNDRED_MILLION);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (foundResult(topRightDiagonal, topLeftDiagonal, bottomLeftDiagonal, bottomRightDiagonal, primes1, i, intersectingPrimes))
                break;
        }

        System.out.println(topRightDiagonal.toString());
        System.out.println(topLeftDiagonal.toString());
        System.out.println(bottomLeftDiagonal.toString());
        System.out.println(bottomRightDiagonal.toString());
    }

    private boolean foundResult(List<Integer> topRightDiagonal, List<Integer> topLeftDiagonal, List<Integer> bottomLeftDiagonal, List<Integer> bottomRightDiagonal, Primes primes1, int i, Set<Integer> intersectingPrimes) {
        addNextRing(topRightDiagonal, topLeftDiagonal, bottomLeftDiagonal, bottomRightDiagonal, intersectingPrimes, primes1);

        int digitsInDiagonals = (topLeftDiagonal.size() * 4) - 1;
        int primesInDiagonals = intersectingPrimes.size();
        double ratio = (double) primesInDiagonals / (double) digitsInDiagonals;

        if (i % 100 == 0 || ratio < 0.1) {
            System.out.println(digitsInDiagonals + " digits on diagonals & " +
                    primesInDiagonals + " primes on diagonals" +
                    " Primes:: " + (ratio * 100) + "%");
        }

        if (ratio < 0.1) {
            System.out.println("Side length:" + ((topRightDiagonal.size() * 2) - 1));
            return true;
        }
        return false;
    }

    private void addNextRing(List<Integer> topRightDiagonal, List<Integer> topLeftDiagonal, List<Integer> bottomLeftDiagonal, List<Integer> bottomRightDiagonal, Set<Integer> intersectingPrimes, Primes primes) {
        addItem(primes, intersectingPrimes, bottomRightDiagonal, bottomRightDiagonal.get(bottomRightDiagonal.size() - 1) + ((bottomRightDiagonal.size() - 1) * 8 + 8));
        addItem(primes, intersectingPrimes, bottomLeftDiagonal, bottomLeftDiagonal.get(bottomLeftDiagonal.size() - 1) + ((bottomLeftDiagonal.size() - 1) * 8 + 6));
        addItem(primes, intersectingPrimes, topLeftDiagonal, topLeftDiagonal.get(topLeftDiagonal.size() - 1) + ((topLeftDiagonal.size() - 1) * 8 + 4));
        addItem(primes, intersectingPrimes, topRightDiagonal, topRightDiagonal.get(topRightDiagonal.size() - 1) + ((topRightDiagonal.size() - 1) * 8 + 2));
    }

    private void addItem(Primes primes1, Set<Integer> intersectingPrimes, List<Integer> diagonal, int value) {
        if (primes1.isPrime(value)) {
            intersectingPrimes.add(value);
        }
        diagonal.add(value);
    }
}

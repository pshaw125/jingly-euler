package com.rhejinald.euler.problems;

import com.google.common.collect.Lists;
import com.rhejinald.euler.lib.Primes;
import com.rhejinald.euler.lib.QuadraticEquation;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * It was proposed by Christian Goldbach that every odd composite number can be written as the sum of a prime and twice a square.
 * <p>
 * 9 =  7 + 2 × 1^2
 * 15 =  7 + 2 × 2^2
 * 21 =  3 + 2 × 3^2
 * 25 =  7 + 2 × 3^2
 * 27 = 19 + 2 × 2^2
 * 33 = 31 + 2 × 1^2
 * It turns out that the conjecture was false.
 * <p>
 * What is the smallest odd composite that cannot be written as the sum of a prime and twice a square?
 * =================
 * Composite number: Non-prime positive integer (4,6,8,9,10...); 1 is neither composite nor prime.
 * So looking for the first non-prime odd which cannot be written as the sum of a prime and (2*n^2)
 * <p>
 * Twice n squared is the set following: 2, 8, 18, 32, 50, 72, 98
 * 6  10  14  18  22  26
 * below, say, 10k, how many primes are there vs how many twice-n-sq values? 1229 primes, 72 2n^2 values
 * <p>
 * So for each odd, C, check for primaltiy, then iter over each 2n^2, t, checking primality of C-t
 * Primes are more densely packed at the bottom, so we want to go from the top down.
 * So quadratic equation can be used to establish the upper N, then we work down from there.
 *
 * Attempt 1: 5777 - Correct! Feb 2 2017, 100ms; 44203rd to solve!
 */
public class Problem46 {

    @Test
    public void problem46() throws Exception {
        int current = 33;
        Primes primes = new Primes();
        primes.getPrimes(10000);
        while (current < 10000){
            current+=2;
            if(primes.isPrime((long) current)) continue;

            if(!hasNandPrimeComponents(current, primes, getUpperNValueForC(current))){
                System.out.println("No prime and 2n^2 combo found for " + current);
                break;
            }
        }
        System.out.println("end");
    }

    private boolean hasNandPrimeComponents(int current, Primes primes, int upperNValueForC) {
        for (int i = upperNValueForC; i > 0; i--) {
            if(primes.isPrime((long) (current - getTwoNSq(i)))) return true;
        }
        return false;
    }

    private int getTwoNSq(int i) {
        int i1 = 2 * (i * i);
        if (i < 0 || i1 < 0) {
            throw new IllegalArgumentException(i + " is too large; overflow exception (or you screwed up and called w negative i)");
        }
        return i1;
    }

    private int getUpperNValueForC(int c) {
        int cValue = -c;
        QuadraticEquation.QuadraticEquationResult quadraticEquationResult = QuadraticEquation.quadraticEquation(2, 0, cValue);
        return (int) Math.floor(quadraticEquationResult.getPositiveArc());
    }

    @Test
    public void testGetTwoNSq() throws Exception {
        assertThat(getTwoNSq(1)).isEqualTo(2);
        assertThat(getTwoNSq(2)).isEqualTo(8);
        assertThat(getTwoNSq(3)).isEqualTo(18);
        assertThat(getTwoNSq(4)).isEqualTo(32);
        assertThat(getTwoNSq(5)).isEqualTo(50);
        assertThat(getTwoNSq(6)).isEqualTo(72);
        assertThat(getTwoNSq(7)).isEqualTo(98);
    }

    @Test
    public void testGetUpperNValueForC() throws Exception {
        assertThat(getUpperNValueForC(9)).isEqualTo(2);
        assertThat(getUpperNValueForC(15)).isEqualTo(2);
        assertThat(getUpperNValueForC(21)).isEqualTo(3);
        assertThat(getUpperNValueForC(25)).isEqualTo(3);
        assertThat(getUpperNValueForC(27)).isEqualTo(3);
        assertThat(getUpperNValueForC(33)).isEqualTo(4);
    }

    @Test
    public void research() throws Exception {
        System.out.println(new Primes().getPrimes(10000).size());
        int currentValue = 2;
        ArrayList<Integer> twicePrimeSquared = Lists.newArrayList();
        for (int delta = 0; currentValue < 10000; delta += 4) {
            twicePrimeSquared.add(currentValue);
            currentValue += delta;
        }
        System.out.println(twicePrimeSquared.size());

    }
}

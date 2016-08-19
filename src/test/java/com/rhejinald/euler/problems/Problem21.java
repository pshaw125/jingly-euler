package com.rhejinald.euler.problems;

import com.google.common.collect.Sets;
import com.rhejinald.euler.lib.Factors;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class Problem21 {
    /**
     * Let d(n) be defined as the sum of proper divisors of n (numbers less than n which divide evenly into n).
     * If d(a) = b and d(b) = a, where a ≠ b, then a and b are an amicable pair,
     * and each of a and b are called amicable numbers.
     * <p>
     * Example: Proper divisors of 220 are 1, 2, 4, 5, 10, 11, 20, 22, 44, 55 and 110;
     * therefore d(220) = 284.
     * <p>
     * The proper divisors of 284 are 1, 2, 4, 71 and 142;
     * so d(284) = 220.
     * <p>
     * Evaluate the sum of all the amicable numbers under 10000.
     * ------------------------
     * Notes: I solved problem 12 with factors, so I pulled that out as a library class. A quick double check
     * should return the proper divisors (factors) - we just have to sum them.
     * Initially I plan to iterate 1..10000 and map against the sums of the resultant factors
     * <p>
     * It can be simplified (I misread the question. We're testing that d(a)=b and d(b)=a
     * This simplifies to d(d(a))=a
     * <p>
     * We want the sum of all amicable numbers, so we add them to a set and sum that
     * It's NOT 40284 (I screwed something up)
     * It IS 31626 - I wasn't counting a≠b in my checks - some numbers will map to themselves (I wonder which ones?)
     */

    @Test
    public void testProblem21() {
        Set<Long> amicableNumbers = Sets.newHashSet();
        for (long i = 1; i < 10000; i++) {
            if (amicableNumbers.contains(i)) continue;
            if (doubleFactorSumFunction(i) == i && singleFactorSumFunction(i) != i) {
                amicableNumbers.add(i);
                amicableNumbers.add(singleFactorSumFunction(i));
            }
        }

        long finalTotal = sumSet(amicableNumbers);
        System.out.printf("Final total = " + finalTotal);
        assertThat(finalTotal).isEqualTo(31626L);
    }

    @Test
    public void testGivenExamples() throws Exception {
        assertThat(singleFactorSumFunction(220)).isEqualTo(284L);
        assertThat(singleFactorSumFunction(284)).isEqualTo(220L);

        //my own NON example:
        assertThat(singleFactorSumFunction(10)).isEqualTo(8L);
        assertThat(singleFactorSumFunction(8)).isEqualTo(7L);
    }

    @Test
    public void testDoubleFactorSumFunction() throws Exception {
        assertThat(doubleFactorSumFunction(220L)).isEqualTo(284L);
        assertThat(doubleFactorSumFunction(284L)).isEqualTo(220L);

        assertThat(doubleFactorSumFunction(10L)).isNotEqualTo(10L); //is actually 7
    }

    private long doubleFactorSumFunction(long subjectNumber) {
        return singleFactorSumFunction(
                singleFactorSumFunction(subjectNumber));
    }

    private long singleFactorSumFunction(long i) {
        return sumSet(Factors.getProperDivisors(i));
    }

    private long sumSet(Set<Long> factors) {
        long runningTotal = 0;
        for (Long factor : factors) {
            runningTotal += factor;
        }
        return runningTotal;
    }
}

package com.rhejinald.euler.problems;


import com.rhejinald.euler.lib.NumbersAsStrings;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * A googol (10^100) is a massive number: one followed by one-hundred zeros; 100^100 is almost unimaginably large:
 * one followed by two-hundred zeros. Despite their size, the sum of the digits in each number is only 1.
 *
 * Considering natural [≥1] numbers of the form, a^b, where a, b < 100, what is the maximum digital sum?
 *
 * ========================================
 * So for 1..100^1..100, sum the digits of the result; find the biggest case. It's going to be less than 1800 (9*200)
 * since 100^100 (max case) is 200 digits in size.
 *
 * Short circuit 1)
 * a-values cannot be mod-10, given the above cases. The product will always be 1, no matter the power.
 *
 * Short circuit 2)
 * We know that the minimum is >100 (99^99 sums to 936), maybe 1000.
 * That means we can cut off at either 11 digits (all 9s = 99)
 * OR maybe even 111 digits as we decrease the power of each numerator. (111 9s => 999, so if the largest number is >1000
 * then there's no way any combination of 111 digits is better.
 *
 * Nothing ever breached 111 digits - it ended up being 99^95
 * Attempt 1 - 972 - Correct!
 *
 * Congratulations, the answer you gave to problem 56 is correct.
 * You are the 47236th person to have solved this problem.
 * This problem had a difficulty rating of 5%.
 **/
public class Problem56 {
    @Test
    public void testExploration() throws Exception {
        int maxValueFound = -1;

        for (int numerator = 99; numerator > 1; numerator--) {
            maxValueFound = checkPowersOfNumerator(maxValueFound, new BigDecimal(numerator));
        }
        System.out.println(maxValueFound);
    }

    private int checkPowersOfNumerator(int maxValueFound, BigDecimal numerator) {
        BigDecimal poweredResult = numerator.pow(99);

        while(poweredResult.toString().length() > 11) {
            int sumOfDigits = NumbersAsStrings.sumOfDigits(poweredResult.toString());
            if (maxValueFound < sumOfDigits) {
                maxValueFound = sumOfDigits;
            }
            poweredResult = poweredResult.divide(numerator);
        }
        return maxValueFound;
    }
}

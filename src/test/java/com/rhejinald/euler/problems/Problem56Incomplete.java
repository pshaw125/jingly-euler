package com.rhejinald.euler.problems;


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
 * As the power-100 value decreases, by the number of digits we know the upper bound a number can have after X iterations.
 * eg. 2^100 has N digits. While that might not be the biggest, we know that once we sum those digits, once we start losing
 * digits as the power decreases, eventually the highest number found will not be attainable with that many digits.
 * This might have a low ceiling - i.e. Once we have a score of over 100, we can exclude all numbers ≤ 100,000,000,000
 * [11 digits all 9s = 99 sum]
 * 111 digits for ≤999 sum..
 * 99^99 is going to be stupidly large still
 *
 *
 **/
public class Problem56Incomplete {
    @Test
    public void testExploration() throws Exception {
        BigDecimal bigDecimal = new BigDecimal(99);
        BigDecimal pow = bigDecimal.pow(99);
        pow.toString();

    }
}

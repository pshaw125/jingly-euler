package com.rhejinald.euler.problems;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * The series, 1^1 + 2^2 + 3^3 + ... + 10^10 = 10405071317.
 *
 * Find the last ten digits of the series, 1^1 + 2^2 + 3^3 + ... + 1000^1000.
 * -----------------------------------------
 * Notes:
 * This sounds scary, but it's not so much depending on the the jvm's ability to easily compute 999^999;
 * If it sucks / takes ages, we can perform a more iterative "multiply then mod" strategy to keep the
 * digit count down.
 *
 * Okay, so it finished in 11ms; I think this will be fine.
 *
 * Simple loop adding the powers; substring.
 *
 * Attempt 1: 9110846700 - Correct! 46ms,  85048th person to have solved this problem.
 *
 */
public class Problem48 {
    @Test
    public void testProblem48() throws Exception {
        BigDecimal bigDecimal = BigDecimal.valueOf(1);
        for (int i = 2; i <= 1000; i++) {
            bigDecimal = bigDecimal.add((BigDecimal.valueOf(i).pow(i)));
        }
        String s = bigDecimal.toString();
        System.out.println(s.substring(s.length()-10));
    }
}

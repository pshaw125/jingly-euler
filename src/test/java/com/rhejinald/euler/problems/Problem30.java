package com.rhejinald.euler.problems;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Surprisingly there are only three numbers that can be written as the sum of fourth powers of their digits:
 * <p>
 * 1634 = 1^4 + 6^4 + 3^4 + 4^4
 * 8208 = 8^4 + 2^4 + 0^4 + 8^4
 * 9474 = 9^4 + 4^4 + 7^4 + 4^4
 * As 1 = 1^4 is not a sum it is not included.
 * <p>
 * The sum of these numbers is 1634 + 8208 + 9474 = 19316.
 * <p>
 * Find the sum of all the numbers that can be written as the sum of fifth powers of their digits.
 *
 *
 * ------------
 * My notes:
 * Ended up brute forcing this one
 * I did spot that the range for 9^n+x^n... doesn't exceed 10^(n+1), so we can cap the range we search by that
 *
 * -> 443839
 * You are the 76298th person to have solved this problem.
 */
public class Problem30 {
    @Test
    public void problem30() throws Exception {
        List<Integer> digitFifthPowers = digitPowers(5);
        Integer runningTotal = 0;
        for (Integer digitFifthPower : digitFifthPowers) {
            runningTotal+=digitFifthPower;
        }
        System.out.println(runningTotal);
    }

    private List<Integer> digitPowers(int power) {
        ArrayList<Integer> successfulMatches = Lists.newArrayList();
        for (int i = 100; i < Math.pow(10, (power + 1)); i++) {
            List<Integer> digits = getDigits(i);
            int total = 0;
            for (Integer digit : digits) {
                total += Math.pow(digit, power);
            }
            if (total == i) {
                successfulMatches.add(i);
            }
        }
        return successfulMatches;
    }

    private List<Integer> getDigits(int number) {
        ArrayList<Integer> digits = Lists.newArrayList();
        while (number > 0) {
            digits.add(number % 10);
            number /= 10;
        }
        Collections.reverse(digits);
        return digits;
    }

    @Test
    public void findDigitPowers() throws Exception {
        assertThat(digitPowers(4)).containsExactly(1634, 8208, 9474);
    }

    @Test
    public void testGetDigits() throws Exception {
        assertThat(getDigits(5432)).containsExactly(5, 4, 3, 2);
        assertThat(getDigits(2468)).containsExactly(2, 4, 6, 8);
        assertThat(getDigits(624)).containsExactly(6, 2, 4);
    }
}

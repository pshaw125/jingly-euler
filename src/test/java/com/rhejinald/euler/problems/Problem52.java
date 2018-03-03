package com.rhejinald.euler.problems;

import com.rhejinald.euler.lib.StringUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * It can be seen that the number, 125874, and its double (2x), 251748, contain exactly the same digits,
 * but in a different order.
 *
 * Find the smallest positive integer, x, such that 2x, 3x, 4x, 5x, and 6x, contain the same digits.
 *
 * =========
 * For something to be 6x and still have the same number of digits, it must be LEQ 1[6]+
 * ========
 * Attempt 1: 142857 - Correct! (time: <100ms) - Solved 26th May 2017, actually submitted on 2nd Jan 2018. Because reasons.
 *
 * Congratulations, the answer you gave to problem 52 is correct.
 *
 * You are the 52307th person to have solved this problem.
 *
 * This problem had a difficulty rating of 5%. The highest difficulty rating you have solved so far is 15%.?
 */
public class Problem52 {
    @Test
    public void testProblem52() throws Exception {
        for (int orderOfMagnitude = 10000; orderOfMagnitude <= 10000000; orderOfMagnitude *= 10) {
            int ceilingForOrderOfMagnitude = (int) Math.floor(1.666666666666f * orderOfMagnitude);
            for (int currentVal = orderOfMagnitude; currentVal <= ceilingForOrderOfMagnitude; currentVal++) {

                if (checkDigitLikenessWithMultipliers(currentVal, 2, 6)) {
                    System.out.println("Match found! " + currentVal);
                    return;
                }
            }
        }
    }

    private boolean checkDigitLikenessWithMultipliers(int value, int lowerMultiplier, int upperMultiplier) {
        String source = StringUtils.sortCharacters(Integer.toString(value));
        for (int multiplier = lowerMultiplier; multiplier <= upperMultiplier; multiplier++) {
            String sortedString = StringUtils.sortCharacters(Integer.toString(value * multiplier));
            if (!sortedString.equals(source))
                return false;
        }
        return true;
    }

    @Test
    public void testDigitLikenessWithMultipliers() throws Exception {
        assertThat(checkDigitLikenessWithMultipliers(125874, 2, 2)).isTrue();
    }
}

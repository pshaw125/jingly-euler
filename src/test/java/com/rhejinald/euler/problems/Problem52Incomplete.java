package com.rhejinald.euler.problems;

import com.rhejinald.euler.lib.StringUtils;
import org.junit.Ignore;
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
 *
 * ========
 * Attempt 1: 142857 - ???
 */
@Ignore("WIP")
public class Problem52Incomplete {

    @Test
    public void testProblem52() throws Exception {
        for (int orderOfMagnitude = 10000; orderOfMagnitude <= 10000000; orderOfMagnitude*=10) {
            System.out.println("Next order of magnitude. Starting from " + orderOfMagnitude);
            int ceilingForOrderOfMagnitude = (int) Math.floor(1.666666666666f * orderOfMagnitude);
            for (int currentVal = orderOfMagnitude; currentVal <= ceilingForOrderOfMagnitude; currentVal++) {

                if(checkDigitLikenessWithMultipliers(currentVal, 2, 6)){
                    System.out.println("Match found! "+currentVal);
                    return;
                }
            }
        }
    }

    private boolean checkDigitLikenessWithMultipliers(int value, int lowerMultiplier, int upperMultiplier) {
        String source = StringUtils.sortString(Integer.toString(value));
        for (int multiplier = lowerMultiplier; multiplier <= upperMultiplier; multiplier++) {
            String sortedString = StringUtils.sortString(Integer.toString(value * multiplier));
            if(!sortedString.equals(source))
                return false;
        }
        return true;
    }

    @Test
    public void testDigitLikenessWithMultipliers() throws Exception {
        assertThat(checkDigitLikenessWithMultipliers(125874, 2,2)).isTrue();
    }
}

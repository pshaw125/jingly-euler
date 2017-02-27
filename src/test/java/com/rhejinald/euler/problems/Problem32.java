package com.rhejinald.euler.problems;

import com.google.common.collect.Sets;
import com.rhejinald.euler.lib.MathExt;
import com.rhejinald.euler.lib.Pandigital;
import org.junit.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * We shall say that an n-digit number is pandigital if it makes use of all the digits 1 to n exactly once;
 * for example, the 5-digit number, 15234, is 1 through 5 pandigital.
 * <p>
 * The product 7254 is unusual, as the identity, 39 × 186 = 7254, containing multiplicand, multiplier, and product
 * is 1 through 9 pandigital.
 * <p>
 * Find the sum of all products whose multiplicand/multiplier/product identity can be written as
 * a 1 through 9 pandigital.
 * <p>
 * HINT: Some products can be obtained in more than one way so be sure to only include it once in your sum.
 * ========================
 * multiplicand: first bit
 * multiplier: second bit
 * product: third bit
 * <p>
 * So, the individual digits from each part of the function include 1-9 exactly once and make a valid equation.
 * <p>
 * Methods to reduce the problem space (brainstorming):
 * a 1 or 2 digit number must be multiplied by at least a 3 digit number (because 97*86=8342 : only 8 digits)
 * <p>
 * Because the multiplier (deemed to always be larger than the multiplicand) is at least 3 digits, the product
 * cannot exceed 5 digits (+3+1=9 digits total). So any time our product exceeds 5 digits, (n/100000>0) then
 * the multiplicand (always higher) is too high and loop must break.
 * <p>
 * Any number multiplied by 1 naturally violates this constraint
 * We can remove identical n-tuples for free (11,22,33,44,111,222,333,444 etc)
 * <p>
 * So we get some cases for free, where neither number cannot violate a set of constraints. As I think of more I'll
 * add them.
 * <p>
 * In the mean time, we have the following structural constraints:
 * multiplicand < multiplier (because results are commutative)
 * 1 < multiplicand < multiplicand cap -1
 * 100 < multiplier   <  4987
 * 100 < product      < 98766
 * <p>
 * 2 is theoretically the smallest multiplicand; therefor the multiplier cannot be bigger than 4999, else we violate
 * the 9 digit constraint (then down to 4987 so we maintain unique digits) - we can simplify the upper bound here.
 * The multiplier cannot exceed 10,000 / multiplicand.
 * <p>
 * This might be enough to run in under 20 seconds (arbitrary time limit I'm setting.
 * <p>
 * -------
 * Attempt 1: 45228 (212ms) - good enough! Done!
 */
public class Problem32 {

    @Test
    public void testProblem32() throws Exception {
        HashSet<String> valueStrings = Sets.newHashSet();
        HashSet<Integer> values = Sets.newHashSet();
        for (int multiplicand = 2; multiplicand < 4986; multiplicand++) {
            if (violatesBaseConstrains(multiplicand)) continue;

            for (int multiplier = multiplicand + 1; multiplier < 4987; multiplier++) {
                if (violatesBaseConstrains(multiplier)) continue;
                int product = multiplicand * multiplier;
                if (!isNoMoreThanThanFiveDigits(product)) continue;
                String allThreeTogether = String.valueOf(multiplicand).concat(String.valueOf(multiplier)).concat(String.valueOf(product));
                if (allThreeTogether.length() != 9) continue;
                if (Pandigital.is1To9Pandigital(multiplicand + "" + multiplier + "" + product)) {
                    valueStrings.add(multiplicand + " * " + multiplier + " = " + product);
                    values.add(product);
                }
            }
        }

        System.out.println(valueStrings.toString());
        System.out.println("sum:" + MathExt.sum(values));

    }

    @Test
    public void testViolatesBaseConstrains() throws Exception {
        assertThat(violatesBaseConstrains(11)).isTrue();
        assertThat(violatesBaseConstrains(111)).isTrue();
        assertThat(violatesBaseConstrains(22)).isTrue();
        assertThat(violatesBaseConstrains(222)).isTrue();
        assertThat(violatesBaseConstrains(33)).isTrue();
        assertThat(violatesBaseConstrains(44)).isTrue();
        assertThat(violatesBaseConstrains(55)).isTrue();
        assertThat(violatesBaseConstrains(66)).isTrue();
        assertThat(violatesBaseConstrains(77)).isTrue();
        assertThat(violatesBaseConstrains(88)).isTrue();
        assertThat(violatesBaseConstrains(888)).isTrue();
        assertThat(violatesBaseConstrains(99)).isTrue();
        assertThat(violatesBaseConstrains(999)).isTrue();
        assertThat(violatesBaseConstrains(132)).isFalse(); //11*12 for %11 constraint
        assertThat(violatesBaseConstrains(968)).isFalse(); //11*88
        assertThat(violatesBaseConstrains(50)).isTrue();
        assertThat(violatesBaseConstrains(100)).isTrue();
        assertThat(violatesBaseConstrains(150)).isTrue();

    }

    /**
     * Constraint list:
     * Cannot mod 11 while i<100
     * Cannot mod 25. Ever.
     * or Mod 10. Ever. No zeros allowed
     */

    private boolean violatesBaseConstrains(int i) {
        return !isNoMoreThanThanFiveDigits(i)
                || i % 10 == 0
                || i % 25 == 0
                || (i < 100 && i % 11 == 0)
                || (i > 100 && i < 1000 && i % 111 == 0)
                ;
    }

    @Test
    public void testGreaterThanFiveDigitConstraint() throws Exception {
        assertThat(isNoMoreThanThanFiveDigits(123456)).isFalse();
        assertThat(isNoMoreThanThanFiveDigits(99999)).isTrue();
        assertThat(isNoMoreThanThanFiveDigits(1234)).isTrue();
        assertThat(isNoMoreThanThanFiveDigits(123)).isTrue();
        assertThat(isNoMoreThanThanFiveDigits(12)).isTrue();
        assertThat(isNoMoreThanThanFiveDigits(100000)).isFalse();
        assertThat(isNoMoreThanThanFiveDigits(999999)).isFalse();
    }

    private boolean isNoMoreThanThanFiveDigits(int i) {
        return i / 100000 == 0;
    }
}

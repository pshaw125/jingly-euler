package com.rhejinald.euler.problems;

import com.rhejinald.euler.lib.NumbersAsStrings;
import org.apache.commons.lang3.math.Fraction;
import org.apache.commons.math3.fraction.BigFraction;
import org.junit.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * It is possible to show that the square root of two can be expressed as an infinite continued fraction.
 * <p>
 * √ 2 = 1 + 1/(2 + 1/(2 + 1/(2 + ... ))) = 1.414213...
 * <p>
 * By expanding this for the first four iterations, we get:
 * <p>
 * 1 + 1/2 = 3/2 = 1.5
 * 1 + 1/(2 + 1/2) = 7/5 = 1.4
 * 1 + 1/(2 + 1/(2 + 1/2)) = 17/12 = 1.41666...
 * 1 + 1/(2 + 1/(2 + 1/(2 + 1/2))) = 41/29 = 1.41379...
 * <p>
 * The next three expansions are 99/70, 239/169, and 577/408, but the eighth expansion, 1393/985, is the first example
 * where the number of digits in the numerator exceeds the number of digits in the denominator.
 * <p>
 * In the first one-thousand expansions, how many fractions contain a numerator with more digits than denominator?
 * <p>
 * =================================
 * Notes
 * <p>
 * So we don't actually need to worry about double type precision if we expand the fractions ourselves.
 * Apache commons math handles the reduce function for finding lowest common denominators.
 * The result of my first pass is actually the same whether or not the reduce function is in there, so I'm not even
 * sure it makes a difference.
 *
 * The fractions represented as numbers actually really quickly overflowed what a long could handle.
 *
 * Anyway:
 * Attempt 1 - 153 - Correct!
 * You are the 33178th person to have solved this problem.
 * This problem had a difficulty rating of 5%.
 */
public class Problem57 {

    @Test
    public void testProblem57() throws Exception {
        BigFraction fraction = new BigFraction(1, 2);
        int count = 0;

        int numberOfExpansions = 1000;
        for (int i = 1; i < numberOfExpansions; i++) {
            fraction = new BigFraction(1).divide(fraction.add(2)).reduce();
            String denominator = fraction.getDenominator().toString();
            String numeratorPlusOneWhole = fraction.getNumerator().add(fraction.getDenominator()).toString();
            if(numeratorPlusOneWhole.length() > denominator.length()){
                count++;
            }
        }
        System.out.println("57>> final count of top-digit-heavy fractions: " + count);
    }

    @Test
    public void testFractionLibWithNonSimplestForm() throws Exception {
        assertThat(Fraction.getFraction("8/4").reduce().toString()).isEqualTo("2/1");
        assertThat(new BigFraction(4, 16).reduce().toString()).isEqualTo("1 / 4");
    }

}

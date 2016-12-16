package com.rhejinald.euler.problems;

import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The fraction 49/98 is a curious fraction, as an inexperienced mathematician in attempting to simplify it may
 * incorrectly believe that 49/98 = 4/8, which is correct, is obtained by cancelling the 9s.
 * <p>
 * We shall consider fractions like, 30/50 = 3/5, to be trivial examples.
 * <p>
 * There are exactly four non-trivial examples of this type of fraction, less than one in value, and containing
 * two digits in the numerator and denominator.
 * <p>
 * If the product of these four fractions is given in its lowest common terms, find the value of the denominator.
 *
 * ========
 * This program generated:
 * 16.0/64.0
 * 19.0/95.0
 * 26.0/65.0
 * 49.0/98.0
 *
 * Simple enough to do the rest by hand
 * 16.0/64.0 -> 1/4
 * 19.0/95.0 -> 1/5
 * 26.0/65.0 -> 2/5
 * 49.0/98.0 -> 1/2
 *
 * (1/4)*(1/5)*(2/5)*(1/2) = 1/100
 *
 * Answer: 100
 * Completed dec 13 2016
 */
public class Problem33 {

    @Test
    public void problem33() throws Exception {
        for (float i = 11; i < 100; i++) {
            if (i % 10 == 0) continue;
            for (float j = i + 1; j < 100; j++) { //j>i enforces less than 1 in value
                if (j % 10 == 0) continue;
                check(i, j);
            }
        }
        new ArrayList<Object>();

    }

    private void check(float numerator, float denominator) {
        for (float i = 1; i <= 9; i++) {
            if (wantToKeepFirstDigit(numerator, i) || wantToKeepSecondDigit(numerator, i)
                    && (wantToKeepFirstDigit(denominator, i) || wantToKeepSecondDigit(denominator,i))) {
                float result = numerator / denominator;
                if (removeCommonDigitsStillHasSameResult(result, numerator, denominator, i)) {
                    System.out.println(numerator + "/" + denominator);
                }
            }
        }

    }

    private boolean removeCommonDigitsStillHasSameResult(float result, float numerator, float denominator, float commonValue) {
        if (wantToKeepFirstDigit(numerator, commonValue) && wantToKeepFirstDigit(denominator, commonValue)) {
            if (keepFirstDigit(numerator, commonValue) / keepFirstDigit(denominator, commonValue) == result)
                return true;
        }


        if (wantToKeepSecondDigit(numerator, commonValue) && wantToKeepFirstDigit(denominator, commonValue)) {
            if (keepSecondDigit(numerator, commonValue) / keepFirstDigit(denominator, commonValue) == result)
                return true;
        }

        if (wantToKeepFirstDigit(numerator, commonValue) && wantToKeepSecondDigit(denominator, commonValue)) {
            if (keepFirstDigit(numerator, commonValue) / keepSecondDigit(denominator, commonValue) == result)
                return true;
        }

        if (wantToKeepSecondDigit(numerator, commonValue) && wantToKeepSecondDigit(denominator, commonValue)) {
            if (keepSecondDigit(numerator, commonValue) / keepSecondDigit(denominator, commonValue) == result)
                return true;
        }

        return false;
    }

    private boolean wantToKeepSecondDigit(float denominator, float commonValue) {
        return denominator - 10 * commonValue < 10 && denominator - (10 * commonValue) > 0;
    }

    private boolean wantToKeepFirstDigit(float numerator, float commonValue) {
        return (numerator - commonValue) % 10 == 0;
    }

    private float keepSecondDigit(float denominator, float commonValue) {
        return denominator - (10 * commonValue);
    }

    private float keepFirstDigit(float numerator, float commonValue) {
        return (numerator - commonValue) / 10;
    }

    @Test
    public void testRemovceCommonDigitsStillHasSameResult() throws Exception {
        assertThat(removeCommonDigitsStillHasSameResult(0.5f, 49, 98, 9)).isTrue();

    }
}

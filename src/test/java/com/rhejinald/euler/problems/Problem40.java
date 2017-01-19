package com.rhejinald.euler.problems;

import org.junit.Test;

/**
 * An irrational decimal fraction is created by concatenating the positive integers:
 * 0.12345678910_1_112131415161718192021...
 * It can be seen that the 12th digit of the fractional part is 1.
 * If d_n represents the n^th digit of the fractional part, find the value of the following expression.
 * d_1 × d_10 × d_100 × d_1000 × d_10000 × d_100000 × d_1000000
 * =====================
 * Notes:
 * So we need to write up to 1M digits
 * 1 digit = 9 chars
 * 2 digits = 90 numbers * 2 digits = 180 chars
 * 3 digits = 900 numbers * 3 digits = 2700 chars
 * 4 digits = 36000
 * 5 digits = 450000
 * 6 digits = 5400000, so up to about 200k should get us 1M chars
 * -> 1088891 characters (incl "0.")
 * generating 185186 numbers leaves us with just enough characters... still takes 90s to generate, which sucks
 * The operation itself is pretty simple after though, so can just brute force it
 * otherwise we could probably math out what the digits are... but leaving it for 90s is easier.
 *
 * Attempt 1: 210 - Correct!
 */
public class Problem40 {
    public static final int NUMBERS_TO_GENERATE = 185186;

    @Test
    public void problem40() throws Exception {
        String sample = getSample();
        long l1 = getLongFromChar(sample.charAt(0));
        long l2 = getLongFromChar(sample.charAt(9));
        long l3 = getLongFromChar(sample.charAt(99));
        long l4 = getLongFromChar(sample.charAt(999));
        long l5 = getLongFromChar(sample.charAt(9999));
        long l6 = getLongFromChar(sample.charAt(99999));
        long l7 = getLongFromChar(sample.charAt(999999));
        long out = l1 * l2 * l3 * l4 * l5 * l6 * l7;
        System.out.println(out);
    }

    private Long getLongFromChar(char s) {
        return Long.valueOf(String.valueOf(s));
    }

    private String getSample() {
        String val = "";
        for (int i = 1; i < NUMBERS_TO_GENERATE; i++) {
            val += String.valueOf(i);
        }
        return val;
    }
}

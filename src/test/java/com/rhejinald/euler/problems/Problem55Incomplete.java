package com.rhejinald.euler.problems;


import com.rhejinald.euler.lib.Palindromes;
import com.rhejinald.euler.lib.StringUtils;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * If we take 47, reverse and add, 47 + 74 = 121, which is palindromic.
 *
 * Not all numbers produce palindromes so quickly. For example,
 *
 * 349 + 943 = 1292,
 * 1292 + 2921 = 4213
 * 4213 + 3124 = 7337
 *
 * That is, 349 took three iterations to arrive at a palindrome.
 *
 * Although no one has proved it yet, it is thought that some numbers, like 196, never produce a palindrome.
 * A number that never forms a palindrome through the reverse and add process is called a Lychrel number.
 * Due to the theoretical nature of these numbers, and for the purpose of this problem, we shall assume that a number
 * is Lychrel until proven otherwise. In addition you are given that for every number below ten-thousand, it will
 * either (i) become a palindrome in less than fifty iterations, or, (ii) no one, with all the computing power that
 * exists, has managed so far to map it to a palindrome. In fact, 10677 is the first number to be shown to require
 * over fifty iterations before producing a palindrome: 4668731596684224866951378664 (53 iterations, 28-digits).
 *
 * Surprisingly, there are palindromic numbers that are themselves Lychrel numbers; the first example is 4994.
 *
 * How many Lychrel numbers are there below ten-thousand?
 *
 * ============================================
 * Rules:
 * > Checking positive integers up to 9999
 * > Checking only up to fifty iterations
 * > Must run at least one iteration
 *
 * Concerns:
 * > Does 9999 test fit in a Long? 9999*(2^50) > 2^63-1... can we use an unsigned long?
 * Or should we just use a BigDecimal and be done with it? (makes adding easier and no overflow concerns).
 *
 * Notably, each number's reverse is also done by this. 1234 also knocks out 4321 (since each adds the other as step 1)
 * Further, at least for the smaller numbers, they tree - 4 -> 8 -> 16 -> 77 contains at least 4 different entry points.
 *
 * So we have 2 options:
 * 1) Brute force it. It's <500k calculations
 * 2) We be smart about rechecking values, such as trees and duplicate values.
 *
 * We COULD brute force it, but I like the idea of trying to do this sensibly, so we're going to map together which
 * numbers "lead to" a Lychrel number.
 */
public class Problem55Incomplete {



    @Test
    public void testProblem55() throws Exception {

    }

    @Test
    public void testKnownCase() throws Exception {
        assertThat(isLychrel(349)).isTrue();

    }

    private boolean isLychrel(int subject) {
        int iteration = 1;


        if(iteration > 50){
            return false;
        }
        BigDecimal source = BigDecimal.valueOf(subject);
        BigDecimal reverse = new BigDecimal(StringUtils.reverse(source.toString()).toCharArray());
        BigDecimal sum = source.add(reverse);
        if(Palindromes.test())
    }
}

package com.rhejinald.euler.problems;

import com.rhejinald.euler.lib.Permutations;
import com.rhejinald.euler.lib.Primes;
import org.junit.Test;

import java.util.Set;

/**
 * We shall say that an n-digit number is pandigital if it makes use of all the digits 1 to n exactly once.
 * For example, 2143 is a 4-digit pandigital and is also prime.
 * What is the largest n-digit pandigital prime that exists?
 * ==============
 * any 9-digit pandigital prime will be bigger than any 8 digit, so check those first
 *
 * Attempt 1: 7652413 - Correct!
 * My Seive implementtaion doesn't perform well up to 1B, but up to root(max_pandigital) is fine the check primality
 * from there seems to work well enough; 500-855ms run time.
 *
 */
public class Problem41 {

    @Test
    public void testProblem41() throws Exception {
        long highestFoundPandigitalPrime = 0;
        Primes primes = new Primes();
        primes.getPrimes(31427); //upped to a 8 GB heap. Because. -Xmx4096M
        //31,427^2 = 987,656,329
        for (int i = 9; i > 3; i--) {
            String digits = "";
            for (int j = 1; j <= i; j++) {
                digits += String.valueOf(j);
            }
            Set<String> permutation = Permutations.of(digits);
            for (String s : permutation) {
                Long subject = Long.valueOf(s);
                if(primes.isAlreadyKnown(subject)){
                    highestFoundPandigitalPrime = Math.max(subject, highestFoundPandigitalPrime);
                }
            }
            if(highestFoundPandigitalPrime > 0){
                break;
            }
        }

        System.out.println(highestFoundPandigitalPrime + " is the largest pandigital prime we found");
    }

}

package com.rhejinald.euler.problems;

import com.google.common.collect.Sets;
import com.rhejinald.euler.lib.MathExt;
import com.rhejinald.euler.lib.Primes;
import org.junit.Test;

import java.util.HashSet;
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
 * from there seems to work well enough 855ms.
 *
 */
public class Problem41 {

    @Test
    public void problem41Incomplete() throws Exception {
        long highestFoundPandigitalPrime = 0;
        Primes primes = new Primes();
        primes.getPrimes(31427); //upped to a 8 GB heap. Because. -Xmx4096M
        //31,427^2 = 987,656,329
        for (int i = 9; i > 3; i--) {
            String digits = "";
            for (int j = 1; j <= i; j++) {
                digits += String.valueOf(j);
            }
            Set<String> permutation = permutation(digits);
            for (String s : permutation) {
                Long subject = Long.valueOf(s);
                if(primes.isPrimeFromAlreadyFound(subject)){
                    highestFoundPandigitalPrime = Math.max(subject, highestFoundPandigitalPrime);
                }
            }
            if(highestFoundPandigitalPrime > 0){
                break;
            }
        }

        System.out.println(highestFoundPandigitalPrime + " is the largest pandigital prime we found");
    }

    private Set<String> permutation(String str) {
        HashSet<String> res = Sets.newHashSetWithExpectedSize((int) MathExt.factorial(str.length()));
        permutationHelper("", str, res);
        return res;
    }

    private void permutationHelper(String prefix, String str, Set<String> res) {
        int n = str.length();
        if (n == 0) res.add(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutationHelper(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), res);
        }
    }
}

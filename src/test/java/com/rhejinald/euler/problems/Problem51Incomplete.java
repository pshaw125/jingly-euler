package com.rhejinald.euler.problems;

import com.rhejinald.euler.lib.Primes;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * By replacing the 1st digit of the 2-digit number *3, it turns out that six of the nine possible values:
 * 13, 23, 43, 53, 73, and 83, are all prime.
 * <p>
 * By replacing the 3rd and 4th digits of 56**3 with the same digit, this 5-digit number is the first example having
 * seven primes among the ten generated numbers, yielding the family:
 * 56003, 56113, 56333, 56443, 56663, 56773, and 56993.
 * Consequently 56003, being the first member of this family, is the smallest prime with this property.
 * <p>
 * Find the smallest prime which, by replacing part of the number (not necessarily adjacent digits) with the same digit,
 * is part of an eight prime value family.
 * <p>
 * =============================
 * For some number, x, some number of values are wildcard.
 * Further, because we're looking for an 8-prime of the 10 possible values, we need to validate that only 3 of them are
 * not present before we can remove that possibility.
 * <p>
 * Chances are, but not for sure, X is larger than 56003
 * <p>
 * Method: Choosing multiple asterisks is a triangle number problem (in complexity, not specifically).
 * I don't see a way more effective than just iterating through the different wild options.
 * <p>
 * We can safely assume that the last digit will never be an asterisk, as it MUST be one of [0,2,4,6,8], which will
 * inherently make it not prime. So for each combination, we need to check only 1,3,7,9 as the last digit.
 * Further, 0 cannot be the first digit. Hopefully that's obvious.
 * <p>
 * The other way we could approach this is with a big-ass hashmap; our primes only go up 2 billion anyway, so what we
 * could do is iterate through the primes; for each digit length we pull a new map; into which we put each permuation
 * of wildcards. It's possible that the first digit is an asterisk, it's just harder (owing to 0 not being an option,
 * there's only 1 possible "miss" instead of 2). This sounds better, let's try this.
 *
 * Substituting these wildcards, I can only choose n digits that are the same to sub, otherwise the result might not
 * be prime. So we'll check all the single digits; then all the digits which are 2-tuples, then all the 3-tuples etc,
 * up to the length of the number -1. The last digit will never be an asterisk, optimizing out families we know will
 * not be 8 in size.
 *
 * Can some digits of a homogeneous n-tuple NOT be substituted? Yes.
 */
@Ignore("Incomplete")
public class Problem51Incomplete {

    public static final int ONE_BILLION = 1000000000;

    @Test
    public void testProblem51() throws Exception {
        Primes primes = new Primes();
        HashMap<String, Integer> wildcardPermutation = new HashMap<>(50000, 0.8f);

        for (int upperBoundPowerOfTen = 1000; upperBoundPowerOfTen <= ONE_BILLION; upperBoundPowerOfTen *= 10) {
            Set<Integer> primeSet = primes.getPrimes(upperBoundPowerOfTen);
            for (Integer prime : primeSet) {
                getPermutationsOfWildcardedNumber(prime);
            }

            wildcardPermutation.clear();
        }
    }

    /**
     * @param prime Number to permutate
     * @return set of all possible wildcarded numbers, where the final digit cannot be set as a wildcard.
     * 1110 would return *110, 1*10, 11*0, **10, *1*0, 1**0, ***0
     * Returns (2^n)-1 different options
     */
    private static Set<String> getPermutationsOfWildcardedNumber(Integer prime) {
        return null;

    }

    @Test
    public void testGetWildcardPermutations() throws Exception {
        assertThat(getPermutationsOfWildcardedNumber(1110)).containsOnly("*110", "1*10", "11*0", "**10","*1*0","1**0","***0");
        assertThat(getPermutationsOfWildcardedNumber(25)).containsOnly("*5");
        assertThat(getPermutationsOfWildcardedNumber(155)).containsOnly("*15", "1*5", "**5");
    }
}

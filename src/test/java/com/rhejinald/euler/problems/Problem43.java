package com.rhejinald.euler.problems;

import com.google.common.collect.Lists;
import com.rhejinald.euler.lib.Permutations;
import com.rhejinald.euler.lib.Primes;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The number, 1406357289, is a 0 to 9 pandigital number because it is made up of each of the digits 0 to 9 in some
 * order, but it also has a rather interesting sub-string divisibility property.
 * Let d1 be the 1st digit, d2 be the 2nd digit, and so on. In this way, we note the following:
 *
 * d2d3d4=406 is divisible by 2
 * d3d4d5=063 is divisible by 3
 * d4d5d6=635 is divisible by 5
 * d5d6d7=357 is divisible by 7
 * d6d7d8=572 is divisible by 11
 * d7d8d9=728 is divisible by 13
 * d8d9d10=289 is divisible by 17
 *
 * Find the sum of all 0 to 9 pandigital numbers with this property.
 * ===================
 * My notes:
 * Brute force: get all permutations of 0-9 pandigital, check each through 2,3,5...
 * Attempt 1: 16695334890 - correct! on Jan 30th 2017
 *   in 5 seconds, most of which was spent building the permutations (4s)
 */
public class Problem43 {
    @Test
    public void problem43() throws Exception {

        List<Long> primesUpToSeventeen = getPrimesUpToSeventeen();
        Set<String> zeroToNinePandigitals = Permutations.of("0123456789");
        long sum = 0;
        for (String value : zeroToNinePandigitals) /* label?? */{
            if(testPandigital(primesUpToSeventeen, value)){
                sum+=Long.valueOf(value);
            }
        }
        System.out.println(sum);

    }

    private boolean testPandigital(List<Long> primesUpToSeventeen, String value) throws InterruptedException {
        for (int i = 1; i <= 7; i++) {
            if(new Integer(value.substring(i, i + 3)) % primesUpToSeventeen.get(i - 1) > 0){
                return false;
            }
        }
        System.out.println(value + " was found to be valid!");
        return true;
    }

    @Test
    public void testPrimesSortCorrectly() throws Exception {
        List<Long> primesUpToSeventeen = getPrimesUpToSeventeen();
        assertThat(primesUpToSeventeen).containsExactly(2L,3L,5L, 7L, 11L, 13L, 17L);
        assertThat(primesUpToSeventeen.get(3)).isEqualTo(7L);

    }

    private List<Long> getPrimesUpToSeventeen() {
        List<Long> primesUpToSeventeen = Lists.<Long>newArrayList(new Primes().getPrimes(17));
        Collections.sort(primesUpToSeventeen);
        return primesUpToSeventeen;
    }
}

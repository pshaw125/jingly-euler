package com.rhejinald.euler.problems;

import com.google.common.collect.Sets;
import com.rhejinald.euler.lib.Primes;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The number, 197, is called a circular prime because all rotations of the digits:
 * 197, 971, and 719, are themselves prime.
 * There are thirteen such primes below 100: 2, 3, 5, 7, 11, 13, 17, 31, 37, 71, 73, 79, and 97.
 * How many circular primes are there below one million?
 * <p>
 * --------
 * Pulled an example so I don't interpret it wrong:
 * 1193 is a circular prime, since 1931, 9311 and 3119 all are also prime
 * <p>
 * So it IS circular rotation, not every permutation
 *
 * Attempt 1: 55 - Correct! Dec 21 2016
 *
 * So the main work here was actually in the previous 2-3 commits, where I had to reoptimize my prime
 * sieve, such that it had decent performance characteristics for larger values. My old implementation
 * took ~9570ms to generate primes up to 100k; didn't even try for up to 1M. Updating it to use streams
 * and removeAll() rather than removing one at a time by checking multiples, 100k too ~300ms, 1M took
 * ~700ms. Much better :)
 */
public class Problem35 {


    @Test
    public void problem35() throws Exception {
        Primes primes = new Primes();
        Set<Long> primesBelowOneMillion = primes.getPrimes(1000000);
        Set<Long> primesFoundToBeCircular = Sets.newHashSetWithExpectedSize(200);

        for (Long prime : primesBelowOneMillion) {
            Set<Long> rotations = getRotations(prime);
            if(primesBelowOneMillion.containsAll(rotations)){
                primesFoundToBeCircular.addAll(rotations);
            }
        }

        System.out.println(primesFoundToBeCircular.size());

    }

    @Test
    public void testNumberRotate() throws Exception {
        assertThat(getRotations(12345L)).containsOnly(12345L, 51234L, 45123L, 34512L, 23451L);
        assertThat(getRotations(123L)).containsOnly(123L, 312L, 231L);
        assertThat(getRotations(19L)).containsOnly(19L, 91L);
        assertThat(getRotations(555L)).containsOnly(555L);
        assertThat(getRotations(1193L)).containsOnly(1193L, 1931L, 9311L, 3119L);
    }

    private Set<Long> getRotations(Long i) {
        String s = String.valueOf(i);
        Set<Long> permutations = Sets.newHashSet();
        for (int j = 0; j < s.length(); j++) {
            permutations.add(Long.parseLong(s.substring(j) + s.substring(0, j)));
        }
        return permutations;
    }
}

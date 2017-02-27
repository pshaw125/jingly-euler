package com.rhejinald.euler.problems;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.rhejinald.euler.lib.Permutations;
import com.rhejinald.euler.lib.Primes;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Prime Permutations
 *
 * The arithmetic sequence, 1487, 4817, 8147, in which each of the terms increases by 3330, is unusual in two ways:
 * (i) each of the three terms are prime, and,
 * (ii) each of the 4-digit numbers are permutations of one another.
 *
 * There are no arithmetic sequences made up of three 1-, 2-, or 3-digit primes, exhibiting this property, but there
 * is one other 4-digit increasing sequence.
 *
 * What 12-digit number do you form by concatenating the three terms in this sequence?
 * ===============================================================
 * Notes:
 * So all 4 digit primes and eliminate from there.
 *
 * Attempt 1 - 296962999629 (from 2969 6299 9629) - Correct ( 42063rd person to have solved this problem., 274ms)
 *
 */
public class Problem49 {
    @Test
    public void testProblem49() throws Exception {
        Primes primeSource = new Primes();
        Set<Integer> primeSet = primeSource.getPrimes(10000);
        primeSet.removeAll(primeSource.getPrimes(1000));

        for (Integer nextPrime : primeSet) {
            Set<Integer> permutations = Permutations.of(nextPrime.toString()).stream().map(Integer::valueOf).collect(Collectors.toSet());
            Sets.SetView<Integer> primeIterations = Sets.intersection(primeSet, permutations);
            if (primeIterations.size() >= 3) {
                HashSet<Integer> differences = Sets.newHashSet();
                ArrayList<Integer> primesToCheck = Lists.newArrayList(primeIterations);
                Collections.sort(primesToCheck);
                for (int i = 0; i < primesToCheck.size() - 2; i++) {
                    int diff = primesToCheck.get(i + 1) - primesToCheck.get(i);
                    int nextValue = primesToCheck.get(i + 1) + diff;
                    if (primesToCheck.contains(nextValue)) {
                        System.out.println(primesToCheck.get(i) + " " + primesToCheck.get(i + 1) + " " + nextValue);
                        break;
                    }
                    differences.add(diff);

                }
            }
        }


    }
}

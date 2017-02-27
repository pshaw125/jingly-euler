package com.rhejinald.euler.problems;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.rhejinald.euler.lib.FunctionalBinarySearcher;
import com.rhejinald.euler.lib.Primes;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The prime 41, can be written as the sum of six consecutive primes:
 * 41 = 2 + 3 + 5 + 7 + 11 + 13
 * This is the longest sum of consecutive primes that adds to a prime below one-hundred.
 * <p>
 * The longest sum of consecutive primes below one-thousand that adds to a prime, contains 21 terms, and is equal to 953.
 * <p>
 * Which prime, below one-million, can be written as the sum of the most consecutive primes?
 * <p>
 * ========================================
 * Notes:
 * So it's not necessarily the largest one that can be written as a sum of primes, but the result which is written
 * using the MOST
 * We know the term size is at least 21
 * <p>
 * I think we binary searching over the set may be the most sensible way to go
 * Our upper bound will be the number of primes, starting at 2, it takes to exceed 1M sum. >> 497
 * So our seed value = (497-21)/2 = 238
 * <p>
 * Where our sequence size is x, we start at 2, run for that many digits. Subtract one off the bottom and add one
 * on the top.
 * <p>
 * "Which prime can be written as the sum of the most consq primes?" indicates that there is probably only one with
 * sequence x, where x is maximized for values below 1M.
 * <p>
 * Now we can do some micro optimizations - Once '2' is not involved, an even number of primes summed will always be
 * even; so for x=even, we only need to check seq starting with 2.
 */
public class Problem50 {
    private long ONE_MILLION = 1000000;
    private List<Integer> primes;

    @BeforeClass
    public void setUp() throws Exception {
        primes = getSortedPrimesUpToOneMillion(); //immutable GTFO
    }

    @Test
    public void testProblem50() throws Exception {
        int lowerBound = 21;
        int upperBound = 497;
        System.out.println(FunctionalBinarySearcher.search(lowerBound, upperBound, this::hasPrimesOfSequenceSize));
    }

    private Boolean hasPrimesOfSequenceSize(Integer sequenceSize) {
        int sum = 0;
        for (int i = 0; i < sequenceSize; i++) {
            sum+=primes.get(i);
        }
        if(primes.contains(sum)){}
        int head = 0;
        int tail = sequenceSize;
        while(sum<ONE_MILLION){

            head++;
            tail++;
        }
        return null; //TODO fix me
    }

    @Test
    @Ignore
    public void testResearch() throws Exception {
        primes = getSortedPrimesUpToOneMillion();
        long total = 0;
        int i = 0;
        while (total < ONE_MILLION) {
            total += primes.get(i);
            i++;
        }
        System.out.println(i);
    }

    private List<Integer> getSortedPrimesUpToOneMillion() {
        ArrayList<Integer> primes = Lists.newArrayList(new Primes().getPrimes(ONE_MILLION));
        Collections.sort(primes);
        return ImmutableList.copyOf(primes);
    }
}

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
import java.util.Iterator;
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
 * So it's not necessarily the largest prime that can be written as a sum of primes, but the result which is written
 * using the MOST terms
 * We know the term size is at least 21
 * <p>
 * I think we binary searching over the set may be the most sensible way to go
 * Our upper bound will be the number of primes, starting at 2, it takes to exceed 1M sum. >> 497
 * So our seed value = (497-21)/2 + 21 = 259
 * <p>
 * Where our sequence size is x, we start at 2, run for that many digits. Subtract one off the bottom and add one
 * on the top.
 * <p>
 * "Which prime can be written as the sum of the most consq primes?" indicates that there is probably only one with
 * sequence x, where x is maximized for values below 1M.
 *
 * -----
 * So what ever math I did above is bollocks. I ran to the max of 496 and still found primes well below 1M
 * So I upped the upper bound to 1k and it searched in on 819, with a prime of 998789 using 11 iterations of the binary
 * searcher. Very happy with that.
 *
 * Attempt 1: 998789 - Wrong! :(
 * Attempt 2: 996341 - Wrong; so my primes implementation had 0 and 1 included too, which seems to have messed with it.
 * Also all the tests failed. I fixed those too.
 * Attempt 3: 448867 - Wrong! Primes class is still broken and wrong.
 * Attempt 4: 796259 - I dunno it seems to just be spouting out random numbers at this point
 *
 * So it would seem my assertion that EVERY value up to X would have a sequence.. but this seems not to be the case.
 * Looks like my fancy binary search won't get me the whole way. I know that the sequence holds for at least x = 485
 * A rerun of my research suggests we have a max sequence size of 547 (now that we're not including everything except
 * multiples of 2 and 3...)
 *
 * Attempt 5: 997651 - Correct!
 * You are the 45642nd person to have solved this problem.
 * Nice work, Rhejinald, you've just advanced to Level 2 .
 * 40557 members (5.89%) have made it this far.
 *
 * So just running iteratively up to 457, 543 turned up. What a mess.
 */
public class Problem50 {
    private int ONE_MILLION = 1000000;
    private List<Integer> sortedPrimes;

    @Before
    public void setUp() throws Exception {
        sortedPrimes = getSortedPrimesUpToOneMillion(); //immutable GTFO
    }

    @Test
    public void testProblem50() throws Exception {
        int lowerBound = 485;
        int upperBound = 548;
        for (int i = 485; i < 548; i++) {
            hasPrimesOfSequenceSize(i);
        }
    }

    private Boolean hasPrimesOfSequenceSize(Integer sequenceSize) {
        Iterator<Integer> low = sortedPrimes.iterator();
        Iterator<Integer> high = sortedPrimes.iterator();
        int runningTotal = getInitialPrimeSequenceSum(sequenceSize, high);
        while(high.hasNext() && runningTotal >= 0 && runningTotal < ONE_MILLION){
            if(sortedPrimes.contains(runningTotal)){
                System.out.println("found " + sequenceSize + " to have a prime: " + runningTotal) ;
                return true;
            }
            Integer add = high.next();
            Integer subtract = low.next();
//            System.out.println(runningTotal + " + " + add + " - " + subtract);
            runningTotal = runningTotal + add - subtract;
        }

        System.out.println("No prime found for " + sequenceSize);
        return false;

    }

    private int getInitialPrimeSequenceSum(Integer sequenceSize, Iterator<Integer> sequence) {
        int runningTotal = 0;
        for (int i = 0; i < sequenceSize; i++) runningTotal += sequence.next();
        return runningTotal;
    }

    @Test
    public void testResearch() throws Exception {
        sortedPrimes = getSortedPrimesUpToOneMillion();
        long total = 0;
        int i = 0;
        while (total < ONE_MILLION) {
            total += sortedPrimes.get(i);
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

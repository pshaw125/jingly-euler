package com.rhejinald.euler.problems;

import org.junit.Test;

import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

public class Problem14 {
    /**
     * The following iterative sequence is defined for the set of positive integers:
     * <p>
     * n → n/2 (n is even)
     * n → 3n + 1 (n is odd)
     * <p>
     * Using the rule above and starting with 13, we generate the following sequence:
     * <p>
     * 13 → 40 → 20 → 10 → 5 → 16 → 8 → 4 → 2 → 1
     * It can be seen that this sequence (starting at 13 and finishing at 1) contains 10 terms.
     * Although it has not been proved yet (Collatz Problem), it is thought that all starting numbers finish at 1.
     * <p>
     * Which starting number, under one million, produces the longest chain?
     * <p>
     * NOTE: Once the chain starts the terms are allowed to go above one million.
     *
     * Impl notes:
     * I just brute forced this one. You can do some fancy tree building (i.e. if you encounter 7 in a chain,
     * 7 is n steps from "home" (1). If some other chain ends you up on a number (2^n * 7^m) then that will chain
     * to seven, then it's those extra n steps to home.
     */

    @Test
    public void testProblem14() {
        int bestSequenceLength = 0;
        int bestStartingValue = 0;
        for (int i = 1; i < 1000000; i++) {
            final LinkedList<Long> currentRunNumbers = runSequence(i);
            final int sizeOfCurrentRun = currentRunNumbers.size();
            if (sizeOfCurrentRun > bestSequenceLength) {
                bestSequenceLength = sizeOfCurrentRun;
                bestStartingValue = i;
                System.out.println("new best: " + bestStartingValue + ": " + sizeOfCurrentRun);
            }
        }
        System.out.println("");
        System.out.println("bestSequenceLength " + bestSequenceLength);
        System.out.println("bestStartingValue " + bestStartingValue);
    }

    @Test
    public void testSequence() throws Exception {
        assertThat(runSequence(1)).hasSize(1);
        assertThat(runSequence(10)).hasSize(7);
        assertThat(runSequence(13)).hasSize(10);

    }

    private LinkedList<Long> runSequence(final long startingValue) {
        long currentValue = startingValue;
        final LinkedList<Long> sequence = new LinkedList<Long>();
        sequence.add(startingValue);
        while (currentValue > 1) {
            final long next = isEven(currentValue) ? evenRule(currentValue) : oddRule(currentValue);

            currentValue=next;
            sequence.add(next);
        }
        return sequence;
    }

    private boolean isEven(long currentValue) {
        return currentValue % 2 == 0;
    }

    @Test
    public void testEvenRule() throws Exception {
        assertThat(evenRule(2)).isEqualTo(1);
        assertThat(evenRule(4)).isEqualTo(2);
        assertThat(evenRule(10)).isEqualTo(5);
        assertThat(evenRule(400)).isEqualTo(200);
    }

    @Test
    public void testOddRule() throws Exception {
        assertThat(oddRule(5)).isEqualTo(16);
        assertThat(oddRule(17)).isEqualTo(52);
        assertThat(oddRule(91)).isEqualTo(274);
    }

    private long oddRule(long n) {
        return (n * 3) + 1;
    }

    private long evenRule(long n) {
        return n / 2;
    }
}

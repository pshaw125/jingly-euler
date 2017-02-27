package com.rhejinald.euler.lib;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberRange {
    public static HashSet<Long> numberRangeHashSet(long lowerBoundInclusive, long upperBoundInclusive) {
        if (upperBoundInclusive - lowerBoundInclusive > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Cannot generate a number range with more than Integer.MAX_VALUE values");
        }
        if (upperBoundInclusive < lowerBoundInclusive) {
            return Sets.newHashSet();
        }

        HashSet<Long> longRange = Sets.newHashSetWithExpectedSize((int) (upperBoundInclusive - lowerBoundInclusive));
        getRangeGeneric(upperBoundInclusive, lowerBoundInclusive, longRange, Lists.newArrayList());
        return longRange;
    }

    public static ArrayList<Long> numberRangeArrayList(long lowerBoundInclusive, long upperBoundInclusive, ArrayList<Integer> divisorsToSkip) {
        if (upperBoundInclusive - lowerBoundInclusive > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Cannot generate a number range with more than Integer.MAX_VALUE values");
        }
        if (upperBoundInclusive < lowerBoundInclusive) {
            return Lists.newArrayList();
        }

        ArrayList<Long> longRange = Lists.newArrayListWithExpectedSize((int) (upperBoundInclusive - lowerBoundInclusive));
        getRangeGeneric(upperBoundInclusive, lowerBoundInclusive, longRange, divisorsToSkip);
        return longRange;
    }

    private static void getRangeGeneric(long upperBoundInclusive, long lowerBoundInclusive, Collection<Long> longRange, ArrayList<Integer> divisorsToSkip) {
        if (upperBoundInclusive - lowerBoundInclusive > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Range too large! " + lowerBoundInclusive + " -> " + upperBoundInclusive);
        }
        if (upperBoundInclusive > 100000) {
            ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(1000);
            int workerCount = 16;
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(workerCount, workerCount, 500, TimeUnit.MILLISECONDS, workQueue);
            List<Collection<Long>> partialLists = Collections.synchronizedList(Lists.newArrayListWithCapacity(workerCount));

            AtomicInteger completionCount = new AtomicInteger(0);
            for (int i = 0; i < workerCount; i++) {
                int workerNumber = i;
                threadPoolExecutor.execute(() -> {
//                    System.out.println("starting worker " + workerNumber);
                    long numberOfElementsForThisWorker = Math.round(((double) (upperBoundInclusive - lowerBoundInclusive + 1) / workerCount));
                    long threadLowerBoundIncl = lowerBoundInclusive + numberOfElementsForThisWorker * workerNumber;
                    long threadUpperBoundIncl = (workerNumber == workerCount - 1)
                            ? upperBoundInclusive
                            : Math.min(threadLowerBoundIncl + numberOfElementsForThisWorker - 1, upperBoundInclusive);

                    ArrayList<Long> values = Lists.newArrayListWithExpectedSize((int) numberOfElementsForThisWorker);
                    for (long j = threadLowerBoundIncl; j <= threadUpperBoundIncl; j++) {
                        if (!shouldSkip(divisorsToSkip, j)) {
                            values.add(j);
                        }
                    }
                    partialLists.add(values);
                    completionCount.incrementAndGet();
                });
            }
            while (completionCount.get() < (workerCount)) {
                try {
                    Thread.sleep(2000);
//                    System.out.println("waiting for workers; " + completionCount.get() + " of " + workerCount + " complete.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (Collection<Long> partialList : partialLists) {
                longRange.addAll(partialList);
            }

        } else {
            for (long i = lowerBoundInclusive; i <= upperBoundInclusive; i++) {
                longRange.add(i);
            }
        }

    }

    private static boolean shouldSkip(ArrayList<Integer> divisorsToSkip, long j) {
        for (Integer divisor : divisorsToSkip) {
            if (j % divisor == 0) return false;

        }
        return true;
    }

    @Test
    public void getRangeGenericMultithreaded() throws Exception {
        ArrayList<Long> longRange = new ArrayList<>();
        int size = 10000000;
        getRangeGeneric(size, 1, longRange, Lists.newArrayList());
        assertThat(longRange).hasSize(size);
    }

    @Test
    public void testNumberRangeSet() throws Exception {
        assertThat(numberRangeHashSet(1L, 4L)).containsOnly(1L, 2L, 3L, 4L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberRangeBounds() throws Exception {
        assertThat(numberRangeHashSet(((long) Integer.MAX_VALUE) + 1, 0L));
    }
}

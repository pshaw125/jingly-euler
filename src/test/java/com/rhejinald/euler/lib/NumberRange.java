package com.rhejinald.euler.lib;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

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
        getRangeGeneric(upperBoundInclusive, lowerBoundInclusive, longRange);
        return longRange;
    }

    public static ArrayList<Long> numberRangeArrayList(long lowerBoundInclusive, long upperBoundInclusive) {
        if (upperBoundInclusive - lowerBoundInclusive > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Cannot generate a number range with more than Integer.MAX_VALUE values");
        }
        if (upperBoundInclusive < lowerBoundInclusive) {
            return Lists.newArrayList();
        }

        ArrayList<Long> longRange = Lists.newArrayListWithExpectedSize((int) (upperBoundInclusive - lowerBoundInclusive));
        getRangeGeneric(upperBoundInclusive, lowerBoundInclusive, longRange);
        return longRange;
    }

    private static void getRangeGeneric(long upperBoundInclusive, long lowerBoundInclusive, Collection<Long> longRange) {
        for (long i = lowerBoundInclusive; i <= upperBoundInclusive; i++) {
            longRange.add(i);
        }
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

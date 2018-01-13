package com.rhejinald.euler.lib;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberRange {

    // The main method, but it's not used right now. I like the syntax to be available so I'm keeping this
    // where I can remember it easily.
    private static Collection<Long> getRange(long upperBoundInclusive, long lowerBoundInclusive, final List<Integer> divisorsToSkip) {
        if(lowerBoundInclusive > upperBoundInclusive) throw new IllegalArgumentException();
        return LongStream.rangeClosed(lowerBoundInclusive, upperBoundInclusive).boxed().filter(e -> {
            for (Integer div : divisorsToSkip) {
                if (e % div == 0) return false;
            }
            return true;
        }).collect(Collectors.toList());
    }

    @Test
    public void getRangeGeneric() throws Exception {
        int size = 10000000;
        Collection<Long> longRange = getRange(size, 1, Lists.newArrayList());
        assertThat(longRange).hasSize(size);
    }

    @Test
    public void testNumberRangeSet() throws Exception {
        assertThat(getRange(4L, 1L, Collections.emptyList())).containsOnly(1L, 2L, 3L, 4L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberRangeBounds() throws Exception {
        assertThat(getRange(0L, (long) Integer.MAX_VALUE + 1, Collections.emptyList()));
    }
}

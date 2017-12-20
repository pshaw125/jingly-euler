package com.rhejinald.euler.lib;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberRange {

    private static Collection<Long> getRangeGenericV2(long upperBoundInclusive, long lowerBoundInclusive, final List<Integer> divisorsToSkip) {
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
        Collection<Long> longRange = getRangeGenericV2(size, 1, Lists.newArrayList());
        assertThat(longRange).hasSize(size);
    }

    @Test
    public void testNumberRangeSet() throws Exception {
        assertThat(getRangeGenericV2(4L, 1L, Collections.emptyList())).containsOnly(1L, 2L, 3L, 4L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberRangeBounds() throws Exception {
        assertThat(getRangeGenericV2(0L, (long) Integer.MAX_VALUE + 1, Collections.emptyList()));
    }
}

package com.rhejinald.euler.lib;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberRangeTest {
    @Test
    public void getRangeGeneric() throws Exception {
        int size = 10000000;
        Collection<Long> longRange = NumberRange.getRange((long) size, (long) 1, Lists.newArrayList());
        assertThat(longRange).hasSize(size);
    }

    @Test
    public void testNumberRangeSet() throws Exception {
        assertThat(NumberRange.getRange(4L, 1L, Collections.emptyList())).containsOnly(1L, 2L, 3L, 4L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberRangeBounds() throws Exception {
        assertThat(NumberRange.getRange(0L, (long) Integer.MAX_VALUE + 1, Collections.emptyList()));
    }
}

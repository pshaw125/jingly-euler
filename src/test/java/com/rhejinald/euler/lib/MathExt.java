package com.rhejinald.euler.lib;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MathExt {
    public static Long sum(Collection<? extends Number> integers) {
        return integers.stream().mapToLong(Number::longValue).sum();
    }

    public static Long sum(Number... integers) {
        return sum(Arrays.asList(integers));
    }

    public static long factorial(long i) {
        long out = 1;
        for (int j = 2; j <= i; j++) {
            out *= j;
        }
        return out;
    }

    public static int median(List<Integer> c) {
        return c.get((c.size() - 1) / 2);
    }

    @Test
    public void testSum() throws Exception {
        assertThat(sum(Lists.newArrayList(1, 2, 3))).isEqualTo(6);
    }

    @Test
    public void testMedian() throws Exception {
        assertThat(median(Lists.newArrayList(1, 2, 3))).isEqualTo(2);
        assertThat(median(Lists.newArrayList(1, 2, 3, 4))).isEqualTo(2);
        assertThat(median(Lists.newArrayList(1, 2, 3, 4, 5, 6))).isEqualTo(3);
        assertThat(median(Lists.newArrayList(1, 2, 3, 4, 5, 6, 7))).isEqualTo(4);

    }
}

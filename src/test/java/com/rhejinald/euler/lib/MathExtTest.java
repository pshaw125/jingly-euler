package com.rhejinald.euler.lib;

import com.google.common.collect.Lists;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MathExtTest {

    @Test
    public void testSum() throws Exception {
        assertThat(MathExt.sum(Lists.newArrayList(1, 2, 3))).isEqualTo(6);
    }

    @Test
    public void testMedian() throws Exception {
        assertThat(MathExt.median(Lists.newArrayList(1, 2, 3))).isEqualTo(2);
        assertThat(MathExt.median(Lists.newArrayList(1, 2, 3, 4))).isEqualTo(2);
        assertThat(MathExt.median(Lists.newArrayList(1, 2, 3, 4, 5, 6))).isEqualTo(3);
        assertThat(MathExt.median(Lists.newArrayList(1, 2, 3, 4, 5, 6, 7))).isEqualTo(4);

    }
}

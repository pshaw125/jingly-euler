package com.rhejinald.euler.lib;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class MathExt {
    @Test
    public void testSum() throws Exception {
        assertThat(sum(Lists.newArrayList(1, 2, 3))).isEqualTo(6);
    }

    public static Long sum(Collection<? extends Number> integers) {
        Long result = 0L;
        for (Number value : integers) {
            result += value.longValue();
        }
        return result;
    }

    public static long factorial(long i) {
        long out = 1;
        for (int j = 2; j <= i; j++) {
            out*=j;
        }
        return out;
    }
}

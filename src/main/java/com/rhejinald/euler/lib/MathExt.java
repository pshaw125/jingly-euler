package com.rhejinald.euler.lib;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MathExt {
    public static Long sum(Collection<? extends Number> integers) {
        return integers.stream().mapToLong(Number::longValue).sum();
    }

    public static Long sum(Number... integers) {
        return sum(Arrays.asList(integers));
    }

    public static int median(List<Integer> c) {
        return c.get((c.size() - 1) / 2);
    }
}

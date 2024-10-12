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

    /**
     * Takes two inputs, provides 2 decimal place output of v1 / (v1+v2).
     * @param v1
     * @param v2
     * @return two decimal place output of v1 of the total
     */
    public static float percentToTwoDecimalPlaces(int v1, int v2){
        float total = v1 + v2 + 0.0f;
        final float percentage = (v1 / total)*10000;
        final int inflatedValue = Math.round(percentage);
        return inflatedValue/10000.0f;
    }

    /**
     * Takes two inputs, provides 2 decimal place output of v1 / (v1+v2).
     * @param v1
     * @param v2
     * @return two decimal place output of v1 of the total
     */
    public static float flooredPercentToTwoDecimalPlaces(int v1, int v2){
        float total = v1 + v2 + 0.0f;
        final float percentage = (v1 / total)*10000;
        final double inflatedValue = Math.floor(percentage);
        return (float) (inflatedValue/10000.0f);
    }
}

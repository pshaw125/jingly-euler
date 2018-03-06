package com.rhejinald.euler.lib;

import java.math.BigDecimal;

public class Factorial {

    public static BigDecimal factorial(long value) {
        BigDecimal out = BigDecimal.valueOf(value);
        if (value == 0) {
            return BigDecimal.valueOf(1);
        }
        for (int multiplicand = 2; multiplicand < value; multiplicand++) {
            out = out.multiply(BigDecimal.valueOf(multiplicand));
        }
        return out;
    }

}

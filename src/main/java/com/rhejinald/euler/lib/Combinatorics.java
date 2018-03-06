package com.rhejinald.euler.lib;

import java.math.BigDecimal;
import java.util.Optional;

import static com.rhejinald.euler.lib.Factorial.factorial;
import static java.math.BigDecimal.ROUND_HALF_UP;

public class Combinatorics {
    public static Optional<Long> combinatoric(int n, int r) {
        BigDecimal numerator = factorial(n);
        BigDecimal divisor = factorial(r).multiply(factorial(n - r));
        return LongUtils.asLong(numerator.divide(divisor, ROUND_HALF_UP));
    }
}

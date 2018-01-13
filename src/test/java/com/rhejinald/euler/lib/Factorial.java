package com.rhejinald.euler.lib;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * If you need combinatorics, they're in Problem 53. Extract if needed.
 */
public class Factorial {

    public static final int LONG_CHARS = String.valueOf(Long.MAX_VALUE).length();

    public static BigDecimal factorial(int value) {
        BigDecimal out = BigDecimal.valueOf(value);
        if (value == 0) {
            return BigDecimal.valueOf(1);
        }
        for (int multiplicand = 2; multiplicand < value; multiplicand++) {
            out = out.multiply(BigDecimal.valueOf(multiplicand));
        }
        return out;
    }

    public static Optional<Long> asLong(BigDecimal value) {
        if (value.toString().length() <= LONG_CHARS) {

            long possibleOverflowParsedLong = Long.parseLong(value.toString());
            if(possibleOverflowParsedLong > 0){
                return Optional.of(possibleOverflowParsedLong);
            }
        }
        return Optional.empty();
    }

    @Test
    public void testOverflow() throws Exception {
        assertThat(asLong(BigDecimal.valueOf(Long.MAX_VALUE))).isEqualTo(Optional.of(Long.MAX_VALUE));
    }
}

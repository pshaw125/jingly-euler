package com.rhejinald.euler.lib;

import java.math.BigDecimal;
import java.util.Optional;

public class LongUtils {
    private static final int LONG_CHARS = String.valueOf(Long.MAX_VALUE).length();

    public static Optional<Long> asLong(BigDecimal value) {

        if (value.toString().length() <= LONG_CHARS) {

            long possibleOverflowParsedLong = Long.parseLong(value.toString());
            if(possibleOverflowParsedLong > 0){
                return Optional.of(possibleOverflowParsedLong);
            }
        }
        return Optional.empty();
    }
}

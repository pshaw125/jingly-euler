package com.rhejinald.euler.lib;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Not used atm, but here for when I need it.
 * It was originally used for primes until a better way was found.
 */
public class NumberRange {
    public static Collection<Long> getRange(long upperBoundInclusive, long lowerBoundInclusive, List<Integer> divisorsToSkip) {
        if(lowerBoundInclusive > upperBoundInclusive) throw new IllegalArgumentException();
        return LongStream.rangeClosed(lowerBoundInclusive, upperBoundInclusive).boxed().filter(e -> {
            for (Integer div : divisorsToSkip) {
                if (e % div == 0) return false;
            }
            return true;
        }).collect(Collectors.toList());
    }
}

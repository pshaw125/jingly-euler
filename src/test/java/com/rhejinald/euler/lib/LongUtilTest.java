package com.rhejinald.euler.lib;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * If you need combinatorics, they're in Problem 53. Extract if needed.
 */
public class LongUtilTest {
    @Test
    public void testOverflow() throws Exception {
        assertThat(LongUtils.asLong(BigDecimal.valueOf(Long.MAX_VALUE))).isEqualTo(Optional.of(Long.MAX_VALUE));
    }

}

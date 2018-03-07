package com.rhejinald.euler.lib;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NumbersAsStringsTest {
    @Test
    public void testSumGetsOnlyDigits() throws Exception {
        assertThat(NumbersAsStrings.sumOfDigits("123")).isEqualTo(6);
        assertThat(NumbersAsStrings.sumOfDigits("333")).isEqualTo(9);
        assertThat(NumbersAsStrings.sumOfDigits("A1B2C3")).isEqualTo(6);
        assertThat(NumbersAsStrings.sumOfDigits("")).isEqualTo(0);
        assertThat(NumbersAsStrings.sumOfDigits("10")).isEqualTo(1);
        assertThat(NumbersAsStrings.sumOfDigits("99999")).isEqualTo(45);
    }
}
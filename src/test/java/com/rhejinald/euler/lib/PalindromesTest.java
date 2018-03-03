package com.rhejinald.euler.lib;

import org.junit.Test;

import static com.rhejinald.euler.lib.Palindromes.isPalindrome;
import static org.assertj.core.api.Assertions.assertThat;

public class PalindromesTest {
    @Test
    public void testPalindromes() throws Exception {
            assertThat(isPalindrome("12321")).isTrue();
            assertThat(isPalindrome("1221")).isTrue();
            assertThat(isPalindrome("11")).isTrue();
            assertThat(isPalindrome("1")).isTrue();

            assertThat(isPalindrome("123")).isFalse();
            assertThat(isPalindrome("1231")).isFalse();
            assertThat(isPalindrome("1231")).isFalse();
            assertThat(isPalindrome("12231")).isFalse();
            assertThat(isPalindrome("123221")).isFalse();

    }
}
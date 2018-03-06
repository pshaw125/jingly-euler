package com.rhejinald.euler.lib;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PermutationsTest {

    @Test
    public void testPermutation() throws Exception {
        assertThat(Permutations.of("abc")).hasSize(6);
        assertThat(Permutations.of("abc")).containsOnly("abc", "acb", "bca", "bac", "cab", "cba");
    }
}

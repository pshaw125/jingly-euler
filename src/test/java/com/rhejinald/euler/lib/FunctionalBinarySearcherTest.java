package com.rhejinald.euler.lib;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionalBinarySearcherTest {
    @Test
    public void testSearchOfArbitraryFunction() throws Exception {
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> val < 7)).isEqualTo(6);
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> val <= 7)).isEqualTo(7);
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> val <= 5)).isEqualTo(5);
        assertThat(FunctionalBinarySearcher.search(4, 10, val -> val <= 5)).isEqualTo(5);
        assertThat(FunctionalBinarySearcher.search(1, 20, val -> val <= 5)).isEqualTo(5);
        assertThat(FunctionalBinarySearcher.search(1, 21, val -> val <= 5)).isEqualTo(5);
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> val <= 10)).isEqualTo(9);
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> val <= 1)).isEqualTo(1);
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> true)).isEqualTo(9);
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> false)).isEqualTo(1);

    }
}

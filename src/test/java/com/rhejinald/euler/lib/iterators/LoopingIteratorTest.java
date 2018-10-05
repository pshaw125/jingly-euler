package com.rhejinald.euler.lib.iterators;

import com.google.common.collect.Lists;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoopingIteratorTest {
    @Test
    public void testIteratorOfNormalList() {
        LoopingIterator<String> iter = new LoopingIterator<>(Lists.newArrayList("one", "two"));
        assertThat(iter.next()).isEqualTo("one");
        assertThat(iter.next()).isEqualTo("two");
        assertThat(iter.next()).isEqualTo("one");
        assertThat(iter.next()).isEqualTo("two");
        assertThat(iter.hasNext()).isTrue();
    }
}
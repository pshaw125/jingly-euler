package com.rhejinald.euler.lib;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsTest {
    @Test
    public void testInstances() throws Exception {
        assertThat(StringUtils.instancesOf("1*2*3", "*")).isEqualTo(2);
        assertThat(StringUtils.instancesOf("*2*3*", "*")).isEqualTo(3);
        assertThat(StringUtils.instancesOf("12345", "*")).isEqualTo(0);
        assertThat(StringUtils.instancesOf("*****", "*")).isEqualTo(5);
        assertThat(StringUtils.instancesOf("112121112", "1")).isEqualTo(6);
        assertThat(StringUtils.instancesOf("1121211121", "1")).isEqualTo(7);
        assertThat(StringUtils.instancesOf("11111121111", "2")).isEqualTo(1);
    }

    @Test
    public void testReverse() throws Exception {
        assertThat(StringUtils.reverse("asdf")).isEqualTo("fdsa");
        assertThat(StringUtils.reverse("")).isEqualTo("");
        assertThat(StringUtils.reverse("ooo")).isEqualTo("ooo");
        assertThat(StringUtils.reverse("odoo")).isEqualTo("oodo");
    }
}

package com.rhejinald.euler.lib;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtils {
    public static String sortCharacters(String term) {
        char[] chars = term.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public static int instancesOf(String source, String term) {
        int count = 0;
        int indexOfLastTerm;
        while (true) {
            indexOfLastTerm = source.indexOf(term);
            if (indexOfLastTerm == -1) break;

            source = source.substring(indexOfLastTerm + 1);
            count++;
        }
        return count;
    }

    public static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    @Test
    public void testInstances() throws Exception {
        assertThat(instancesOf("1*2*3", "*")).isEqualTo(2);
        assertThat(instancesOf("*2*3*", "*")).isEqualTo(3);
        assertThat(instancesOf("12345", "*")).isEqualTo(0);
        assertThat(instancesOf("*****", "*")).isEqualTo(5);
        assertThat(instancesOf("112121112", "1")).isEqualTo(6);
        assertThat(instancesOf("1121211121", "1")).isEqualTo(7);
        assertThat(instancesOf("11111121111", "2")).isEqualTo(1);
    }

    @Test
    public void testReverse() throws Exception {
        assertThat(reverse("asdf")).isEqualTo("fdsa");
        assertThat(reverse("")).isEqualTo("");
        assertThat(reverse("ooo")).isEqualTo("ooo");
        assertThat(reverse("odoo")).isEqualTo("oodo");
    }
}

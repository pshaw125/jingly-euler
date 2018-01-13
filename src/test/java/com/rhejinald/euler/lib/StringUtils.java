package com.rhejinald.euler.lib;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtils {
    public static String sortString(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public static int instances(String source, String term) {
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

    @Test
    public void testInstances() throws Exception {
        assertThat(instances("1*2*3", "*")).isEqualTo(2);
        assertThat(instances("*2*3*", "*")).isEqualTo(3);
        assertThat(instances("12345", "*")).isEqualTo(0);
        assertThat(instances("*****", "*")).isEqualTo(5);
        assertThat(instances("112121112", "1")).isEqualTo(6);
        assertThat(instances("1121211121", "1")).isEqualTo(7);
        assertThat(instances("11111121111", "2")).isEqualTo(1);
    }
}

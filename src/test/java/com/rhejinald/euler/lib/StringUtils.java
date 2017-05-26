package com.rhejinald.euler.lib;

import java.util.Arrays;

public class StringUtils {
    public static String sortString(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}

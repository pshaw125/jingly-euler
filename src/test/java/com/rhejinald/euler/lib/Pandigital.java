package com.rhejinald.euler.lib;

import java.util.Arrays;

public class Pandigital {
    public static boolean isPandigital(String string) {
        char[] chars = string.toCharArray();
        if (chars.length != 9) {
            return false;
        }
        Arrays.sort(chars);
        return new String(chars).equals("123456789");
    }
}

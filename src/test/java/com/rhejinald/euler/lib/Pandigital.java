package com.rhejinald.euler.lib;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Pandigital {
    public static boolean is1To9Pandigital(String string) {
        char[] chars = string.toCharArray();
        if (chars.length != 9) {
            return false;
        }
        Arrays.sort(chars);
        return new String(chars).equals("123456789");
    }


}

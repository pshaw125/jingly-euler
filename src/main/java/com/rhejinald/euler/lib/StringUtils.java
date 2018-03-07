package com.rhejinald.euler.lib;

import java.util.Arrays;

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

    public static String reverse(String term) {
        return new StringBuilder(term).reverse().toString();
    }

}

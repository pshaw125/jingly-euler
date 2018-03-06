package com.rhejinald.euler.lib;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

public class Permutations {
    public static Set<String> of(String str) {
        HashSet<String> res = Sets.newHashSetWithExpectedSize(Factorial.factorial(str.length()).intValue());
        recursePermutations("", str, res);
        return res;
    }

    private static void recursePermutations(String prefix, String str, Set<String> resultSet) {
        int remainingCharCount = str.length();
        if (remainingCharCount == 0) {
            resultSet.add(prefix);
            return;
        }
        for (int i = 0; i < remainingCharCount; i++) {
            recursePermutations(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, remainingCharCount), resultSet);
        }
    }

}

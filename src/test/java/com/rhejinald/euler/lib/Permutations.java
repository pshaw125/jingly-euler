package com.rhejinald.euler.lib;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class Permutations {
    /**
     * Returns a SET of all permutations of some string str.
     * If provided with "aabc", the function would return: "aabc, aacb, abac, abca, acab, acba, baac..."
     *
     * Performance is a bit sluggish, dropping to ~4s once it hits 10 characters (3M permutations)
     */
    public static Set<String> of(String str) {
        HashSet<String> res = Sets.newHashSetWithExpectedSize((int) MathExt.factorial(str.length()));
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

    @Test
    public void testPermutation() throws Exception {
        assertThat(of("abc")).hasSize(6);
        assertThat(of("abc")).containsOnly("abc", "acb", "bca", "bac", "cab", "cba");
    }
}

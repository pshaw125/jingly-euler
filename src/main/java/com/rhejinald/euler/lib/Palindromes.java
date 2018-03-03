package com.rhejinald.euler.lib;

public class Palindromes {
    public static boolean isPalindrome(String string) {
        int index = 0;
        while (index <= string.length() / 2) {
            if (string.charAt(index) != string.charAt(string.length() - (index + 1)))
                return false;
            index++;
        }
        return true;

    }
}

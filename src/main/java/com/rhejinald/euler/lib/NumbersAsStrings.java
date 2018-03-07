package com.rhejinald.euler.lib;

public class NumbersAsStrings {
    public static int sumOfDigits(String number){
        return number
                .chars()
                .map(e -> e-48)
                .filter(e -> e>=1 && e<=9)
                .sum();
    }
}

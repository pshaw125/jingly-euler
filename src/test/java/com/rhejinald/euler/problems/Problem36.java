package com.rhejinald.euler.problems;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

/**
 *
 * The decimal number, 585 = 1001001001_2 (binary), is palindromic in both bases.
 *
 * Find the sum of all numbers, less than one million, which are palindromic in base 10 and base 2.
 *
 * (Please note that the palindromic number, in either base, may not include leading zeros.)
 *=================
 * My notes:
 * 2^20=1048576
 *
 * Attempt 1: 157 - Wrong! :(
 * Haha oops - my digit count was still set to 7, instead of 20, while I tuned performance.
 * Attempt 2: 872187 - Correct! That fixed it.
 * You are the 65506th person to have solved this problem.
 * Jan 13th 2017
 */
public class Problem36 {

    @Test
    public void testGeneratePalindromaticBinaryDigits() throws Exception {
        assertThat(generatePalindromaticBinaryDigits(3)).containsOnly("1", "11", "101", "111");
        assertThat(generatePalindromaticBinaryDigits(4)).containsOnly("1", "11", "101", "111", "1111", "1001");
        assertThat(generatePalindromaticBinaryDigits(5)).containsOnly("1", "11", "101", "111", "1111", "1001", "11011", "10001", "11111", "10101");
        assertThat(generatePalindromaticBinaryDigits(6)).containsOnly("1", "11", "101", "111", "1111", "1001", "11011", "10001", "11111", "10101", "100001", "110011", "101101", "111111");

    }

    private Set<String> generatePalindromaticBinaryDigits(int maxLength) {
        HashSet<String> firstHalves = Sets.newHashSet();
        int halfway = maxLength / 2;
        generateFirstHalfOfEvens(firstHalves, halfway, "11");
        generateFirstHalfOfEvens(firstHalves, halfway, "10");
        HashSet<String> palindromes = Sets.newHashSet();
        if (maxLength >= 4) {
            firstHalves.add("10");
            firstHalves.add("11");
        }
        if (maxLength >= 2) {
            firstHalves.add("1");
        }
        getPalindromeBase2(maxLength, firstHalves, palindromes);
        return palindromes;
    }

    private void getPalindromeBase2(double maxLength, HashSet<String> firstHalves, HashSet<String> palindromes) {
        palindromes.add("1");

        double upperBoundInclOddNumbers = Math.ceil(maxLength / 2);
        for (String firstHalf : firstHalves) {
            palindromes.add(firstHalf + new StringBuilder(firstHalf).reverse());
            if (firstHalf.length() < upperBoundInclOddNumbers) {
                palindromes.add(firstHalf + "0" + new StringBuilder(firstHalf).reverse());
                palindromes.add(firstHalf + "1" + new StringBuilder(firstHalf).reverse());
            }
        }
    }

    private void generateFirstHalfOfEvens(HashSet<String> firstHalves, int upperBoundOfFirstHalf, String currentString) {
        if (currentString.length() >= upperBoundOfFirstHalf) return;
        String addZero = currentString + "0";
        firstHalves.add(addZero);
        generateFirstHalfOfEvens(firstHalves, upperBoundOfFirstHalf, addZero);
        String addOne = currentString + "1";
        firstHalves.add(addOne);
        generateFirstHalfOfEvens(firstHalves, upperBoundOfFirstHalf, addOne);
    }


    @Test
    public void problem36() throws Exception {
        int digitCount = 20;
        Set<String> strings = generatePalindromaticBinaryDigits(digitCount);
        long runningSum=0;
        for (String base2palindrome : strings) {
            Integer base10Value = base2ToBase10(base2palindrome);
            if (base10Value>=1000000)continue;
            if(isPalindrome(base10Value)){
                runningSum+=base10Value;
            }
        }
        System.out.println(runningSum);
    }

    private boolean isPalindrome(Integer value) {
        String s = String.valueOf(value);
        int index = 0;
        while(index <= s.length()/2){
            if(s.charAt(index) != s.charAt(s.length()-(index+1)))
                return false;
            index++;
        }
        return true;
    }

    @Test
    public void testIsPalindrome() throws Exception {
        assertThat(isPalindrome(12321)).isTrue();
        assertThat(isPalindrome(1221)).isTrue();
        assertThat(isPalindrome(11)).isTrue();
        assertThat(isPalindrome(1)).isTrue();
        assertThat(isPalindrome(123)).isFalse();
        assertThat(isPalindrome(1231)).isFalse();
        assertThat(isPalindrome(1231)).isFalse();
        assertThat(isPalindrome(12231)).isFalse();
        assertThat(isPalindrome(123221)).isFalse();

    }

    private Integer base2ToBase10(String base2palindrome) {
        return Integer.valueOf(base2palindrome, 2);
    }

    private Integer baseTwoToInt(String baseTwoString) {
        return Integer.parseInt(baseTwoString, 2);
    }

    @Test
    public void testBaseTwoToInt() throws Exception {
        assertThat(baseTwoToInt("10101")).isEqualTo(21);
        assertThat(baseTwoToInt("010101")).isEqualTo(21);
        assertThat(baseTwoToInt("111")).isEqualTo(7);


    }
}

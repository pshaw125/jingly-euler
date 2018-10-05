package com.rhejinald.euler.problems;


import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import com.rhejinald.euler.lib.StringUtils;
import com.rhejinald.euler.lib.iterators.LoopingIterator;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Each character on a computer is assigned a unique code and the preferred standard is ASCII (American Standard Code
 * for Information Interchange). For example, uppercase A = 65, asterisk (*) = 42, and lowercase k = 107.
 * <p>
 * A modern encryption method is to take a text file, convert the bytes to ASCII, then XOR each byte with a given value,
 * taken from a secret key. The advantage with the XOR function is that using the same encryption key on the cipher text,
 * restores the plain text; for example, 65 XOR 42 = 107, then 107 XOR 42 = 65.
 * <p>
 * For unbreakable encryption, the key is the same length as the plain text message, and the key is made up of random
 * bytes. The user would keep the encrypted message and the encryption key in different locations, and without both
 * "halves", it is impossible to decrypt the message.
 * <p>
 * Unfortunately, this method is impractical for most users, so the modified method is to use a password as a key.
 * If the password is shorter than the message, which is likely, the key is repeated cyclically throughout the message.
 * The balance for this method is using a sufficiently long password key for security, but short enough to be memorable.
 * <p>
 * Your task has been made easy, as the encryption key consists of three lower case characters. Using cipher.txt
 * (saved to resources), a file containing the encrypted ASCII codes, and the knowledge that
 * the plain text must contain common English words, decrypt the message and find the sum of the ASCII values in the
 * original text.
 * ==-==-==-==-==-==-==-==-==-==-==-==
 * Yeesh, so our key is 3 lower case characters; that's still 26^3=17576 options.
 * Maybe go 100 at a time?
 * <p>
 * As for the operation itself, it's a binary XOR as follows:
 * 1 0 0 1 0 1 1 = 75
 * 1 1 0 1 0 1 1 = 107
 * 0 1 0 0 0 0 0 = 32 = XOR
 * <p>
 * 1 0 0 0 0 0 1 = A = 65
 * 1 1 0 1 0 1 1 = 107
 * 0 1 0 1 0 1 0 = 42 '*'
 * <p>
 * Next question is, is there an acceptable library to perform this function?
 * Today I learned: a^b is java-speak for "a XOR b". That class I wrote just went *poof!*
 *
 * So I just printed all 17k possibilities and started scanning by eye in a file; searched for "the" and spotted the
 * entry at cipher 'god', which was an encrypted passage from the Bible.
 *
 * Attempt 2 - 107359 - Correct - You are the 33786th person to have solved this problem.
 * This problem had a difficulty rating of 5%. The highest difficulty rating you have solved so far is 15%.
 * (I forget what attempt number 1 was - something daft. I didn't check the ascii value of each character, rather I \
 * counted the value of the first "(" and applied that once per the number of characters.)
 */
public class Problem59 {
    @Test
    public void testGetLowerCaseCharacterRanges() throws Exception {
//        System.out.println("k".hashCode()); // 107
//        System.out.println("K".hashCode()); // 75
//        System.out.println("a".hashCode()); // 97
//        System.out.println("z".hashCode()); // 122
        char[] chars = getIntegerParsedDataAsString().toCharArray();


        char upperCaseA = 65;
        char lowerCaseK = 107;


        Character param1 = 'g';
//        for (int param1Loop = 0; param1Loop < 26; param1Loop++) {
        Character param2 = 'o';
//            for (int param2Loop = 0; param2Loop < 26; param2Loop++) {
        Character param3 = 'd';
//                for (int param3Loop = 0; param3Loop < 26; param3Loop++) {
        ArrayList<Character> cipherCharacters = Lists.newArrayList(param1, param2, param3);
        System.out.print(String.valueOf(param1) + String.valueOf(param2) + String.valueOf(param3) + ">>");
        String x = applyCipher(chars, cipherCharacters);
        System.out.println(x);
        int sum = 0;
        for (int i = 0; i < x.length(); i++){
            sum += ((Character)(x.charAt(i))).hashCode();
        }
        System.out.println(sum);

//                    param3++;
//                }
//                param2++;
//            }
//            param1++;
//        }
    }

    private String applyCipher(char[] chars, ArrayList<Character> cipherCharacters) {
        StringBuilder builder = new StringBuilder();
        Iterator<Character> cipher = new LoopingIterator<>(cipherCharacters);
        for (int i = 0; i < chars.length; i++) {
            builder.append((char) (chars[i] ^ cipher.next()));
        }
        return builder.toString();
    }

    private String getIntegerParsedDataAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(readFile("p059_cipher.txt").split(","))
                .map(Integer::parseInt)
                .forEach(e -> stringBuilder.append((char) e.intValue()));
        return stringBuilder.toString();
    }

    private String readFile(String name) {
        try {
            return CharStreams.toString(new InputStreamReader(this.getClass().getResourceAsStream("/" + name)));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Test
    public void testApplyCipher() {
        String source = "hello";
        String encoded = ")'-.."; // source run through repeating cipher
        ArrayList<Character> cipher = Lists.newArrayList('A', 'B');

        assertThat(applyCipher(source.toCharArray(), cipher)).isEqualTo(encoded);
        assertThat(applyCipher(encoded.toCharArray(), cipher)).isEqualTo(source);
    }

    @Test
    public void testXorBehavesAsExpected() {
        /*
         * * 1 0 0 1 0 1 1 = 75
         * 1 1 0 1 0 1 1 = 107
         * 0 1 0 0 0 0 0 = 32 = XOR
         *
         * 1 0 0 0 0 0 1 = A = 65
         * 1 1 0 1 0 1 1 = 107
         * 0 1 0 1 0 1 0 = 42 '*'
         *
         * 'l' = 108 = 1 1 0 1 1 0 0
         * 'K' = 75  = 1 0 0 1 0 1 1
         * XOR   39  = 0 1 0 0 1 1 1 (XOR is bit-wise)
         */

        assertThat((char) ((char) 75 ^ (char) 107)).isEqualTo((char) 32);
        assertThat((char) ('K' ^ 'k')).isEqualTo((char) 32);
        assertThat((char) ('A' ^ 'k')).isEqualTo('*');
        assertThat((char) ('k' ^ 'A')).isEqualTo('*');
        assertThat((char) ('A' ^ 'A')).isEqualTo((char) 0);
        assertThat((char) ('K' ^ 'l')).isEqualTo((char) 39);

    }

}

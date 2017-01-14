package com.rhejinald.euler.problems;

import com.google.common.collect.Sets;
import com.rhejinald.euler.lib.Primes;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The number 3797 has an interesting property. Being prime itself, it is possible to continuously remove digits from left to right, and remain prime at each stage: 3797, 797, 97, and 7. Similarly we can work from right to left: 3797, 379, 37, and 3.
 * Find the sum of the only eleven primes that are both truncatable from left to right and right to left.
 * NOTE: 2, 3, 5, and 7 are not considered to be truncatable primes.
 * =================================
 * Testing up to 5000 upper bound of primes reveals 10 of the 11 primes.
 * [3137, 37, 73, 53, 373, 3797, 23, 313, 317, 797] + 33797
 *
 * Attempt 1:
 * 3137 + 37 + 73 + 53 + 373 + 3797 + 23 + 313 + 317 + 797 + 33797 = 42717 : Incorrect! :(
 */
public class Problem37 {

    public static final int ONE_MILLION = 1000000;
    private Primes primes;

    @Test
    public void problem37() throws Exception {
        primes = new Primes();
        Set<Long> primes = this.primes.getPrimes(3797);
        Set<Long> truncatablePrimes = Sets.newHashSet();
        for (Long prime : primes) {
            if (isPrimeTruncatableLeftAndRight(prime)) {
                truncatablePrimes.add(prime);
            }
        }
        truncatablePrimes.removeAll(Sets.newHashSet(2L, 3L, 5L, 7L));
        System.out.println(truncatablePrimes);

        for (int i = 0; i < 10; i++) {
            isNextThingPrime(i, "3137");
            isNextThingPrime(i, "3797");
        }

    }

    private void isNextThingPrime(int i, String s) {
        Long newValue = Long.valueOf(String.valueOf(i) + s);
        if(this.primes.isPrime(newValue)){
                System.out.println(newValue);
        }
    }

    @Test
    public void testGivenCase() throws Exception {
        primes = new Primes();
        int knownTruncatablePrime = 3797;
        primes.getPrimes(knownTruncatablePrime);

        assertThat(isPrimeTruncatableLeftAndRight(knownTruncatablePrime)).isTrue();
    }

    private boolean isPrimeTruncatableLeftAndRight(long i) {
        String baseString = String.valueOf(i);
        return isPrimeTruncatableLeft(baseString) && isPrimeTruncatableRight(baseString);
    }

    private boolean isPrimeTruncatableLeft(String string) {
        if (string.length() == 1) return true;
        String truncatedString = truncateLeft(string);
        return primes.isPrime(Long.valueOf(truncatedString)) && isPrimeTruncatableLeft(truncatedString);
    }

    private boolean isPrimeTruncatableRight(String string) {
        if (string.length() == 1) return true;
        String truncatedString = truncateRight(string);
        return primes.isPrime(Long.valueOf(truncatedString)) && isPrimeTruncatableRight(truncatedString);
    }


    @Test
    public void truncateLeftTest() throws Exception {
        assertThat(truncateLeft("1234")).isEqualTo("123");
        assertThat(truncateLeft("43")).isEqualTo("4");
    }

    @Test
    public void truncateRightTest() throws Exception {
        assertThat(truncateRight("1234")).isEqualTo("234");
        assertThat(truncateRight("43")).isEqualTo("3");
    }

    private String truncateLeft(String s) {
        return s.substring(0, s.length() - 1);
    }

    private String truncateRight(String s) {
        return s.substring(1, s.length());
    }
}

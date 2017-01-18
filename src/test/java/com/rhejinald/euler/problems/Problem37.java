package com.rhejinald.euler.problems;

import com.google.common.collect.Sets;
import com.rhejinald.euler.lib.MathExt;
import com.rhejinald.euler.lib.Primes;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The number 3797 has an interesting property. Being prime itself, it is possible to continuously remove digits from left to right, and remain prime at each stage: 3797, 797, 97, and 7. Similarly we can work from right to left: 3797, 379, 37, and 3.
 * Find the sum of the only eleven primes that are both truncatable from left to right and right to left.
 * NOTE: 2, 3, 5, and 7 are not considered to be truncatable primes.
 * =================================
 * Testing up to 5000 upper bound of primes reveals 10 of the 11 primes.
 * [3137, 37, 73, 53, 373, 3797, 23, 313, 317, 797] + 33797
 * <p>
 * Attempt 1:
 * 3137 + 37 + 73 + 53 + 373 + 3797 + 23 + 313 + 317 + 797 + 33797 = 42717 : Incorrect! :(
 * -> 33797 isn't double truncatable - 3379/31=109.
 * <p>
 * Attempt 2: I didn't add digits to the other end to discover the 11th prime - trying that...
 * Found 31379 instead;
 * 3137 + 37 + 73 + 53 + 373 + 3797 + 23 + 313 + 317 + 797 + 31379 = 40299 : Incorrect!
 * <p>
 * 2 digit: 23, 37, 53, 73
 * ==========================
 * 3797, 797, 97, and 7. Similarly we can work from right to left: 3797, 379, 37, and 3.
 * This gives us various primes, but it doesn't mean all constituent branch primes are also in this set.
 * 797 is the key example; 797 is in the set, but 97 is not.
 * Additional rule, any number must have a prime at either end to be a member of this set.
 * Any prime which is double truncatable is cool either way.
 * add out 2 digit truncatables (via research):
 * [23, 37, 54, 73] = [73, 23, 37, 53] + [3797, 797, 379, 97, 37]
 * <p>
 * We have several numbers that can only be part of either branch;
 * Number is only right-truncatable (lop off left-most digit), it can only be in one half of the tree, so we
 * only need to prepend digits to build the potential tree. It doesn't have to be a prime that we add, but the
 * digit we add must make it prime.
 * <p>
 * So, for each 2 digit LEFT truncatable only (eg 71), try adding a digit to the end, if it's prime, add that
 * to the tree, and test for double truncality - if it tests positive, add to result set.
 * If it's a RIGHT truncatable (eg 67), add a digit to the start and run the same test.
 * <p>
 * We have a tree with 4 origin nodes, and we'll build children from there.
 *
 * Attempt 3: 748317 - Correct!
 * [3137, 37, 73, 739397, 53, 3797, 373, 23, 313, 797, 317]
 * 985ms
 * The trick was to pre-populate the primes for the range up to what we needed. Doing the seieve one at a time
 * for gradually larger and larger intervals is really inefficient for my algo
 * This also has the interesting property that we had to go through 2 layers (5 and 6 digits) where there were
 * ONLY left-only or right-only truncatables, then the tree converged again at level 7.
 */
public class Problem37 {

    private Primes primes;

    @Before
    public void setUp() throws Exception {
        primes = new Primes();

    }

    @Test
    public void problem37() throws Exception {
        primes.getPrimes(1000000);
        PrimeTree truncatablePrimes = getTruncatablePrimes(6);
        Sets.SetView<Long> intersection = Sets.intersection(truncatablePrimes.getLeftSet(), truncatablePrimes.getRightSet());
        HashSet<Long> longs = new HashSet<>(intersection);
        longs.removeAll(Sets.newHashSet(2L,3L,5L,7L));
        System.out.println(longs);
        System.out.println(MathExt.sum(longs));
    }

    @Test
    public void leftTruncatabilityTree() throws Exception {
        primes.getPrimes(100);
        PrimeTree truncatablePrimes = getTruncatablePrimes(2);
        assertThat(truncatablePrimes.getLeftBranch()).contains(23L, 29L, 31L, 37L, 53L, 59L, 71L, 73L, 79L);
        assertThat(truncatablePrimes.getLeftSet()).contains(2L, 3L, 5L, 7L, 23L, 29L, 31L, 37L, 53L, 59L, 71L, 73L, 79L);
        assertThat(truncatablePrimes.getRightBranch()).contains(13L, 23L, 37L, 43L, 47L, 53L, 67L, 73L, 83L, 97L);
        assertThat(truncatablePrimes.getRightSet()).contains(2L, 3L, 5L, 7L, 13L, 23L, 37L, 43L, 47L, 53L, 67L, 73L, 83L, 97L);
        assertThat(truncatablePrimes.getIntersection()).contains(23L, 37L, 53L, 73L);
        for (Long intersectionPrime : truncatablePrimes.getIntersection()) {
            assertThat(isPrimeTruncatableLeftAndRight(intersectionPrime)).isTrue();
        }
    }

    private PrimeTree getTruncatablePrimes(final int depth) {
        HashSet<Long> leftBranch = Sets.newHashSet(2L, 3L, 5L, 7L);
        HashSet<Long> rightBranch = new HashSet<>(leftBranch);
        HashSet<Long> leftSet = new HashSet<>(leftBranch);
        HashSet<Long> rightSet = new HashSet<>(leftBranch);

        int currentDepth = 1;
        while (currentDepth < depth) {
            HashSet<Long> nextLeftBranchStep = Sets.newHashSet();
            HashSet<Long> nextRightBranchStep = Sets.newHashSet();

            for (Long value : leftBranch) {
                for (long n = 1; n < 10; n += 2) {
                    Long nextValue = Long.valueOf(value + "" + n);
                    if (primes.isPrime(nextValue)) {
                        nextLeftBranchStep.add(nextValue);
                    }
                }
            }

            for (Long value : rightBranch) {
                for (long n = 1; n < 10; n++) {
                    Long nextValue = Long.valueOf(n + "" + value);
                    if (primes.isPrime(nextValue)) {
                        nextRightBranchStep.add(nextValue);
                    }
                }
            }

            leftSet.addAll(nextLeftBranchStep);
            rightSet.addAll(nextRightBranchStep);
            leftBranch = nextLeftBranchStep;
            rightBranch = nextRightBranchStep;
            currentDepth++;
        }

        return new PrimeTree(leftBranch, rightBranch, leftSet, rightSet);

    }

    @Test
    @Ignore("dud solution")
    public void problem37dud() throws Exception {
        Set<Long> primes = this.primes.getPrimes(3797);
        Set<Long> truncatablePrimes = Sets.newHashSet();
        for (Long prime : primes) {
            if (isPrimeTruncatableLeftAndRight(prime)) {
                truncatablePrimes.add(prime);
            }
        }
        truncatablePrimes.removeAll(Sets.newHashSet(2L, 3L, 5L, 7L));
        System.out.println(truncatablePrimes);

        HashSet<Integer> integers = Sets.newHashSet(2, 3, 5, 7);
        for (Integer i : integers) {
            isNextThingPrime(String.valueOf(i) + "3137");
            isNextThingPrime("3137" + String.valueOf(i));
            isNextThingPrime(String.valueOf(i) + "3797");
            isNextThingPrime("3797" + String.valueOf(i));
        }
    }

    private void isNextThingPrime(String s1) {
        Long newValue = Long.valueOf(s1);
        if (this.primes.isPrime(newValue)) {
            System.out.println(newValue);
        }
    }

    @Test
    public void testResearchGetTwoDigitTruncatables() throws Exception {
        Set<Long> primes = this.primes.getPrimes(100);
        for (Long prime : primes) {
            if (isPrimeTruncatableLeftAndRight(prime)) {
                System.out.println(prime + " Truncatable both ways"); // should be 73, 23, 37, 53 (and the single digits, which we discount.
                continue;
            }
            if (isPrimeTruncatableLeft(String.valueOf(prime))) {
                System.out.println(prime + " Truncatable Left ONLY <__");
            }
            if (isPrimeTruncatableRight(String.valueOf(prime))) {
                System.out.println(prime + " Truncatable Right ONLY __>");
            }
        }

    }

    @Test
    public void testGivenCase() throws Exception {
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

    private static class PrimeTree {

        private final Set<Long> leftBranch;
        private final Set<Long> rightBranch;
        private final HashSet<Long> leftSet;
        private final HashSet<Long> rightSet;

        public PrimeTree(HashSet<Long> leftBranch, HashSet<Long> rightBranch, HashSet<Long> leftSet, HashSet<Long> rightSet) {
            this.leftBranch = leftBranch;
            this.rightBranch = rightBranch;
            this.leftSet = leftSet;
            this.rightSet = rightSet;
        }

        public HashSet<Long> getRightSet() {
            return rightSet;
        }

        public HashSet<Long> getLeftSet() {
            return leftSet;

        }

        public Set<Long> getLeftBranch() {
            return leftBranch;
        }

        public Set<Long> getRightBranch() {
            return rightBranch;
        }

        public Set<Long> getIntersection() {
            return Sets.intersection(leftBranch, rightBranch);
        }
    }
}

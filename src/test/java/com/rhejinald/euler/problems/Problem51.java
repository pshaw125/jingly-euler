package com.rhejinald.euler.problems;

import com.google.common.collect.Lists;
import com.rhejinald.euler.lib.Primes;
import com.rhejinald.euler.lib.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * By replacing the 1st digit of the 2-digit number *3, it turns out that six of the nine possible values:
 * 13, 23, 43, 53, 73, and 83, are all prime.
 * <p>
 * By replacing the 3rd and 4th digits of 56**3 with the same digit, this 5-digit number is the first example having
 * seven primes among the ten generated numbers, yielding the family:
 * 56003, 56113, 56333, 56443, 56663, 56773, and 56993.
 * Consequently 56003, being the first member of this family, is the smallest prime with this property.
 * <p>
 * Find the smallest prime which, by replacing part of the number (not necessarily adjacent digits) with the same digit,
 * is part of an eight prime value family.
 * <p>
 * =============================
 * For some number, x, some number of values are wildcard.
 * Further, because we're looking for an 8-prime of the 10 possible values, we need to validate that only 3 of them are
 * not present before we can remove that possibility.
 * <p>
 * Chances are, but not for sure, X is larger than 56003
 * <p>
 * Method: Choosing multiple asterisks is a triangle number problem (in complexity, not specifically).
 * I don't see a way more effective than just iterating through the different wild options.
 * <p>
 * We can safely assume that the last digit will never be an asterisk, as it MUST be one of [0,2,4,6,8], which will
 * inherently make it not prime. So for each combination, we need to check only 1,3,7,9 as the last digit.
 * Further, 0 cannot be the first digit. Hopefully that's obvious.
 * <p>
 * The other way we could approach this is with a big-ass hashmap; our primes only go up 2 billion anyway, so what we
 * could do is iterate through the primes; for each digit length we pull a new map; into which we put each permuation
 * of wildcards. It's possible that the first digit is an asterisk, it's just harder (owing to 0 not being an option,
 * there's only 1 possible "miss" instead of 2). This sounds better, let's try this.
 * <p>
 * Substituting these wildcards, I can only choose n digits that are the same to sub, otherwise the result might not
 * be prime. So we'll check all the single digits; then all the digits which are 2-tuples, then all the 3-tuples etc,
 * up to the length of the number -1. The last digit will never be an asterisk, optimizing out families we know will
 * not be 8 in size.
 * <p>
 * Can some digits of a homogeneous n-tuple NOT be substituted? Yes.
 * ======================
 * Check 1: 121313 (from "*2*3*3") -> Correct! Jan 2nd 2018, 13:31pm (totally at work) - takes ~5s
 *
 * Congratulations, the answer you gave to problem 51 is correct.
 * <p>
 * You are the 25755th person to have solved this problem.
 * <p>
 * This problem had a difficulty rating of 15%. The highest difficulty rating you had previously solved was 5%.
 * This is a new record. Well done!
 */
public class Problem51 {
    private static final String WILDCARD = "*";

    @Test
    public void testProblem51() throws Exception {
        Primes primes = new Primes();
        Map<String, Integer> wildcardPermutation = new HashMap<>(50000, 0.8f);
        int lowerBound = 100000;
        int upperBound = lowerBound*10;
        Set<Integer> primeSet = primes.getPrimes(upperBound);

        primeSet.stream().filter(prime -> prime > lowerBound && prime < upperBound)
                .map(prime -> getHomogeneousWildSubs(prime, getPermutationsOfWildcardedNumber(prime)))
                .forEach(e -> e
                        .forEach(v -> {
                            int value = wildcardPermutation.getOrDefault(v, 0) + 1;
                            wildcardPermutation.put(v, value);
                            if (value == 8) {
                                System.out.println("<><><><><><> FOUND for 8 primes: " + v);
                            }
                        }));
    }

    /**
     * @param prime Number to permutate
     * @return set of all possible wildcarded numbers, where the final digit cannot be set as a wildcard.
     * 1110 would return *110, 1*10, 11*0, **10, *1*0, 1**0, ***0
     * Returns (2^n)-1 different options
     */
    private static List<String> getPermutationsOfWildcardedNumber(Integer prime) {
        if (prime.toString().length() <= 1) return Collections.emptyList();
        HashSet<String> results = new HashSet<>();

        permutationsHelper(prime.toString(), 0, results);

        results.remove(prime.toString());
        ArrayList<String> sortedResults = Lists.newArrayList(results);
        Comparator<String> wildCardComparator = getWildCardComparator();
        sortedResults.sort(wildCardComparator);
        return sortedResults;
    }

    private static Comparator<String> getWildCardComparator() {
        return (o1, o2) -> {
            int wildCount = Integer.compare(StringUtils.instances(o1, WILDCARD), StringUtils.instances(o2, WILDCARD));
            if (wildCount != 0) {
                return wildCount;
            }
            int o1LastIndex = o1.lastIndexOf(WILDCARD);
            int o2LastIndex = o2.lastIndexOf(WILDCARD);
            if (o1LastIndex != o2LastIndex) {
                return Integer.compare(o2.lastIndexOf(WILDCARD), o1.lastIndexOf(WILDCARD));
            }
            for (int i = 0; i < Math.min(o1.length(), o2.length()); i++) {
                boolean isO1Wild = o1.substring((o1.length() - i) - 1, (o1.length() - i)).equals(WILDCARD);
                boolean isO2Wild = o2.substring((o2.length() - i) - 1, (o2.length() - i)).equals(WILDCARD);
                if ((isO1Wild && isO2Wild) || (!isO1Wild && !isO2Wild)) {
                    continue;
                }
                return isO1Wild ? -1 : 1;
            }
            return 0;
        };
    }

    private static void permutationsHelper(String source, int index, Set<String> results) {
        if (index + 1 >= source.length()) return;
        String before = source.substring(0, index);
        String after = source.substring(index + 1, source.length());
        String marked = before + WILDCARD + after;
        results.add(source);
        results.add(marked);

        permutationsHelper(marked, index + 1, results);
        permutationsHelper(source, index + 1, results);
    }

    @Test
    public void testPermutationsFilteredToOnlyIncludeHomogenousWilds() throws Exception {
        assertThat(getHomogeneousWildSubs(12345, getPermutationsOfWildcardedNumber(12345))).hasSize(4);
        assertThat(getHomogeneousWildSubs(1122, getPermutationsOfWildcardedNumber(1122))).containsOnly("**22");
        assertThat(getHomogeneousWildSubs(1111, getPermutationsOfWildcardedNumber(1111))).isEmpty();
        assertThat(getHomogeneousWildSubs(24240, getPermutationsOfWildcardedNumber(24240))).containsOnly("*4*40", "2*2*0");
    }

    private List<String> getHomogeneousWildSubs(int source, List<String> permutations) {
        return permutations.stream().filter(p -> {
            for (int i = 0; i <= 9; i++) {
                char newChar = String.valueOf(i).toCharArray()[0];
                if (String.valueOf(source).equals(p.replace('*', newChar))) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Test
    public void testGetWildcardPermutations() throws Exception {
        assertThat(getPermutationsOfWildcardedNumber(1110)).containsOnly("*110", "1*10", "11*0", "**10", "*1*0", "1**0", "***0");
        assertThat(getPermutationsOfWildcardedNumber(25)).containsOnly("*5");
        assertThat(getPermutationsOfWildcardedNumber(155)).containsOnly("*55", "1*5", "**5");
        assertThat(getPermutationsOfWildcardedNumber(54321)).containsOnly("****1", "***21", "**3*1", "**321", "*4**1", "*4*21", "*43*1", "*4321", "5***1", "5**21", "5*3*1", "5*321", "54**1", "54*21", "543*1");
    }

    @Test
    public void testComparator() throws Exception {
        Comparator<String> c = getWildCardComparator();
        assertThat(sortAsList(c, "*01", "10*", "1*0")).containsExactly("10*", "1*0", "*01");
        assertThat(sortAsList(c, "10*", "*01", "1*0")).containsExactly("10*", "1*0", "*01");
        assertThat(sortAsList(c, "10*", "1*0", "*01")).containsExactly("10*", "1*0", "*01");

        assertThat(sortAsList(c, "**1", "10*", "1*0")).containsExactly("10*", "1*0", "**1");
        assertThat(sortAsList(c, "10*", "**1", "1*0")).containsExactly("10*", "1*0", "**1");

        assertThat(sortAsList(c, "**01", "100*", "1*01", "10**")).containsExactly("100*", "1*01", "10**", "**01");
        assertThat(sortAsList(c, "100*", "10**", "**01", "1*01")).containsExactly("100*", "1*01", "10**", "**01");

        assertThat(sortAsList(c, "0**", "*0*")).containsExactly("0**", "*0*");
        assertThat(sortAsList(c, "*0*", "0**")).containsExactly("0**", "*0*");

        assertThat(sortAsList(c, "10**", "1*0*")).containsExactly("10**", "1*0*");
        assertThat(sortAsList(c, "1*0*", "10**")).containsExactly("10**", "1*0*");

        assertThat(sortAsList(c, "0**", "0**")).containsExactly("0**", "0**");

        assertThat(sortAsList(c, "1**", "0**")).containsOnly("0**", "1**");
        assertThat(sortAsList(c, "0**", "1**")).containsOnly("0**", "1**");
    }

    private ArrayList<String> sortAsList(Comparator<String> wildCardComparator, String... elements) {
        ArrayList<String> strings = Lists.newArrayList(elements);
        strings.sort(wildCardComparator);
        return strings;
    }
}

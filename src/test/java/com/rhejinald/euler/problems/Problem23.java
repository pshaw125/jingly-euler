package com.rhejinald.euler.problems;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.rhejinald.euler.lib.Factors;
import com.rhejinald.euler.lib.MathExt;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A perfect number is a number for which the sum of its proper divisors is exactly equal to the number.
 * For example, the sum of the proper divisors of 28 would be 1 + 2 + 4 + 7 + 14 = 28, which means that
 * 28 is a perfect number.
 * <p>
 * A number n is called deficient if the sum of its proper divisors is less than n and it is called
 * abundant if this sum exceeds n.
 * <p>
 * As 12 is the smallest abundant number, 1 + 2 + 3 + 4 + 6 = 16, the smallest number that can be written
 * as the sum of two abundant numbers is 24. By mathematical analysis, it can be shown that all integers
 * greater than 28123 can be written as the sum of two abundant numbers. However, this upper limit cannot
 * be reduced any further by analysis even though it is known that the greatest number that cannot be
 * expressed as the sum of two abundant numbers is less than this limit.
 * <p>
 * Find the sum of all the positive integers which cannot be written as the sum of two abundant numbers.
 * <p>
 * ==================
 * This is a doozy...
 * So we need to find all Abundant numbers - numbers for which the proper divisors (method available in
 * Factors class) sum to greater than the number itself; so it will include naturally highly divisible numbers
 * <p>
 * Abundant numbers may continue past 28123, but we only care up to that bound as anything else can be written
 * as the sum of smaller abundant numbers. So we have a 28123 element hashset, and remove elements we can make
 * as a result of each abundant sum. This includes multiples of the sums.
 * If an abundant sum is a multiple of a smaller abundant sum, it can be discounted from our list as it's
 * covered by its lesser factor.
 * <p>
 * First, let's find how many abundant numbers there are: There are 6965.
 * I see 12, 24, 36, 48... many more multiples of 12.
 * <p>
 * OH except I read the question wrong - It's about the positive integers written as the sum of TWO abundant numbers;
 * okay, so no removal of powers, and this becomes O(n^2) problem
 * <p>
 * ----
 * Attempt 1: 52749780 - Wrong! But why? :(
 * Attempt 2:  4179871 - streamlined a few things, but what really changed the answer was just tossing out any sum that
 * was greater than 28123, as noted in the question. Why that made a difference is unclear.
 */
public class Problem23 {

    private Factors factors;

    @Test
    public void testProblem23() throws Exception {
        factors = new Factors();
        HashSet<Integer> abundantNumberSums = Sets.newHashSet();

        ArrayList<Integer> abundantNumbers = getAbundantNumbers();
        for (int i = 0; i < abundantNumbers.size(); i++) {
            for (int j = i; j < abundantNumbers.size(); j++) {
                int sum = abundantNumbers.get(i) + abundantNumbers.get(j);
                if (sum > 28123) break;
                abundantNumberSums.add(sum);
            }
        }

        System.out.println((long) findSumOfIntegersNotIncludedInSetUpperBoundedBySet(abundantNumberSums));
    }

    /**
     * takes in Set of numbers
     * creates set of numbers up to upper bound N of set
     * returns sum of numbers NOT in provided set, 1 ≤ x < N
     *
     * @param numbersToExcludeFromSum set of numbers to be subtracted, as noted above
     * @return double containing sum of numbers not included in upper bounding set
     */
    private double findSumOfIntegersNotIncludedInSetUpperBoundedBySet(Set<Integer> numbersToExcludeFromSum) {
        Long upperBound = Long.valueOf(Collections.max(numbersToExcludeFromSum));
        Long sumOfAbundantNumberSums = MathExt.sum(numbersToExcludeFromSum);

        //even: (n+1)* n/2
        //odd:  n *((n/2)+0.5)
        //-> 1+2+3+..+N
        double sumOfAllDigitsUpToUpperBound;
        if (upperBound % 2 == 0) {
            sumOfAllDigitsUpToUpperBound = (upperBound + 1) * (upperBound / 2);
        } else {
            sumOfAllDigitsUpToUpperBound = (upperBound) * (Math.floor((upperBound / 2)) + 1);
        }

        return sumOfAllDigitsUpToUpperBound - sumOfAbundantNumberSums;
    }

    @Test
    public void testFindSumOfIntegersNotIncludedInSetUpperBoundedBySet() throws Exception {
        assertThat(findSumOfIntegersNotIncludedInSetUpperBoundedBySet(Sets.newHashSet(1, 3, 5, 10))).isEqualTo(2 + 4 + 6 + 7 + 8 + 9);
        assertThat(findSumOfIntegersNotIncludedInSetUpperBoundedBySet(Sets.newHashSet(2, 6, 9, 11))).isEqualTo(1 + 3 + 4 + 5 + 7 + 8 + 10);
        assertThat(findSumOfIntegersNotIncludedInSetUpperBoundedBySet(Sets.newHashSet(1, 2, 3, 7, 14))).isEqualTo(15 * 7 - (1 + 2 + 3 + 7 + 14));
        assertThat(findSumOfIntegersNotIncludedInSetUpperBoundedBySet(Sets.newHashSet(1, 2, 3, 7, 17))).isEqualTo(17 * 9 - (1 + 2 + 3 + 7 + 17));

    }

    private ArrayList<Integer> getAbundantNumbers() {
        ArrayList<Integer> abundantNumbers = Lists.newArrayListWithExpectedSize(7000);
        for (int currentNumber = 12; currentNumber <= 28123; currentNumber++) {
            if (factors.isAbundantNumber(currentNumber)) {
                abundantNumbers.add(currentNumber);
            }
        }
        return abundantNumbers;
    }

}

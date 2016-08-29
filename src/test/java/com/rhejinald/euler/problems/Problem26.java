package com.rhejinald.euler.problems;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.rhejinald.euler.lib.Primes;
import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * not:
 * 563 -> 281
 * 911 -> 495
 * 743 -> 742
 * 823 -> 822
 *
 *
 * success! Run time with 2500 digits and d=3..999 was 13m 19s :(
 * SequenceSize=982 using denominator 983
 */
public class Problem26 {


    @Test
    @Ignore ("Takes a really goddamn long time")
    public void problem26() throws Exception {
        int bestSequenceSize = 3;
        int bestDenominator = 0;
        String bestDecimalDigits = "";
        int digitCount = 2500;
        int upperBound = 999;

        for (Integer denominator : getInfiniteRepeatingDenominators(3, upperBound)) {
            String decimalDigits = getDecimalDigits(denominator, digitCount);
            int sequenceSizeForThisDenominator = findSequence(decimalDigits);
            if (sequenceSizeForThisDenominator == -1) {
                System.out.println("SEQUENCE WITH NO PATTERN FOUND FOR DENOMINATOR: " + denominator);
            }
            if (bestSequenceSize < sequenceSizeForThisDenominator) {
                bestSequenceSize = sequenceSizeForThisDenominator;
                bestDenominator = denominator;
                bestDecimalDigits = decimalDigits;
            }
            System.out.println(denominator + "\t -> " + sequenceSizeForThisDenominator + " \t\t| best: " + bestDenominator + " -> " + bestSequenceSize
            );
        }
        System.out.println("Best we found: SequenceSize=" + bestSequenceSize + " using denominator " + bestDenominator + " which has sequence: " + bestDecimalDigits);
    }


    @Test
    public void testFindSmallestSequence() throws Exception {
        assertThat(findSequence("123412341234")).isEqualTo(4);
        assertThat(findSequence("123123123123123123123")).isEqualTo(3);
        assertThat(findSequence("121212")).isEqualTo(2);
        assertThat(findSequence("3333333333333333")).isEqualTo(1);

        assertThat(findSequence("1234567")).isEqualTo(-1);
        assertThat(findSequence("25")).isEqualTo(-1);
        assertThat(findSequence("2")).isEqualTo(-1);

        assertThat(findSequence("16666666666666666")).isEqualTo(1); //prefix only
        assertThat(findSequence("12346666666666666666")).isEqualTo(1); //prefix only

        assertThat(findSequence("123456123456123")).isEqualTo(6); //suffix only
        assertThat(findSequence("12345612345612345")).isEqualTo(6); //suffix only

        assertThat(findSequence("1123456123456123")).isEqualTo(6); //prefix and suffix

        assertThat(findSequence("14285714285714285")).isEqualTo(6);

        assertThat(findSequence("769230769230769")).isEqualTo(6); //769230 - suffix only
    }

    @Test
    public void testHasSequence() throws Exception {
        assertThat(hasSequence("123456", 6)).isFalse();
        assertThat(hasSequence("123123", 3)).isTrue();
        assertThat(hasSequence("123123", 6)).isFalse();
        assertThat(hasSequence("123456", 3)).isFalse();
        assertThat(hasSequence("123123123", 3)).isTrue();
        assertThat(hasSequence("123123123", 4)).isFalse();
        assertThat(hasSequence("1234512345", 5)).isTrue();
    }

    @Test
    public void testGetDecimalDigits() throws Exception {
        assertThat(getDecimalDigits(7d, 17)).isEqualTo("14285714285714285");
        assertThat(getDecimalDigits(997d, 17)).isEqualTo("100300902708124");
        assertThat(getDecimalDigits(13d, 17)).isEqualTo("7692307692307692");
        assertThat(getDecimalDigits(7d, 20)).isEqualTo("14285714285714285714");

        assertThat(getDecimalDigits(3d, 20)).isEqualTo("33333333333333333333");
        assertThat(getDecimalDigits(3d, 40)).isEqualTo("3333333333333333333333333333333333333333");
        assertThat(getDecimalDigits(6d, 40)).isEqualTo("1666666666666666666666666666666666666666");
        assertThat(getDecimalDigits(99d, 40)).isEqualTo("101010101010101010101010101010101010101");
        assertThat(getDecimalDigits(998d, 40)).isEqualTo("10020040080160320641282565130260521042");
    }

    @Test
    public void testGetInfiniteRepeatingDenominators() throws Exception {
        assertThat(getInfiniteRepeatingDenominators(1, 20)).containsOnly(3, 6, 7, 9, 11, 12, 13, 14, 15, 17, 18, 19);
        assertThat(getInfiniteRepeatingDenominators(4, 20)).containsOnly(6, 7, 9, 11, 12, 13, 14, 15, 17, 18, 19);
        assertThat(getInfiniteRepeatingDenominators(4, 18)).containsOnly(6, 7, 9, 11, 12, 13, 14, 15, 17, 18);

    }


    /**
     * remove numbers whose denominators are only 2 and 5
     * <p>
     * The theory I need to prove is that if the only prime factors of an entry are 2 and/or 5,
     * the decimal will be non-repeating.
     * http://www.onemathematicalcat.org/algebra_book/online_problems/finite_or_inf_rep.htm
     */
    private Set<Integer> getInfiniteRepeatingDenominators(int lowerBoundInclusive, int upperBoundInclusive) {
        HashSet<Integer> denominators = Sets.newHashSet();
        for (int i = lowerBoundInclusive; i <= upperBoundInclusive; i++) {
            Set<Long> primeFactors = new Primes().getFactors(i);
            primeFactors.removeAll(Lists.newArrayList(2L, 5L));
            if (primeFactors.size() > 0) {
                denominators.add(i);
            }
        }
        return denominators;
    }


    private int findSequence(String value) {
        int halfSizeRoundedDown = (int) Math.floor(value.length() / 2);
        if (halfSizeRoundedDown == 0) return -1;
        for (int size = 1; size <= halfSizeRoundedDown; size++) {
            if (hasSequence(value, size)) return size;
        }
        return -1;
    }

    private boolean hasSequence(String value, int sequenceSize) {
        if (Math.floor(value.length() / 2) < sequenceSize) return false;
        ArrayList<String> attempted = Lists.newArrayList();
        for (int index = 0; index <= Math.floor(value.length() / 2) - sequenceSize; index++) {
            String phrase = value.substring(index, index + sequenceSize);
            if (attempted.contains(phrase)) {
                continue;
            }
            String[] unsequencedDigits = value.split("(" + phrase + ")+");
            if (unsequencedDigits.length == 2 && unsequencedDigits[0].length() < phrase.length() && unsequencedDigits[1].length() < phrase.length()
                    || unsequencedDigits.length == 1 && (unsequencedDigits[0].length() < phrase.length() || phrase.length() == 1)
                    || unsequencedDigits.length == 0) {
                return true;
            }
            attempted.add(phrase);
        }
        return false;
    }

    private String getDecimalDigits(double denominator, int digitLimit) {
        String value = BigDecimal.valueOf(1).divide(BigDecimal.valueOf(denominator), digitLimit, BigDecimal.ROUND_DOWN).toString();
        return StringUtils.stripStart(StringUtils.stripStart(value, "0."), "0");
    }

}

package com.rhejinald.euler.problems;


import com.rhejinald.euler.lib.LongUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static com.rhejinald.euler.lib.Factorial.factorial;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * There are exactly ten ways of selecting three from five, 12345:
 * <p>
 * 123, 124, 125, 134, 135, 145, 234, 235, 245, and 345
 * <p>
 * In combinatorics, we use the notation ^5C_3 (I'll use 5_C_3) = 10
 * <p>
 * In general,
 * n_C_r = n! / (r!*(n-r)!)
 * where: r ≤ n,
 * n! = n*(n-1)*(n-2)...*3*2*1
 * 0! = 1;
 * <p>
 * It is not until n = 23, that a value exceeds one-million: 23_C_10 = 1144066
 * How many, not necessarily distinct, values of n_C_r, for 1 ≤ n ≤ 100, are greater than one-million?
 * <p>
 * =========================================
 * Should be easy enough, but we should also check lower n values just in case; -> Confirming that c=22 & r=11 is < 1M
 * n_C_r for some n, should have a parabolic combinatorial (number of elements), where r=11 when c=22 should be highest
 * <p>
 * But as a starting thought, n=23, c=10 > 1M, is one.
 * n=23, c=11 is probably another
 * n=23, c=12 is likely another
 * <p>
 * Since it's parabolic, r=peak-1 and r=peak+1 are the same, and for any given n, we know the peak. Once we find the first
 * f(r) > 1000000 for that n, we know how many values to add.
 * <p>
 * Check 1: 4075 - Correct! Easy :-) Jan 2nd 2018, time <200ms
 * <p>
 * Congratulations, the answer you gave to problem 53 is correct.
 * You are the 47173rd person to have solved this problem.
 * This problem had a difficulty rating of 5%. The highest difficulty rating you have solved so far is 15%.
 */
@SuppressWarnings("ConstantConditions")
public class Problem53 {
    public static final int ONE_MILLION = 1000000;

    @Test
    public void testProblem53() throws Exception {
        int values = 0;
        for (int n = 23; n <= 100; n++) {
            int lowestR = findLowestCombinatoricAboveOneMillion(n);
            int midpoint = n / 2;
            int count = (midpoint + 1 - lowestR) * 2 - (1 - (n % 2));
            values += count;
        }
        System.out.println("--> " + values);
    }

    private int findLowestCombinatoricAboveOneMillion(int n) {
        for (int r = 0; r < 15; r++) {
            if (combinatoric(n, r).get() > ONE_MILLION) {
                return r;
            }
        }
        return -1;
    }

    @Test
    public void testSampleData() throws Exception {
        assertThat(combinatoric(23, 10)).isEqualTo(1144066);
    }

    @Test
    public void testValidateCombinatoricsAreParabolic() throws Exception {
        int n = 10;
        int midpoint = 5;
        assertThat(combinatoric(n, 0).get()).isLessThan(combinatoric(n, 1).get());
        assertThat(combinatoric(n, 1).get()).isLessThan(combinatoric(n, 2).get());
        assertThat(combinatoric(n, 2).get()).isLessThan(combinatoric(n, 3).get());
        assertThat(combinatoric(n, 3).get()).isLessThan(combinatoric(n, 4).get());
        assertThat(combinatoric(n, 4).get()).isLessThan(combinatoric(n, midpoint).get());
        assertThat(combinatoric(n, midpoint).get()).isGreaterThan(combinatoric(n, 6).get());
        assertThat(combinatoric(n, 6).get()).isGreaterThan(combinatoric(n, 7).get());
        assertThat(combinatoric(n, 7).get()).isGreaterThan(combinatoric(n, 8).get());
        assertThat(combinatoric(n, 8).get()).isGreaterThan(combinatoric(n, 9).get());
        assertThat(combinatoric(n, 9).get()).isGreaterThan(combinatoric(n, 10).get());
    }

    private Optional<Long> combinatoric(int n, int r) {
        BigDecimal numerator = factorial(n);
        BigDecimal divisor = factorial(r).multiply(factorial(n - r));
        return LongUtils.asLong(numerator.divide(divisor, ROUND_HALF_UP));
    }

}

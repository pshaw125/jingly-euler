package com.rhejinald.euler.problems;

import com.google.common.collect.Lists;
import com.rhejinald.euler.lib.Pandigital;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Take the number 192 and multiply it by each of 1, 2, and 3:
 * 192 × 1 = 192
 * 192 × 2 = 384
 * 192 × 3 = 576
 * <p>
 * By concatenating each product we get the 1 to 9 pandigital, 192384576. We will call 192384576 the concatenated
 * product of 192 and (1,2,3)
 * <p>
 * The same can be achieved by starting with 9 and multiplying by 1, 2, 3, 4, and 5, giving the pandigital, 918273645,
 * which is the concatenated product of 9 and (1,2,3,4,5).
 * <p>
 * What is the largest 1 to 9 pandigital 9-digit number that can be formed as the concatenated product of an integer
 * with (1,2, ... , n) where n > 1?
 * ==========================
 * Odd, but looks pretty straight forward; the answer should lie between n=2 and n=9, UNLESS we have a low multiplier
 * such that some of the values are like 2, and 11+ value digits make up the difference. Really, what I need to test,
 * is an ever increasing set size, such that
 * <p>
 * Once the size is above 8 digits, we start evaluating all of them until we hit 10 digits.
 * <p>
 * Can we minimise the number of digits we need to check? multiples of 5, for instance. That will always include 2*5=10,
 * 2*10=20, 2*15=30; owing 2 two being definitely a member, x%5 must be discounted.
 * What about even numbers? 2*2, 4*2, 6*2 etc - all even. Is there a way to get the even digits? Doesn't look like it.
 * So our current multipliers are 1,3,7,9... [no even, no 5*]
 *
 * Attmept 1: 932718654 - Correct! Ran in 21ms
 * > found pandigital for n=2, m=9327 -> Pandigital: 932718654
 *
 * A quick bit of outputting showed that we did indeed not even need to take our n value above 9. Probably knew that,
 * but this confirmed it. Onwards!
 */
public class Problem38 {

    public static final int ONE_BILLION = 1000000000;

    @Test
    public void problem38() throws Exception {
        int highestPandigitalFound = 123456789;
        ArrayList<Integer> currentDigits = Lists.newArrayList(1);
        for (int n = 2; n < 10; n++) {
            currentDigits.add(n);
            int multiplier = 1;
            int currentValue = -1;
            while (currentValue < ONE_BILLION) {
                //validation
                if (multiplier % 5 == 0) {
                    multiplier += 2;
                    continue;
                }

                //execution
                String valueString = "";
                for (Integer digit : currentDigits) {
                    valueString += "" + (digit * multiplier);
                }

                //iteration
                multiplier += 2;
                if (valueString.length() > 9) {
                    currentValue = ONE_BILLION;
                    continue;
                }
                currentValue = Integer.valueOf(valueString);

                //storage
                if (Pandigital.isPandigital(valueString)) {
                    System.out.println("found pandigital for n=" +n+", m="+(multiplier-2) + " -> Pandigital: " + valueString);
                    highestPandigitalFound = Math.max(highestPandigitalFound, currentValue);
                }
            }
        }
        System.out.println("best pandigital found: " + highestPandigitalFound);
    }
}

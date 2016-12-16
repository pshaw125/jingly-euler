package com.rhejinald.euler.problems;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 145 is a curious number, as 1! + 4! + 5! = 1 + 24 + 120 = 145.
 * <p>
 * Find the sum of all numbers which are equal to the sum of the factorial of their digits.
 * <p>
 * Note: as 1! = 1 and 2! = 2 are not sums they are not included.
 * ===============================================================
 * So we're working with digits less than 10, otherwise this breaks. Plus 10!=3628800, which in no way works with this
 * <p>
 * 1!=     1
 * 2!=     2
 * 3!=     6
 * 4!=    24
 * 5!=   120
 * 6!=   720
 * 7!=  5040
 * 8!= 40320
 * 9!=362880
 * <p>
 * Let's try it for numbers up to a million. We can skip over all numbers (mod%(10*n)==0) (has a zero in the value)
 * Easiest way is probably to string both sides and check equality
 * Once shit starts starting with 7,8,9 the likelihood of values drops pretty low; eh, I'll just try brute forcing it,
 * see if I can do it in less than 20s.
 *
 * Answer 1: 145 - (trick question: 145 is the only one) NOPE
 * So I ran it for 5..1000000000 and still only found 145 as an answer, so I'm think I need to build out a tree instead
 * Each node in the tree represents a set of factorials which will be summed, so 1-3-5 would represent 1!+3!+5!=127
 * The sum does not care about the order, so we have to sort the digits of the summed output, That way we can quickly
 * spot if the two match - if so then we can worry about the right order.
 *
 * Because the tree is unordered, each number should only need to spawn children of its own value or greater
 * If the sum at the current node is less than the smallest possible permutation, the parent does not need to spawn any
 * smaller children.
 * For the path: 1-2-2: 1+2+2 < "122", so 1-2 does not need to check 1-2-1
 * but what about 1125, for instance? Argh factorials suck.
 *
 * -------
 * so having a think about this, the biggest number, our upper bound, will be N 9s. Once N 9s exceeds N digits, we
 * mathematically CANNOT make a number that will catch up; owing to adding another 9 being the best way to increase
 * the total. One 9 is 6 digits in length, seven 9s is 7 digits in length, eight 9s is still 7 digits in length; so
 * we can safely upper bound our search at 10,000,000.
 *
 * Okay, it can def be brute forced with the lower bound:
 * Attempt 2: 40730 - Correct! (Dec 16th 2016) Solution runs in under 2 seconds.
 * Where I fucked up: factorial 0 is actually a valid factorial, therefore I was wrong to exclude it.
 * 0!=1 :: If you don't believe your past self, look here https://zero-factorial.com/whatis.html
 *
 */
public class Problem34 {

    private ArrayList<Long> factorials;

    @Before
    public void setup() throws Exception {
        long currentValue = 1;
        factorials = Lists.newArrayListWithExpectedSize(9);
        factorials.add(0, 1L);
        for (int i = 1; i <= 9; i++) {
            currentValue *= i;
            factorials.add(i, currentValue);
        }
    }

    @Test
    public void problem34() throws Exception {
        int upperBound = 2903040; //9 * (9!)
        for (int i = 3; i < upperBound; i++) {
            if(String.valueOf(factorialSum(intToList(i))).equals(String.valueOf(i))){
                System.out.println(i);
            }
        }

    }

    @Test
    public void testFactorialList() throws Exception {
        assertThat(factorials.get(0)).isEqualTo(1);
        assertThat(factorials.get(1)).isEqualTo(1);
        assertThat(factorials.get(2)).isEqualTo(2);
        assertThat(factorials.get(3)).isEqualTo(6);
        assertThat(factorials.get(4)).isEqualTo(24);
        assertThat(factorials.get(5)).isEqualTo(120);
        assertThat(factorials.get(6)).isEqualTo(720);
        assertThat(factorials.get(7)).isEqualTo(5040);
        assertThat(factorials.get(8)).isEqualTo(40320);
        assertThat(factorials.get(9)).isEqualTo(362880);
    }


    private List<Integer> intToList(int i) {
        ArrayList<Integer> integers = Lists.newArrayList();
        int n = i;
        while (n > 0) {
            int d = n / 10;
            integers.add(0, n - d * 10);
            n = d;
        }
        return integers;
    }

    @Test
    public void testGetIntegersAsList() throws Exception {
        assertThat(intToList(123)).isEqualTo(Lists.newArrayList(1, 2, 3));
        assertThat(intToList(4321)).isEqualTo(Lists.newArrayList(4, 3, 2, 1));
        assertThat(intToList(11)).isEqualTo(Lists.newArrayList(1, 1));

    }

    private long factorialSum(Collection<Integer> digits) {
        return digits.stream()
                .map(a -> factorials.get(a))
                .collect(Collectors.summingLong(Long::valueOf));
    }

    @Test
    public void testFactorialSum() throws Exception {
        assertThat(factorialSum(intToList(145))).isEqualTo(145);
        assertThat(factorialSum(intToList(123))).isEqualTo(9);
        assertThat(factorialSum(intToList(333))).isEqualTo(18);
        assertThat(factorialSum(intToList(789))).isEqualTo(408240);
        assertThat(factorialSum(intToList(11111111))).isEqualTo(8);


    }

}

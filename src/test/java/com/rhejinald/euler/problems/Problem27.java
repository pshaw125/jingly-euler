package com.rhejinald.euler.problems;


import com.rhejinald.euler.lib.Primes;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Problem outline : Problem 27
 * Euler discovered the remarkable quadratic formula:
 * <p>
 * n2+n+41
 * It turns out that the formula will produce 40 primes for the consecutive integer values 0≤n≤39.
 * However, when n=40,402+40+41=40(40+1)+41 is divisible by 41, and certainly when
 * n=41,412+41+41 is clearly divisible by 41.
 * <p>
 * The incredible formula n2−79n+1601 was discovered, which produces 80 primes for the consecutive
 * values 0≤n≤79. The product of the coefficients, −79 and 1601, is −126479.
 * <p>
 * Considering quadratics of the form:
 * <p>
 * n2+an+b, where |a|<1000 and |b|≤1000
 * <p>
 * where |n| is the modulus/absolute value of nn
 * e.g. |11|=11 and |−4|=4
 * <p>
 * Find the product of the coefficients, a and b, for the quadratic expression that produces the
 * maximum number of primes for consecutive values of n, starting with n=0.
 * <p>
 * ================================================================================================
 * My notes
 * Rules deduced:
 * -64 < a < 1000
 * 2 ≤ b ≤ 999 (prime values only)
 * <p>
 * Where a < 0:
 * -> n: |a|/2 ≤ n          (start checking n at values abs(a)/2
 * -> b: b > 0.25*(a^2)     (start checking b at 0.25*(a^2) - everything less is immediately invalid; b must still be still prime)
 * <p>
 * Where a > 0:
 * ... not been able to deduce any rules here.
 * <p>
 * Let's try it!
 * <p>
 * Formula: f(n)=n^2+an+b
 * <p>
 * Let k be the lowest n for some (a,b) which is NOT prime
 * So for  n2+n+41     -> k=40 (a= 0,  b=41)
 * and for n2-79n+1601 -> k=80 (a=-79, b=1601) - 41*41 = 1681 = 80^2 -79*80 + 1601
 * <p>
 * We can eliminate a lot of values out the gate - It's a parabolic function, so it's always going
 * to be divergent against n.
 * <p>
 * f(0) > 1, so if a=0, b>0
 * every sequence starts with n=0
 * f(0) = 0^2 + 0*a + b = b ... so 2 ≤ b ≤ 1000
 * Further, for any n=0 case to work, b must be prime
 * <p>
 * Where a is negative, the inflexion point is at n=|a|/2 (irrespective of b)
 * so if all the values for f(n,a,b) leading up to the inflexion point at n=m are primes, m values
 * following the inflexion point will be too... so in essence we can start at m and go from there
 * <p>
 * Where a < 0:
 * -> n: |a|/2 ≤ n          (start checking n at values abs(a)/2
 * -> b: b > 0.25*(a^2)     (start checking b at 0.25*(a^2) - everything less is immediately invalid)
 * b <= 1000; for a=-63, f(31,-63,1000) > 0, for a=-64, b=1000, f(n,a,b) < 0; so a cannot ever go
 * below -63
 * <p>
 * Where a > 0:
 * can we further constrain b?
 * Because we must go from low values of n, maybe we can constrain something there
 * We already deduced that because of n=0, b >=2
 * <p>
 * For even values of n, a becomes even (because a*even = even), even^2 = even; so b must be odd
 * There may be a few primes where b is not odd, but no sequences better than a=0, b=41
 *
 *
 * ==============
 * Congratulations, the answer you gave to problem 27 is correct.

 You are the 60573rd person to have solved this problem.

 Nice work, Rhejinald, you've just advanced to Level 1 .
 84913 members (13.54%) have made it this far.

 You have earned 2 new awards:

 The Journey Begins: Progress to Level 1 by solving twenty-five problems
 Easy Prey: Solve twenty-five of the fifty easiest problems

 -> Interestingly, almost all of the chains greater than 35 in size were for a<0
 -> Best sequence of primes found: Chain=71; a=-61; b=971
 -> Product of coefficients: -59231

 */
public class Problem27 {

    private Primes primes;

    @Before
    public void setUp() throws Exception {
        primes = new Primes();
    }

    @Test
    public void problem27() throws Exception {
        int aLowerBoundExcl = -64;
        int aUpperBoundExcl = 1000;
        Set<Long> bValues = this.primes.getPrimes(1000L);

        int bestSequenceSize = 0;
        int bestAValue = -2000;
        long bestBValue = -2000;

        //a = -63 .. 0;
        for (int a = aLowerBoundExcl + 1; a < 0; a++) {
            for (Long b : bValues) {
                if (b < 0.25 * Math.pow(a, 2)) continue;
                int sequenceSize = sequenceSize(a, b);
                if (sequenceSize >= 30){
                    System.out.println("Sequence greater (or eq) than 40 in size: " +
                            "Chain=" + sequenceSize
                            + "; a=" + a
                            + "; b=" + b);
                }
                if (sequenceSize > bestSequenceSize) {
                    bestSequenceSize = sequenceSize;
                    bestAValue = a;
                    bestBValue = b;
                }
            }
        }

        //a = 0..999
        for (int a = 1; a < aUpperBoundExcl; a++) {
            for (Long b : bValues) {
                if (b < 0.25 * Math.pow(a, 2)) continue;
                int sequenceSize = sequenceSize(a, b);
                if (sequenceSize >= 30){
                    System.out.println("Sequence greater (or eq) than 40 in size: " +
                            "Chain=" + sequenceSize
                            + "; a=" + a
                            + "; b=" + b);
                }
                if (sequenceSize > bestSequenceSize) {
                    bestSequenceSize = sequenceSize;
                    bestAValue = a;
                    bestBValue = b;
                }
            }
        }

        System.out.println("Best sequence of primes found: " +
                "Chain=" + bestSequenceSize
                + "; a=" + bestAValue
                + "; b=" + bestBValue);
        System.out.println("Product of coefficients: " + bestAValue * bestBValue);

    }

    @Test
    public void testSequenceSize() throws Exception {
        assertThat(sequenceSize(1, 41L)).isEqualTo(40);
        assertThat(sequenceSize(-79, 1601L)).isEqualTo(80);
        assertThat(sequenceSize(5, 13L)).isEqualTo(2);
        assertThat(sequenceSize(4, 7L)).isEqualTo(1);
        assertThat(sequenceSize(9, 13L)).isEqualTo(2);
        assertThat(sequenceSize(-16, 13L)).isEqualTo(1);
        assertThat(sequenceSize(-16, 12L)).isEqualTo(0);
    }

    private int sequenceSize(int a, Long b) {
        primes.getPrimes(b);

        int sequenceSize = 0; //n
        while (primes.isPrime(function(sequenceSize, a, b))) {
            sequenceSize++;
        }
        return sequenceSize;
    }

    @Test
    public void testFunction() throws Exception {
        assertThat(function(0, 5, 7)).isEqualTo(7);
        assertThat(function(1, 5, 7)).isEqualTo(13);
        assertThat(function(2, 5, 7)).isEqualTo(21);
        assertThat(function(10, 6, 7)).isEqualTo(167);
        assertThat(function(10, 7, 6)).isEqualTo(176);
    }

    private long function(int n, int a, int b) {
        return (n * n) + (n * a) + b;
    }

    private long function(int n, int a, long b) {
        return (n * n) + (n * a) + b;
    }
}

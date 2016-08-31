package com.rhejinald.euler.problems;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * Starting with the number 1 and moving to the right in a clockwise direction a 5 by 5 spiral is formed as follows:

 21 22 23 24 25
 20  7  8  9 10
 19  6  1  2 11
 18  5  4  3 12
 17 16 15 14 13

 It can be verified that the sum of the numbers on the diagonals is 101.

 What is the sum of the numbers on the diagonals in a 1001 by 1001 spiral formed in the same way?

 ==============
 43 44 45 46 47 48 49
 42 21 22 23 24 25 26
 41 20  7  8  9 10 27
 40 19  6  1  2 11 28
 39 18  5  4  3 12 29
 38 17 16 15 14 13 30
 37 36 35 34 33 32 31

 My notes:

 Just a sum? should be easy. First, the sum should be easy to calculate for the example
 for a 5x5, 2 steps out from center, or (n-1)/2 steps, it's 1 + running_total+2{4 times} +running_total+4{4 times}
 for the 7x7 I added above, it's 1 + running_total+2{4 times} +running_total+4{4 times} + running_total+6{4 times}
 3x3 = 1 step
 5x5= 2 steps
 7x7 = 3 steps
 1001x1001 = 500 steps


 =============
 Solution: 669171001
 You are the 77187th person to have solved this problem.
 */
public class Problem28 {
    @Test
    public void problem28() throws Exception {
        System.out.println(addSpiral(1001));

    }

    @Test
    public void testAssertSpiral() throws Exception {
        assertThat(addSpiral(3)).isEqualTo(25);
        assertThat(addSpiral(5)).isEqualTo(101);
        assertThat(addSpiral(7)).isEqualTo(261);
    }

    private long addSpiral(int spiralDiameter){
        long runningTotal=1L;
        long currentValue=1L;
        int stepSize=2;
        for (int i = 0; i < (spiralDiameter-1)/2; i++) {
            for (int j = 0; j < 4; j++) {
                currentValue+=stepSize;
                runningTotal+=currentValue;
            }
            stepSize+=2;
        }
        return runningTotal;
    }
}

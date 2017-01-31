package com.rhejinald.euler.problems;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * If p is the perimeter of a right angle triangle with integral length sides, {a,b,c}, there are exactly three solutions for p = 120.
 * {20,48,52}, {24,45,51}, {30,40,50}
 * For which value of p ≤ 1000, is the number of solutions maximised?
 * <p>
 * =====================================
 * Notes:
 * known formulae
 * c^2 = a^2 - b^2
 * c ≤ b < a
 *
 * brute force with constrained space went in 383 ms;
 * Attempt 1 - 8 for p=840 - correct!
 */
public class Problem39 {

    @Test
    public void problem39() throws Exception {
        for (double p = 5; p <= 1000; p++) {
            int solutionsFound = 0;
            double cLimit = p / 3;
            for (double c = 1; c < cLimit; c++) {
                double bLimit = cLimit * 2;
                for (double b = c; b < bLimit; b++) {
                    double a = getHypotenuse(b, c);
                    if (a + b + c == p){
                        solutionsFound++;
                    }
                }
            }
            if(solutionsFound > 0) System.out.println(solutionsFound + " for p=" + p);
        }

    }

    @Test
    public void testIsRounded() throws Exception {
        assertThat(isRounded(13.4f)).isFalse();
        assertThat(isRounded(13f)).isTrue();
    }

    private boolean isRounded(double v) {
        return v == Math.floor(v);
    }

    @Test
    public void testGetHypotenuse() throws Exception {
        assertThat(getHypotenuse(10d, 15d)).isEqualTo(Math.sqrt(325d));
        assertThat(getHypotenuse(20d, 48d)).isEqualTo(52d);
        assertThat(getHypotenuse(24d, 45d)).isEqualTo(51d);
        assertThat(getHypotenuse(30d, 40d)).isEqualTo(50d);
    }

    private double getHypotenuse(double l1, double l2) {
        return Math.sqrt((l1 * l1) + (l2 * l2));
    }
}

package com.rhejinald.euler.lib;

import org.junit.Test;

import static com.rhejinald.euler.lib.QuadraticEquation.quadraticEquation;
import static org.assertj.core.api.Assertions.assertThat;

public class GeometryNumbers {

    /**
     * Pn=n(3n−1)/2 -> 1, 5, 12, 22, 35, ...
     */
    public static long getPentagonalNumber(int n) {
        return n * (3 * n - 1) / 2;
    }

    /**
     * Tn=n(n+1)/2 -> 1, 3, 6, 10, 15, ...
     */
    public static long getTriangleNumber(int n) {
        return n * (n + 1) / 2;
    }

    /**
     * Hn=n(2n−1) -> 1, 6, 15, 28, 45, ...
     */
    public static long getHexagonalNumber(int n) {
        return n * ((2 * n) - 1);
    }

    public static boolean isPentagonalNumber(long n) {
        if(n > Long.MAX_VALUE /2) throw new IllegalArgumentException("n is too great!");
        long c = -n * 2;
        double supposedPentagonalNumber = quadraticEquation(3, 1, c).getNegativeArc() * -1;
        return supposedPentagonalNumber == Math.floor(supposedPentagonalNumber);
    }

    public static boolean isHexagonalNumber(long n) {
        long c = -n;
        double supposedHexagonalNumber = quadraticEquation(2, 1, c).getNegativeArc() * -1;
        return supposedHexagonalNumber == Math.floor(supposedHexagonalNumber);
    }

    public static boolean isTriangleNumber(long value) {
        double result = getNForTriangleNumber(value);
        return Math.floor(result) == result;
    }

    public static double getNForTriangleNumber(long value) {
        long c = value * -2;
        return quadraticEquation(1, 1, c).getPositiveArc();
    }

    @Test
    public void testIsHexagonalNumber() throws Exception {
        assertThat(isHexagonalNumber(1)).isTrue();
        for (int i = 2; i <= 5; i++) {
            assertThat(isHexagonalNumber(i)).isFalse();
        }
        assertThat(isHexagonalNumber(6)).isTrue();
        for (int i = 7; i <= 14; i++) {
            assertThat(isHexagonalNumber(i)).isFalse();
        }
        assertThat(isHexagonalNumber(15)).isTrue();
        for (int i = 16; i <= 27; i++) {
            assertThat(isHexagonalNumber(i)).isFalse();
        }
        assertThat(isHexagonalNumber(28)).isTrue();
        assertThat(isHexagonalNumber(45)).isTrue();

    }

    @Test
    public void testGetHexagonalNumber() throws Exception {
        assertThat(getHexagonalNumber(1)).isEqualTo(1);
        assertThat(getHexagonalNumber(2)).isEqualTo(6);
        assertThat(getHexagonalNumber(3)).isEqualTo(15);
        assertThat(getHexagonalNumber(4)).isEqualTo(28);
        assertThat(getHexagonalNumber(5)).isEqualTo(45);
    }

    @Test
    public void testIsPentagonalNumber() throws Exception {
        assertThat(isPentagonalNumber(1)).isTrue();
        assertThat(isPentagonalNumber(5)).isTrue();
        for (int i = 6; i <= 11; i++) {
            assertThat(isPentagonalNumber(i)).isFalse();
        }
        assertThat(isPentagonalNumber(12)).isTrue();
        for (int i = 13; i <= 21; i++) {
            assertThat(isPentagonalNumber(i)).isFalse();
        }
        assertThat(isPentagonalNumber(22)).isTrue();
        assertThat(isPentagonalNumber(35)).isTrue();
        assertThat(isPentagonalNumber(51)).isTrue();
        assertThat(isPentagonalNumber(70)).isTrue();
        assertThat(isPentagonalNumber(92)).isTrue();
        assertThat(isPentagonalNumber(117)).isTrue();
    }

    @Test
    public void testGetPentagonalNumber() throws Exception {
        assertThat(getPentagonalNumber(1)).isEqualTo(1);
        assertThat(getPentagonalNumber(2)).isEqualTo(5);
        assertThat(getPentagonalNumber(3)).isEqualTo(12);
        assertThat(getPentagonalNumber(4)).isEqualTo(22);
        assertThat(getPentagonalNumber(5)).isEqualTo(35);
        assertThat(getPentagonalNumber(6)).isEqualTo(51);
        assertThat(getPentagonalNumber(7)).isEqualTo(70);
        assertThat(getPentagonalNumber(8)).isEqualTo(92);
        assertThat(getPentagonalNumber(9)).isEqualTo(117);
    }

    @Test
    public void testIsTriangleNumber() throws Exception {
        assertThat(isTriangleNumber(1)).isTrue();
        assertThat(isTriangleNumber(3)).isTrue();
        assertThat(isTriangleNumber(6)).isTrue();
        assertThat(isTriangleNumber(10)).isTrue();
        assertThat(isTriangleNumber(15)).isTrue();
        assertThat(isTriangleNumber(21)).isTrue();
        assertThat(isTriangleNumber(13)).isFalse();
        assertThat(isTriangleNumber(16)).isFalse();
        assertThat(isTriangleNumber(22)).isFalse();
        assertThat(isTriangleNumber(23)).isFalse();
        assertThat(isTriangleNumber(24)).isFalse();
        assertThat(isTriangleNumber(25)).isFalse();
        assertThat(isTriangleNumber(26)).isFalse();
        assertThat(isTriangleNumber(27)).isFalse();
        assertThat(isTriangleNumber(28)).isTrue();
    }

}

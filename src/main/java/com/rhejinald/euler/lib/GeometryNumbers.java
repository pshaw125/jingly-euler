package com.rhejinald.euler.lib;

import static com.rhejinald.euler.lib.QuadraticEquation.quadraticEquation;

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
    public static long  getHexagonalNumber(int n) {
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

    private static double getNForTriangleNumber(long value) {
        long c = value * -2;
        return quadraticEquation(1, 1, c).getPositiveArc();
    }
}

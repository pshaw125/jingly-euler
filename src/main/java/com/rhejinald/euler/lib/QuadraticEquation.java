package com.rhejinald.euler.lib;

public class QuadraticEquation {

    public static QuadraticEquationResult quadraticEquation(long a, long b, long c) {
        if (b > 0 && b * b < 0) throw new IllegalArgumentException("b is not valid - too great, causing overflow");
        if (a > 0 && a * 4 < 0) throw new IllegalArgumentException("a is not valid - too great, causing overflow");
        if (c > 0 && c * 4 < 0) throw new IllegalArgumentException("c is not valid - too great, causing overflow");
        if (a > 0 && c > 0 && a * c * 4 < 0) throw new IllegalArgumentException("c is not valid - too great, causing overflow");
        double sqrt = Math.sqrt((b * b) - (4 * a * c));
        double vPos = (-b + sqrt) / (2 * a);
        double vNeg = (-b - sqrt) / (2 * a);
        return new QuadraticEquationResult(vPos, vNeg);


    }

    public static class QuadraticEquationResult {
        private final double vPos;
        private final double vNeg;

        private QuadraticEquationResult(double vPos, double vNeg) {
            this.vPos = vPos;
            this.vNeg = vNeg;
        }

        public double getPositiveArc() {
            return vPos;
        }

        public double getNegativeArc() {
            return vNeg;
        }
    }


}

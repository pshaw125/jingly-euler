package com.rhejinald.euler.lib;

public class QuadraticEquation {

    public static class QuadraticEquationResult{
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

    public static QuadraticEquationResult quadraticEquation(int a, int b, int c) {
        double sqrt = Math.sqrt((b * b) - (4 * a * c));
        double vPos = (-b + sqrt) / (2 * a);
        double vNeg = (-b - sqrt) / (2 * a);
        return new QuadraticEquationResult(vPos, vNeg);
    }


}

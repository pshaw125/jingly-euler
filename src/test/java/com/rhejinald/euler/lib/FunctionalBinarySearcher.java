package com.rhejinald.euler.lib;

import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionalBinarySearcher {

    /**
     * The lower and upper bounds are inclusive; so as we narrow in we want to have our upper and lower indicate what
     * we've checked. Because our value could actually be lower bound (always true) or upper bound (always false),
     * we should indicate that we've "checked" the upper bound and lower bound.
     *
     * If the function apply is true, we lean up. If the function apply is false, we lean down.
     *
     * The assumption is that for some f(n), n<x is true, n>x is false; and we want to find x if it exists
     * within the bounds.
     * @return
     */
    public static int search(int lowerBound, int upperBound, Function<Integer, Boolean> function) {
        int currentUpperBound = upperBound+1;
        int currentLowerBound = lowerBound-1;
        int currentPivot = Math.round((upperBound - lowerBound) / 2f);

        while(currentPivot > currentLowerBound && currentPivot < currentUpperBound ) {
            System.out.println(currentLowerBound +":"+currentPivot+":"+currentUpperBound);
            if (function.apply(currentPivot)) {
                System.out.println("pivot of "+currentPivot+" applied: true");
                currentLowerBound = currentPivot;
            } else {
                System.out.println("pivot of "+currentPivot+" applied: true");
                currentUpperBound = currentPivot;
            }
            currentPivot = Math.round((currentUpperBound - currentLowerBound) / 2f)+currentLowerBound;
            System.out.println(currentLowerBound +":"+currentPivot+":"+currentUpperBound);
            System.out.println("-------------------------------");
        }
        return Math.max(currentLowerBound, lowerBound); //cover case if the lower bound didn't move, because we modified it
    }

    @Test
    public void testSearchOfArbitraryFunction() throws Exception {
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> val < 7)).isEqualTo(6);
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> val <= 7)).isEqualTo(7);
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> val <= 5)).isEqualTo(5);
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> true)).isEqualTo(10);
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> false)).isEqualTo(1);
        assertThat(FunctionalBinarySearcher.search(1, 10, val -> val == 3)).isEqualTo(3);

    }
}

package com.rhejinald.euler.problems;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * In England the currency is made up of pound, £, and pence, p, and there are eight coins in general circulation:
 * <p>
 * 1p, 2p, 5p, 10p, 20p, 50p, £1 (100p) and £2 (200p).
 * It is possible to make £2 in the following way:
 * <p>
 * 1×£1 + 1×50p + 2×20p + 1×5p + 1×2p + 3×1p
 * How many different ways can £2 be made using any number of coins?
 * <p>
 * -------------------------
 * My notes:
 * Factors of 200
 * 200 -> [1, 2, 4, 5, 8, 10, 20, 25, 40, 50, 100, 200]
 * <p>
 * All the ways to make 10:
 * 10
 * 5,5
 * 5,2,2,1
 * 5,2,1,1,1
 * 5,1,1,1,1,1
 * 2,2,2,2,2
 * 2,2,2,2,1,1
 * 2,2,2,1,1,1,1
 * 2,2,1,1,1,1,1,1
 * 2,1,1,1,1,1,1,1,1
 * 1,1,1,1,1,1,1,1,1,1
 * <p>
 * Using coins:
 * 5
 * |...,..,
 * 2   2  1
 * |.,
 * 1 1
 * <p>
 * This tree thing might be useful for "coin-factors"
 * But how to reliably build the full derivative tree of possible outcomes?
 * <p>
 * Each combination can be written as the sum:
 * a*200p + b*100p + c*50p + d*20p + e*10p + f*5p + g*2p + h*1p
 * <p>
 * We might be able to get away with some iterate over every a..f combination where f(a..f) < 200
 * and pad with ones and twos.
 * So it would look like:
 * 1f; 2f; 3f; 4f; 5f .. 40f (200p)
 * ... nah still ugly efficiency-wise. Maybe still easier to model as a tree
 * <p>
 * Because we're adding, factors may not be the best way to think about it
 * For our base denominators, 1,2,5 (everything bigger is in nice multiples):
 * 10 can be made in the ways above... but we can learn this best with a tree I think
 * <p>
 * Everything can be built using the following relationships. 6 is special to facilitate the multiples of 2
 * 1 -> 1                           // 1
 * 2 -> {1,1}, 2                    // 2
 * 5 -> {2,2,1}, 5                  // 4 -> 5, 2+2+1, 2+1+1+1, 1+1+1+1+1
 * 10 -> {2,2,2,2,2}, {5,5}, 10     // 1 + 1 + 2*5-tree + 1 = 11
 * 20 -> {10,10}, 20                // 2 + 2*tree = 24
 * 50 -> {20,20,10}, 50             // 2 + 6*tree = 146
 * 100 -> {50,50}, 100              // 2 + 2*tree = 294
 * 200 -> {100,100}, 200            // 2 + 2*tree = 590
 * <p>
 * 162 -> factors: 1, 2, 3, 6, 9, 18, 27, 54, 81, 162
 * -> For a more complex example like that, we need to see if we can build it using the smaller constituent parts
 * 162 = 100+50+10+2 - that's our first combo. Then for each, we need to
 */
@Ignore("It doesn't work yet :(")
public class Problem31Incomplete {


}

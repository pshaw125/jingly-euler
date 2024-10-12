package com.rhejinald.euler.problems;

import static org.assertj.core.api.Assertions.assertThat;

import com.rhejinald.euler.lib.MathExt;
import com.rhejinald.euler.lib.Primes;
import java.util.function.BiFunction;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Starting with 1 and spiralling anticlockwise in the following way, a square spiral with side
 * length 7 is formed.
 * <p>
 * <p>
 * 37 36 35 34 33 32 31
 * 38 17 16 15 14 13 30
 * 39 18  5  4  3 12 29
 * 40 19  6  1  2 11 28
 * 41 20  7  8  9 10 27
 * 42 21 22 23 24 25 26
 * 43 44 45 46 47 48 49
 * <p>
 * It is interesting to note that the odd squares lie along the bottom right diagonal, but what is
 * more interesting is that 8 out of the 13 numbers lying along both diagonals are prime; that is, a
 * ratio of 8/13 ≈ 62%.
 * <p>
 * If one complete new layer is wrapped around the spiral above, a square spiral with side length 9
 * will be formed. If this process is continued, what is the side length of the square spiral for
 * which the ratio of primes along both diagonals first falls below 10%?
 * <p>
 * ==-==-==-==-==-==-==-==-== Attempt 1 - 233 digits on diagonals; 23 primes on diagonals; With
 * Ratio: 0.09871244635193133; Side length:117 117: Wrong :) - I stopped adding primes after the
 * initial gen
 * <p>
 * So doing a bit more work and this one is a doozy. I keep OOM-ing the test JVM; The numbers are
 * into the hundreds of millions with 5000+ on each diagonal from the center outwards. The primes
 * implementation has had some optimizations and still peaks somewhere above 300,000,000 before
 * OOM-ing.
 * =-=-=-=-=-=-=-=-=-=-=-=-=
 * Attempt 2; my hunch is last time I screwed something up. This time I've got calculations for just
 * corners being incremented in place.
 *
 * Upping the memory to 11GB let me run up to 800M primes
 *
 * Moving to ring 13301; prime%=10.0% side length = 26601 (5318 primes vs 47882 non-primes.)	(corners: 707560001 | 707533401 | 707586601 | 707613201)
 * Moving to ring 13306; prime%=9.99% side length = 26611 (5319 primes vs 47901 non-primes.)	(corners: 708092101 | 708065491 | 708118711 | 708145321)
 * but 26611 was incorrect. Found my percentage value was rounding, meaning I am coming in too high as 9.555555555% still rounds to 10%
 *
 * Ok rerunning with it flooring instead of rounding in the % calculation
 * Moving to ring 13122; prime%=9.99% side length = 26243 (5248 primes vs 47236 non-primes.)	(corners: 688642565 | 688616323 | 688668807 | 688695049)
 * 5248/(5248+47236)
 * 26243 still incorrect :(
 * 26241 was correct!, must have been in the wayyyyyy deep decimal places 0.09999999999* -> 0.1
 *
 * Line for that item:
 * Moving to ring 13121; prime%=10.0% side length = 26241 (5248 primes vs 47232 non-primes.)	(corners: 688537601 | 688511361 | 688563841 | 688590081)
 *
 * So I guess my first attempt was on the money that without a test like Miller-Rabin for primality the memory will remain an issue.
 */
public class Problem58 {
  private int topRight;
  private int topLefty;
  private int btmLefty;
  private int btmRight;
  private Primes primes;
  private int primesFound;
  private int nonPrimesFound;

  @Before
  public void setup() {
    topRight = 1;
    topLefty = 1;
    btmLefty = 1;
    btmRight = 1;
    primesFound = 0;
    nonPrimesFound = 0;
    primes = new Primes();
  }

  private final BiFunction<Integer, Integer, Integer> fnIncrementTopLeft =
      (value, ring) -> value + (4 * (ring * 2 - 1));
  private final BiFunction<Integer, Integer, Integer> fnIncrementTopRight =
      (value, ring) -> fnIncrementTopLeft.apply(value, ring) - 2;
  private final BiFunction<Integer, Integer, Integer> fnIncrementBottomLeft =
      (value, ring) -> fnIncrementTopLeft.apply(value, ring) + 2;
  private final BiFunction<Integer, Integer, Integer> fnIncrementBottomRight =
      (value, ring) -> fnIncrementTopLeft.apply(value, ring) + 4;

  /**
   * Primes needed warmed up to 800M; required upping heap size to 11GB lol
   * @throws Exception
   */
  @Test
  @Ignore //requires 11 GB Memory
  public void testProblem58() throws Exception {
    int cap = 8*100*1000*1000;
    primes.warmPrimes(cap);
    for (int ringCount = 1; btmRight<cap; ringCount++) {
      topRight = incrementAndCatalog(topRight, ringCount, fnIncrementTopRight);
      topLefty = incrementAndCatalog(topLefty, ringCount, fnIncrementTopLeft);
      btmLefty = incrementAndCatalog(btmLefty, ringCount, fnIncrementBottomLeft);
      btmRight = incrementAndCatalog(btmRight, ringCount, fnIncrementBottomRight);
      if (ringCount % 100 == 0 || ringCount>13110 || MathExt.flooredPercentToTwoDecimalPlaces(primesFound, nonPrimesFound) < 0.1f) {
        System.out.println("Moving to ring " + (ringCount + 1) + "; prime%="
            + MathExt.flooredPercentToTwoDecimalPlaces(primesFound, nonPrimesFound)*100 + "%"
            + " side length = " + sideLength(topLefty, topRight)
            + " (" + primesFound + " primes vs " + nonPrimesFound + " non-primes.)\t"
            + "(corners: " + topLefty + " | " + topRight + " | " + btmLefty + " | " + btmRight + ")"
        );
      }
      if(MathExt.flooredPercentToTwoDecimalPlaces(primesFound, nonPrimesFound) < 0.1f){
        break;
      }
    }
  }

  @Test
  public void testExample() {
    // 1 is not prime, and ring 1 is a special case
    nonPrimesFound = 1;
    primesFound = 0;
    for (int ringCount = 1; ringCount < 4; ringCount++) {
      topRight = incrementAndCatalog(topRight, ringCount, fnIncrementTopRight);
      topLefty = incrementAndCatalog(topLefty, ringCount, fnIncrementTopLeft);
      btmLefty = incrementAndCatalog(btmLefty, ringCount, fnIncrementBottomLeft);
      btmRight = incrementAndCatalog(btmRight, ringCount, fnIncrementBottomRight);
    }

    assertThat(topRight).isEqualTo(31);
    assertThat(topLefty).isEqualTo(37);
    assertThat(btmLefty).isEqualTo(43);
    assertThat(btmRight).isEqualTo(49);
    assertThat(primesFound).isEqualTo(8);
    assertThat(nonPrimesFound).isEqualTo(5);
    assertThat(sideLength(topLefty, topRight)).isEqualTo(7);
  }

  private int sideLength(int topLefty, int topRight) {
    return topLefty - topRight + 1;
  }

  private int incrementAndCatalog(int value, int ring, BiFunction<Integer, Integer, Integer> incrementFunction) {
    int newValue = incrementFunction.apply(value, ring);
    if(primes.isPrime(newValue)){
      primesFound++;
    } else {
      nonPrimesFound++;
    }
    return newValue;
  }

  @Test
  public void testIncrementTopLeft() {
    assertThat(fnIncrementTopLeft.apply(1, 1)).isEqualTo(5);
    assertThat(fnIncrementTopLeft.apply(5, 2)).isEqualTo(17);
    assertThat(fnIncrementTopLeft.apply(17, 3)).isEqualTo(37);
    assertThat(fnIncrementTopLeft.apply(37, 4)).isEqualTo(65);
  }

  @Test
  public void testIncrementTopRight() {
    assertThat(fnIncrementTopRight.apply(1, 1)).isEqualTo(3);
    assertThat(fnIncrementTopRight.apply(3, 2)).isEqualTo(13);
    assertThat(fnIncrementTopRight.apply(13, 3)).isEqualTo(31);
    assertThat(fnIncrementTopRight.apply(31, 4)).isEqualTo(57);
  }

  @Test
  public void testIncrementBottomLeft() {
    assertThat(fnIncrementBottomLeft.apply(1, 1)).isEqualTo(7);
    assertThat(fnIncrementBottomLeft.apply(7, 2)).isEqualTo(21);
    assertThat(fnIncrementBottomLeft.apply(21, 3)).isEqualTo(43);
    assertThat(fnIncrementBottomLeft.apply(43, 4)).isEqualTo(73);
  }

  @Test
  public void testIncrementBottomRight() {
    assertThat(fnIncrementBottomRight.apply(1, 1)).isEqualTo(9);
    assertThat(fnIncrementBottomRight.apply(9, 2)).isEqualTo(25);
    assertThat(fnIncrementBottomRight.apply(25, 3)).isEqualTo(49);
    assertThat(fnIncrementBottomRight.apply(49, 4)).isEqualTo(81);
  }

  @Test
  public void testSideLength() {
    assertThat(sideLength(37, 31)).isEqualTo(7);
    assertThat(sideLength(17, 13)).isEqualTo(5);
    assertThat(sideLength(5, 3)).isEqualTo(3);
  }
}

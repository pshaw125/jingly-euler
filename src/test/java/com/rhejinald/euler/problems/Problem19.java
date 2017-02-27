package com.rhejinald.euler.problems;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Problem19 {
    int[] nonleap = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int[] leap =    {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    @Test
    public void testProblem19() throws Exception {
        //day 0 = 1 jan 1901 = Tuesday
        //if day%7==5 then it's a sunday!
        int currentDay = 0;
        int firstSundayCounter = 0;
        int monthsChecked = 0;
        for (int year = 1901; year < 2001; year++) {
            int[] monthsThisYear = isLeap(year) ? leap : nonleap;
            for (int daysThisMonth : monthsThisYear) {
                if (currentDay % 7 == 5) {
                    firstSundayCounter++;
                }
                monthsChecked++;
                currentDay += daysThisMonth;
            }
        }

        assertThat(monthsChecked).isEqualTo(1200);
        System.out.println("Count of Sundays between 1/1/1901 and 12/31/2000 is: "+firstSundayCounter);
    }

    @Test
    public void testIsLeap() throws Exception {
        assertThat(isLeap(1)).isFalse();
        assertThat(isLeap(4)).isTrue();
        assertThat(isLeap(5)).isFalse();
        assertThat(isLeap(100)).isFalse();
        assertThat(isLeap(200)).isFalse();
        assertThat(isLeap(300)).isFalse();
        assertThat(isLeap(400)).isTrue();
        assertThat(isLeap(500)).isFalse();
        assertThat(isLeap(1900)).isFalse();
    }

    private boolean isLeap(int year) {
        return year % 4 == 0 && (year % 100 > 0 || year % 400 == 0);
    }
}

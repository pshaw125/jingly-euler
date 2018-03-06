package com.rhejinald.euler.lib;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GeometryNumbersTest {

    @Test
    public void testIsHexagonalNumber() throws Exception {
        assertThat(GeometryNumbers.isHexagonalNumber(1)).isTrue();
        for (int i = 2; i <= 5; i++) {
            assertThat(GeometryNumbers.isHexagonalNumber(i)).isFalse();
        }
        assertThat(GeometryNumbers.isHexagonalNumber(6)).isTrue();
        for (int i = 7; i <= 14; i++) {
            assertThat(GeometryNumbers.isHexagonalNumber(i)).isFalse();
        }
        assertThat(GeometryNumbers.isHexagonalNumber(15)).isTrue();
        for (int i = 16; i <= 27; i++) {
            assertThat(GeometryNumbers.isHexagonalNumber(i)).isFalse();
        }
        assertThat(GeometryNumbers.isHexagonalNumber(28)).isTrue();
        assertThat(GeometryNumbers.isHexagonalNumber(45)).isTrue();

    }

    @Test
    public void testGetHexagonalNumber() throws Exception {
        assertThat(GeometryNumbers.getHexagonalNumber(1)).isEqualTo(1);
        assertThat(GeometryNumbers.getHexagonalNumber(2)).isEqualTo(6);
        assertThat(GeometryNumbers.getHexagonalNumber(3)).isEqualTo(15);
        assertThat(GeometryNumbers.getHexagonalNumber(4)).isEqualTo(28);
        assertThat(GeometryNumbers.getHexagonalNumber(5)).isEqualTo(45);
    }

    @Test
    public void testIsPentagonalNumber() throws Exception {
        assertThat(GeometryNumbers.isPentagonalNumber(1)).isTrue();
        assertThat(GeometryNumbers.isPentagonalNumber(5)).isTrue();
        for (int i = 6; i <= 11; i++) {
            assertThat(GeometryNumbers.isPentagonalNumber(i)).isFalse();
        }
        assertThat(GeometryNumbers.isPentagonalNumber(12)).isTrue();
        for (int i = 13; i <= 21; i++) {
            assertThat(GeometryNumbers.isPentagonalNumber(i)).isFalse();
        }
        assertThat(GeometryNumbers.isPentagonalNumber(22)).isTrue();
        assertThat(GeometryNumbers.isPentagonalNumber(35)).isTrue();
        assertThat(GeometryNumbers.isPentagonalNumber(51)).isTrue();
        assertThat(GeometryNumbers.isPentagonalNumber(70)).isTrue();
        assertThat(GeometryNumbers.isPentagonalNumber(92)).isTrue();
        assertThat(GeometryNumbers.isPentagonalNumber(117)).isTrue();
    }

    @Test
    public void testGetPentagonalNumber() throws Exception {
        assertThat(GeometryNumbers.getPentagonalNumber(1)).isEqualTo(1);
        assertThat(GeometryNumbers.getPentagonalNumber(2)).isEqualTo(5);
        assertThat(GeometryNumbers.getPentagonalNumber(3)).isEqualTo(12);
        assertThat(GeometryNumbers.getPentagonalNumber(4)).isEqualTo(22);
        assertThat(GeometryNumbers.getPentagonalNumber(5)).isEqualTo(35);
        assertThat(GeometryNumbers.getPentagonalNumber(6)).isEqualTo(51);
        assertThat(GeometryNumbers.getPentagonalNumber(7)).isEqualTo(70);
        assertThat(GeometryNumbers.getPentagonalNumber(8)).isEqualTo(92);
        assertThat(GeometryNumbers.getPentagonalNumber(9)).isEqualTo(117);
    }

    @Test
    public void testIsTriangleNumber() throws Exception {
        assertThat(GeometryNumbers.isTriangleNumber(1)).isTrue();
        assertThat(GeometryNumbers.isTriangleNumber(3)).isTrue();
        assertThat(GeometryNumbers.isTriangleNumber(6)).isTrue();
        assertThat(GeometryNumbers.isTriangleNumber(10)).isTrue();
        assertThat(GeometryNumbers.isTriangleNumber(15)).isTrue();
        assertThat(GeometryNumbers.isTriangleNumber(21)).isTrue();
        assertThat(GeometryNumbers.isTriangleNumber(13)).isFalse();
        assertThat(GeometryNumbers.isTriangleNumber(16)).isFalse();
        assertThat(GeometryNumbers.isTriangleNumber(22)).isFalse();
        assertThat(GeometryNumbers.isTriangleNumber(23)).isFalse();
        assertThat(GeometryNumbers.isTriangleNumber(24)).isFalse();
        assertThat(GeometryNumbers.isTriangleNumber(25)).isFalse();
        assertThat(GeometryNumbers.isTriangleNumber(26)).isFalse();
        assertThat(GeometryNumbers.isTriangleNumber(27)).isFalse();
        assertThat(GeometryNumbers.isTriangleNumber(28)).isTrue();
    }

}

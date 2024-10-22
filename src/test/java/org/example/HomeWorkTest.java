package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeWorkTest {

    HomeWork homeWork = new HomeWork();

    @Test
    void check() {
        assertEquals("2 3 4 * + 5 - 7 6 * 3 / + 2 3 2 pow * - 5 2 - 2 * +", HomeWork.translate("2 + 3 * 4 - 5 + 7 * 6 / 3 - 2 * pow ( 3 , 2 ) + ( 5 - 2 ) * 2"));
    }

    @Test
    public void should_calculateSinSuccess() {
        assertEquals(11.0698d, homeWork.calculate("3 * 4 + sin ( 130 )"), 0.0001);
        assertEquals(11.0698d, homeWork.calculate("sin ( 130 ) + 3 * 4"), 0.0001);
    }

    @Test
    public void should_calculateCosSuccess() {
        assertEquals(11.6327d, homeWork.calculate("3 * 4 + cos ( 130 )"), 0.0001);
        assertEquals(11.6327d, homeWork.calculate("cos ( 130 ) + 3 * 4"), 0.0001);
    }

    @Test
    public void should_calculateSqrSuccess() {
        assertEquals(14d, homeWork.calculate("3 * 4 + sqr ( 4 )"), 0.0001);
        assertEquals(14d, homeWork.calculate("sqr ( 4 ) + 3 * 4"), 0.0001);
    }

    @Test
    public void should_calculatePowSuccess() {
        assertEquals(16d, homeWork.calculate("3 * 4 + pow ( 2 , 2 )"), 0.0001);
        assertEquals(16d, homeWork.calculate("pow ( 2 , 2 ) + 3 * 4"), 0.0001);
    }
}
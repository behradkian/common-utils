package ir.radman.common.util.number;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/02
 */
class NumberUtilityTest {

    @Test
    @DisplayName("isInteger - valid and invalid cases")
    void testIsInteger() {
        assertTrue(NumberUtility.isInteger("123"));
        assertTrue(NumberUtility.isInteger("-456"));
        assertFalse(NumberUtility.isInteger("123.4"));
        assertFalse(NumberUtility.isInteger("x123"));
        assertFalse(NumberUtility.isInteger(null));
    }

    @Test
    @DisplayName("safeParseInt - parses correctly or returns default")
    void testSafeParseInt() {
        assertEquals(42, NumberUtility.safeParseInt("42", 0));
        assertEquals(-10, NumberUtility.safeParseInt("-10", 99));
        assertEquals(99, NumberUtility.safeParseInt("abc", 99));
        assertEquals(99, NumberUtility.safeParseInt(null, 99));
    }

    @Test
    @DisplayName("safeParseDouble - parses correctly or returns default")
    void testSafeParseDouble() {
        assertEquals(3.14, NumberUtility.safeParseDouble("3.14", 0.0));
        assertEquals(-2.5, NumberUtility.safeParseDouble("-2.5", 0.0));
        assertEquals(10.0, NumberUtility.safeParseDouble("notANumber", 10.0));
        assertEquals(10.0, NumberUtility.safeParseDouble(null, 10.0));
    }

    @Test
    @DisplayName("isInRange - check values inside and outside range")
    void testIsInRange() {
        assertTrue(NumberUtility.isInRange(5, 0, 10));
        assertTrue(NumberUtility.isInRange(0, 0, 10));
        assertTrue(NumberUtility.isInRange(10, 0, 10));
        assertFalse(NumberUtility.isInRange(-1, 0, 10));
        assertFalse(NumberUtility.isInRange(11, 0, 10));
    }

    @Test
    @DisplayName("average - computes average of list")
    void testAverage() {
        List<Double> list = Arrays.asList(2.0, 4.0, 6.0);
        assertEquals(4.0, NumberUtility.average(list));

        assertEquals(0, NumberUtility.average(Collections.emptyList()));
        assertEquals(0, NumberUtility.average(null));
    }

    @Test
    @DisplayName("max - returns correct maximum")
    void testMax() {
        List<Double> list = Arrays.asList(1.0, 3.0, 2.0);
        assertEquals(3.0, NumberUtility.max(list));
        assertNull(NumberUtility.max(Collections.emptyList()));
        assertNull(NumberUtility.max(null));
    }

    @Test
    @DisplayName("min - returns correct minimum")
    void testMin() {
        List<Double> list = Arrays.asList(1.0, 3.0, 2.0);
        assertEquals(1.0, NumberUtility.min(list));
        assertNull(NumberUtility.min(Collections.emptyList()));
        assertNull(NumberUtility.min(null));
    }

    @Test
    @DisplayName("round - rounds to given decimal places")
    void testRound() {
        assertEquals(3.14, NumberUtility.round(3.14159, 2));
        assertEquals(3.142, NumberUtility.round(3.14159, 3));
    }

    @Test
    @DisplayName("round - throws exception for negative scale")
    void testRoundNegativeScale() {
        assertThrows(IllegalArgumentException.class, () -> NumberUtility.round(3.14, -1));
    }

    @Test
    @DisplayName("formatWithComma - adds thousand separators")
    void testFormatWithComma() {
        String formatted = NumberUtility.formatWithComma(9909909009085000989890012d);
        assertTrue(formatted.contains(","));
    }

    @Test
    @DisplayName("isEven & isOdd - works correctly")
    void testEvenOdd() {
        assertTrue(NumberUtility.isEven(4));
        assertFalse(NumberUtility.isEven(5));
        assertTrue(NumberUtility.isOdd(5));
        assertFalse(NumberUtility.isOdd(4));
    }

    @Test
    @DisplayName("isPositive & isNegative")
    void testPositiveNegative() {
        assertTrue(NumberUtility.isPositive(5.1));
        assertFalse(NumberUtility.isPositive(0));
        assertTrue(NumberUtility.isNegative(-2.5));
        assertFalse(NumberUtility.isNegative(0));
    }

    @Test
    @DisplayName("randomInt - generates number in range")
    void testRandomInt() {
        int random = NumberUtility.randomInt(1, 5);
        assertTrue(random >= 1 && random <= 5);
    }

    @Test
    @DisplayName("randomInt - throws exception if min > max")
    void testRandomIntInvalidRange() {
        assertThrows(IllegalArgumentException.class, () -> NumberUtility.randomInt(10, 5));
    }

    @Test
    @DisplayName("isPrime - detects prime numbers correctly")
    void testIsPrime() {
        assertTrue(NumberUtility.isPrime(2));
        assertTrue(NumberUtility.isPrime(13));
        assertFalse(NumberUtility.isPrime(1));
        assertFalse(NumberUtility.isPrime(4));
        assertFalse(NumberUtility.isPrime(0));
        assertFalse(NumberUtility.isPrime(-7));
    }

    @Test
    @DisplayName("safeEquals - handles nulls and equality correctly")
    void testSafeEquals() {
        assertTrue(NumberUtility.safeEquals(null, null));
        assertFalse(NumberUtility.safeEquals(null, 1));
        assertTrue(NumberUtility.safeEquals(10, 10.0));
        assertFalse(NumberUtility.safeEquals(10, 11));
    }
}
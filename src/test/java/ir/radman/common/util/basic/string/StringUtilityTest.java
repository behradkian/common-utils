package ir.radman.common.util.basic.string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/02
 */
class StringUtilityTest {

    @Test
    @DisplayName("should count occurrences of a character correctly")
    void testCountCharacter() {
        assertEquals(3, StringUtility.countCharacter("banana", 'a'));
        assertEquals(0, StringUtility.countCharacter(null, 'x'));
    }

    @Test
    @DisplayName("should detect valid numbers")
    void testIsNumber() {
        assertTrue(StringUtility.isNumber("123"));
        assertTrue(StringUtility.isNumber("-45.67"));
        assertFalse(StringUtility.isNumber("abc"));
        assertFalse(StringUtility.isNumber("12a"));
    }

    @Test
    @DisplayName("should reverse string")
    void testReverse() {
        assertEquals("cba", StringUtility.reverse("abc"));
        assertEquals("", StringUtility.reverse(null));
    }

    @Test
    @DisplayName("should identify palindrome")
    void testIsPalindrome() {
        assertTrue(StringUtility.isPalindrome("madam"));
        assertFalse(StringUtility.isPalindrome("hello"));
        assertFalse(StringUtility.isPalindrome(""));
        assertFalse(StringUtility.isPalindrome(null));
    }

    @Test
    @DisplayName("should convert to camelCase")
    void testToCamelCase() {
        assertEquals("helloWorld", StringUtility.toCamelCase("Hello world"));
        assertEquals("javaRocks", StringUtility.toCamelCase("java_rocks"));
    }

    @Test
    @DisplayName("should convert to PascalCase")
    void testToPascalCase() {
        assertEquals("HelloWorld", StringUtility.toPascalCase("hello world"));
    }

    @Test
    @DisplayName("should convert to snake_case")
    void testToSnakeCase() {
        assertEquals("hello_world", StringUtility.toSnakeCase("HelloWorld"));
    }

    @Test
    @DisplayName("should convert to kebab-case")
    void testToKebabCase() {
        assertEquals("hello-world", StringUtility.toKebabCase("HelloWorld"));
    }

    @Test
    @DisplayName("should remove first character")
    void testRemoveFirstCharacter() {
        assertEquals("ello", StringUtility.removeFirstCharacter("Hello"));
        assertEquals("", StringUtility.removeFirstCharacter(""));
    }

    @Test
    @DisplayName("should trim safely and handle null")
    void testSafeTrim() {
        assertEquals("abc", StringUtility.safeTrim("  abc  "));
        assertEquals("", StringUtility.safeTrim(null));
    }

    @Test
    @DisplayName("should normalize multiple spaces")
    void testNormalizeSpaces() {
        assertEquals("a b c", StringUtility.normalizeSpaces("a    b   c"));
    }

    @Test
    @DisplayName("should pad left and right correctly")
    void testPadLeftRight() {
        assertEquals("00A", StringUtility.padLeft("A", 3, '0'));
        assertEquals("A00", StringUtility.padRight("A", 3, '0'));
    }

    @Test
    @DisplayName("should slugify text correctly")
    void testSlugify() {
        assertEquals("hello-world", StringUtility.slugify("Hello World!"));
    }

    @Test
    @DisplayName("should join non blank parts")
    void testJoinNonBlank() {
        assertEquals("a-b", StringUtility.joinNonBlank("-", "a", "", "b"));
        assertEquals("", StringUtility.joinNonBlank("-", ""));
    }

    @Test
    @DisplayName("should repeat and capitalize")
    void testRepeatCapitalize() {
        assertEquals("aaa", StringUtility.repeat("a", 3));
        assertEquals("Hello", StringUtility.capitalize("hello"));
    }

    @Test
    @DisplayName("should remove non-alphanumeric")
    void testRemoveNonAlphanumeric() {
        assertEquals("Hello123", StringUtility.removeNonAlphanumeric("Hello@#123"));
    }

    @Test
    @DisplayName("should count words correctly")
    void testCountWords() {
        assertEquals(3, StringUtility.countWords("one two three"));
        assertEquals(0, StringUtility.countWords(""));
    }

    @Test
    @DisplayName("should abbreviate correctly")
    void testAbbreviate() {
        assertEquals("Hel...", StringUtility.abbreviate("HelloWorld", 6));
        assertEquals("Hi", StringUtility.abbreviate("Hi", 6));
    }

    @Test
    @DisplayName("should compare strings safely")
    void testEqualsVariants() {
        assertTrue(StringUtility.equalsIgnoreCase("abc", "ABC"));
        assertFalse(StringUtility.equalsIgnoreCase("abc", "xyz"));

        assertTrue(StringUtility.equals("abc", "abc"));
        assertFalse(StringUtility.equals("abc", null));
    }

    @Test
    @DisplayName("should convert Arabic digits from Base64 to normal number")
    void testConvertArabicToNumber() {
        String arabicBase64 = Base64.getEncoder().encodeToString("۱۲۳۴۵".getBytes(StandardCharsets.UTF_8));
        assertEquals("12345", StringUtility.convertArabicToNumber(arabicBase64));
    }
}

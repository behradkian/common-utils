package ir.radman.common.util.basic.collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.IntPredicate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/04
 */
public class CollectionUtilityTest {

    @Test
    @DisplayName("Find minimum and maximum values in an array")
    void testMinMaxArray() {
        int[] arr = {3, 1, 7, 2};
        assertEquals(1, CollectionUtility.min(arr));
        assertEquals(7, CollectionUtility.max(arr));
    }

    @Test
    @DisplayName("Find minimum and maximum values in a list")
    void testMinMaxList() {
        List<Integer> list = List.of(5, 2, 9, 1);
        assertEquals(1, CollectionUtility.min(list));
        assertEquals(9, CollectionUtility.max(list));
    }

    @Test
    @DisplayName("Remove duplicate elements from array and list")
    void testDistinctArrayAndList() {
        int[] arr = {1, 2, 2, 3, 1};
        assertArrayEquals(new int[]{1, 2, 3}, CollectionUtility.distinct(arr));

        List<Integer> list = List.of(1, 1, 2, 3, 2);
        assertEquals(List.of(1, 2, 3), CollectionUtility.distinct(list));
    }

    @Test
    @DisplayName("Merge multiple arrays into a single array")
    void testMergeArrays() {
        int[] merged = CollectionUtility.mergeArrays(new int[]{1, 2}, new int[]{3, 4});
        assertArrayEquals(new int[]{1, 2, 3, 4}, merged);
    }

    @Test
    @DisplayName("Find first and last index of a value in array")
    void testIndexOfAndLastIndexOf() {
        int[] arr = {1, 2, 3, 2, 4};
        assertEquals(1, CollectionUtility.indexOf(arr, 2));
        assertEquals(3, CollectionUtility.lastIndexOf(arr, 2));
    }

    @Test
    @DisplayName("Calculate average, median and mode of an array")
    void testAverageMedianMode() {
        int[] arr = {1, 2, 2, 3, 4};
        assertEquals(2.4, CollectionUtility.average(arr));
        assertEquals(2, CollectionUtility.median(arr));
        assertEquals(2, CollectionUtility.mode(arr));
    }

    @Test
    @DisplayName("Convert between arrays and lists")
    void testToListAndToArray() {
        int[] arr = {1, 2, 3};
        List<Integer> list = CollectionUtility.toList(arr);
        assertEquals(List.of(1, 2, 3), list);
        assertArrayEquals(arr, CollectionUtility.toArray(list));
    }

    @Test
    @DisplayName("Check equality of arrays ignoring order")
    void testEqualsIgnoreOrder() {
        int[] a = {1, 2, 3};
        int[] b = {3, 2, 1};
        assertTrue(CollectionUtility.equalsIgnoreOrder(a, b));
    }

    @Test
    @DisplayName("Calculate sum and product of an array")
    void testSumAndProduct() {
        int[] arr = {2, 3, 4};
        assertEquals(9, CollectionUtility.sum(arr));
        assertEquals(24, CollectionUtility.product(arr));
    }

    @Test
    @DisplayName("Generate frequency map of array elements")
    void testFrequencyMap() {
        int[] arr = {1, 1, 2, 3};
        Map<Integer, Long> freq = CollectionUtility.frequencyMap(arr);
        assertEquals(2L, freq.get(1));
        assertEquals(1L, freq.get(2));
    }

    @Test
    @DisplayName("Check containsAll for given values")
    void testContainsAll() {
        int[] arr = {1, 2, 3, 4};
        assertTrue(CollectionUtility.containsAll(arr, 1, 3));
        assertFalse(CollectionUtility.containsAll(arr, 5));
    }

    @Test
    @DisplayName("Check anyMatch and allMatch with predicates")
    void testAnyMatchAndAllMatch() {
        int[] arr = {1, 2, 3, 4};
        IntPredicate even = n -> n % 2 == 0;
        assertTrue(CollectionUtility.anyMatch(arr, even));
        assertFalse(CollectionUtility.allMatch(arr, even));
    }

    @Test
    @DisplayName("Split array into equal-sized chunks")
    void testChunk() {
        int[] arr = {1, 2, 3, 4, 5};
        List<int[]> chunks = CollectionUtility.chunk(arr, 2);
        assertEquals(3, chunks.size());
        assertArrayEquals(new int[]{1, 2}, chunks.get(0));
        assertArrayEquals(new int[]{3, 4}, chunks.get(1));
        assertArrayEquals(new int[]{5}, chunks.get(2));
    }

    @Test
    @DisplayName("Rotate array elements by distance")
    void testRotate() {
        int[] arr = {1, 2, 3, 4, 5};
        CollectionUtility.rotate(arr, 2);
        assertArrayEquals(new int[]{4, 5, 1, 2, 3}, arr);
    }
}

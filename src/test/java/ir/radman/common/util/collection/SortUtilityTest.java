package ir.radman.common.util.collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/04
 */
class SortUtilityTest {

    @Test
    @DisplayName("Bubble Sort - should sort int array ascending")
    void testBubbleSort_IntArray() {
        int[] arr = {5, 1, 4, 2, 8};
        int[] expected = {1, 2, 4, 5, 8};
        assertArrayEquals(expected, SortUtility.bubbleSort(arr));
    }

    @Test
    @DisplayName("Merge Sort - should sort int array ascending")
    void testMergeSort_IntArray() {
        int[] arr = {38, 27, 43, 3, 9, 82, 10};
        int[] expected = {3, 9, 10, 27, 38, 43, 82};
        assertArrayEquals(expected, SortUtility.mergeSort(arr));
    }

    @Test
    @DisplayName("Quick Sort - should sort int array ascending")
    void testQuickSort_IntArray() {
        int[] arr = {10, 7, 8, 9, 1, 5};
        int[] expected = {1, 5, 7, 8, 9, 10};
        assertArrayEquals(expected, SortUtility.quickSort(arr));
    }

    @Test
    @DisplayName("SortDescending - should sort array in descending order")
    void testSortDescending_IntArray() {
        int[] arr = {2, 9, 1, 7, 3};
        SortUtility.sortDescending(arr);
        assertArrayEquals(new int[]{9, 7, 3, 2, 1}, arr);
    }

    @Test
    @DisplayName("Reverse - should reverse array order")
    void testReverse_IntArray() {
        int[] arr = {1, 2, 3, 4, 5};
        SortUtility.reverse(arr);
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, arr);
    }

    @Test
    @DisplayName("isSorted - should detect sorted array")
    void testIsSorted_IntArray() {
        assertTrue(SortUtility.isSorted(new int[]{1, 2, 3}));
        assertFalse(SortUtility.isSorted(new int[]{3, 1, 2}));
    }

    @Test
    @DisplayName("Shuffle - should randomize array (content preserved)")
    void testShuffle_IntArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] copy = arr.clone();
        SortUtility.shuffle(arr);
        Arrays.sort(arr);
        Arrays.sort(copy);
        assertArrayEquals(copy, arr);
    }

    @Test
    @DisplayName("Bubble Sort - should sort List of Integers ascending")
    void testBubbleSort_List() {
        List<Integer> input = Arrays.asList(5, 2, 8, 1, 3);
        List<Integer> result = SortUtility.bubbleSort(input);
        assertEquals(Arrays.asList(1, 2, 3, 5, 8), result);
    }

    @Test
    @DisplayName("Bubble Sort with Comparator - should sort descending")
    void testBubbleSort_ListWithComparator() {
        List<Integer> input = Arrays.asList(5, 2, 8, 1, 3);
        List<Integer> result = SortUtility.bubbleSort(input, Comparator.reverseOrder());
        assertEquals(Arrays.asList(8, 5, 3, 2, 1), result);
    }

    @Test
    @DisplayName("Merge Sort - should sort generic List ascending")
    void testMergeSort_List() {
        List<String> input = Arrays.asList("pear", "apple", "orange", "banana");
        List<String> expected = Arrays.asList("apple", "banana", "orange", "pear");
        assertEquals(expected, SortUtility.mergeSort(input));
    }

    @Test
    @DisplayName("Quick Sort - should sort generic List ascending")
    void testQuickSort_List() {
        List<Double> input = Arrays.asList(3.5, 1.2, 2.8, 4.0);
        List<Double> expected = Arrays.asList(1.2, 2.8, 3.5, 4.0);
        assertEquals(expected, SortUtility.quickSort(input));
    }

    @Test
    @DisplayName("Shuffle - should randomize list order but keep same elements")
    void testShuffle_List() {
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> copy = new ArrayList<>(input);
        SortUtility.shuffle(input);
        Collections.sort(input);
        Collections.sort(copy);
        assertEquals(copy, input);
    }

    @Test
    @DisplayName("Empty List - should remain unchanged")
    void testEmptyList() {
        List<Integer> input = new ArrayList<>();
        List<Integer> result = SortUtility.bubbleSort(input);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Single Element List - should remain unchanged")
    void testSingleElementList() {
        List<Integer> input = Collections.singletonList(42);
        List<Integer> result = SortUtility.mergeSort(input);
        assertEquals(Collections.singletonList(42), result);
    }

    @Test
    @DisplayName("Array with duplicates - should handle correctly")
    void testArrayWithDuplicates() {
        int[] arr = {5, 3, 5, 1, 3};
        int[] expected = {1, 3, 3, 5, 5};
        assertArrayEquals(expected, SortUtility.mergeSort(arr));
    }

    @Test
    @DisplayName("Already sorted array - should not modify order")
    void testAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] sorted = SortUtility.bubbleSort(arr.clone());
        assertArrayEquals(arr, sorted);
    }
}
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
    @DisplayName("Bubble sort on integer array")
    void testBubbleSortArray() {
        int[] arr = {5, 3, 1, 4, 2};
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, SortUtility.bubbleSort(arr));
    }

    @Test
    @DisplayName("Bubble sort on list")
    void testBubbleSortList() {
        List<Integer> list = List.of(3, 1, 4, 2);
        List<Integer> sorted = SortUtility.bubbleSort(list);
        assertEquals(List.of(1, 2, 3, 4), sorted);
    }

    @Test
    @DisplayName("Merge sort on array")
    void testMergeSortArray() {
        int[] arr = {9, 5, 1, 3};
        assertArrayEquals(new int[]{1, 3, 5, 9}, SortUtility.mergeSort(arr));
    }

    @Test
    @DisplayName("Quick sort on array")
    void testQuickSortArray() {
        int[] arr = {4, 2, 7, 1, 3};
        assertArrayEquals(new int[]{1, 2, 3, 4, 7}, SortUtility.quickSort(arr));
    }

    @Test
    @DisplayName("Check if array is sorted ascending or descending")
    void testIsSorted() {
        assertTrue(SortUtility.isSorted(new int[]{1, 2, 3}));
        assertTrue(SortUtility.isSortedDescending(new int[]{5, 4, 3}));
        assertFalse(SortUtility.isSorted(new int[]{3, 2, 1}));
    }

    @Test
    @DisplayName("Reverse and descending sort on array")
    void testReverseAndDescendingSort() {
        int[] arr = {1, 2, 3};
        SortUtility.reverse(arr);
        assertArrayEquals(new int[]{3, 2, 1}, arr);

        int[] arr2 = {3, 1, 2};
        SortUtility.sortDescending(arr2);
        assertArrayEquals(new int[]{3, 2, 1}, arr2);
    }

    @Test
    @DisplayName("Shuffle array and list")
    void testShuffle() {
        int[] arr = {1, 2, 3, 4, 5};
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));

        SortUtility.shuffle(arr);
        SortUtility.shuffle(list);

        assertEquals(5, arr.length);
        assertEquals(5, list.size());
    }

    @Test
    @DisplayName("Counting sort on array")
    void testCountingSort() {
        int[] arr = {4, 1, 3, 2, 1};
        assertArrayEquals(new int[]{1, 1, 2, 3, 4}, SortUtility.countingSort(arr));
    }

    @Test
    @DisplayName("Sort by frequency of occurrence")
    void testSortByFrequency() {
        int[] arr = {4, 4, 1, 1, 1, 2};
        assertArrayEquals(new int[]{2, 4, 4, 1, 1, 1}, SortUtility.sortByFrequency(arr));
    }

    @Test
    @DisplayName("Alphabetical sort ignoring case")
    void testAlphabeticalSortIgnoreCase() {
        List<String> list = List.of("banana", "Apple", "cherry");
        List<String> sorted = SortUtility.sortAlphabeticallyIgnoreCase(list);
        assertEquals(List.of("Apple", "banana", "cherry"), sorted);
    }

    @Test
    @DisplayName("Top-K elements extraction")
    void testTopK() {
        int[] arr = {9, 1, 7, 3, 5};
        assertArrayEquals(new int[]{9, 7, 5}, SortUtility.topK(arr, 3));
    }

    @Test
    @DisplayName("Compare sorting algorithm execution times")
    void testCompareSortingAlgorithms() {
        int[] arr = {3, 1, 2, 4, 5};
        Map<String, Long> results = SortUtility.compareSortingAlgorithms(arr);
        assertTrue(results.containsKey("BubbleSort"));
        assertTrue(results.containsKey("MergeSort"));
        assertTrue(results.containsKey("QuickSort"));
    }

    @Test
    @DisplayName("mergeSort(List<T>) should correctly sort integers in ascending order")
    void testMergeSortAscending() {
        List<Integer> input = Arrays.asList(5, 2, 8, 1, 3);
        List<Integer> expected = Arrays.asList(1, 2, 3, 5, 8);

        List<Integer> result = SortUtility.mergeSort(input);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("mergeSort(List<T>) should handle empty list")
    void testMergeSortEmptyList() {
        List<Integer> input = Collections.emptyList();

        List<Integer> result = SortUtility.mergeSort(input);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("quickSort(List<T>) should correctly sort strings alphabetically")
    void testQuickSortStrings() {
        List<String> input = Arrays.asList("banana", "apple", "pear", "orange");
        List<String> expected = Arrays.asList("apple", "banana", "orange", "pear");

        List<String> result = SortUtility.quickSort(input);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("quickSort(List<T>) should handle list with duplicate values")
    void testQuickSortDuplicates() {
        List<Integer> input = Arrays.asList(4, 2, 4, 1, 2);
        List<Integer> expected = Arrays.asList(1, 2, 2, 4, 4);

        List<Integer> result = SortUtility.quickSort(input);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("bubbleSort(List<T>, Comparator) should sort list based on custom comparator (descending order)")
    void testBubbleSortWithComparatorDescending() {
        List<Integer> input = Arrays.asList(1, 3, 2, 5, 4);
        List<Integer> expected = Arrays.asList(5, 4, 3, 2, 1);

        List<Integer> result = SortUtility.bubbleSort(input, Comparator.reverseOrder());

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("bubbleSort(List<T>, Comparator) should handle list with equal elements")
    void testBubbleSortWithEqualElements() {
        List<Integer> input = Arrays.asList(2, 2, 2);
        List<Integer> result = SortUtility.bubbleSort(input, Comparator.naturalOrder());

        assertEquals(Arrays.asList(2, 2, 2), result);
    }

    @Test
    @DisplayName("quickSort(List<T>, Comparator) should correctly sort custom objects by comparator")
    void testQuickSortWithComparatorCustomType() {
        record Person(String name, int age) {}

        List<Person> input = Arrays.asList(
                new Person("Alice", 25),
                new Person("Bob", 20),
                new Person("Charlie", 30)
        );

        List<Person> result = SortUtility.quickSort(input, Comparator.comparingInt(Person::age));

        assertEquals(Arrays.asList(
                new Person("Bob", 20),
                new Person("Alice", 25),
                new Person("Charlie", 30)
        ), result);
    }

    @Test
    @DisplayName("quickSort(List<T>, Comparator) should handle empty list gracefully")
    void testQuickSortWithComparatorEmptyList() {
        List<Integer> input = Collections.emptyList();

        List<Integer> result = SortUtility.quickSort(input, Comparator.naturalOrder());

        assertTrue(result.isEmpty());
    }
}
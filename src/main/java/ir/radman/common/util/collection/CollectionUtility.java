package ir.radman.common.util.collection;

import java.util.*;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/04
 */
public final class CollectionUtility {

    public CollectionUtility() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static int min(int[] arr) {
        if (arr.length == 0) throw new IllegalArgumentException("Array is empty");
        int min = arr[0];
        for (int val : arr) if (val < min) min = val;
        return min;
    }

    public static int max(int[] arr) {
        if (arr.length == 0) throw new IllegalArgumentException("Array is empty");
        int max = arr[0];
        for (int val : arr) if (val > max) max = val;
        return max;
    }

    public static <T extends Comparable<? super T>> T min(List<T> list) {
        return Collections.min(list);
    }

    public static <T extends Comparable<? super T>> T max(List<T> list) {
        return Collections.max(list);
    }

    public static int[] distinct(int[] arr) {
        return Arrays.stream(arr).distinct().toArray();
    }

    public static <T> List<T> distinct(List<T> list) {
        return new ArrayList<>(new LinkedHashSet<>(list));
    }

    public static int[] mergeArrays(int[]... arrays) {
        return Arrays.stream(arrays).flatMapToInt(Arrays::stream).toArray();
    }

    public static int indexOf(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++)
            if (arr[i] == value) return i;
        return -1;
    }

    public static int lastIndexOf(int[] arr, int value) {
        for (int i = arr.length - 1; i >= 0; i--)
            if (arr[i] == value) return i;
        return -1;
    }

    public static double average(int[] arr) {
        if (arr.length == 0) return 0;
        return Arrays.stream(arr).average().orElse(0);
    }

    public static double median(int[] arr) {
        int[] sorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(sorted);
        int n = sorted.length;
        if (n == 0) return 0;
        if (n % 2 == 0)
            return (sorted[n / 2 - 1] + sorted[n / 2]) / 2.0;
        else
            return sorted[n / 2];
    }

    public static int mode(int[] arr) {
        Map<Integer, Long> freq = new HashMap<>();
        for (int num : arr)
            freq.put(num, freq.getOrDefault(num, 0L) + 1);
        return freq.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow();
    }

    public static List<Integer> toList(int[] arr) {
        return Arrays.stream(arr).boxed().toList();
    }

    public static int[] toArray(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    public static boolean equalsIgnoreOrder(int[] a, int[] b) {
        if (a.length != b.length) return false;
        int[] aCopy = Arrays.copyOf(a, a.length);
        int[] bCopy = Arrays.copyOf(b, b.length);
        Arrays.sort(aCopy);
        Arrays.sort(bCopy);
        return Arrays.equals(aCopy, bCopy);
    }

    public static long sum(int[] arr) {
        return Arrays.stream(arr).asLongStream().sum();
    }

    public static long product(int[] arr) {
        return Arrays.stream(arr).reduce(1, (a, b) -> a * b);
    }

    public static Map<Integer, Long> frequencyMap(int[] arr) {
        return Arrays.stream(arr)
                .boxed()
                .collect(java.util.stream.Collectors.groupingBy(i -> i, java.util.stream.Collectors.counting()));
    }

    public static boolean containsAll(int[] arr, int... values) {
        Set<Integer> set = Arrays.stream(arr).boxed().collect(java.util.stream.Collectors.toSet());
        for (int v : values) if (!set.contains(v)) return false;
        return true;
    }

    public static boolean anyMatch(int[] arr, java.util.function.IntPredicate predicate) {
        return Arrays.stream(arr).anyMatch(predicate);
    }

    public static boolean allMatch(int[] arr, java.util.function.IntPredicate predicate) {
        return Arrays.stream(arr).allMatch(predicate);
    }


    public static List<int[]> chunk(int[] arr, int size) {
        List<int[]> chunks = new ArrayList<>();
        for (int i = 0; i < arr.length; i += size) {
            chunks.add(Arrays.copyOfRange(arr, i, Math.min(arr.length, i + size)));
        }
        return chunks;
    }

    public static void rotate(int[] arr, int distance) {
        int n = arr.length;
        distance = distance % n;
        if (distance < 0) distance += n;
        reverse(arr, 0, n - 1);
        reverse(arr, 0, distance - 1);
        reverse(arr, distance, n - 1);
    }

    private static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start++] = arr[end];
            arr[end--] = temp;
        }
    }

}
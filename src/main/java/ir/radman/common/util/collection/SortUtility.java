package ir.radman.common.util.collection;

import java.util.*;

/**
 * A comprehensive utility class for sorting and array/list manipulation.
 * Provides multiple algorithms (Bubble, Merge, Quick) and helper methods.
 *
 * @author : Pedram Behradkian
 * @date : 2025/11/04
 */
public final class SortUtility {

    private SortUtility() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static int[] bubbleSort(int[] array) {
        int n = array.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break; // Optimization
        }
        return array;
    }

    public static int[] mergeSort(int[] array) {
        mergeSort(array, 0, array.length - 1);
        return array;
    }

    public static int[] quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
        return array;
    }

    public static <T extends Comparable<? super T>> List<T> bubbleSort(List<T> list) {
        return bubbleSort(list, Comparator.naturalOrder());
    }

    public static <T> List<T> bubbleSort(List<T> list, Comparator<? super T> comparator) {
        List<T> sorted = new ArrayList<>(list);
        int n = sorted.size();
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(sorted.get(j), sorted.get(j + 1)) > 0) {
                    Collections.swap(sorted, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        return sorted;
    }

    public static <T extends Comparable<? super T>> List<T> mergeSort(List<T> list) {
        if (list.size() <= 1) {
            return new ArrayList<>(list);
        }
        int mid = list.size() / 2;
        List<T> left = mergeSort(list.subList(0, mid));
        List<T> right = mergeSort(list.subList(mid, list.size()));
        return merge(left, right);
    }

    public static <T extends Comparable<? super T>> List<T> quickSort(List<T> list) {
        if (list.size() <= 1) {
            return new ArrayList<>(list);
        }
        T pivot = list.get(list.size() / 2);
        List<T> less = new ArrayList<>();
        List<T> equal = new ArrayList<>();
        List<T> greater = new ArrayList<>();
        for (T elem : list) {
            int cmp = elem.compareTo(pivot);
            if (cmp < 0) less.add(elem);
            else if (cmp > 0) greater.add(elem);
            else equal.add(elem);
        }
        List<T> result = new ArrayList<>(quickSort(less));
        result.addAll(equal);
        result.addAll(quickSort(greater));
        return result;
    }

    public static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            if (arr[i] > arr[i + 1])
                return false;
        return true;
    }

    public static void reverse(int[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            swap(arr, i, arr.length - 1 - i);
        }
    }

    public static void sortDescending(int[] arr) {
        Arrays.sort(arr);
        reverse(arr);
    }

    public static void shuffle(int[] arr) {
        Random random = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            swap(arr, i, j);
        }
    }

    public static <T> void shuffle(List<T> list) {
        Collections.shuffle(list);
    }

    private static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            arr[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
        }

        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    public static int[] sortByFrequency(int[] arr) {
        Map<Integer, Long> freq = new HashMap<>();
        for (int n : arr) freq.put(n, freq.getOrDefault(n, 0L) + 1);
        return Arrays.stream(arr)
                .boxed()
                .sorted(Comparator.<Integer, Long>comparing(freq::get)
                        .thenComparingInt(Integer::intValue))
                .mapToInt(Integer::intValue)
                .toArray();
    }

    public static List<String> sortAlphabeticallyIgnoreCase(List<String> list) {
        List<String> sorted = new ArrayList<>(list);
        sorted.sort(String.CASE_INSENSITIVE_ORDER);
        return sorted;
    }

    public static <T> List<T> quickSort(List<T> list, Comparator<? super T> comparator) {
        if (list.size() <= 1) {
            return new ArrayList<>(list);
        }
        T pivot = list.get(list.size() / 2);
        List<T> less = new ArrayList<>();
        List<T> equal = new ArrayList<>();
        List<T> greater = new ArrayList<>();
        for (T elem : list) {
            int cmp = comparator.compare(elem, pivot);
            if (cmp < 0) less.add(elem);
            else if (cmp > 0) greater.add(elem);
            else equal.add(elem);
        }
        List<T> result = new ArrayList<>(quickSort(less, comparator));
        result.addAll(equal);
        result.addAll(quickSort(greater, comparator));
        return result;
    }


    public static int[] countingSort(int[] arr) {
        if (arr.length == 0) return arr;
        int min = Arrays.stream(arr).min().orElse(0);
        int max = Arrays.stream(arr).max().orElse(0);
        int[] count = new int[max - min + 1];
        for (int num : arr) count[num - min]++;
        int index = 0;
        for (int i = 0; i < count.length; i++)
            while (count[i]-- > 0) arr[index++] = i + min;
        return arr;
    }

    public static boolean isSortedDescending(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            if (arr[i] < arr[i + 1]) return false;
        return true;
    }

    public static int[] topK(int[] arr, int k) {
        return Arrays.stream(arr)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .limit(k)
                .mapToInt(Integer::intValue)
                .toArray();
    }

    public static Map<String, Long> compareSortingAlgorithms(int[] arr) {
        Map<String, Long> results = new LinkedHashMap<>();
        results.put("BubbleSort", benchmark(() -> bubbleSort(Arrays.copyOf(arr, arr.length))));
        results.put("MergeSort", benchmark(() -> mergeSort(Arrays.copyOf(arr, arr.length))));
        results.put("QuickSort", benchmark(() -> quickSort(Arrays.copyOf(arr, arr.length))));
        return results;
    }


    public static long benchmark(Runnable sortMethod) {
        long start = System.nanoTime();
        sortMethod.run();
        return System.nanoTime() - start;
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                swap(arr, ++i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static <T extends Comparable<? super T>> List<T> merge(List<T> left, List<T> right) {
        List<T> merged = new ArrayList<>();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).compareTo(right.get(j)) <= 0) merged.add(left.get(i++));
            else merged.add(right.get(j++));
        }
        while (i < left.size()) merged.add(left.get(i++));
        while (j < right.size()) merged.add(right.get(j++));
        return merged;
    }
}
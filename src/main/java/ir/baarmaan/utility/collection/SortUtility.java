package ir.baarmaan.utility.collection;

import java.util.List;

public class SortUtility {

    private SortUtility() {}

    public static int[] getBubbleSort(int[] array) {

        int arraySize = array.length;
        for (int i = 0; i < arraySize - 1; i++)
            for (int j = 0; j < arraySize - i - 1; j++)
                if (array[j] > array[j + 1]) {
                    // Swap temp and arr[i]
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
        return array;
    }

    public static int[] getMergeSort(int[] array) {
        return mergeSort(array, 0, array.length - 1);
    }

    public static List<Integer> getBubbleSort(List<Integer> array) {
        int arraySize = array.size();
        for (int i = 0; i < arraySize - 1; i++) {
            for (int j = 0; j < arraySize - i - 1; j++) {
                if (array.get(j) > array.get(j + 1)) {
                    int temp = array.get(j);
                    array.set(j, array.get(j + 1));
                    array.set(j + 1, temp);
                }
            }
        }
        return array;
    }

    private static int[]  mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
        return arr;
    }

    private static int[] merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        while (i < n1) {
            arr[k++] = L[i++];
        }

        while (j < n2) {
            arr[k++] = R[j++];
        }
        return arr;
    }


}

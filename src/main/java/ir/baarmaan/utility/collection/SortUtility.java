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

}

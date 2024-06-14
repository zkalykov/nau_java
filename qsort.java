// This code for course work only

import java.util.*;

public class qsort {

    public static void main(String[] args) {
        int[] arr = {5, 3, 8, 9, 1, 7, 0, 2, 6, 4};

        int pivot = arr[0];  // Using 5 as the pivot
        int i = -1;
        
        for (int j = 0; j < arr.length; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        for (var x : arr) {
            System.out.print(x + " ");
        }
    }
}

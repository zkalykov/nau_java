import java.util.*;

public class msort {
    int[] arr = new int[10];
    static int cnt = 0;
    public static void merge_sort(int[] arr) {
        if(cnt>=2) {
            return;
        }
        if (arr.length > 1) {
            int mid = arr.length / 2;
            int[] LEFT = new int[mid];
            int[] RIGHT = new int[arr.length - mid];
            
            for (int i = 0; i < mid; i++) {
                LEFT[i] = arr[i];
            }
            for (int i = mid; i < arr.length; i++) {
                RIGHT[i - mid] = arr[i];
            }
            merge_sort(LEFT);
            merge_sort(RIGHT);
            
            int i = 0, j = 0, k = 0;
            
            while (i < LEFT.length && j < RIGHT.length) {
                if (LEFT[i] < RIGHT[j]) {
                    arr[k] = LEFT[i];
                    i += 1;
                } else {
                    arr[k] = RIGHT[j];
                    j += 1;
                }
                k += 1;
            }
            while (i < LEFT.length) {
                arr[k] = LEFT[i];
                i += 1;
                k += 1;
            }
            while (j < RIGHT.length) {
                arr[k] = RIGHT[j];
                j += 1;
                k += 1;
            }

        }
        cnt+=1;
    }

    public static void main(String[] args) {
        int[] arr = {5,  3,  8,  9,  1,  7,  0,  2,  6,  4};
        merge_sort(arr);
        
        for (var x : arr) {
            System.out.print(x + " ");
        }
    }
}

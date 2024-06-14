// This code for course work only

import java.util.*;

public class ssort{

    public static void main(String[]args){
        int []  arr = {5,  3,  8,  9,  1,  7,  0,  2,  6,  4};

        for(int i=0;i<arr.length;i++){
            int mi = i;
            for(int j=i+1;j<arr.length;j++){
                if(arr[mi] > arr[j] ){
                    mi = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[mi];
            arr[mi]  = temp;
            if(i==1){
                break;
            }
        }

        for(var x:arr){
            System.out.print(x+" ");
        }
    }
}
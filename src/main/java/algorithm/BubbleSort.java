package algorithm;

import java.util.Arrays;

/**
 * @author ：li zhen
 * @description:
 * @date ：2024/1/4 13:46
 */
public class BubbleSort {

    public static void bubbleSort(int[] array){

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i+1; j < array.length; j++) {
                if (array[i] > array[j]){
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {

        int[] array = new int[]{5,2,3,10,7,0};
        bubbleSort(array);
        System.out.println(Arrays.toString(array));
    }
}

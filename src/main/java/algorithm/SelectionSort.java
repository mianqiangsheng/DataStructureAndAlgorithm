package algorithm;

import java.util.Arrays;

/**
 * 选择排序，时间界O(N2)
 *
 * n 值较小时，选择排序比冒泡排序快。
 *
 * 是一种简单直观的排序算法，其基本原理，对于一组记录的数据，通过第一次比较得到最小的记录，然后将该记录
 * 与第一条记录的位置交换；接着对不包含第一个以外的记录进行比较，得到最小记录并与第二个记录进行位置交换；重复该
 * 过程，知道进行比较的记录只有一个时为止。
 *
 * 以数组 {38,65,97,76,13,27,49} 为例：
 * 13[65 97 76 38 27 49]
 * 13 27[97 76 38 65 49]
 * 13 27 38[76 97 65 49]
 * 13 27 38 49[97 65 76]
 * 13 27 38 49 65[97 76]
 * 13 27 38 49 65 76[97]
 * 13 27 38 49 65 76 97
 *
 * @author ：li zhen
 * @description:
 * @date ：2024/1/4 13:46
 */
public class SelectionSort {

    public static void selectionSort(int[] array){

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
        selectionSort(array);
        System.out.println(Arrays.toString(array));
    }
}

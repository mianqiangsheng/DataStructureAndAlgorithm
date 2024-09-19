package algorithm;

import java.util.Arrays;

/**
 * 冒泡排序，时间界O(N2)
 *
 * 顾名思义就是整个过程就像气泡一样往上升，单向冒泡排序的基本思想是（假设由小到大排序）：对于给定的 n 个
 * 记录，从第一个记录开始依次对相邻的两个记录进行比较，当前面的记录大于后面的记录时，交换位置，进行一轮比较和换
 * 位后，n 个记录中的最大记录将位于第 n位；然后对前（n-1）个记录进行第二轮比较；重复该过程直到进行比较的记录只
 * 剩下一个为止。
 *
 * 以数组 {45,3,2,5,7,8,32,9,1,22,0} 为例，冒泡排序的具体步骤如下：
 * [3, 2, 5, 7, 8, 32, 9, 1, 22, 0, 45]
 * [2, 3, 5, 7, 8, 9, 1, 22, 0, 32, 45]
 * [2, 3, 5, 7, 8, 1, 9, 0, 22, 32, 45]
 * [2, 3, 5, 7, 1, 8, 0, 9, 22, 32, 45]
 * [2, 3, 5, 1, 7, 0, 8, 9, 22, 32, 45]
 * [2, 3, 1, 5, 0, 7, 8, 9, 22, 32, 45]
 * [2, 1, 3, 0, 5, 7, 8, 9, 22, 32, 45]
 * [1, 2, 0, 3, 5, 7, 8, 9, 22, 32, 45]
 * [1, 0, 2, 3, 5, 7, 8, 9, 22, 32, 45]
 * [0, 1, 2, 3, 5, 7, 8, 9, 22, 32, 45]
 *
 */
public class BubbleSort {

    public static void bubbleSort(int[] array){

//        for(int i =1;i<array.length;i++) {
//            for(int j=0;j<array.length-i;j++) {
        for (int i = array.length - 1; i >=0 ; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j+1]){
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
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

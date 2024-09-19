package algorithm;

import java.util.Arrays;

/**
 * 插入排序，时间界O(N2)
 *
 * 对于给定的一组记录,初始时假设第一个记录自成一个有序序列,其余记录为无序序列。
 * 接着从而个记录开始，按照记录的大小依次将当前处理的记录插入到其之前的有序序列中，直至最后一条记录插入到有序序列中为止。
 *
 * 例如：数组 {38,65,97,76,13,27,49}
 * 第一步插入38以后：[38]65 97 76 13 27 49
 * 第一步插入65以后：[38 65]97 76 13 27 49
 * 第一步插入97以后：[38 65 97]76 13 27 49
 * 第一步插入76以后：[38 65 76 97]13 27 49
 * 第一步插入13以后：[13 38 65 76 97]27 49
 * 第一步插入27以后：[13 27 38 65 76 97]49
 * 第一步插入49以后：[13 27 38 49 65 76 97]
 *
 * Created by lizhen on 2018/9/20.
 */
public class InsertionSort {

    /**
     *
     * @param array 待排序数组
     * @param m 数组中需要排序的开始元素下标
     * @param n 数组中需要排序的结束元素下标
     * @param <T> 元素类型
     */
    public static<T extends Comparable<? super T>> void insertionSort(T[] array,int m,int n) {

        for(int i=m+1;i<n+1;i++) {
            T get = array[i]; //暂存插入的元素
            int j = i-1; //从已排序的数组末尾开始比较
            while(j>=m && array[j].compareTo(get)>0) { //从已排序的数组里向前遍历比较插入的元素
                array[j+1] = array[j]; //如果插入元素较小，则将比较的元素j向后移
                j--; //继续向前比较
            }
            array[j+1] = get; //直到上面跳出，j就是找到的比插入元素小的位置，将插入的元素放置到位置j的后面，即j+1
        }
    }

    public static void main(String[] args) {
        Integer[] example = {6,5,3,1,8,7,2,4,10,9};
        int size =example.length;
        insertionSort(example,0,size-1);
        System.out.println("插入排序结果： ");
        System.out.print(Arrays.toString(example));
    }

}

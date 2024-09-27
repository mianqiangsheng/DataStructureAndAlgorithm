package algorithm;

import java.util.Arrays;
import java.util.Scanner;

import static algorithm.InsertionSort.*;
/**
 * 快速排序，平均时间界O(NlogN),最坏时间界O(N2)
 *
 * Created by lizhen on 2018/9/20.
 */
public class QuickSort {

    /**
     * 使用插入排序或使用快速排序的数组元素阈值，默认为10
     */
    private static final int CUTOFF = 10;

    /**
     *
     * @param array 待排序数组，这里进行互换数组中的元素
     * @param i 进行互换的数组下标1
     * @param j 进行互换的数组下标2
     * @param <T> 元素类型
     */
    private static<T extends Comparable<? super T>> void swap(T[] array, int i,int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     *返回进行分割排序后，子数组的枢纽元下标，这里默认枢纽元选择子数组的最右边的元素（要求数组至少1个元素）
     * （按书上应该选取中位元素作为枢纽元，能更提高效率）
     *
     * @param array 待排序数组
     * @param left 进行分割排序的子数组第一个元素下标
     * @param right 进行分割排序的子数组最后一个元素下标
     * @param <T> 元素类型
     * @return 分割排序后枢纽元所在的数组下标
     */
    private static<T extends Comparable<? super T>> int partition(T[] array, int left,int right) {
        T pivot = array[right]; //选取最右边元素作为枢纽元
        int tail = left-1; //标志性比枢纽元小的元素的最大下标
        for(int i = left;i<right;i++) { //从数组头开始遍历，如果元素比枢纽元小，则最大下标+1，且互换位置
            if(array[i].compareTo(pivot)<=0) {
                swap(array, ++tail, i);
            }
        }
        swap(array, tail+1, right); //最后将枢纽元与此时的最大下标+1互换位置

        return tail+1; //返回枢纽元所在位置
    }

    /**
     * 更好的分割排序策略，三数中值分割法(要求数组至少3个元素)
     * @param array 待排序数组
     * @param left 进行分割排序的子数组第一个元素下标
     * @param right 进行分割排序的子数组最后一个元素下标
     * @param <T> 元素类型
     * @return 分割排序后枢纽元所在的数组下标
     */
    private static<T extends Comparable<? super T>> int betterPartition(T[] array, int left,int right) {
        int center = (left+right)/2;
        /**
         * 比较array[left]、array[center]、array[right]中的中间值作为枢纽值pivot，并摆放到对应的位置
         */
        if(array[center].compareTo(array[left])<0)
            swap(array,left,center);
        if(array[right].compareTo(array[left])<0)
            swap(array,left,right);
        if(array[right].compareTo(array[center])<0)
            swap(array,center,right);
        /**
         * 将枢纽值暂时放到right-1位置，此时保证了array[left]、array[right-1]、array[right]是有序的
         */
        swap(array,center,right-1);

        T pivot = array[right-1];

        /**
         * 双指针从array[left+1]，array[right-2]首尾两端开始遍历与枢纽值pivot比较，
         * 将小于枢纽值的和大于枢纽值的互相调换位置，直到定位到比枢纽值pivot大的i位置
         * 此时保证array[left]、array[left+1]-array[i-1]比枢纽值小，array[i+1]-array[right-2]、array[right]比枢纽值大
         */
        int i=left,j=right-1;
        for (;;){
            while (array[++i].compareTo(pivot)<0){}
            while (array[--j].compareTo(pivot)>0){}
            if(i<j)
                swap(array,i,j);
            else
                break;
        }
        /**
         * 最后将位置i和位置right-1的元素互换，此时保证比枢纽值小的都在位置i左边，比枢纽值大的都在位置i右边
         */
        swap(array,i,right-1);
        return i;
    }


    /**
     * 快速排序，这里做了优化，如果待排序数组元素较少则使用插入排序(实际上只需要考虑元素少于partition算法时的退出机制即可)
     *
     * 递归的过程，给定一个待排序数组，应用快速排序F(array)
     * 1、查看元素个数是否满足数量，数量过小则使用其他排序算法，这里使用插入排序（递归的退出条件）
     * 2、按照取枢纽元的逻辑获取枢纽元位置并将所有小的排在左边，所有大的排在右边
     * 3、对枢纽元左边和右边的数组递归使用快速排序F(array)
     *
     * @param array 待排序数组
     * @param left 待排序数组起始排序的第一个元素下标
     * @param right 待排序数组起始排序的最后一个元素下标
     * @param <T> 元素类型
     */
    private static<T extends Comparable<? super T>> void quickSort(T[] array, int left,int right) {

        //当待排序数组个数在10个以内，使用插入排序
        if(left+CUTOFF<=right){
//            int pivot_index = partition(array, left, right); //默认取最右边元素作为枢纽元
            int pivot_index = betterPartition(array, left, right); //更好获取枢纽位置方法
            quickSort(array, left, pivot_index-1);
            quickSort(array, pivot_index+1, right);
        }else {
            insertionSort(array,left,right);
        }

    }


    /**
     * 快速排序的变种，快速选择第k个小的元素，平均时间界O(N)，最坏时间界O(N2)
     *
     * 应用partition()算法排序并返回枢纽元的特性，只需要某次递归返回的枢纽元下标=k-1（即第k个小的元素）并返回
     * 此时取array[k-1]就是要求的那个元素
     *
     * @param array 待选择数组
     * @param left 待选择数组起始的第一个元素下标
     * @param right 待选择数组起始的最后一个元素下标
     * @param k 选取第k个小的元素
     * @param <T> 元素类型
     */
    private static<T extends Comparable<? super T>> void quickSelect(T[] array, int left,int right, int k) {

        if(left+CUTOFF<=right){
            int pivot_index = partition(array, left, right);

            if(k-1==pivot_index) {
            }
            else if(k-1<pivot_index)
                quickSelect(array,left,pivot_index-1,k);
            else if(k>pivot_index+1)
                quickSelect(array,pivot_index+1,right,k);
        }else {
            insertionSort(array,left,right);
        }
    }


    public static void main(String[] args) {

        int i1 = betterPartition(new Integer[]{10,1,2}, 0, 2);
        int i2 = partition(new Integer[]{10}, 0, 0);

        Integer[] example = {6,5,3,20,8,7,2,4,10,9,12,11};
        int size =example.length;
        quickSort(example,0,size-1);
        System.out.println(Arrays.toString(example));

        Integer[] example1 = new Integer[]{6,5,3,1,8,7,2,4,10,9,12,11};
        Scanner scanner = new Scanner(System.in);
        int k =0;
        if(scanner.hasNextInt()){
            k = scanner.nextInt();
        }

        quickSelect(example1,0,example1.length-1,k);
        System.out.println(Arrays.toString(example1));
        System.out.printf("该数组第%d个小的元素是:%d",k,example1[k-1]);
    }
}

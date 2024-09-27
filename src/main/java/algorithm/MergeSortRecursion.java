package algorithm;

import java.util.Arrays;

/**
 * 归并排序，时间界O(NlogN)
 *
 * Created by lizhen on 2018/9/20.
 */
public class MergeSortRecursion {

    /**
     * 给出一个数组array，left位置到mid位置是已经排序好的子数组A，mid+1位置到right位置是已经排序好的子数组B，
     * 双指针遍历两个子数组进行排序，是一个归并、治的过程
     * @param array 待排序数组
     * @param left 合并子数组的最左边元素数组下标1
     * @param mid 合并子数组的中间元素数组下标2
     * @param right 合并子数组的最右边元素数组下标3
     * @param <T> 元素类型
     */
    private static<T extends Comparable<? super T>> void merge(T[] array,int left,int mid,int right) {
        int len = right -left +1;
        Object[] tempArray = new Object[len];

        int index = 0;
        int i = left;
        int j =mid+1;

        while(i<=mid && j<=right) {
            tempArray[index++]=array[i].compareTo(array[j])<=0?array[i++]:array[j++];
        }

        while(i<=mid) {tempArray[index++] = array[i++];}

        while(j<=right) {tempArray[index++] = array[j++];}

        for(int k=0;k<len;k++) {
            array[left++]=(T)tempArray[k];
        }

        /**
         * 逆序双指针算法进行合并
         */
//        Object[] nums2 = new Object[right - mid];
//        System.arraycopy(array,mid+1,nums2,0,right - mid);
//        int p1 = mid, p2 = nums2.length - 1;
//        int tail = right;
//        T cur;
//        while (p1 >= left || p2 >= 0) {
//            if (p1 == left-1) {
//                cur = (T) nums2[p2--];
//            } else if (p2 == -1) {
//                cur = array[p1--];
//            } else if (array[p1].compareTo((T)nums2[p2]) > 0) {
//                cur = array[p1--];
//            } else {
//                cur = (T)nums2[p2--];
//            }
//            array[tail--] = cur;
//        }

    }

    /**
     * 归并排序，分治-合并
     *
     * 递归的过程，给定一个待排序数组，应用分治排序F(array)
     * 1、查看是否只有一个元素，是则直接返回（递归的退出条件）
     * 2、拆分成2个子数组
     * 3、对2个子数组分别应用分治排序F(array)，得到排序好的2个子数组
     * 4、归并2个排序好的子数组
     *
     * @param all 待排序数组
     * @param left 进行排序的起始元素数组下标1
     * @param right 进行排序的末尾元素数组下标2
     * @param <T> 元素类型
     */
    private static<T extends Comparable<? super T>> void mergeSortRecursion(T[] all,int left,int right) {
        if(left == right) {return;}
        int mid = (left+right)/2;
        mergeSortRecursion(all,left,mid);
        mergeSortRecursion(all,mid+1,right);
        merge(all,left,mid,right);
    }


    public static void main(String[] args) {

        Integer[] example = {6,5,3,1,8,7,2,4,10,9};
        int size =example.length;
        mergeSortRecursion(example,0,size-1);
        System.out.println(Arrays.toString(example));
    }
}

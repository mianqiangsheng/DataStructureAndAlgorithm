package algorithm.interview;

import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author ：li zhen
 * @description:
 * @date ：2024/1/3 19:46
 */
public class Test {

    public static void insertionSort(int[] array,int m,int n) {

        for (int i = m+1; i < n+1; i++) {
            int get = array[i];
            int j = i - 1;
            while (j >= m && array[j]>get){
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = get;
        }
    }

    public static String insertSort(int[] array){

        if (array.length <= 1){
            return Arrays.toString(array);
        }

        for (int i = 1; i < array.length; i++) {
            int temp = array[i];

            int j = i - 1;
            while (j>=0 && array[j] > temp){
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = temp;
        }

        return Arrays.toString(array);
    }


    public static int maxSubArray(int[] array) {

        if (array.length < 2)
            throw new RuntimeException("array length at least 2");

        int sum = 0;
        int n = array.length;

        //计算第一个窗口数组元素和
        for (int i = 0; i < 2; i++) {
            sum += array[i];
        }

        int max = sum;
        for (int i = 2; i < n; i++) {
            sum = sum - array[i-2] + array[i];
            if (sum > max)
                max = sum;
        }

        return max;

    }

    public static int maxSubArray1(int[] array) {

        if (array.length < 2)
            throw new RuntimeException("array length at least 2");

        int result;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < array.length -1; i++) {
            result = array[i] + array[i+1];
            if (result>max)
                max = result;
        }

        return max;
    }

    public static String solution(int height) {
        // 在这⾥写代码
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < height; i++){
            int i1 = height - i - 1;
            for(int j = 0; j < i1; j++){
                sb.append(" ");
            }
            int i2 = 2 * (i+1) - 1;
            for(int j = 0; j < i2; j++){
                sb.append("*");
            }
            for(int j = 0; j < i1; j++){
                sb.append(" ");
            }
            if(i < height -1){
                sb.append("\n");
            }
        }

        return sb.toString();

    }

    public static int find(int[] array, int element){

        int[] clone = array.clone();

        int low = 0;
        int high = array.length - 1;

        Arrays.sort(clone);

        while (low <= high){
            int mid =  (low + high) >>> 1;
            if (clone[mid] > element){
                high = mid - 1;
            }else if (clone[mid] < element){
                low = mid + 1;
            }else {
                return clone[mid];
            }
        }

        throw new RuntimeException();

    }

    static char[] low = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    static char[] upper = new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    static char[] number = new char[]{'0','1','2','3','4','5','6','7','8','9'};

    public static String solution(String password) {
        // 1234567890Abcd
        //排序字符，进行重复字符计数+字符类型计数
        char[] charArray = password.toCharArray();

        boolean hasNumber =false;
        boolean hasLow =false;
        boolean hasUpper =false;

        if(charArray.length < 8 || charArray.length > 22) {
            return "weak";
        }

        for(int i = 0;i<charArray.length - 2;i++){
            if(charArray[i] == charArray[i+1] && charArray[i+1] == charArray[i+2]){
                return "weak";
            }
            if(Arrays.asList(low).contains(charArray[i])) {
                hasLow = true;
            }
            if(Arrays.asList(upper).contains(charArray[i])) {
                hasUpper = true;
            }
            if(Arrays.asList(number).contains(charArray[i])) {
                hasNumber = true;
            }

            if(i == charArray.length - 3){
                if(Arrays.asList(low).contains(charArray[i+1]) || Arrays.asList(low).contains(charArray[i+2])) {
                    hasLow = true;
                }
                if(Arrays.asList(upper).contains(charArray[i+1]) || Arrays.asList(upper).contains(charArray[i+2])) {
                    hasUpper = true;
                }
                if(Arrays.asList(number).contains(charArray[i+1]) || Arrays.asList(number).contains(charArray[i+2])) {
                    hasNumber = true;
                }
            }

        }

        if(!(hasLow && hasUpper && hasNumber)) {
            return "weak";
        }

        return "strong";
    }


    public static String feibo(int n){
        if (n == 0)
            return "a";
        if (n == 1)
            return "b";
        String[] array = new String[2];
        array[0] = "a";
        array[1] = "b";
        for (int i = 3; i <= n ; i++) {
            array[(i - 1) & 1] = array[(i - 1)  & 1] + array[(i - 2) & 1];
        }
        return array[( n - 1)  & 1];
    }

    public static char mingzhong(int index){

        int i = 0;
        String pre;
        String next;
        do {
            pre = feibo(i);
            next = feibo(++i);
        } while (next.length() < index);

        if (pre.length() == index){
            return pre.charAt(index-1);
        }else {
            return next.charAt(index-pre.length() - 1);
        }

    }





    public static void main(String[] args) {

        char mingzhong = mingzhong(7);
        System.out.println(mingzhong);
    }
}

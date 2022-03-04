package algorithm;

/**
 *
 * 两个整数相加，解决超出int范围后的计算方法
 * @author ：li zhen
 * @description:
 * @date ：2022/3/4 10:37
 */
public class IntAverage {

    public static void main(String[] args) {
        int a = 2147483647;
        int b = 2147483645;
        System.out.println(a);
        System.out.println(b);

        int i = (a + b) / 2;
        System.out.println(i); //特定数值有用

        int i1 = a + (a - b) / 2;
        System.out.println(i1); //特定数值有用

        int i2 = (a / 2) + (b / 2) + (a & b & 1);
        System.out.println(i2);  // 2016年过期的专利方法

        int i3 = (a & b) + (a ^ b) / 2;
        System.out.println(i3); //另一种方法
    }
}

package algorithm.interview;

import java.util.Arrays;
import java.util.Random;

public class Summary {

    /**
     * 1、贪心算法
     * <p>
     * 在对问题求解时，总是作出在当前看来是最好的选择。
     * 也就是说，不从整体上加以考虑，所作出的仅仅是在某种意义上的局部最优解(是否是全局最优，需要证明)
     * <p>
     * 形式：贪心常常表现为以某个顺序进行排序，每一步/最终操作按照某个顺序进行
     * 证明：可用反证法证明某种贪心方式是正确的
     * <p>
     * ----------------------------------------------------------------------
     */


    /**
     * EXAMPLE1: 给定n个正整数，将它们联成一排，相邻数字首尾相接，问能拼成的最大的多位整数
     * <p>
     * 从位置0开始遍历元素，看哪个元素应该放在第0位置，依次确定所有位置的元素
     * length(【a】【bc】) = length(【b】【ac】)，所以只要从高位开始看就可以了
     * <p>
     * 算法：最直观的思路，按照高位优先的原则，从高位开始决定放哪个元素
     *
     * @param array
     */
    public static void pieceNumber(String[] array) {

        String a = String.join("", array); //原始排序下的最大值

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                String temp = array[j];
                array[j] = array[i];
                array[i] = temp;

                String b = String.join("", array); //尝试将不同元素摆到位置i

                if (a.compareTo(b) < 0) {
                    a = b; //标记新的最大值
                } else {
                    String temp1 = array[j]; //如果比原来最大值小，则还原调换的元素
                    array[j] = array[i];
                    array[i] = temp1;
                }
            }
        }

        System.out.println(Arrays.toString(array));
    }

    /**
     * 给定n个正整数，将它们联成一排，相邻数字首尾相接，问能拼成的最大的多位整数
     * <p>
     * 每一次都从位置0开始，相邻两元素互换比较这2个元素组成的字符大小，如果变大则互换位置，标记为有改进；
     * 循环再次从位置0开始，直到不存在互换相邻两元素使拼成的字符变大，说明找到了一个全局最优解
     * <p>
     * 算法：按照贪心的原始思路，看相邻元素互换是否局部状况变好
     *
     * @param array
     */
    public static void pieceNumber1(String[] array) {
        boolean fixedpoint; //标识是否可以有优化空间
        do {
            fixedpoint = true;
            for (int i = 0; i < array.length - 1; i++) {
                String a = array[i] + array[i + 1];
                String b = array[i + 1] + array[i];
                if (a.compareTo(b) < 0) {
                    String temp = array[i + 1];
                    array[i + 1] = array[i];
                    array[i] = temp;
                    fixedpoint = false; //如果确实有优化，则置为false
                }
            }
        } while (!fixedpoint); //不断循环，继续查看是否可以优化

        System.out.println(Arrays.toString(array));
    }

    /**
     * 给定n个正整数，将它们联成一排，相邻数字首尾相接，问能拼成的最大的多位整数
     * <p>
     * 算法：双指针
     *
     * @param array
     */
    public static void pieceNumber2(String[] array) {

        for (int i = 0; i < array.length; i++) { //遍历所有元素，依次从位置0开始确定每个位置上的元素

            Character max = null; //标识每轮比较元素下标的最大值
            int idex = 0; //标识每轮比较的元素下标
            Character dual = null; //标识元素下标上是否有重复的最大值

            for (int j = i; j < array.length; j++) { //从已经确定元素的后面开始挑选元素
                Character c;
                try {
                    c = array[j].charAt(idex); //获取对应下标的值
                } catch (StringIndexOutOfBoundsException e) {
                    continue;
                }
                if (max == null || c.compareTo(max) > 0) { //将下标上具有最大值的元素放置到位置i
                    max = c;
                    String temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                } else if (c.compareTo(max) == 0) {
                    dual = c;
                }
                if (j == array.length - 1) {
                    if (dual == max) { //遍历完后如果当前下标存在重复最大值，则比较后面下标的值
                        idex++;
                        j = i - 1; //重新从i位置处遍历，由于for循环体里有j++，所以这里多减1
                    }
                }
            }

        }

        System.out.println(Arrays.toString(array));
    }

    /**
     * 给定n个正整数，将它们联成一排，相邻数字首尾相接，问能拼成的最大的多位整数
     *
     * 算法：贪心，首先证明a与b按照a+b和b+a定义可以比较大小，由于证明可以比较，所以这里直接使用冒泡算法（本质上也是贪心算法）
     *
     * @param array
     */
    public static void pieceNumber3(String[] array) {

        for (int i = array.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (less(array[j], array[j + 1])) {
                    String temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }

        System.out.println(Arrays.toString(array));
    }


    /**
     * 校验两个字符串相加比较大小的数学性质
     * 【这里没有使用严格的数学证明，而是使用大量的数据来验证数学定义上的less是否符合】
     */
    static String[] stringArray = new String[]{"1", "2", "7", "8", "9", "88", "9", "888", "999"};

    public static String gen() {
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(stringArray.length - i) + i;
            // Swap the current element with the random element
            String temp = stringArray[i];
            stringArray[i] = stringArray[index];
            stringArray[index] = temp;
        }

        return stringArray[0] + stringArray[1];
    }

    /**
     * 定义 “a小于b”：a+b组成的字符串小于b+a
     *
     * @param a
     * @param b
     * @return
     */
    public static Boolean less(String a, String b) {
        return (a + b).compareTo(b + a) < 0;
    }

    /**
     * 检查less是否满足 “a不能小于a自身”：a+a不能小于a+a
     *
     * @param a
     * @return
     */
    public static Boolean check_reflexive(String a) {
        return !less(a, a);
    }

    /**
     * 检查less是否满足 “a和b不同，a一定和b可比”：按照less定义，a+b和b+a有一个更小
     * 这里有特例，当a+b等于b+a时，虽然a不等于b（比如88和888），但是两者按照less定义是相同的，由于他们组成字符串时不影响最终效果，所以这里不要求严格有序
     *
     * @param a
     * @param b
     * @return
     */
    public static Boolean check_antisymmetric(String a, String b) {
//        if (!(a + b).equals(b + a)){
//           return less(a,b) || less(b,a);
//        }
//        return true;

        boolean b1 = true;
        if (!a.equals(b)) {
            b1 = less(a, b) || less(b, a);
            if (!b1) {
                System.out.printf("a:%s,b:%s", a, b);
            }
        }

        return b1;
    }

    /**
     * 定义 “比较传递性”：a<b且b<c,可以推出a<c
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static Boolean check_transitive(String a, String b, String c) {
        if (less(a, b) && less(b, c)) {
            return less(a, c);
        }
        return true;
    }


    /**
     * EXAMPLE2: 给定一个未经排序的整数数组找到最长且连续递增的子序列并返回该序列的长度
     * 贪心算法
     *
     * @param array
     * @return
     */
    public static int[] incrementalSub1(int[] array) {
        int startIndex = 0;
        int maxLength = 1;
        int start = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] <= array[i - 1]) {
                startIndex = i; //后一个元素小于前一个元素，重置子数组开始下标
            }
            if (i - startIndex + 1 > maxLength) { //累计当前子数组长度，定位子数组开始下标、长度
                start = startIndex;
                maxLength = i - startIndex + 1;
            }
        }
        int[] output = new int[maxLength];
        System.arraycopy(array, start, output, 0, maxLength);
        return output;
    }



    /**
     * EXAMPLE3: 求取数组中最大的差值
     * <p>
     * 给定一个数组prices，它的第i个元素prices[i]表示一支给定股票第i天的价格。你只能选择某一天买入这只股
     * 票，并选择在未来的某一个不同的日子卖出该股票。设计一个算法来计算你所能获取的最大利润。返回你可以从这笔交易中
     * 获取的最大利润。如果你不能获取任何利润，返回0。
     * <p>
     * 理解成每隔1天即观察是否有盈利空间，累加每一天可能的利润，即为所能取得的最大利润
     *
     */
        public static int maxProfit(int[] prices) {
            int ans = 0;
            int n = prices.length;
            for (int i = 1; i < n; ++i) {
                ans += Math.max(0, prices[i] - prices[i - 1]);
            }
            return ans;
        }


    /**
     * 2、并查集
     * ----------------------------------------------------------------------
     */
    static int[] array;
    public static int[] init(int numElements){
        array = new int[numElements];
        for (int i=0;i<array.length;i++)
            array[i] = i;
        return array;
    }
    public static int find(int x){
        int r = x;
        while (array[r] != r){
            r = array[r];
        }
        return array[x] = r;
    }
    public static void merge(int x, int y){
        int fx,fy;
        fx = find(x);
        fy = find(y);
        if (fx != fy){
            array[fy] = fx;
        }
    }

    public static void main(String[] args) {
        int[] array = init(5);
        merge(4,2);
        merge(0,1);
        merge(2,3);
        merge(1,4);
        System.out.println(Arrays.toString(array));

        for (int i = 0; i < array.length; i++) {
            find(i);
        }
        System.out.println(Arrays.toString(array));

    }


}

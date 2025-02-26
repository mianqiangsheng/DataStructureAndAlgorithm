package algorithm.interview;

import org.springframework.util.CollectionUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Summary {

    /**
     * #算法1、贪心算法
     * <p>
     * 在对问题求解时，总是作出在当前看来是最好的选择。
     * 也就是说，不从整体上加以考虑，所作出的仅仅是在某种意义上的局部最优解(是否是全局最优，需要证明)
     * <p>
     * 形式：贪心常常表现为以某个顺序进行排序，每一步/最终操作按照某个顺序进行
     * 证明：可用反证法证明某种贪心方式是正确的
     * <p>
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
     * #算法2、并查集
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

    /**
     * EXAMPLE1: 求省份数量
     * <p>
     * 有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
     * 省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
     * 给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，而 isConnected[i][j] = 0 表示二者不直接相连。
     * 返回矩阵中 省份 的数量。
     * <p>
     *
     * 思路：应用并查集，将所有相连的城市归属于一个集合，集合的数量就是省份数量
     *
     */
    public static int findCircleNum(int[][] isConnected) {
        int[] temp = init(isConnected.length);
        for (int i=0;i<isConnected.length;i++){
            int[] array = isConnected[i];
            for (int j=0;j<array.length -1 ;j++){
                int val = array[j];
                if(val > 0){
                    merge(i,j);
                }
            }
        }

        int count = 0;
        for (int i=0;i<temp.length;i++){
            if(temp[i] == i){
                count++;
            }
        }

        return count;
    }


    /**
     * #算法3、递推求解
     *
     * 首先，确认:能否容易地得到初始状态的解?
     * 然后，假设:规模不大于N-1的状态已经得到解决。
     * 最后，重点分析:当规模扩大到N时，如何枚举出所有的情况，然后用子问题的状态(F(1)、F(2)、...F(N-1))表示出最终的状态F(N)(即:状态转移)
     *
     * 递推和递归，递推是从小问题出发到大问题，递归是从大问题出发到小问题，两者应用的基础都是递推公式。
     * 递归算法写的时候先判断问题规模是否是最小规模问题，写该问题的出口，再写一般的递推公式情况。
     */

    /**
     * EXAMPLE1: 求骨牌的铺法数
     * <p>
     * 有个1xn的长方形，用1x1、1x2、1x3的骨牌铺满方格。例如:当n=3时为1x3的方格，此时共有四种铺法。
     * <p>
     *
     * 思路：从最后一块方格开始考虑，填满的话可以用1x1、1x2、1x3这三种骨牌。
     * 当使用1x1时，剩余n-1块方格未填满，f(n-1)表示之前n-1个方格的铺法，则n个方格在这种情形下就是f(n-1)种铺法；
     * 当使用1x2时，剩余n-2块方格未填满，f(n-2)表示之前n-2个方格的铺法，则n个方格在这种情形下就是f(n-2)种铺法；
     * 当使用1x3时，剩余n-3块方格未填满，f(n-3)表示之前n-3个方格的铺法，则n个方格在这种情形下就是f(n-3)种铺法；
     *
     * 以上所有可能性加起来，即f(n-1)+f(n-2)+f(n-3)，就是f(n)的铺法总数
     */
    public static int layingMethod(int n){

        if (n <= 3){
            return n==1?1:n==2?2:n==3?4:0;
        }else {
            int[] array = new int[n+1];
            array[1] = 1;
            array[2] = 2;
            array[3] = 4;
            for (int i = 4; i < n+1; i++) {
                array[i] = array[i-1] + array[i-2] + array[i-3];
            }
            return array[n];
        }
    }

    /**
     * EXAMPLE2: 求女生排队的组合数
     * <p>
     * PHT学校有很多学生。有一天，校长希望所有学生站成一排，并且规定女孩不能单独站。换句话说，要么队列中没有女孩，要么不止一个女孩并排站。
     * 比如，n=4的时候，有以下7种可能的合法队列(F女生，M男):FFFF、FFFM、MFFF、FFMM、MFFM、MMFF、MMMMM
     * 给定人数n，求所有可能的n个人的合法队列数。
     * <p>
     *
     * 思路：这里组合相较于一般的组合多了一个“合法性”限制条件，不如上一题那么明显，无论如何推断要紧靠这个“合法性”条件。
     *
     * 考虑最后一个是男生的情况：
     * 1、如果之前n-1队列是合法的，那么加上一个M，还是合法的，按照定义可知此种情形下排列数=f(n-1)
     * 2、如果之前n-1队列是非法的，那么加上一个M，还是非法的，则此种情形下排列数=0
     *
     * 考虑最后一个是女生的情况：
     * 首先为了队列合法，按照定义可知n-1位一定是F，
     * 1、此时考虑之前n-2队列是合法的话，加上2个F，还是合法的，按照定义可知此种情形下排列数=f(n-2)
     * 2、考虑之前n-2队列是非法的话，加上2个FF，为了使n队列变成合法，所以n-3、n-2位置只能是M和F
     * 2.1、继续考虑之前n-4队列，只能是合法的情形下，满足加上MFFF变成合法队列，按照定义可知此种情形下排列数=f(n-4)
     *
     * 综上，考虑完所有情形后，f(n)=f(n-1)+f(n-2)+f(n-4)
     *
     */

    /**
     * EXAMPLE3: 卡特兰数相关题目
     * f(n) = f(0)*f(n-1) + f(1)*f(n-2) + ... + f(n-1)*f(0)
     * 推导出的公式就是 f(n) = C(2n,n)/(n+1)，其中C(2n,n)表示从2n种选择n个元素的排列组合数
     *
     * 对于C(n,m)组合数公式的推导：
     * 挑选第1个，可以选择n种，顺序可能m个位置
     * 挑选第2个，可以选择(n-1)种，顺序可能(m-1)个位置
     * ...
     * 挑选第m个，可以选择(n-m+1)种，顺序可能1个位置
     *
     * 首先考虑有序的排列组合，即
     * n*(n-1)*...*(n-m+1) = n!/(n-m)!
     * 其次由于是组合，所以需要排除掉所有元素相同仅仅顺序不同的情况，即
     * m*(m-1)*....*1 = m!
     * 综上，
     * C(n,m) = n!/(n-m)!/m! = n!/((n-m)! * m!)
     *
     */

    /**
     * #算法4、DP（Dynamic Programming）动态规划
     *
     * 问题随着规模变大，相邻之间虽然存在规律，但不像是递推时那种静态的关系，
     * 而是动态变化的（常常表现为Max、Min，从几种备选方案中选择一个最优的）。
     *
     * 主要特点：
     * 1、最优子结构：较大规模的最优解成立时，较小规模的最优解也同时成立
     * 2、重叠子问题：较大规模的问题需要考虑较小规模的最优解
     * 3、无后效性：较大规模的问题解决时，不会反过来影响较小规模的问题的最优解
     *
     */

    /**
     *
     * EXAMPLE1: 免费馅饼问题
     * 一共有0-10共11个位置，没秒有馅饼可能掉落在各个位置上，而你可以向左、向右或呆在原地接住这些馅饼。
     * 起始位置是5，每个位置可能在每一秒掉落多个馅饼，现在给定每秒馅饼掉落情况，求最多可以接住多少馅饼？
     *
     * 可以得出这样的数塔结构:
     *
     * 第0秒                       5             （这里的数字指的是第N秒可能到达的位置坐标）
     * 第1秒                     4 5 6
     * 第2秒                   3 4 5 6 7
     * 第3秒                 2 3 4 5 6 7 8
     * 第4秒               1 2 3 4 5 6 7 8 9
     * 第5秒             0 1 2 3 4 5 6 7 8 9 10
     * 第6秒             0 1 2 3 4 5 6 7 8 9 10
     * 第i秒第j的位置始终存放这从此位置可得到的最大馅饼数，
     * 那么在0秒的5位置处就是最大可得到的馅饼数
     *
     * dp[i][j]: 第i秒在第j个位置上，走到最后一秒能取得的最大馅饼数
     * val[i][j]: 第i秒在第j个位置上，掉落在该位置的馅饼数
     * dp[i][j] = val[i][j] + max(dp[i+1][j-1], dp[i+1][j], dp[i+1][j+1])
     * 倒着推，从最后一秒开始计算，依次向前找
     *
     * @param dp
     * @return
     */
    public static int catchPie(int[][] dp) {
        int time = dp.length - 1;
        for (int i = time - 1; i >= 0; i--) {//时间
            for (int j = 0; j < 11; j++) {//步数
                if(j == 0){
                    dp[i][j] += Math.max(dp[i+1][j],dp[i+1][j+1]);
                }else if(j == 10){
                    dp[i][j] += Math.max(dp[i+1][j],dp[i+1][j-1]);
                }else {
                    dp[i][j] += Math.max(Math.max(dp[i + 1][j], dp[i + 1][j + 1]), dp[i + 1][j - 1]);
                }
            }
        }

        return dp[0][5]; //返回初始位置的最大馅饼数
    }

    /**
     *
     * EXAMPLE2: 求数组最长有序子序列（这里简单找出按照原数组相对位置递增的子序列）
     * 比如：{1, 10, 4, 7, 2, 5, 8, 3, 6, 9, 11, 12}
     * 其最长递增子序列（某个，可能不唯一）是：[1, 4, 7, 8, 9, 11, 12]
     *
     * dp[i]: 第i个位置上，从数组开始位置开始最长的子序列数组
     * val[i]: 第i个位置上的元素值
     * dp[i] =
     * 1、如果dp[i-1]为空，则dp[i]的第一个元素置为val[i]
     *
     * 2、如果dp[i-1]不为空且val[i]大于dp[i-1]的第一个元素，
     * 2.1、且如果val[i]大于dp[i-1]子序列最大的元素，则dp[i-1]子序列加上val[i]元素
     * 2.2、且如果val[i]小于dp[i-1]子序列最大的元素
     * 2.2.1、且如果dp[i-1]的子序列长度大于2且val[i]大于dp[i-1]倒数第二大元素，则replace(dp[i-1]的最后一个元素,val[i])
     * 2.2.2、且如果dp[i-1]的子序列长度小于2，则replace(dp[i-1]的最后一个元素,val[i])
     *
     * 3、如果dp[i-1]不为空且val[i]小于dp[i-1]的第一个元素，可能存在以val[i]为首的子序列，存入tmp直到和之前存的dp[i-1]的子序列一样长，进行替换
     *
     * @param array
     * @return
     */
    @Deprecated(since = "错误的思路，因为定义了不恰当的dp[i]，找了不恰当的dp关系")
//    public static int[] errorMaxLengthSubArray(int[] array) {
//
//        int[][] dp = new int[array.length][array.length];
//        dp[0][0] = array[0]; //初始化dp第一个元素
//
//        int[] tmp = new int[array.length];
//
//        for (int i = 1; i < dp.length; i++) {
//            int[] lastSubArray = dp[i-1]; //dp[i-1]
//            //将dp[i-1]赋给dp[i]
//            System.arraycopy(lastSubArray,0,dp[i],0,lastSubArray.length); //深复制数组，不然会改变原来数组
//            int lastLength = 0; //记录dp[i-1]的子序列长度
//            for (int i1 : lastSubArray) {
//                if (i1 > 0){ //默认0，表示空白的元素，不统计为子序列
//                    lastLength++;
//                }
//            }
//
//            if (lastLength >0){ //如果dp[i-1]的子序列长度大于0
//                if (lastSubArray[0] < array[i]){
//                    if (lastSubArray[lastLength-1] < array[i]){ //子序列最大值小于当前数组值，将当前元素添加到dp[i]子序列中
//                        dp[i][lastLength] = array[i];
//                    }else { //用一个元素置换掉一个较大的元素，保持最长子序列长度不变
//                        if (lastLength >= 2){
//                            if (lastSubArray[lastLength-2] < array[i]){
//                                dp[i][lastLength-1] = array[i];
//                            }
//                        }else {
//                            dp[i][lastLength-1] = array[i];
//                        }
//                    }
//                }else {
//                    int ll = 0;
//                    for (int i1 : tmp) {
//                        if (i1 > 0){ //默认0，表示空白的元素，不统计为子序列
//                            ll++;
//                        }
//                    }
//
//                    if (ll == 0){
//                        tmp[0] = array[i];
//                    }else{
//                        tmp[ll] = array[i];
//                    }
//
//                    if (ll >= lastLength - 1){
//                        System.arraycopy(tmp,0,dp[i],0,tmp.length);
//                    }
//
//                }
//            }else if (lastLength == 0){ //如果dp[i-1]子序列为空，则是当前元素添加到dp[i]的子序列的第一个元素
//                dp[i][0] = array[i];
//            }
//        }
//
//        /**
//         * 规范化输出子数组
//         */
//        int index = 0;
//        for (int i = 0; i < dp[array.length - 1].length; i++) {
//            if (dp[array.length - 1][i] == 0){
//                index = i;
//                break;
//            }
//        }
//        int[] result = new int[index];
//
//        System.arraycopy(dp[array.length - 1],0,result,0,index);
//        return result;
//
//    }

    /**
     * 输出数组最长有序子序列长度（这里简单找出按照原数组相对位置递增的子序列）
     *
     * 关键：意识到最长的某个子序列一定是某个从数组第一个元素开始且包含某个val[i]的序列
     * 可以将dp[i]按照以上定义出来，就可以写出一个比较清晰简单的关系
     *
     * dp[i]: 第i个位置上，从数组第一个元素开始且包含当前val[i]的最长的子序列数组长度
     * val[i]: 第i个位置上的元素值
     * dp[i] = 对于所有之前小于val[i]的元素，Max(dp[0],dp[1],...,dp[i-1]) + 1
     *
     * @param array
     * @return
     */
    public static int maxLengthSubArrayLength(int[] array) {
        int[] dp = new int[array.length];
        dp[0] = 1;
        for (int i = 1; i < dp.length; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                if (array[j] < array[i]){
                    int num = dp[j] + 1;
                    if (max < num){
                        max = num;
                    }
                }
            }
            if (max > 0){
                dp[i] = max;
            }else {
                dp[i] = 1;
            }

        }

        return Arrays.stream(dp).max().getAsInt();
    }

    /**
     * 输出数组最长有序子序列（这里简单找出按照原数组相对位置递增的子序列）
     *
     * @param array
     * @return
     */
    public static int[] maxLengthSubArray(int[] array) {
        int[][] dp = new int[array.length][array.length]; //记录每个位置最长子序列
        dp[0][0] = array[0]; //赋初始序列
        int[] lengths = new int[array.length]; //记录每个位置的最长子序列长度
        lengths[0] = 1; //赋初始长度
        for (int i = 1; i < dp.length; i++) {
            int maxLastLength = 0; //标记当前元素前的最长子序列长度
            for (int j = 0; j < i; j++) {
                if (array[j] < array[i]){
                    int lastLength = 0; //记录dp[j]的子序列长度
                    for (int i1 : dp[j]) {
                        if (i1 > 0){ //默认0，表示空白的元素，不统计为子序列
                            lastLength++;
                        }
                        if (i1 == 0){
                            break;
                        }
                    }
                    if (lastLength > maxLastLength){ //选择最长的子序列长度
                        maxLastLength = lastLength;
                        System.arraycopy(dp[j],0,dp[i],0,array.length);
                        dp[i][lastLength] = array[i]; //将当前元素拼到之前的最长子序列尾部
                        lengths[i] = lastLength + 1;
                    }
                }
                if (maxLastLength == 0){ //如果当前元素前的最长子序列长度为零，当前元素的最长子序列就是自己本身
                    dp[i][0] = array[i];
                }
            }
        }

        int maxLength = 0;
        int maxIndex = 0; //定位所有元素中拥有最长子序列的下标
        for (int i = 0; i < lengths.length; i++) {
            if (lengths[i] > maxLength){
                maxLength = lengths[i];
                maxIndex = i;
            }
        }

        /**
         * 规范化输出子数组
         */
        int index = 0;
        for (int i = 0; i < dp[maxIndex].length; i++) {
            if (dp[maxIndex][i] == 0){
                index = i;
                break;
            }
        }
        int[] result = new int[index];

        System.arraycopy(dp[maxIndex],0,result,0,index);
        return result;
    }

    /**
     *
     * EXAMPLE3: 如果存在一组长度为n的数据，有2个槽位存储数据进行处理，
     * 即每次取出2个数据进行处理（必须满槽）每次处理会计算(X1-X2)^2，得到一次数据偏离度，
     * 要求给定处理次数k，2k≤n，求如何选择数据进行满槽处理使得k次处理后累计的偏离度最小。
     * <p>
     * 推理
     *     证明结论1: 首先某次处理时包含第n位数据，显而易见选择这时候选择一个大小与其最接近的第n-1位数据，
     *     这时候偏离度最小。所以，在考虑这个问题时先把数据进行从小到大排序。
     * <p>
     *     [a,b]，k=1，处理a和b，最小偏离度=(b-a)^2
     *     [a,b,c]，k=1，可以处理a和b或b和c，最小偏离度= min[(b-a)^2,(c-b)^2]
     *     [a,b,c,d]，k=1，可以处理a和b或b和c或c和d，最小偏离度= min[(b-a)^2,(c-b)^2,(d-c)^2]
     *     ....
     *     [a,b,c,d...]，k=1，
     *     dp[n,1]: n(n>=2)个数据，处理1次时最小的累计偏离度
     *     dp[n,1] = 分两种情况：处理一次时包含第n位数据，(Xn-Xn-1)^2
     *                         处理一次时不包含第n位数据，dp[n-1,1]
*          即，dp[n,1] = min[dp[n-1,1],(Xn-Xn-1)^2]
     * <p>
     *     [a,b,c,d]，k=2，可以通过数学式子展开后证明。
     *     或者这么想，处理2次一定处理了b和d，而又有证明结论1，所以一定是b和a，d和c绑定处理取得最小偏离度，最小偏离度= (b-a)^2+(d-c)^2
     *     [a,b,c,d,e]，k=2，
     *     一样分两种情况：2次处理包含e，(e-d)^2；另一次就是在剩余a,b,c里处理，按照定义就是d[3,1]，
     *                  所以累计偏离度 = (e-d)^2 + d[3,1]
     *                  2次处理不包含e，在剩余a,b,c,d里处理，按照定义就是d[4,2]
     *     即，dp[5,2] = min[dp[4,2],(e-d)^2 + d[3,1]]
     *     ...
     *     [a,b,c,d,e,f]，k=2，
     *     dp[6,2] = min[dp[5,2],(f-e)^2 + d[4,1]]
     *     ...
     *     dp[n,2] = min[dp[n-1,2],(Xn-Xn-1)^2 + d[n-1,1]]
     * <p>
     *     ...
     *     归纳，针对n个数据，处理k次，即n(n>=2)，2k≤n，
     *     dp[n,k] = min[dp[n-1,k],(Xn-Xn-1)^2 + d[n-1,k-1]]
     *
     */

    /**
     * EXAMPLE4-1: 0-1背包问题，给定一个固定容量的背包，现在有一堆物品，每个物品有其占空间大小和各自的价值，
     * 问如何选择物品使得放入背包的物品的总价值最大？
     * <p>
     * dp[i,v]: 给定物品可以占据的空间v，放置前i个物品，能达到的最大价值
     * val[i]: 第i个物品的价值
     * cost[i]: 第i个物品占据的空间
     * <p>
     * 针对物品i和某个给定的空间v，
     * 如果空间够，可以选择放或者不放
     *  如果放置：剩余空间v-cost[i]，在这个空间上有一个dp[i-1,v-cost[i]]，总价值增加新放入的物品i（val[i]）
     *  如果不放：仅仅将占据的空间变大到v，总价值是不放置物品i之前的价值
     * 如果空间不够，只能不放
     *  如果不放：仅仅将占据的空间变大到v，总价值是不放置物品i之前的价值
     * <p>
     * 这里前i个物品，根据推导的物品顺序不一样，由于最终是要考虑所有的物品，所以最终结果是一样的
     * <p>
     * dp[i,v] = MAX(dp[i-1,v-cost[i]]) + val[i], dp[i-1,v]), when v-cost[i]≥0
     *           dp[i-1,v], when v-cost[i]<0
     *
     *
     */

    /**
     * 优化以上算法到一维数组
     * @param val 给定所有物品的价值
     * @param cost 给定所有物品的占据空间
     * @param volume 给定总空间大小
     * @return
     */
    public static int zero_one_bag(int[] val, int[] cost, int volume){
        int num = val.length;
        int[] dp = new int[volume+1];
        for (int i = 0; i < num; i++) {
            for (int j = volume; j >= cost[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j-cost[i]] + val[i]);
            }
        }
        return dp[volume];
    }

    /**
     * EXAMPLE4-2：完全背包问题：每种物品可以无限拿，空间有限求最大价值
     * 只需要把01背包里的空间遍历顺序改变成从前往后，就达到了物品可以无限取的条件的应用
     * @param val 给定所有物品的价值
     * @param cost 给定所有物品的占据空间
     * @param volume 给定总空间大小
     * @return
     */
    public static int full_bag(int[] val, int[] cost, int volume){
        int num = val.length;
        int[] dp = new int[volume+1];
        for (int i = 0; i < num; i++) {
            for (int j = cost[i]; j <= volume; j++) {
                dp[j] = Math.max(dp[j], dp[j-cost[i]] + val[i]);
            }
        }
        return dp[volume];
    }

    /**
     * EXAMPLE4-3：多重背包问题：每种物品有给定的件数，空间有限求最大价值
     * <p>
     * 可以将每种相同的物品看作01背包问题里的独立的物品，按照01背包算法进行计算
     * 但是这里涉及一个问题，就是如果某种物品件数很多，会很大增加遍历的次数。
     * <p>
     * 有一个计算优化，就是商品的件数num可以进行打包拆分，达到和分开考虑一样的效果。
     * 即任何数字C可以用[1,2,4,8...2^(k-1),C-2^k+1]这组数字表示
     * 因为[1,2,4,8...2^(k-1)]这组数字可以表示2^k-1中的任何数字，
     * 可知，拿C-2^k+1从[1,2,4,8...2^(k-1)]中挑选组合就可以表示C中的任何数字。
     * <p>
     * 所以，算法还是和01背包一样，只是需要做前期准备，按照前面的方法构造恰当的 int[] val, int[] cost
     * 减少输入数据的规模
     *
     * @param val 某种物品的价值
     * @param cost 某种物品的占用空间
     * @param num 某种物品的件数
     * @return
     */
    public static void construct_multi_bag(int val, int cost, int num){

        /**
         * 构造某种物品的价值数组
         */
        List<Integer> subVal = new ArrayList<>();
        /**
         * 构造某种物品的占用空间数组
         */
        List<Integer> subCost = new ArrayList<>();

        int t = 1;
        while (num >= t){
            subVal.add(val*t);
            subCost.add(cost*t);
            num -= t;
            t <<= 1;
        }
        if (num>0){
            subVal.add(val*num);
            subCost.add(cost*num);
        }
    }

    /**
     * #算法5、BFS 广度优先搜索算法（Breadth-First-Search）
     * <p>
     * BFS基本思想:
     * 从初始状态S开始，利用规则，生成所有可能的状态构成树的下一层节点。
     * 检查是否出现目标状态G，若未出现，就对该层所有状态节点，分别依次利用规则，生成再下一层的所有状态节点。
     * 对新一层的所有状态节点继续检查是否出现G，若未出现，继续按上面思想生成再下一层的所有状态节点，这样一层一层往下展开，直到出现目标状态为止。
     * <p>
     * 在树的遍历过程中，按层处理顶点：先输出父节点，再输出所有儿子节点，最后输出所有孙子节点，以此类推。
     * 在图的遍历过程中，按层处理顶点：距离开始点最近的那些顶点首先被求值，最远的顶点最后被求值。类似树中的层序遍历（在求最短路径等用到）
     * <p>
     * 算法实现：维护一个队列Queue，用于存放节点信息。当访问到一个节点的时候，先访问该节点，然后将该节点的“同一层”节点分别如队列。
     * <p>
     * ccbl(int root)
     * queue<int>Q;创建一个队列
     * Q.push(root);将根节点入队列
     * while(队列不为空){
     *  获得队首元素
     *  将队首元素出队
     *  输出当前节点的值（或做其他需要的业务处理和判断）
     *  如果该节点的相邻节点不为空，将相邻加入到队列中（树中表现的就是子节点，图中表现就是连通的节点）
     * }
     * <p>
     * 关键点在于应用类似于动态规划里的状态的思想，定义出节点的状态描述信息，以及描述清楚不同节点之间的遍历规则，即可以用前面的BFS算法进行搜索。
     * ps：这里还涉及“剪枝”，即在遍历节点过程中省去那些转移到之前出现过的状态节点的转移，一般只需要做好出现过的状态节点的标记即可。
     * <p>
     * 是一种层序遍历的实现，实现使用队列，而非其他遍历递归默认的栈。
     *
     */

    /**
     * EXAMPLE1：有3个杯子，容量分别是S、M，N，其中S=M+N，初始状态时容量S的杯子水装满。
     * 现在可以在这3个杯子之间互相倒水，要求将容量S进行平分，
     * 求如果能平分时候，进行倒水的最少次数是多少？
     * <p>
     * 定义状态节点：三杯水的量，当前状态的最少倒水次数
     *
     */
    public static int pour_water(List<PourWaterState.Bottle> bottles){

        /**
         * BFS算法队列
         */
        Deque<PourWaterState> q = new LinkedList<>();

        /**
         * 标记达到过的状态
         */
        Set<PourWaterState> vis = new HashSet<>();

        /**
         * 原始状态
         */
        PourWaterState root = new PourWaterState(bottles,0);

        q.offer(root);
        vis.add(root);

        PourWaterState current;
        PourWaterState next;
        while (!q.isEmpty()){
            current = q.poll();

            List<PourWaterState.Bottle> bottleList = current.getBottles();
            int pourNum = current.getPourNum();

            /**
             * 判断当前状态是否满足算法要求的结果: 仅有2个瓶子有水且量相同
             */
            List<PourWaterState.Bottle> collect = bottleList.stream().filter(b -> b.getVolume() > 0).collect(Collectors.toList());
            if (collect.size() == 2){
                if (collect.get(0).getVolume() == collect.get(1).getVolume()){
                    System.out.printf("最少倒水次数%d",current.getPourNum());
                    return current.getPourNum();
                }
            }

            /**
             * 遍历当前状态下所有瓶子可能倒水后达到的状态
             */
            for (int i = 0; i < bottleList.size(); i++) {
                for (int j = 0; j < bottleList.size(); j++) {
                    if (i == j)
                        continue;
                    List<PourWaterState.Bottle> tmp = new ArrayList<>();
                    try {
                        deepCopy(bottleList,tmp);
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                    PourWaterState.Bottle bottle1 = tmp.get(i);
                    PourWaterState.Bottle bottle2 = tmp.get(j);

                    if (bottle1.getVolume()>0){
                        if (bottle1.getVolume()> (bottle2.getCapacity() - bottle2.volume)){
                            bottle1.setVolume(bottle1.getVolume() - bottle2.getCapacity() + bottle2.volume);
                            bottle2.setVolume(bottle2.capacity);
                        }else if (bottle1.getVolume() < (bottle2.getCapacity() - bottle2.volume)){
                            bottle2.setVolume(bottle2.getVolume() + bottle1.getVolume());
                            bottle1.setVolume(0);
                        }

                        next = new PourWaterState(tmp,pourNum+1);

                        if (!vis.contains(next)){
                            q.offer(next);
                            vis.add(next);
                        }
                    }
                }
            }
        }

        return -1;
    }

    private static void deepCopy(List<PourWaterState.Bottle> originalList, List<PourWaterState.Bottle> newList) throws CloneNotSupportedException {
        for (PourWaterState.Bottle bottle : originalList) {
            newList.add(bottle.clone()); //假设每个元素都实现了clone()方法
        }
    }

    private static class PourWaterState{

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PourWaterState that = (PourWaterState) o;
            return Objects.equals(bottles, that.bottles);
        }

        @Override
        public int hashCode() {
            return Objects.hash(bottles);
        }

        private static class Bottle implements Cloneable{
            private int capacity; //杯子容量
            private int volume; //杯子里水的量

            public Bottle(int capacity, int volume) {
                this.capacity = capacity;
                this.volume = volume;
            }

            public int getCapacity() {
                return capacity;
            }

            public void setCapacity(int capacity) {
                this.capacity = capacity;
            }

            public int getVolume() {
                return volume;
            }

            public void setVolume(int volume) {
                this.volume = volume;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Bottle bottle = (Bottle) o;
                return capacity == bottle.capacity && volume == bottle.volume;
            }

            @Override
            protected Bottle clone() throws CloneNotSupportedException {
                return (Bottle)super.clone();
            }

            @Override
            public int hashCode() {
                return Objects.hash(capacity, volume);
            }
        }

        /**
         * 维护每个瓶子里的水的量
         */
        private List<Bottle> bottles = new ArrayList<>();

        /**
         * 标记当前状态的最少倒水次数
         */
        private int pourNum;

        public PourWaterState(List<Bottle> bottles, int pourNum) {
            this.bottles = bottles;
            this.pourNum = pourNum;
        }

        public List<Bottle> getBottles() {
            return bottles;
        }

        public void setBottles(List<Bottle> bottles) {
            this.bottles = bottles;
        }

        public int getPourNum() {
            return pourNum;
        }

        public void setPourNum(int pourNum) {
            this.pourNum = pourNum;
        }
    }


    /**
     * #算法6、DFS 深度优先搜索算法（Depth First Search）
     * <p>
     * 中序遍历：递归调用方法，方法先应用方法处理左子树，然后是对当前节点元素做实际工作，最后是应用方法处理右子树。（方法栈顺序：左子树→当前节点→右子树→当前节点，方法返回）
     * <p>
     * 后序遍历：递归调用方法，方法先应用方法处理左子树，然后是应用方法处理右子树，最后是对当前节点元素做实际工作。（方法栈顺序：左子树→右子树→当前节点，方法返回）
     * <p>
     * 先序遍历：递归调用方法，方法先对当前节点元素做实际工作，然后是应用方法处理左子树，最后是应用方法处理右子树。（方法栈顺序：当前节点→左子树→右子树→当前节点，方法返回）
     * <p>
     * 给定中序、后序、先序中其中2种遍历（中序和后序 or 中序和先序）的结果，可以唯一确定一颗二叉树。
     * 比如给出
     * 先序：2 7 1 6 5 3 8 9 4
     * 中序：1 7 5 6 3 2 8 4 9
     * 后序：1 5 3 6 7 4 9 8 2
     * <p>
     * 先序中确定2是根，中序中确定1 7 5 6 3是左子树，8 4 9是右子树；
     * 继续回看先序中7是左子树根，中序中确定1是左子树的左子树，5 6 3是左子树的右子树；
     * 以此类推，总能通过先序结果确定根，中序确定左右子树。
     * 后序和中序也是一样的效果。
     * 但是给出先序和后序的遍历结果，无法确定左右子树，所以无法唯一确定二叉树的结构。
     * <p>
     * 深度优先搜索的关键在于解决“当下该如何做”？
     * 至于下一步怎么做，则与当前一样(只是参数不同而已)
     * 深度优先搜索的基本模型:
     * void dfs(int step){
     *     特殊情况处理(一般是结束递归的情况)
     *     枚举当前每一种可能for(i=1;i<=n;++i){
     *         在枚举的每一种可能中，递归dfs(step+1);
     *     }
     * }
     *
     *
     *
     *
     */

    /**
     * EXAMPLE1：输入一个正整数n,请按照字典序输出1-n的全排列，每个排列输出一行，每个数字后面跟一个空格
     * Sample Input
     * 3
     * Sample output
     * 123
     * 132
     * 213
     * 231
     * 312
     * 321
     *
     */
    static class Permutation{
        static int n; //标记数字上限
        static int[] vis; //标记在组合时每个数字是否使用过
        static int[] num; //标记某次排列的数字排列

        public static void exe(int n){
            Permutation.n = n+1;
            vis = new int[n+1];
            num = new int[n+1];

            permutation(1); //从第一个数字开始取
        }

        public static void permutation(int step){

            /**
             * 输出5个数字，当step=6，即5个数字已经定好后输出
             */
            if (step == n){
                for (int i = 1; i < n; i++) {
                    System.out.print(num[i] + " ");
                }
                System.out.println();
            }

            /**
             * 从1开始输出，所以i起始值是1
             * 第step个数字，从vis[]中选择一个未被使用的数字
             * 递归继续考虑第step+1个数字
             *
             * 套用先序遍历树的模型：
             *  先处理当前节点（取第step个数字）
             *  递归处理左右节点（取第step+1个数字）
             *  回溯处理（将第step步取的数字变为可选）
             */
            for (int i = 1; i < n; i++) {
                if (vis[i] == 0){ //看标志位 1-5数字是否已经取过
                    num[step] = i; //没取过则放入待输出的数组num
                    vis[i] = 1; //标志某个数字i已经取过
                    permutation(step+1); //继续取下一个数字
                    vis[i] = 0; //走到这一步说明给定前num[step-1]数字排列，num[step]=i时剩余数字所有可能性取完，将i重新放入待选数字池（回溯概念），num[step]=i+1继续遍历所有可能性
                }
            }
        }
    }

    /**
     * EXAMPLE2：给出一个起始位置S，需要在指定的时间点到达终点位置D，其中存在不能通行的障碍物X，每走一格花费时间1
     * 给出迷宫和迷宫开门的时间点T，求是否存在路线可以在指定的时间到达终点D
     * Sample Input
     * 4 4 5
     * S.X.
     * ..X.
     * ..XD
     * ....
     * Sample Output
     * NO（最短花费时间也要7，不能在时间等于5的时候到达终点）
     */
     static class EscapeMap{
         static Boolean escape; //标记是否存在满足题目要求的路线
         static int[][] direction = {{0,-1},{0,1},{1,0},{-1,0}}; //标记每次走的4个可能的方向
         static char[][] map; //存放地图
         static int n,m,t,di,dj; //n、m:输入的地图的大小 t:要求到达终点的时间 di、dj:目的地坐标

         public static void exe(int openTime, char[][] input){

             n = input.length;
             m = input[0].length;
             map = new char[n+2][m+2]; //初始化的地图上下左右扩大一格，以免后续算法运行越界，简化了是否数组越界检查
             t = openTime;

             int si = 0, sj = 0, wall = 0, tmp; //si、sj:出发地坐标 wall:障碍物数量 tmp:存放判断是否有必要继续运行算法的变量

             /**
              * 读取输入的地图信息
              */
             for (int i = 1; i <= n; i++) {
                 for (int j = 1; j <= m; j++) {
                     map[i][j] = input[i-1][j-1];
                     if (map[i][j] == 'S'){
                         si = i;
                         sj = j;
                     }else if (map[i][j] == 'D'){
                         di = i;
                         dj = j;
                     }else if (map[i][j] == 'X'){
                         wall++;
                     }

                     if (n*m-wall <= t){
                         System.out.println("NO");
                         return;
                     }
                 }
             }

             /**
              *
              * 奇偶性剪枝：
              * 可以把Map看成这样:
              * 010101
              * 101010
              * 010101
              * <p>
              * 0->1->0->...->0: 0->0 必然经过2的倍数步
              * 0->1->0->...->1: 0->1 必然经过2的倍数步-1
              * 0->1或1->0 必然是奇数步
              * 0->0走1->1 必然是偶数步
              * <p>
              * 由于题目要求必须每1单位时间都要移动，而剩余时间和起始和终点的坐标差同步变化，
              * 所以只要看初始状态的(剩余时间-剩余坐标距离)是否满足是偶数，如果是奇数，直接返回
              * 因为如果剩余时间如果是奇数，剩余坐标距离也一定要是奇数；如果剩余时间如果是偶数，剩余坐标距离也一定要是偶数；
              * 而奇数-奇数、偶数-偶数，差都是偶数
              *
              */
             tmp = t - Math.abs(si - di) - Math.abs(sj - dj);
             if (tmp % 2 == 1){
                 System.out.println("NO");
                 return;
             }

             escape = false;
             map[si][sj] = 'X'; //走过的初始坐标标为障碍物，以免后续递归再走回来
             escape(si,sj,0); //开始递归

             if (escape){
                 System.out.println("YES");
             }else {
                 System.out.println("NO");
             }
         }

        /**
         *
         * @param si 出发横坐标
         * @param sj 出发纵坐标
         * @param cnt 已花费时间
         */
        public static void escape(int si,int sj,int cnt){
             if (si > n || sj > m || si <= 0 || sj <= 0) //越界直接返回
                 return;
             if (cnt == t && si == di && sj == dj){  //递归存在满足题目要求的状态，返回
                 escape = true;
                 return;
             }

            int tmp = (t - cnt) - Math.abs(si - di) - Math.abs(sj - dj);
            if (tmp < 0) //在递归尝试过程中，如果出现剩余坐标距离大于剩余时间，则直接返回
                return;
            for (int i = 0; i < 4; i++) { //尝试4个方向移动
                if (map[si+direction[i][0]][sj+direction[i][1]] != 'X'){ //如果不是障碍物
                    map[si+direction[i][0]][sj+direction[i][1]] = 'X'; //走过的标为障碍物，以免后续递归再走回来
                    escape(si+direction[i][0],sj+direction[i][1],cnt + 1); //在新的坐标上递归继续走下一步
                    map[si+direction[i][0]][sj+direction[i][1]] = '.'; //如果下一步最终递归走不通，则回溯，将已经标为障碍物的坐标变回来
                }
            }
        }
    }

    /**
     * #算法7、二分匹配算法（bipartite graph）
     * <p>
     * 如果一个图 G=<V,E> 中的顶点集合 V 可以被划分成不相交的两部分，
     * 并且 G 中所有的边的两个顶点都分别属于这两个部分，
     * 那么称 G 为二分图（或者二部图）(Bipartite Graph)
     * <p>
     * 顶点覆盖数：G=<V,E> ，如果 V 的一个子集 V0，使得所有边至少有一个点在 V0 中，则称 V0 为 G 的一个顶点覆盖。
     * 最小顶点覆盖数：含有最少顶点数的一个顶点覆盖。
     * 匹配数：G=<V,E> ，如果 E 的一个子集 E0，其中任意两条属于 E0 的边都没有公共顶点，则称 E0 是 G 的一个匹配。
     * 最大匹配数：含有最多边数的一个匹配。
     * <p>
     * 增广路（argumenting path）：对于二分图 G 和对于 G 的一个匹配 E0 而言，若存在一条路径<e0, e1, …, ek>，
     * 起始点和终点均为“未匹配的点”，并且“未匹配边”和“匹配边”交替出现（开始和结尾均为“未匹配边”），那么称这条路径为增广路。
     * <p>
     * 引理1：最大匹配中不存在增广路。
     *  证明：按照增广路的定义，如果存在增广路匹配数+1，与最大匹配矛盾，得证
     * 引理2：最小顶点覆盖的顶点数量 ≥ 最大匹配中的边数
     *  证明：由于最大匹配是一个匹配，边之间没有公共点，那么每条边中至少有一个点属于最小顶点覆盖，因而得证。
     * 引理3：最小顶点覆盖的顶点数量 = 最大匹配中的边数
     *  证明：使用“半条增广路”、“增广路”证明：（Konig定理）：https://reimuyk.github.io/2021-04-01-konig-theorem/
     * <p>
     * 参考：https://blog.csdn.net/m0_75027890/article/details/128890405
     * 最小路径覆盖：在一个有向图中，找出最少的路径，使得这些路径经过了所有的点。
     * 最小不相交路径覆盖：每一条路径经过的顶点各不相同。
     * 最小可相交路径覆盖：每一条路径经过的顶点可以相同。
     * <p>
     * 对DAG图做一个变形，就可以将DAG图问题转为二分图问题。
     * 1->3->4,2->3->5
     * =》
     * 1 ----       1'
     * 2 --- \      2'
     * 3 -- \-\---- 3'
     *    \\   \
     * 4   \\---\-- 4'
     * 5    \------ 5'
     * <p>
     * 引理4：DAG图(Directed Acyclic Graph)的最小路径覆盖数（最小不相交路径覆盖）= 节点数(n)-最大匹配数(m)
     * 最小可相交路径覆盖 = 节点数(n)-最大匹配数(m)，不过二分图需要做一下变化，即将那些间接可连通的点也要画上边
     * 最大独立集：选最多的点，满足两两之间没有边相连。
     * 引理5：最大独立集 = 节点数(n)-最大匹配数(m)
     *
     *
     */

    /**
     * EXAMPLE1：匈牙利算法，也就是应用增广路，我们可以通过不停地找增广路来增加匹配中的匹配边和匹配点。找不到增广路时，达到最大匹配。
     * DFS实现
     * 参考：https://www.cnblogs.com/liuzhen1995/p/6736238.html
     *
     */
    static class HungarianDFS{

        public static int n = 0, m = 0;  //二分图的左边顶点数目 = n，右边顶点数目 = m
        /*
         * 参数map：给定的二分图，map[i][j]等于1表示左侧顶点i到右侧顶点j连通，为0则表示不连通
         * 参数match：match[i] = u表示右侧顶点i与左侧顶点u连接，表示已经匹配
         * 参数start：当前左侧start顶点出发，寻找增广路径
         * 参数check：针对某个左侧点，当前右侧哪些节点被尝试过
         * 函数功能：如果能够找到已顶点start开始的增广路径返回true，否则返回false
         */
        public static boolean dfs(int[][] map, boolean[] check, int[] match, int start) {
            for(int i = 0;i < m;i++) { //针对某个左侧顶点start，遍历尝试右侧顶点
                if(!check[i] && map[start][i] == 1) { //如果右侧顶点未被尝试过且可以连通
                    check[i] = true; //标记已经被尝试
                    if(match[i] == -1 || dfs(map, check, match, match[i])) { //如果未被匹配或递归继续尝试已经匹配的左侧顶点可以找到新的未被匹配的边
                        match[i] = start; //将当前尝试的右侧顶点与新的左侧顶点连通（原来连通的左侧顶点已经连了另一个顶点）
                        return true; //找到一条成功的增广路径，返回true
                    }
                }
            }
            return false; //不能找到一条新的增广路径，返回false
        }

        public static int getMaxNum(int[][] map) {
            m = map[0].length; //列数就是右侧顶点数目
            n = map.length; //行数就是左侧顶点数目
            int count = 0;
            int[] match = new int[m];
            for(int i = 0;i < m;i++) //初始化右侧顶点均未被匹配
                match[i] = -1;
            for(int i = 0;i < n;i++) {
                boolean[] check = new boolean[m];  //每次考虑新的左侧顶点，初始化m部分顶点均未被访问
                if(dfs(map, check, match, i))  //从顶点i出发能够得到一条增广路径
                    count++;
            }
            return count;
        }

    }

    /**
     * BFS实现
     * 参考：https://www.cnblogs.com/lsgwr/p/17308237.html
     */
    static class HungarianBFS{

        public interface IGraph {
            /**
             * 返回顶点数
             */
            int V();

            /**
             * 返回边数
             */
            int E();

            /**
             * 确保v在有效范围
             *
             * @param v 顶点编号
             */
            void validateVertex(int v);

            /**
             * 向邻接表/邻接矩阵中添加一条边
             *
             * @param v 起点
             * @param w 终点
             */
            void addEdge(int v, int w);

            /**
             * 判断顶点v到顶点w之间是否有路径连通
             *
             * @param v 起点
             * @param w 终点
             * @return 是否有路径连通
             */
            boolean hasEdge(int v, int w);

            /**
             * 顶点的度，即有多少条临边
             *
             * @param v 顶点
             * @return 多少条临边
             */
            int degree(int v);

            /**
             * 删除边v-w
             *
             * @param v 顶点v
             * @param w 顶点w
             */
            void removeEdge(int v, int w);

            /**
             * 打印当前的图
             */
            void show();

            /**
             * 返回顶点v的邻接表的迭代器，方便进行循环遍历v的所有邻接点
             *
             * @param v
             * @return
             */
            Iterable<Integer> adj(int v);
        }

        static class Graph implements IGraph, Cloneable {
            /**
             * 图的顶点数
             */
            private int vertices;
            /**
             * 图的边数
             */
            private int edges;
            /**
             * 当前图是有向图还是无向图
             */
            private boolean directed;
            /**
             * 邻接表，采用vector套vector的形式
             */
            private TreeSet<Integer>[] adj;
            /**
             * 所有顶点的入度
             */
            private int[] inDegrees;
            /**
             * 所有顶点的出度
             */
            private int[] outDegrees;

            public Graph(boolean directed) {
                this(0, directed);
            }

            public Graph(int vertices, boolean directed) {
                this.vertices = vertices;
                this.edges = 0;
                this.directed = directed;
                inDegrees = new int[vertices];
                outDegrees = new int[vertices];
                // 泛型数组需要强制转换，可以认为是Java语言的缺陷
                adj = (TreeSet<Integer>[]) new TreeSet[vertices];
                for (int i = 0; i < vertices; i++) {
                    // 每个顶点都有一组邻边组成邻接表，用TreeSet可以提高性能
                    adj[i] = new TreeSet<>();
                }
            }


            /**
             * 返回顶点数
             */
            @Override
            public int V() {
                return vertices;
            }

            /**
             * 设置图的顶点数
             *
             * @param vertices 顶点数
             */
            public void setVertices(int vertices) {
                this.vertices = vertices;
                inDegrees = new int[vertices];
                outDegrees = new int[vertices];
                // 泛型数组需要强制转换，可以认为是Java语言的缺陷
                adj = (TreeSet<Integer>[]) new TreeSet[vertices];
                for (int i = 0; i < vertices; i++) {
                    // 每个顶点都有一组邻边组成邻接表，用TreeSet可以提高性能
                    adj[i] = new TreeSet<>();
                }
            }

            /**
             * 返回边的数目
             */
            @Override
            public int E() {
                return edges;
            }

            @Override
            public void validateVertex(int v) {
                assert (v >= 0 && v < vertices);
            }

            /**
             * 添加边,在顶点v和顶点w之间建立一条边
             */
            @Override
            public void addEdge(int v, int w) {
                // 先确保元素不越界
                validateVertex(v);
                validateVertex(w);
                // v和w之间是连接地,不需要再加一次，就直接退出。这样是为了防止平行边，但是从hasEdge实现可知成本较高，所以就by了
                // 平行边可以在所有边加完之后统一去掉，自己实现
                // if (hasEdge(v, w)) {
                //    return;
                // }

                // v=w会生成自环边
                if (v == w) {
                    throw new IllegalArgumentException("Self Loop is Detected!");
                }
                adj[v].add(w);
                if (!directed) {
                    // 无向图实际上是双向图，所以w到v也应该为true.如果是有向图这步就不用处理了
                    adj[w].add(v);
                } else {
                    // 有向图，入度和出度都要更新，因为是v-->w，所以v的出度+1，w的入度+1
                    outDegrees[v]++;
                    inDegrees[w]++;
                }
                // 边加1
                edges++;
            }

            /**
             * v和w之间是否存在边
             */
            @Override
            public boolean hasEdge(int v, int w) {
                // 先确保元素不越界
                validateVertex(v);
                validateVertex(w);
                // v的邻接表中是否有w
                return adj[v].contains(w);
            }

            @Override
            public int degree(int v) {
                // Todo:针对有向图的度比较麻烦，第13章最后讲
                if (directed) {
                    throw new RuntimeException("degree()方法仅能用于无向图");
                }
                return adj[v].size();
            }

            /**
             * 顶点v的入度(其他边指向v的条数)
             */
            public int inDegree(int v) {
                if (!directed){
                    throw new RuntimeException("inDegree()方法仅适用于有向图");
                }
                validateVertex(v);
                return inDegrees[v];
            }

            /**
             * 顶点v的出度(从v指向其他顶点的边数)
             */
            public int outDegree(int v) {
                if (!directed){
                    throw new RuntimeException("outDegree()方法仅适用于有向图");
                }
                validateVertex(v);
                return outDegrees[v];
            }

            @Override
            public void removeEdge(int v, int w) {
                validateVertex(v);
                validateVertex(w);
                if (adj[v].contains(w)){
                    // 边数-1
                    edges--;
                    // v的邻接点包含w才进行删除边
                    adj[v].remove(w);
                    if (!directed) {
                        // 无向图才需要删除边w-v
                        adj[w].remove(v);
                    }else {
                        // 有向图需要更新入度和出度.v->w所以v的出度-1，w的入度-1
                        outDegrees[v]--;
                        inDegrees[w]--;
                    }
                }
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("顶点数V = %d, 边数E = %d\n", vertices, edges));
                // 遍历所有顶点vertex(顶点都是按照编号顺序来地)，顶点是用从0开始的连续正整数表示时v才代表顶点，
                // 如果顶点不是用连续的正整数或者是用字符等形式来表示时，就要建立顶点数下标v和顶点实际含义的映射关系了，可以用map来表示
                // 参考 https://coding.imooc.com/learn/questiondetail/133447.html
                // vertices是vertex的复数形式，两者都是顶点的意思
                for (int v = 0; v < vertices; v++) {
                    sb.append(String.format("vertex %d:\t", v));
                    // 遍历顶点vertex的所有邻接点
                    for (Integer w : adj[v]) {
                        sb.append(String.format("%d\t", w));
                    }
                    sb.append("\n");
                }
                return sb.toString();
            }

            @Override
            public void show() {
                System.out.println(toString());
            }


            /**
             * 返回顶点v的所有临边
             * 由于java使用引用机制，返回一个Iterable对象不会带来额外开销,TreeSet、Vector、HashSet等都实现了Iterable接口
             */
            @Override
            public Iterable<Integer> adj(int v) {
                validateVertex(v);
                // 邻接表本身v处就是表达地v的所有邻接点
                return adj[v];
            }

            /**
             * 实现Graph对象的深拷贝(adj要完成拷贝过去)
             *
             * @return 深拷贝后的graph，在调用时不会改变原对象
             * @throws CloneNotSupportedException 不支持Clone的异常
             */
            @Override
            public Object clone() {
                // 实现深拷贝
                Graph cloned = null;
                try {
                    cloned = (Graph) super.clone();
                    cloned.adj = new TreeSet[vertices];
                    for (int v = 0; v < vertices; v++) {
                        cloned.adj[v] = new TreeSet<>();
                        for (int w : adj[v]) {
                            cloned.adj[v].add(w);
                        }
                    }
                    return cloned;
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                // 出异常了就返回null
                return null;
            }

            /**
             * 返回图片是否是有向图
             */
            public boolean isDirected() {
                return directed;
            }
        }

        static class GraphDFSBiPartitionDetect {
            private Graph graph;

            /**
             * 存储顶点是否被访问的数组
             */
            private boolean[] visited;

            /**
             * 存放图的深度优先遍历的结果
             */
            private List<Integer> orderList = new ArrayList<>();

            /**
             * 颜色数组，存储每个节点的颜色
             */
            private int[] colors;

            /**
             * 是否是二分图,默认成是二分图
             */
            private boolean biPartition = true;

            public GraphDFSBiPartitionDetect(Graph graph) {
                this.graph = graph;
                // 初始化访问数组，用图的顶点个数来访问
                visited = new boolean[graph.V()];
                // 初始化颜色数组
                this.colors = new int[graph.V()];
                // 初始化colors为-1，后面检测到二分图会更新这个数组，会有0和1两种
                Arrays.fill(this.colors, -1);
                // 从dfs(0)改成下面的代码，可以支持非连通的图,不用考虑连通分量的时候直接用dfs(v)即可
                for (int v = 0; v < graph.V(); v++) {
                    if (!visited[v]) {
                        // 第一个节点染成蓝色(0)
                        if (!dfs(v, 0)) {
                            // 某一个联通分量不是二分图，整个图就不是二分图了，直接返回，不再检测剩下的二分图了
                            biPartition = false;
                            // 一旦检测到不是二分图立马跳出，一定别忘
                            break;
                        }
                    }
                }
            }

            public Iterable<Integer> getOrderList() {
                return orderList;
            }

            /**
             * 是否是二分图
             */
            public boolean isBiPartition() {
                return biPartition;
            }

            /**
             * 获取图划分后的二分图数组
             */
            public int[] getColors() {
                return colors;
            }

            /**
             * dfs过程中检测当前图是否是二分图
             *
             * @param v     当前的顶点
             * @param color v点的染色
             * @return 是否是二分图
             */
            private boolean dfs(int v, int color) {
                visited[v] = true;
                orderList.add(v);
                colors[v] = color;
                for (Integer w : graph.adj(v)) {
                    if (!visited[w]) {
                        // 颜色只有蓝(0)、绿(1)两种，w是v的邻接点，根据二分图的检测原理，w、v的颜色必须相反，只能一蓝一绿，蓝+绿 = 0 + 1 = 1,所以1-v的颜色 = 1-color = w的颜色
                        if (!dfs(w, 1 - color)) {
                            // 返回false表示不是二分图
                            return false;
                        }
                    } else if (colors[w] == colors[v]) {
                        // 如果w已经访问过，但是w作为v的邻接点和v的颜色相同，说明不是二分图
                        return false;
                    }
                }
                return true;
            }
        }

        private static Scanner scanner;

        public static void init(Graph graph, String filepath) {
            readFile(filepath);

            try {
                int V = scanner.nextInt();
                if (V < 0) {
                    throw new IllegalArgumentException("number of vertices in a Graph must be nonnegative");
                }
                // 1.设置顶点数
                graph.setVertices(V);
                // 2.设置边，边数在下面的addEdge()中累计
                int E = scanner.nextInt();
                if (E < 0) {
                    throw new IllegalArgumentException("number of edges in a Graph must be nonnegative");
                }
                for (int i = 0; i < E; i++) {
                    int v = scanner.nextInt();
                    int w = scanner.nextInt();
                    assert v >= 0 && v < V;
                    assert w >= 0 && w < V;
                    graph.addEdge(v, w);
                }
                // 3.初始化完顶点数和边数后显示下
                System.out.println(String.format("顶点数V = %d, 边数E = %d", graph.V(), graph.E()));
            } catch (InputMismatchException e) {
                String token = scanner.next();
                throw new InputMismatchException("attempts to read an 'int' value from input stream, but the next token is \"" + token + "\"");
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException("attemps to read an 'int' value from input stream, but there are no more tokens available");
            }

        }

        private static void readFile(String filepath) {
            assert filepath != null;
            try {
                File file = new File(filepath);
                if (file.exists()) {
                    FileInputStream fis = new FileInputStream(file);
                    scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");
                    scanner.useLocale(Locale.ENGLISH);
                } else {
                    throw new IllegalArgumentException(filepath + "doesn't exist.");
                }
            } catch (IOException ioe) {
                throw new IllegalArgumentException("Could not open " + filepath, ioe);
            }
        }

        /**
         * 要找最大匹配的二分图
         */
        private Graph graph;
        /**
         * 最大匹配的值
         */
        private int maxMatch = 0;

        /**
         * match[v]=w表示顶点v在图中匹配的顶点是w
         */
        private int[] matching;

        public HungarianBFS(Graph graph) {
            GraphDFSBiPartitionDetect biPartitionDetect = new GraphDFSBiPartitionDetect(graph);
            if (!biPartitionDetect.isBiPartition()) {
                throw new IllegalArgumentException("匹配问题必须针对地是二分图!");
            }
            this.graph = graph;
            // 二分图中的顶点颜色区分，一半为0(左侧)，一半为1(右侧)
            int[] colors = biPartitionDetect.getColors();
            int V = graph.V();
            matching = new int[V];
            Arrays.fill(matching, -1);
            for (int v = 0; v < V; v++) {
                // 只遍历二分图中左侧还未被遍历的点(颜色为0)
                if (colors[v] == 0 && matching[v] == -1) {
                    // 每个左侧的点都进行一次bfs来找增广路径
                    if (bfs(v)) {
                        // 本地bfs找到了一条增广路径
                        maxMatch++;
                    }
                }
            }
        }

        /**
         * bfs找增广路径
         *
         * @param source bfs遍历的起点
         * @return 本次bfs是否找到了增广路径
         */
        private boolean bfs(int source) {
            Queue<Integer> queue = new ArrayDeque<>();
            int V = graph.V();
            // 记录节点访问顺序的数组，每次bfs都需要新建自己的pre
            int[] pre = new int[V];
            Arrays.fill(pre, -1);
            queue.add(source);
            // 起点的上一个访问节点认为是自己
            pre[source] = source;
            while (!queue.isEmpty()) {
                // v一定要用二分图左侧的顶点(本类中0标记)
                int v = queue.remove();
                for (int w : graph.adj(v)) {
                    // w是二分图右侧的顶点
                    if (pre[w] == -1) {
                        if (matching[w] != -1) {
                            // w已经匹配
                            pre[w] = v;
                            // 和w匹配的边的前一条边记为w
                            pre[matching[w]] = w;
                            // 添加左侧的点到queue中，queue中只存左侧的点
                            queue.add(matching[w]); //尝试右侧顶点，但是已经被某个左侧顶点匹配时，继续递归，BFS就是入队列中
                        } else {
                            // 在二分图右侧找到了一个未匹配的点，即找到了增广路径
                            pre[w] = v;
                            List<Integer> augPath = getAugPath(pre, v, w);
                            // 匈牙利算法核心：匹配状态取反。即匹配边变非匹配边、非匹配边变匹配边
                            for (int i = 0; i < augPath.size(); i += 2) {
                                matching[augPath.get(i)] = augPath.get(i + 1);
                                matching[augPath.get(i + 1)] = augPath.get(i);
                            }
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        /**
         * 获取增广路径详情
         *
         * @param pre   节点访问顺序数组
         * @param start 路径起点
         * @param end   路径终点
         * @return 路径上的点的顺序列表
         */
        private List<Integer> getAugPath(int[] pre, int start, int end) {

            List<Integer> path = new ArrayList<>();
            int cur = end;
            while (cur != start) {
                path.add(cur);
                cur = pre[cur];
            }
            path.add(start);
            // 增广路径无论正反都是增广路径，所以这里逆序与否均可了
            Collections.reverse(path);
            return path;
        }

        /**
         * 获取最大匹配数
         */
        public int getMaxMatch() {
            return maxMatch;
        }

        /**
         * 当前的最大匹配是否是完全匹配(也可以叫完全匹配，即所有的点都有二分图另一侧的点和自己匹配而且匹配对互不干扰O)
         */
        public boolean isPrefectMatch() {
            return maxMatch * 2 == graph.V();
        }

    }


    /**
     * #算法8、组合博弈入门（simple game theory）
     * <p>
     * 组合游戏：
     * 1、有两个玩家;
     * 2、游戏的操作状态是一个有限的集合(比如:限定大小的棋盘);
     * 3、游戏双方轮流操作;
     * 4、双方的每次操作必须符合游戏规定;
     * 5、当一方不能将游戏继续进行的时候，游戏结束，同时，对方为获胜方;
     * 6、无论如何操作，游戏总能在有限次操作后结束;
     * <p>
     * 必败点：当前状态下，轮到行动的玩家如何操作都会在对方理性的操作下输掉游戏；
     * 必胜点：当前状态下，轮到行动的玩家只要理性操作必会使对方输掉游戏；
     * 终结点：游戏按照规则结束的状态；
     * <p>
     * 1、所有终结点都是必败点（面对终结点，玩家无法行动；而且游戏规则下可能游戏终结点有多个）；
     * 2、从任何必胜点操作，至少有一种方法可以进入必败点（即按照理性在必胜点行动的玩家，至少有一种策略使得下一个行动的玩家必败）;
     * 3、无论如何操作，从必败点都只能进入必胜点（即在必败点行动的玩家，所有策略都使得下一个行动的玩家按照理性行动必胜）；
     * <p>
     * 组合游戏算法逻辑：
     * step1：将所有终结位置标记为必败点
     * step2：将所有一步操作能进入必败点的位置标记为必胜点
     * step3：针对所有必胜点，如果从某个点开始的所有一步操作都只能进入该必胜点，则将该点标记为必败点
     * step4：如果在针对所有必胜点按照步骤3未能找到新的必败点，则算法终止；否则，返回到步骤2继续寻找下一轮必胜点
     * <p>
     * example：23张扑克牌，轮流取牌，每人每次限取1/2/3张，扑克牌取完游戏结束，最后取牌的一方获胜。
     *
     * <p>
     * Nim-sum：尼姆博弈中定义的运算，即将多个数字进行二进制按位异或运算。
     * 在尼姆博弈中，Nim-sum的值为0时，当前先手玩家必败；当Nim-sum不为0时，先手玩家有必胜策略。
     * 因为针对Nim-sum不为0的情况，至少有一种通过改变某个数字的二进制位1/0，使得Nim-sum变为非0；针对Nim-sum为0的情况，总会使得Nim-sum变成非0，也就是对应上面对必胜点和必败点的定义了。
     * <p>
     * example：3堆扑克牌（分别5，7，9张），每人取任意一堆中的任意张牌，最后取完的人赢
     *
     * <p>
     * Sprague-Grundy Function：可以将组合博弈游戏的各个状态描述称状态转移图，也就是有向无环图，针对图中的每个节点定义一个sg值。
     * 其中，sg(x)= min{n ≥0:n<>sg(y) for y 属于 F(x)}，即X节点的SG值是去除X的后继节点的SG值后的最小的非负整数。
     * 经过这样计算，有以下结论
     * 必败点：当节点x的Sg(x) = 0
     * 必胜点：当节点x的Sg(x) > 0
     * 因为按照sg值的定义，也是符合对应上面对必胜点和必败点的定义的效果的。
     * <p>
     * example：3堆扑克牌（分别5，7，9张），每人取任意一堆中的1/2/3张牌，最后取完的人赢
     * <p>
     * 以上问题无法直接使用组合sg或Nim-sum来解决，可以将3堆扑克牌考虑成3个独立的小游戏，计算每一堆牌的sg值后，计算3堆牌的sg值的Nim-sum
     * 必胜点：Nim-sum不为0
     * 必败点：Nim-sum等于0
     *
     */

    /**
     * EXAMPLE1：
     * 三堆扑克牌，分别为5,7,9张；
     * 双方轮流取牌，每次可以选择任意一堆牌取走1、2、3张；
     * 最后取牌的一方获胜
     * 请问，先手的人是输还是赢？
     * <p>
     * 记忆化的DFS实现
     *
     */
    static class SimpleGame{

        //牌堆
        static int[] cards;
        //取牌规则
        static int[] rules;
        //一堆剩余不同牌的sg值
        static int[] sgs;

        static int sg(int p){

            //记录某个数量剩余牌是否计算过sg值，可能剩余0张牌，所以容量+1
            int[] memo = new int[sgs.length+1];

            int t; //针对一堆牌，按规则取一次后剩余的牌
            for (int i = 0; i < rules.length; i++) {
                t = p - rules[i];
                if (t < 0) //由于对取牌规则做了排序，最小取牌数剩余小于0即可退出，不用再尝试其他规则
                    break;
                if (sgs[t] == -1)
                    sgs[t] = sg(t);
                memo[sgs[t]] = 1;
            }

            for (int i = 0;  ; i++) {
                if (memo[i] == 0){
                    return i;
                }
            }
        }

        static void identify(int[] rules, int[] cards){
            Arrays.sort(rules);
            SimpleGame.rules = rules;
            SimpleGame.cards = cards;

            int max = Arrays.stream(SimpleGame.cards).max().getAsInt(); //最大牌堆数量作为存储sg数组的容量上限
            //需要存储初始数量牌的sg值，所以容量+1
            sgs = new int[max+1];
            Arrays.fill(sgs,-1); //赋值初始sg=-1，表示未计算
            sgs[0] = 0; //根据sg定义，初始节点的sg值=0

//            int sg = sg(SimpleGame.cards[0]);

            int S = 0;

            for (int card : cards) {
                if (sgs[card] == -1)
                    sgs[card] = sg(card);
                S = S ^ sgs[card]; //计算所有牌堆的sg值的Nim-sum
            }

            if (S == 0) {
                System.out.println("LOSE");
            } else {
                System.out.println("WIN");
            }
        }
    }

    /**
     * #算法9、拓扑排序及链式前向星
     * <p>
     * 节点的度：如果存在该节点指向其他节点或者其他节点指向该节点的边，则表示该节点的度+1
     * 入度：存在其他节点指向该节点的边，则表示该节点的度+1
     * 出度：存在该节点指向其他节点的边，则表示该节点的度+1
     * <p>
     * 拓扑排序就是应用入度概念和BFS算法来实现对有向无环图的节点排序
     * <p>
     * 参考：algorithm.TopSort
     *
     * <p>
     * 链式前向星：用2个数组（节点数组+边数组）实现的邻接表，解决二维数组表示的信息稀疏问题
     *
     */

    /**
     * #算法10、二分、三分查找及二分答案
     * <p>
     * 二分查找：前提数据是单调有序的
     * <p>
     * 三分查找：前提数据是有“凸性”的，即数据存在极值
     * LeftThird = (Left*2+Right)/3;
     * RightThird = (Left+Right*2)/3;
     * 每次循环比较 F(LeftThird)和F(RightThird)的大小，如果是存在极大值，
     * if(F(LeftThird) < F(RightThird))，在LeftThird和Right之间重新计算2个3分点，继续比较；
     * if(F(LeftThird) > F(RightThird))，在Left和RightThird之间重新计算2个3分点，继续比较；
     * if(F(LeftThird) = F(RightThird))，在LeftThird和RightThird之间重新计算2个3分点，继续比较；
     * <p>
     * 二分答案：类似二分查找，在答案可能的范围内进行二分查找的方法，直到找到最优答案。
     * 1、证明问题满足单调性
     * 2、确定问题的单调区间上下界；
     * 3、设计check()函数，判断当前值是否满足问题指定的限定条件；
     * 4、在单调区间上下界循环二分答案，
     * 若当前值不行，缩小一半查询范围求更优解；
     * 若当前值可行，为候选解，继续缩小一半查询范围求更优解；
     * 当单调区间查询完毕（或已达指定精度），查找结束。
     *
     */

    /**
     * EXAMPLE1：
     * 给出函数：F(x) = 6*x^7 + 8*x^6 + 7*x^3 + 5*x^2 - 10*x，
     * 求x在区间[0，100]时函数F(x)的最小值，结果精确到小数点后4位
     */
    public static double threePointSearch(double left, double right){

        double leftThird = (left*2+right)/3;
        double rightThird = (left+right*2)/3;
        if (Math.abs(leftThird - rightThird) < 0.0001){
            return leftThird;
        }else if (calculate(leftThird) > calculate(rightThird)){
            left = leftThird + 0.00001;
            return threePointSearch(left,right);
        }else if (calculate(leftThird) < calculate(rightThird)){
            right = rightThird - 0.00001;
            return threePointSearch(left,right);
        }else if (calculate(leftThird) == calculate(rightThird)){
            left = leftThird + 0.00001;
            right = rightThird - 0.00001;
            return threePointSearch(left,right);
        }

        return -1;

    }

    private static double calculate(double x){
        return 6 * Math.pow(x,7) +  8 * Math.pow(x,6) + 7 * Math.pow(x,3) + 5 * Math.pow(x,2) - 10 * x;
    }

    /**
     *  #算法11、最短路径问题（Dijkstra 狄杰斯特拉算法）
     *  <p>
     *  参考：algorithm.Dijkstra
     */


    /**
     * 视频：bilibili BV1ntsieoE6E
     *
     * @param args
     */
    public static void main(String[] args) {
//        int[] array = init(5);
//        merge(4,2);
//        merge(0,1);
//        merge(2,3);
//        merge(1,4);
//        System.out.println(Arrays.toString(array));
//
//        for (int i = 0; i < array.length; i++) {
//            find(i);
//        }
//        System.out.println(Arrays.toString(array));

//        int[][] ints = new int[3][3];
//        ints[0][0] = 1;
//
//        int circleNum = findCircleNum(new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}});
//        System.out.println(circleNum);


//        int time = 5;
//        int[][] dp = new int[time+1][11]; //首先初始化构造val[i][j]，这里复用了dp[i][j]
//        dp[1][4] = dp[1][4] + 1; //第1秒第4个位置掉落一个馅饼
//        dp[1][6] = dp[1][6] + 1;
//        dp[2][5] = dp[2][5] + 1;
//        dp[3][4] = dp[3][4] + 1;
//        dp[3][7] = dp[3][7] + 1;
//        dp[4][8] = dp[4][8] + 1;
//        dp[5][9] = dp[5][9] + 1;
//
//        System.out.println(layingMethod(4));
//
//        int i1 = catchPie(dp);
//        System.out.println(i1);

//        int[] array = {1, 10, 4, 7, 2, 5, 8, 3, 6, 9, 11, 12};
//        int[] array = {6, 1, 2, 7, 3, 8, 5};
//        int[] array = {6, 10, 11, 1, 7, 2, 6,10};
//        int[] array = {10, 11, 1, 2, 7, 5};
//        int[] array = {1, 3, 5, 2, 3, 4};

//        int result = maxLengthSubArrayLength(array);
//        System.out.println(result);
//        int[] a = maxLengthSubArray(array);
//        System.out.println(Arrays.toString(a));

//        int[] val = new int[]{1,2,3,4,5};
//        int[] cost = new int[]{5,4,3,2,1};
//        int volume = 10;
//        System.out.println(zero_one_bag(val,cost,volume));
//        System.out.println(full_bag(val,cost,volume));

//        List<PourWaterState.Bottle> bottles = new ArrayList<>();
//        bottles.add(new PourWaterState.Bottle(4,4));
//        bottles.add(new PourWaterState.Bottle(1,0));
//        bottles.add(new PourWaterState.Bottle(3,0));
//        pour_water(bottles);

//        Permutation.exe(5);

//        char[][] input = {{'S','.','X','.'},{'.','.','X','.'},{'.','.','X','D'},{'.','.','.','.'}};
//        char[][] input = {{'S','.','X','.'},{'.','.','X','.'},{'.','.','.','D'}};
//        EscapeMap.exe(5,input);

//        int[][] map = new int[4][3];
//        map[0][0] = 1;
//        map[1][0] = 1;
//        map[1][1] = 1;
//        map[1][2] = 1;
//        map[2][1] = 1;
//        map[3][2] = 1;
//        System.out.println(HungarianDFS.getMaxNum(map));
//
//        HungarianBFS.Graph graph = new HungarianBFS.Graph(true);
//        HungarianBFS.init(graph, "src/main/java/algorithm/interview/graph.txt");
//        HungarianBFS maxMatchingBFS = new HungarianBFS(graph);
//        System.out.println("最大匹配对数：" + maxMatchingBFS.getMaxMatch());

//        SimpleGame.identify(new int[]{1,2,3,4,5},new int[]{2,3,7,12});

        System.out.println(threePointSearch(0,100));
    }


}

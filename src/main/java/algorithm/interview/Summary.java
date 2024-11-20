package algorithm.interview;

import org.springframework.util.CollectionUtils;

import java.util.*;

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
     * 首先，确认:能否容易的得到初始状态的解?
     * 然后，假设:规模不大于N-1的状态已经得到解决。
     * 最后，重点分析:当规模扩大到N时，如何枚举出所有的情况，然后用子问题的状态(F(1)、F(2)、...F(N-1))表示出最终的状态F(N)(即:状态转移)
     *
     */

    /**
     * EXAMPLE1: 求骨牌的铺法数
     * <p>
     * 有个1xn的长方形，用1x1、1x2、1x3的骨牌铺满方格。例如:当n-3时为1x3的方格，此时共有四种铺法。
     * <p>
     *
     * 思路：从最后一块方格开始考虑，填满的话可以用1x1、1x2、1x3这三种骨牌。
     * 当使用1x1时，剩余n-1块方格未填满，f(n-1)表示之前n-1个方格的铺法，则n个方格在这种情形下就是f(n-1)种铺法；
     * 当使用1x2时，剩余n-2块方格未填满，f(n-2)表示之前n-2个方格的铺法，则n个方格在这种情形下就是f(n-2)种铺法；
     * 当使用1x3时，剩余n-3块方格未填满，f(n-3)表示之前n-3个方格的铺法，则n个方格在这种情形下就是f(n-3)种铺法；
     *
     * 以上所有可能性加起来，即f(n-1)+f(n-2)+f(n-3)，就是f(n)的铺法总数
     */

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
     * 视频：bilibili BV1DK421876p、BV1Xt4214741
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
//        int i1 = catchPie(dp);
//        System.out.println(i1);

        int[] array = {1, 10, 4, 7, 2, 5, 8, 3, 6, 9, 11, 12};
//        int[] array = {6, 1, 2, 7, 3, 8, 5};
//        int[] array = {6, 10, 11, 1, 7, 2, 6,10};
//        int[] array = {10, 11, 1, 2, 7, 5};
//        int[] array = {1, 3, 5, 2, 3, 4};

        int result = maxLengthSubArrayLength(array);
        System.out.println(result);
        int[] a = maxLengthSubArray(array);
        System.out.println(Arrays.toString(a));

    }


}

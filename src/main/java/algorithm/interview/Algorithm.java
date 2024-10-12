package algorithm.interview;


import java.util.*;

/**
 * @author ：li zhen
 * @description:
 * @date ：2023/12/27 13:25
 */
public class Algorithm {

    /**
     * 寻找数组的中心下标
     * 中心下标是数组的一个下标，其左侧所有元素相加的和等于右侧所有元素相加的和
     * 如果数组不存在中心下标，返回 -1 。如果数组有多个中心下标，应该返回最靠近左边的那一个。
     *
     * 双指针，遍历数组，sum递减元素，total递增元素，当sum=total，则取到该下标
     *
     * @param nums
     * @return
     */
    public static int pivotIndex(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        int total = 0;
        for (int i = 0; i < nums.length; i++) {
            total += nums[i];
            if (total == sum)
                return i;
            sum -= nums[i];
        }
        return -1;
    }

    /**
     * 遍历数组元素，total记录左边元素和，sum记录整个数组和
     * 应满足 2 * total + current = sum
     *
     * @param nums
     * @return
     */
    public static int pivotIndex1(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        int total = 0;
        for (int i = 0; i < nums.length; i++) {
            if (2 * total + nums[i] == sum) {
                return i;
            }
            total += nums[i];
        }
        return -1;
    }


    /**
     * 使用牛顿递归计算某个整数的平方根
     *
     * @param x
     * @return
     */
    public static double newton(int x) {
        if (x == 0) {
            return 0;
        }
        return sqrt(x, x);
    }

    private static double sqrt(double i, int x) {
        double res = (i + x / i) / 2; // i可以传任何已知的x的因数
        if (res == i) { //由于double存在精度，所以递归后一定出现相等的时候，如果相等，说明就是平方根
            return i;
        } else {
            return sqrt(res, x); //如果i≠i和x/i的平均值，则继续递归
        }
    }

    /**
     * 统计整数n以内的素数个数
     * 埃氏筛选法——以空间换时间的一种算法
     *
     * @param n
     * @return
     */
    public static int eratosthenes(int n) {
        boolean[] isComposite = new boolean[n]; //构造长度正好等于n的布尔数组，默认都表示是素数
        int count = 0; //素数计数器
        for (int i = 2; i < n; i++) { //从2开始遍历，0、1都不是素数，起始2是素数
            if (!isComposite[i]) { //正好i=2传入，2不是合数(即表示是宿素数)
                count++; //计数器+1
                for (int j = i * i; j < n; j += i) { //从i²开始的所有小于n的数标记为合数，减少了外层遍历次数
                    isComposite[j] = true;
                }
            }
        }
        return count;
    }

    /**
     * 移除nums数组中所有重复的项，并返回去重后的数据数量
     * 前提：nums数组是一个排序好的数组
     * 效果：不使用额外的空间进行复制，将nums数组中的非重复项前移到原数组前面的位置
     * 原理：i指针、j指针，j指针不断后移，如果nums[i] != nums[j]，就把nums[i++]的位置替换为非重复项nums[j]，同时i指针也后移
     *
     * @param nums
     * @return
     */
    public static int removeDuplicate(int[] nums) {
        if (nums.length == 0)
            return 0;

        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        return i;
    }

    /**
     * 求出 nums 数组中所有和为0的三元组（无重复）
     *
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();

        int len = nums.length;
        if (len < 3)
            return ans;

        // 首先排序数组
        Arrays.sort(nums);

        // a + b + c = 0 ==> b + c = -a
        // 遍历 a，对于每一个 a，找到 b 和 c，使得 b + c = -a
        // a = nums[i]，b = nums[left]，c = nums[right]
        for (int i = 0; i < len - 2; i++) {
            // 跳过相同的num[i]，因为遍历时nums[i - 1]已经被处理过了
            if (i != 0 && nums[i] == nums[i - 1]) continue;

            // 目标就是找到 nums[left] + nums[right] = target
            int target = -nums[i];
            // 两个滑动指针从数组两边往中间遍历
            // left指向当前元素的后一个位置，right指向数组的最后一个位置。
            int left = i + 1, right = len - 1;
            while (left < right) {
                // 由于 nums 数组是升序的，所以 b + c > -a 时应减少 c 的值
                // 三数相加的和大于0时，把right往左边移动
                if (nums[left] + nums[right] > target)
                    right--;
                else if (nums[left] + nums[right] < target) // 三数相加的和小于0时，把left往右边移动
                    left++;
                else {
                    // 三数相加的和等于0时，加入解集
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[left]);
                    list.add(nums[right]);
                    ans.add(list);
                    // 找到满足条件的left和right后，继续遍历，跳过重复的，继续下一轮nums[left] + nums[right] = target判断
                    while (left + 1 < right && nums[left + 1] == nums[left])
                        left++;
                    while (right - 1 > left && nums[right - 1] == nums[right])
                        right--;
                    left++; //移动指针
                    right--; //移动指针
                }
            }
        }
        return ans;
    }

    /**
     * 简化文件路径,比如"/a/../b"解析成"/b"
     * 队列（先进先出）
     *
     * @param path
     * @return
     */
    public static String simplifyPath(String path) {

        Deque<String> deque = new ArrayDeque<>();

        String[] spiltArr = path.split("/");

        for (int i = 0; i < spiltArr.length; i++) {

            if ("".equals(spiltArr[i]))
                continue;
            else if ("..".equals(spiltArr[i])) {
                if (deque.size() > 0)
                    deque.poll();
            } else if (!".".equals(spiltArr[i])) {
                deque.offer(spiltArr[i]);
            }
        }

        if (deque.size() != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("/");
            while (deque.size() != 0) {
                sb.append(deque.poll());
                if (deque.size() > 0) {
                    sb.append("/");
                }
            }
            return sb.toString();
        } else {
            return "/";
        }


    }

    /**
     * 输出半个菱形
     *
     * @param height 菱形高度
     */
    public static String rhombus(int height) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            int i1 = height - i - 1;
            for (int j = 0; j < i1; j++) {
                sb.append(" ");
            }
            int i2 = 2 * i + 1;
            for (int j = 0; j < i2; j++) {
                sb.append("*");
            }
            sb.append("\r\n");
        }

        return sb.toString();

    }

    /**
     * 返回指定下标的斐波那契数列值
     * 双指针
     *
     * @param num 斐波那契数列下标
     * @return
     */
    public static int fibonacci(int num) {

        if (num == 0)
            return 0;
        if (num == 1)
            return 1;

        int low = 0, high = 1;
        for (int i = 2; i < num + 1; i++) {
            int sum = low + high;
            low = high;
            high = sum;
        }

        return high;
    }

    /**
     * 给一个整数数组，找出平均数最大且长度为 k 的下标连续的子数组，并输出该最大平均数
     * 滑动窗口
     *
     * @param nums 数组
     * @param k    子数组长度
     * @return
     */
    public static double findMaxAverage(int[] nums, int k) {

        if (nums.length < k)
            throw new RuntimeException("array length at least k");

        int sum = 0;
        int n = nums.length;

        //计算第一个窗口数组元素和
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }

        int max = sum;
        for (int i = k; i < n; i++) {
            sum = sum - nums[i - k] + nums[i];
            if (sum > max)
                max = sum;
        }

        return 1.0 * max / k;
    }

    static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 判断链表是否存在环
     * 双指针——快慢指针
     *
     * @param head
     * @return
     */
    public static boolean findCycle(ListNode head) {
        if (head == null || head.next == null)
            return false;

        ListNode slow = head; //slow指针
        ListNode quick = head.next; //quick指针

        //如果快慢指针不重合，则遍历直到快指针到达链表末尾
        while (slow != quick) {
            if (quick == null || quick.next == null) { //这种时候说明没有环
                return false;
            }
            slow = slow.next;
            quick = quick.next.next; //快指针比慢指针多走一步，如果进入环，就会在某一节点重合
        }

        return true; //如果跳出上面while循环，说明快慢指针重合，则表示有环
    }

    /**
     * 给定一个未经排序的整数数组找到最长且连续递增的子序列并返回该序列的长度
     * 自己写的算法
     *
     * @param array
     * @return
     */
    public static int[] incrementalSub(int[] array) {
        int maxLength = 0;
        int[] output = new int[0];
        for (int i = 0; i < array.length; ) {
            List<Integer> result = new ArrayList();
            result.add(array[i]);
            for (int j = i; j < array.length - 1; j++) {
                if (array[j + 1] > array[j]) {
                    result.add(array[j + 1]);
                } else {
                    i = j + 1 + 1;
                    break;
                }
            }
            if (result.size() > maxLength) {
                output = new int[result.size()];
                for (int j = 0; j < result.size(); j++) {
                    output[j] = result.get(j);
                }
                maxLength = result.size();
            }
        }
        return output;
    }

    /**
     * 给定一个未经排序的整数数组找到最长且连续递增的子序列并返回该序列的长度
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
     * 给定由一些正数(代表长度)组成的数组array，返回由其中三个长度组成的、面积不为零的三角形的最大周长
     *
     * @param array
     * @return
     */
    public static int maxPerimeter(int[] array) {
        Arrays.sort(array);
        for (int i = array.length - 1; i > 1; i--) {
            if (array[i] < array[i - 1] + array[i - 2]) {
                return array[i] + array[i - 1] + array[i - 2];
            }
        }
        return 0;
    }

    /**
     * 给定一个表示分数的非负整数数组。 玩家1从数组任意一端拿取一个分数，随后玩家2继续从剩余数组任意一端拿取分数，然后玩家1。
     * 每次一个玩家只能拿取一个分数，分数被拿取之后不再可取。直到没有剩余分数可取时游戏结束。最终获得分数总和最多的玩家获胜。
     * <p>
     * 假设每个玩家的玩法都会使他的分数最大化。
     * <p>
     * 预测玩家1是否会成为赢家。
     * <p>
     * 递归，得出按照以上游戏规则玩家可以获得的最高分数（假定对手也是按照一样的策略）
     *
     * @param array
     * @param left
     * @param right
     * @return
     */
    public static int maxScore(int[] array, int left, int right) {
        if (left == right) //当数组只有一个元素，(左右)得分均为这个元素值
            return array[left];

        int leftResult = 0, rightResult = 0; //分别用leftResult记录选择左侧元素的得分，rightResult记录选择右侧元素的得分
        if (right - left == 1) { //当数组只有二个元素，左侧得分和右侧得分就是左右元素值
            leftResult = array[left];
            rightResult = array[right];
        }
        if (right - left >= 2) { //当数组剩余元素大于2
            //选择左侧元素的得分=左侧元素值+对手按照相同策略留下的数组的得分最小值（对手评估选择左或右，让你后续选择得分最低）
            leftResult = array[left] + Math.min(maxScore(array, left + 2, right), maxScore(array, left + 1, right - 1));
            //选择右侧元素的得分=右侧元素值+对手按照相同策略留下的数组的得分最小值（对手评估选择左或右，让你后续选择得分最低）
            rightResult = array[right] + Math.min(maxScore(array, left, right - 2), maxScore(array, left + 1, right - 1));
            //todo 可以发现以上存在重复递归，有改进余地
        }

        return Math.max(leftResult, rightResult);
    }

    /**
     * 思路2：每一轮玩家1和玩家2选择完成后，对玩家1来说，玩家1-玩家2存在一个差值，就是该轮玩家1领先的数值
     * 对于玩家1来说，最优策略是让数组元素选尽后，差值最大
     * <p>
     * 递归：得出按照以上游戏规则玩家可以获得的最高分数差值（假定对手也是按照一样的策略）
     *
     * @param array
     * @param left
     * @param right
     * @return
     */
    public static int maxScore1(int[] array, int left, int right) {
        if (left == right) //当数组只有一个元素，差值就是这个元素值
            return array[left];

        //甲选择左元素，（甲-乙）差值 = （甲）左元素值 - （乙-甲）剩余元素差值 -> 甲选左元素时，甲-乙的差值
        int leftResult = array[left] - maxScore1(array, left + 1, right);
        int rightResult = array[right] - maxScore1(array, left, right - 1);
        //todo 可以发现以上存在重复递归，有改进余地

        return Math.max(leftResult, rightResult);
    }

    /**
     * 思路3：以上递归思路里都会存在重复计算递归的问题，比如计算了maxScore(array, 1, 10)，
     * 由于这个值没有存，导致后续递归可能还会重新计算maxScore(array, 1, 10)，导致效率低下。
     * 自然可以想到是否可以把每次计算的maxScore(array, left, right)值存储下来，针对相同的[left, right]直接查表获取。
     * <p>
     * 动态规划 dp数组
     *
     * @param array
     * @return
     */
    public static int dp(int[] array) {
        int length = array.length;
        int[][] dpArray = new int[length][length]; //初始化dp二维数组，2个下标分别表示待选数组的左右下标
        for (int i = 0; i < length; i++) {
            dpArray[i][i] = array[i]; //针对左右下标相同的情况，差值就是该元素本身
        }

        /**
         * 精髓所在！
         * (0,1) ->(0,2) ->(0,3) ->(0,4) ->...
         *      斜杠
         * (1,2) ->(1,3) ->(1,4) ->(1,5) ->...
         */
//        for (int i = 0; i < length -1; i++) { //不能从0开始遍历，这是由推导顺序决定的
        for (int i = length - 2; i >= 0; i--) { //i表示数组左下标，玩家最多可以选择到待选数组length -2下标，必须从大到小进行推导
            for (int j = i + 1; j < length; j++) { //j表示数组右下标，而且必须从i+1位置开始，从小到大进行推导
                dpArray[i][j] = Math.max(array[i] - dpArray[i + 1][j], array[j] - dpArray[i][j - 1]);
            }
        }

        return dpArray[0][length - 1]; //返回整个待选数组array的玩家最大可能得分差值
    }

    /**
     * 动态规划-改进版本
     * <p>
     * 其实我们要得到的是dp[0][length-1]的值，不关心dp[1][length-1]、dp[2][length-1]...的值
     * 所以我们可以简化dp为一维数组，默[][]前下标取0
     * <p>
     * i=0,j=length-1 最终可以根据
     * dp[0][1] = max(array[0] - dp[1][1], array[1] - dp[0][0])
     * dp[1][2] = max(array[1] - dp[2][2], array[2] - dp[1][1])
     * ...
     * dp[length-1-1][length-1] = max(array[length-1-1] - dp[length-1][length-1], array[length-1] - dp[length-1-1][length-1-1])
     * 求得
     * <p>
     * dp[0][2] = max(array[0] - dp[1,2], array[2] - dp[0,1])
     * <p>
     * 由于i--，所以dp[1,2]先被算出来
     * 由于j++,所以dp[0,1]先被算出来
     * <p>
     * 最终用dpArray[j]一维数组就可以存储出dp[0][j]的结果
     *
     * @param array
     * @return
     */
    public static int dp1(int[] array) {
        int length = array.length;
//        int[][] dpArray = new int[length][length]; //初始化dp二维数组，2个下标分别表示待选数组的左右下标
        int[] dpArray = new int[length];
        for (int i = 0; i < length; i++) {
//            dpArray[i][i] = array[i]; //针对左右下标相同的情况，差值就是该元素本身
            dpArray[i] = array[i]; //针对左右下标相同的情况，差值就是该元素本身
        }

        for (int i = length - 2; i >= 0; i--) { //i表示数组左下标，玩家最多可以选择到待选数组length -2下标
            for (int j = i + 1; j < length; j++) { //j表示数组右下标，针对某个可选择的左下标i，玩家可选择的右下标范围是[i+1,length -1]
//                dpArray[i][j] = Math.max(array[i] - dpArray[i+1][j], array[j] - dpArray[i][j-1]);
                dpArray[j] = Math.max(array[i] - dpArray[j], array[j] - dpArray[j - 1]);
            }
        }

//        return dpArray[0][length-1]; //返回整个待选数组array的玩家最大可能得分差值
        return dpArray[length - 1];
    }


    /**
     * KMP算法，求str中pattern字符串出现的下标，实现String.indexOf()功能，但是效率更高
     *
     * 【最浅显易懂的 KMP 算法讲解】 https://www.bilibili.com/video/BV1AY4y157yL/?share_source=copy_web&vd_source=7eac51e02115ba3237899e50cd3dd523
     *
     * @param str     待匹配字符串
     * @param pattern 目标字符串
     * @param next    next数组
     * @return
     */
    public static int search(char[] str, char[] pattern, int[] next) {
        int i = 0, j = 0;

        while (i < str.length && j < pattern.length) {
            if (j == -1 || str[i] == pattern[j]) { //如果i元素和j元素相同，则继续移动i和j进行匹配
                i++;
                j++;
            } else {  //如果不等，则从前面推出的next[]中找到下标使得i元素和j元素相同，重新开始匹配
                j = next[j];
            }
        }

        if (j == pattern.length)
            return i - j;
        else
            return -1;
    }


    /**
     * 获得待匹配字符串的next数组
     * next数组：PMT值（每个位置字符的前缀和后缀字符串相同的最大长度）右移，第一个位置设置为-1
     * ABCABCD
     * <p>
     * 对于倒数第二位C，
     * 前缀：A,AB,ABC,ABCA,ABCAB
     * 后缀：B,BC,ABC,CABC,BCABC
     * 前缀和后缀都有ABC，所以PMT值是3
     * <p>
     * PMT数组：  0 0 0 1 2 3 0
     * next数组：-1 0 0 0 1 2 3
     *
     * @param pattern
     * @param next
     */
    public static void getNext(char[] pattern, int[] next) {

        next[0] = -1;
        int i = 0, j = -1;

        while (i < pattern.length) {
            if (j == -1) { //重置j，从头开始匹配
                i++;
                j++;
            } else if (pattern[i] == pattern[j]) { //如果i元素和j元素相同，则记录next[i+1]的值为当前累计的j
                i++;
                j++;
                next[i] = j;
            } else { //如果不等，则从前面推出的next[]中找到下标使得i元素和j元素相同
                j = next[j];
            }
        }
    }

    /**
     * 求一个数组中所有不相邻元素和最大的值
     * <p>
     * 递归，针对第i个元素是否取，有2种结果，哪种结果大就选择取或不取
     *
     * @param array
     * @param end
     * @return
     */
    public static int rob(int[] array, int end) {
        if (end == 0)
            return array[0];

        if (end == 1)
            return Math.max(array[0], array[1]);

        //针对位置i的元素，要不要选
        //子结构的最优解 -> 总体的最优解
        return Math.max(rob(array, end - 2) + array[end], rob(array, end - 1));
        //todo 可以发现以上存在重复递归，有改进余地
    }

    /**
     * 求一个数组中所有不相邻元素和最大的值
     * <p>
     * 动态规划 dp数组解法
     *
     * @param array
     * @return
     */
    public static int rob(int[] array) {
        int length = array.length;
        if (array == null || length == 0)
            return 0;
        if (length == 1)
            return array[0];

        //定义dp数组
        int[] dp = new int[length];
        //定义dp数组初始值
        dp[0] = array[0];
        dp[1] = Math.max(array[0], array[1]);

        for (int i = 2; i < length; i++) {
            dp[i] = Math.max(dp[i - 2] + array[i], dp[i - 1]);
        }

        return dp[length - 1];
    }

    /**
     * 田忌赛马，给定数组A和数组B，将数组A元素重新排序，使得数组A中比数组B对应位置大的元素个数最多
     *
     * @param arrayA
     * @param arrayB
     * @return
     */
    public static int[] advantage(int[] arrayA, int[] arrayB) {

        //存储数组A需要移动到数组B对应位置的元素
        Map<Integer, Integer> aMap = new HashMap<>();

        //缓存数组B元素的原始下标
        Map<Integer, Integer> bCache = new HashMap<>();
        for (int i = 0; i < arrayB.length; i++) {
            bCache.put(arrayB[i], i);
        }

        //复制数组B到tempB
        int[] tempB = new int[arrayB.length];
        System.arraycopy(arrayB, 0, tempB, 0, arrayB.length);

        //对tempB进行排序
        Arrays.sort(tempB);
        Arrays.sort(arrayA);

        int j = 0;

        for (int i = 0; i < tempB.length; i++) {
            if (tempB[j] >= arrayA[i]) {
                continue;
            } else if (tempB[j] < arrayA[i]) {
                aMap.put(bCache.get(tempB[j]), arrayA[i]);
                j++;
            }
        }

        int[] result = new int[arrayA.length];

        Set<Integer> keySet = aMap.keySet();

        for (Integer key : keySet) {
            result[key] = aMap.get(key);
        }

        return result;

    }

    /**
     * 田忌赛马，一种更规范的解题思路
     * <p>
     * 使用队列存储垃圾元素，链表存储排位元素
     *
     * @param arrayA
     * @param arrayB
     * @return
     */
    public static int[] advantage1(int[] arrayA, int[] arrayB) {

        int[] sortB = arrayB.clone();
        Arrays.sort(sortB);
        Arrays.sort(arrayA);

        //给数组B每个位置添加队列，用于存放数组A中对应较大元素
        Map<Integer, Deque<Integer>> bMap = new HashMap<>();
        for (int b : arrayB) {
            bMap.put(b, new LinkedList<>());
        }
        //构造队列，用于存储数组A中无用的垃圾元素
        Deque<Integer> aq = new ArrayDeque<>();

        int j = 0;
        for (int a : arrayA) {
            if (a > sortB[j]) {
                bMap.get(sortB[j++]).add(a); // 将数组A中较大元素放置到数组B对应位置元素的队列中，并同步后移数组B
            } else {
                aq.add(a); //将数组A中较小元素扔到垃圾队列中
            }
        }

        int[] result = new int[arrayA.length];

        for (int i = 0; i < arrayB.length; i++) {
            if (bMap.get(arrayB[i]).size() > 0) {
                result[i] = bMap.get(arrayB[i]).poll();
            } else {
                result[i] = aq.poll();
            }
        }

        return result;

    }

    /**
     * 将数组中0元素移动到最后，保持其他剩余元素顺序不变
     * Input: [1,0,1,2,0,1,3]
     * Output: [1,1,2,1,3,0,0]
     *
     * @param arr
     * @return
     */
    public static int[] moveZero(int[] arr) {

        int[] result = new int[arr.length];

        int j = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                result[j++] = arr[i];
            } else {
                result[arr.length - 1 - j] = arr[i];
            }
        }

        return result;
    }

    /**
     * https://blog.csdn.net/chenxy_bwave/article/details/123911967
     *
     * @param password
     * @return
     */
    public static String solution2(String password) {
        // 1234567890Abcd
        //排序字符，进行重复字符计数+字符类型计数
        char[] charArray = password.toCharArray();

        boolean hasNumber = false;
        boolean hasLow = false;
        boolean hasUpper = false;

        if (charArray.length < 8 || charArray.length > 22) {
            throw new UnsupportedOperationException("length must between 8 to 22");
        }

        for (int i = 0; i < charArray.length - 2; i++) {
            if (charArray[i] == charArray[i + 1] && charArray[i + 1] == charArray[i + 2]) {
                return "weak";
            }
            if (charArray[i] >= 97 && charArray[i] <= 122) {
                hasLow = true;
            }
            if (charArray[i] >= 65 && charArray[i] <= 90) {
                hasUpper = true;
            }
            if (charArray[i] >= 48 && charArray[i] <= 57) {
                hasNumber = true;
            }

            if (i == charArray.length - 3) {
                if (charArray[i + 1] > 97 && charArray[i + 1] <= 122 || charArray[i + 2] > 97 && charArray[i + 2] <= 122) {
                    hasLow = true;
                }
                if (charArray[i + 1] > 65 && charArray[i + 1] <= 90 || charArray[i + 2] > 65 && charArray[i + 2] <= 90) {
                    hasUpper = true;
                }
                if (charArray[i + 1] > 48 && charArray[i + 1] <= 57 || charArray[i + 2] > 48 && charArray[i + 2] <= 57) {
                    hasNumber = true;
                }
            }

        }

        if (!(hasLow && hasUpper && hasNumber)) {
            return "weak";
        }

        return "strong";
    }

    /**
     * 判断括号是否使用正确
     * () => true
     * )(())) => false
     * <p>
     * 左括号进栈，右括号判断是否与栈顶元素成对使用。
     *
     * @param password
     * @return
     */
    public static boolean validateParenthesis(String password) {

//        char[] charArray = password.toCharArray();
//
//        Stack<Character> stack = new Stack<>();
//
//        HashMap<Character, Character> map = new HashMap<>();
//        map.put('(',')');map.put('{','}');map.put('[',']');
//        Set<Character> left = map.keySet();
//        Collection<Character> right = map.values();
//        for (int i = 0; i < charArray.length; i++) {
//            if (left.contains(charArray[i])){
//                stack.push(charArray[i]);
//            }
//            if (right.contains(charArray[i])){
//                if (stack.size() > 0 && map.get(stack.peek()).equals(charArray[i])){
//                    stack.pop();
//                }else {
//                    return false;
//                }
//            }
//        }
//
//        if (stack.size() == 0){
//            return true;
//        }
//
//        return false;

        Stack<Character> stack = new Stack<>();
        for (char c : password.toCharArray()) {
            if (c == '(')
                stack.push(')');
            else if (c == '{')
                stack.push('}');
            else if (c == '[')
                stack.push(']');
            else if ((c == ')' || c == '}' || c == ']') && (stack.isEmpty() || stack.pop() != c))
                return false;
        }
        return stack.isEmpty();
    }

    /**
     * 合并两个有序数组
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {

        /**
         * 解法1 合并排序算法思想应用
         */
        int index = 0, i = 0, j = 0;
        int[] tempArray = new int[m + n];
        while (i < m && j < n) {
            if (nums1[i] >= nums2[j]) {
                tempArray[index] = nums2[j];
                index++;
                j++;
            } else {
                tempArray[index] = nums1[i];
                index++;
                i++;
            }
        }
        while (i < m) {
            tempArray[index++] = nums1[i++];
        }
        while (j < n) {
            tempArray[index++] = nums2[j++];
        }

        System.arraycopy(tempArray, 0, nums1, 0, tempArray.length);

        /**
         * 解法2 逆序双指针 关键在于从后开始处理
         */
        int p1 = m - 1, p2 = n - 1;
        int tail = m + n - 1;
        int cur;
        while (p1 >= 0 || p2 >= 0) {
            if (p1 == -1) {
                cur = nums2[p2--];
            } else if (p2 == -1) {
                cur = nums1[p1--];
            } else if (nums1[p1] > nums2[p2]) {
                cur = nums1[p1--];
            } else {
                cur = nums2[p2--];
            }
            nums1[tail--] = cur;
        }

    }

    /**
     * 移除元素
     * 给你一个数组nums和一个值val，你需要"原地"移除所有数值等于val的元素，并返回移除后数组的新长度。
     * 不要使用额外的数组空间，你必须仅使用O(1)额外空间并"原地"修改输入数组。元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     * <p>
     * 算法：双指针
     */
    public static int removeElement1(int[] nums, int val) {
        int pointer = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[pointer++] = nums[i];
            }
        }
        System.out.println(Arrays.toString(nums));
        return pointer;
    }

    /**
     * 移除元素
     * 更简练的写法
     *
     * @param nums
     * @param val
     * @return
     */
    public static int removeElement2(int[] nums, int val) {
        int result = nums.length;

        for (int i = 0; i < result; i++) {
            if (nums[i] == val) {
                nums[i--] = nums[--result]; //遇到相同的元素，i原地踏步，同时把数组尾部开始的元素放到i位置进行再次比较，由于result不断变小，所以for循环可以退出
            }
        }

        return result;
    }

    /**
     * 获取数组中占半数以上的元素
     * <p>
     * 给定一个大小为n的数组nums，返回其中的多数元素。多数元素是指在数组中出现次数大于n/2的元素。你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     * 尝试设计时间复杂度为O(n)、空间复杂度为O(1)的算法解决此问题。
     * <p>
     * Boyer-Moore 投票算法： 如果我们把众数记为+1，把其他数记为−1，将它们全部加起来，显然和大于0，从结果本身我们可以看出众数比其他数多。
     *
     * @param nums
     * @return
     */
    public static int majorityElement(int[] nums) {
        /**
         * 要求数组存在众数
         */
        int count = 0; //对当前candidate出现的次数进行计数
        Integer candidate = null; //记录当前的候选众数

        for (int num : nums) {
            if (count == 0) {
                candidate = num; //当当前的candidate次数减为零，则将当前遍历到的num赋为候选众数
            }
            count += (num == candidate) ? 1 : -1; //当前遍历到的num等于candidate，则count+1，否则-1
        }

        return candidate; //遍历到最后的candidate就是众数


        /**
         * 要求数组存在众数
         */
//        Arrays.sort(nums);
//        return (nums[nums.length/2]);

        /**
         * 最普遍的算法
         */
        // 思想： 通过 hashMap 存放 value 和 count , 如果 count > n/2 直接返回
//        if (nums.length == 0) {
//            return 0;
//        }
//        int mid = nums.length/2;
//        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
//        for (int i = 0; i < nums.length; i++) {
//            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
//            if (map.getOrDefault(nums[i], 0) > mid) {
//                return nums[i];
//            }
//        }
//        return 0;

    }

    /**
     * 求取数组中最大的差值
     * <p>
     * 给定一个数组prices，它的第i个元素prices[i]表示一支给定股票第i天的价格。你只能选择某一天买入这只股
     * 票，并选择在未来的某一个不同的日子卖出该股票。设计一个算法来计算你所能获取的最大利润。返回你可以从这笔交易中
     * 获取的最大利润。如果你不能获取任何利润，返回0。
     * <p>
     * 算法：双向双指针
     *
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {
        int buyDay = 0;
        int sellDay = prices.length - 1;

        while (buyDay < sellDay) {
            int maxBuyDay = buyDay;
            int maxSellDay = sellDay;
            if (prices[buyDay + 1] < prices[buyDay]) {
                maxBuyDay = buyDay++;
            }
            if (prices[sellDay - 1] > prices[sellDay]) {
                maxSellDay = sellDay--;
            }

            /**
             * 退出条件，如果买入价和卖出价都到了最优，则跳出
             */
            if (maxBuyDay == buyDay && maxSellDay == sellDay)
                break;

        }

        return prices[sellDay] - prices[buyDay];

        // 思想：1、定义一个最小值，如果下一个元素小于当前值，更新最小值，
        // 定义一个最大收益，如果当前值大于最小值，求差，并判断是否大于当前最大值
        // 遍历按顺序取最小值，然后计算当前最小值下的最大收益
        // 下降趋势中取一个最小值，上升趋势中取一个最大值，然后比较不同这种趋势中的利润，取一个最大值
//        int minBuyPrice = Integer.MAX_VALUE, maxProfit = 0;
//        for (int i = 0; i < prices.length; i++) {
//            if (prices[i] < minBuyPrice) {
//                minBuyPrice = prices[i];
//            } else {
//                maxProfit = Math.max(maxProfit, prices[i] - minBuyPrice);
//            }
//        }
//        return maxProfit;

    }


    /**
     * 求取数组中累计最大的差值和
     * <p>
     * 给你一个整数数组prices，其中prices[i]表示某支股票第i天的价格。在每一天，你可以决定是否购买和/或出售股
     * 票。你在任何时候最多只能持有一股股票。你也可以先购买，然后在同一天出售。可以多次买卖。求累计你能获得的最
     * 大利润。
     * <p>
     * 算法：动态规划
     *
     * @param prices
     * @return
     */
    public static int maxMultiProfit(int[] prices) {

        /**
         * 基于价格下降段买入，价格上升段卖出的理念
         */
//        int temBuyPrice = Integer.MAX_VALUE, temSellPrice = 0, maxProfit = 0;
//        for (int i = 0; i < prices.length; i++) {
//            if (prices[i] < temBuyPrice) {
//                temBuyPrice = prices[i];
//            } else {
//                if (prices[i]>temSellPrice){
//                    temSellPrice = prices[i];
//                }else {
//                    maxProfit += temSellPrice - temBuyPrice;
//                    temBuyPrice = prices[i];
//                    temSellPrice = 0;
//                }
//            }
//        }
//
//        if (temSellPrice - temBuyPrice > 0){
//            maxProfit += temSellPrice - temBuyPrice;
//        }
//
//        return maxProfit;

        /**
         * 动态规划：将问题化为不同状态之间的转换过程
         *
         * 定义状态dp[i][0]表示第i天交易完后手里没有股票的最大利润，dp[i][1]表示第i天交易完后手里持有一支股票的最大利润（i从0开始）。
         * 考虑dp[i][0]的转移方程，如果这一天交易完后手里没有股票，那么可能的转移状态为前一天已经没有股票，即dp[i−1][0]，或者前一天
         * 结束的时候手里持有一支股票，即dp[i−1][1]，这时候我们要将其卖出，并获得prices[i]的收益。因此为了收益最大化，我们列出如下的
         * 转移方程：dp[i][0]=max{dp[i−1][0],dp[i−1][1]+prices[i]}
         *
         * 再来考虑dp[i][1]，按照同样的方式考虑转移状态，那么可能的转移状态为前一天已经持有一支股票，即dp[i−1][1]，或者前一天结束时还
         * 没有股票，即dp[i−1][0]，这时候我们要将其买入，并减少prices[i]的收益。
         * 可以列出如下的转移方程：dp[i][1]=max{dp[i−1][1],dp[i−1][0]−prices[i]}
         *
         * 对于初始状态，根据状态定义我们可以知道第0天交易结束的时候dp[0][0]=0，dp[0][1]=−prices
         *
         * 因此，我们只要从前往后依次计算状态即可。由于全部交易结束后，持有股票的收益一定低于不持有股票的收益，因此这时
         * 候dp[n−1][0]的收益必然是大于dp[n−1][1]的，最后的答案即为dp[n−1][0]。
         */
        if (prices.length < 2) {
            return 0;
        }
        // 思路：通过二维数组表示当前的两种状态 prices[i][0] 表示持有现金 prices[i][1]表示持有股票，每次遍历获取Max
        int[][] dp = new int[prices.length][2];

        // 初始化0
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[prices.length - 1][0];

        //注意到上面的状态转移方程中，每一天的状态只与前一天的状态有关，而与更早的状态都无关，因此我们不必存储这些无关的状态，只需要将dp[i−1][0]和dp[i−1][1]存放在两个变量中
//        int n = prices.length;
//        int dp0 = 0, dp1 = -prices[0];
//        for (int i = 1; i < n; ++i) {
//            int newDp0 = Math.max(dp0, dp1 + prices[i]);
//            int newDp1 = Math.max(dp1, dp0 - prices[i]);
//            dp0 = newDp0;
//            dp1 = newDp1;
//        }
//        return dp0;


        /**
         * 贪心算法：将总体的最优问题分解成局部的最优问题
         *
         * 理解成每隔1天即观察是否有盈利空间，累加每一天可能的利润，即为所能取得的最大利润
         *
         */
//        public int maxProfit(int[] prices) {
//            int ans = 0;
//            int n = prices.length;
//            for (int i = 1; i < n; ++i) {
//                ans += Math.max(0, prices[i] - prices[i - 1]);
//            }
//            return ans;
//        }

    }


    /**
     * 加油站路线规划问题
     * <p>
     * 算法：找到关键过程等式
     *
     * @param gas
     * @param cost
     * @return
     */
    public static int canCompleteCircuit(int[] gas, int[] cost) {

        for (int start = 0; start < gas.length; start++) {
            boolean found = true;
            int leftGas = gas[start];
            int current = start;
            do {
                int next = (current + 1) % gas.length;
                leftGas = leftGas + gas[next] - cost[current];
                current = next;

                if (leftGas < cost[next]) {
                    found = false;
                    start = current; //从断掉的节点处开始重新遍历，前面的节点无须再遍历
                    break;
                }

            } while (start != current);

            if (found) {
                return start;
            } else {
                start += 1; //如果遍历到初始点断掉，则从后一个节点重新遍历
            }
        }

        return -1;


//        int len = gas.length; // 外层我只遍历一次即可
//        int i = 0;
//        while (i < len) {
//            // 每次进来都是重新判断是否可以走完全程，所以总的加油和耗油都是0
//            int sumGas = 0, sumCost = 0;
//            int count = 0;
//            // 确定子循环的次数
//            while (count < len) {
//                // i是变化的，所以我们要去摸获取下标
//                int cur = (i + count) % len ;
//                // 判断加油是否大于耗油
//                sumCost += cost[cur];
//                sumGas += gas[cur];
//                if (sumCost > sumGas) {
//                    break;
//                }
//                count++;
//            }
//
//            // 当 count == len 的时候表示循环完毕，i就是符合条件的
//            if (count == len) {
//                return i;
//            }
//            i = i + count + 1;
//        }
//        return -1;

    }

    /**
     * 跳跃游戏
     *
     * @param nums
     * @return
     */
    public static boolean canJump(int[] nums) {

//        int length = nums.length;
//
//        /**
//         * 第i个节点
//         * 可走步长 step = [1,nums[i]]
//         * 选择某个步长 step，走到下一个节点，
//         *
//         * nums[i+step]
//         * 可走步长 step = [1,nums[i+step]]
//         * 选择某个步长 step，走到第下一个节点，
//         *
//         * ......
//         *
//         * 直到走到nums[length - 1]
//         *
//         */
//
//        int stepScope = nums[0]; //标识为当前节点可选最大步长
//        int pre = 0; //标识为上一个节点
//
//        while (stepScope > 0){ //如果到某个节点的可选步长是零，还未返回true，说明不存在这样的路径
//            int maxStep = 0;
//            int next = 0;
//            for (int step = 1; step <= stepScope; step++) { //从当前节点可选步长中选择一个
//                int s = step + nums[pre + step]; //获取当前步长下的最大可能步长
//
//                if (s > maxStep){
//                    next = pre + step; //选择其中最长的作为下一个节点
//                    maxStep = s;
//                }
//                if (step == stepScope){ //当把当前所有可能的步长尝试完后
//                    if (next >= length-1){ //如果下一个节点序号已经大于数组最大序号，则返回true
//                        return true;
//                    }
//                    pre = next; //将选择的下一个节点赋给pre节点
//                    stepScope = nums[pre]; //将pre节点的可选步长赋给stepScope
//                }
//            }
//        }
//
//        return false;


        /**
         * 如果走到当前点时的最大步长：pathlen = Math.max(pathlen, i + nums[i])
         */
        if (nums == null || nums.length == 0) {
            return false;
        }
        int len = nums.length;
        int pathlen = 0;
        // 如果可达路径大于等于下表表示可达，则判断是否大于数组的长度-1；
        for (int i = 0; i < len; i++) {
            if (pathlen >= i) { //当前最大步长允许走到i位置的话，看是否可以获得一个更大的最大步长
                pathlen = Math.max(pathlen, i + nums[i]);

                if (pathlen >= len - 1) { //看此时的pathlen，是否已经大于等于数组最大序号
                    return true;
                }
            }
        }
        return false;


    }

    /**
     * 跳跃游戏
     * <p>
     * 返回到达nums[n - 1]的最小跳跃次数。
     *
     * @param nums
     * @return
     */
    public static int jump(int[] nums) {

//        int jumpCount = 0;
//
//        int nextIndex = nums.length - 1;
//        int preIndex = nums.length - 2;
//
//        while (preIndex >= 0){
//            for (int i = preIndex; i >= 0 ; i--) {
//                if( i + nums[i] >= nextIndex){
//                    if (i < preIndex){
//                        preIndex = i;
//                    }
//                }
//
//                if (i == 0){
//                    jumpCount++;
//                    if (preIndex == 0){
//                        return jumpCount;
//                    }
//                    nextIndex = preIndex;
//                    preIndex = nextIndex - 1;
//                    break;
//                }
//            }
//        }
//
//        return jumpCount;


        /**
         * 从后往前选择距离最远（从0开始尝试）且步长>=position的下标，然后继续这样寻找再前一个下标，直到position=0
         */
        int position = nums.length - 1;
        int steps = 0;
        while (position > 0) {
            for (int i = 0; i < position; i++) {
                if (i + nums[i] >= position) {
                    position = i;
                    steps++;
                    break;
                }
            }
        }
        return steps;
    }


    /**
     * 删除有序数组中的重复项
     * <p>
     * 给你一个非严格递增排列的数组nums，请你原地删除重复出现的元素，使每个元素只出现一次，返回删除后数组的新长
     * 度。元素的相对顺序应该保持一致。然后返回nums中唯一元素的个数。
     * <p>
     * 算法：快慢双指针
     *
     * @param nums
     * @return
     */
    public static int removeDuplicates(int[] nums) {

        int pointeri = 0;
        int pointerj = 1;

        while (pointerj < nums.length) {
            if (nums[pointeri] != nums[pointerj]) {
                nums[++pointeri] = nums[pointerj];
            }
            pointerj++;
        }

        return pointeri + 1;

    }

    /**
     * 给你一个有序数组nums，请你原地删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的
     * 新长度。不要使用额外的数组空间，你必须在原地修改输入数组并在使用O(1)额外空间的条件下完成。
     * <p>
     * 算法：快慢双指针(慢指针起始位置变为+2)
     *
     * @param nums
     * @return
     */
    public static int removeTwiceDuplicates(int[] nums) {

        //        int i = 0;
//        int pointeri = 0;
//        int pointerj = 1;
//
//        while(pointerj < nums.length){
//            if(nums[pointeri] == nums[pointerj]){
//                if (pointerj == nums.length - 1){
//                    if (pointeri == pointerj){
//                        nums[i] = nums[pointeri];
//                    }else {
//                        nums[i] = nums[pointeri];
//                        nums[i+1] = nums[pointerj];
//                        i = i + 1;
//                    }
//                    return i;
//                }else {
//                    pointerj++;
//                }
//            }else if(nums[pointeri] != nums[pointerj]){
//                if (pointerj > pointeri + 1){
//                    nums[i] = nums[i+1] = nums[pointeri];
//                    nums[i+2] = nums[pointerj];
//                    i = i + 2;
//                }else {
//                    nums[i] = nums[pointeri];
//                    nums[i+1] = nums[pointerj];
//                    i = i + 1;
//                }
//
//                pointeri = pointerj;
//            }
//        }
//
//        return i+1;

//        int pointeri = 0;
//        int pointerj = 1;
//
//        boolean duplicate = false;
//
//        while(pointerj < nums.length){
//            if(nums[pointeri] != nums[pointerj]){
//                nums[++pointeri] = nums[pointerj];
//                duplicate = false;
//            }else if (!duplicate){
//                nums[++pointeri] = nums[pointerj];
//                duplicate = true;
//            }
//            pointerj++;
//        }
//
//        return pointeri + 1;

        // 思路：定义快慢两个指针fast slow 当 fast == fast - 2 时，替换掉当前元素
        if (nums.length < 3) {
            return 2;
        }
        int slow = 2, fast = 2;
        while (fast < nums.length) {
            // 这里需要用slow去-2
            if (nums[slow - 2] != nums[fast]) {
                nums[slow++] = nums[fast];
            }
            fast++;
        }
        return slow;

    }

    /**
     * 返回字符串最长公共字符串
     * <p>
     * 编写一个函数来查找字符串数组中的最长公共前缀。如果不存在公共前缀，返回空字符串""。
     *
     * @param strs
     * @return
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        // 思想：纵向比较：以strs[0]作为比较基本，循环依次取Char与其它元素进行比较，不相同直接退出即可。
        String prex = strs[0];
        for (int i = 0; i < prex.length(); i++) {
            // 获取比较的元素
            char p = prex.charAt(i);
            // 遍历其它元素，但是其它元素可能并没有第一个元素长，可以先比较长度
            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != p) {
                    return prex.substring(0, i);
                }
            }
        }
        return prex;
    }

    /**
     * 给你一个整数数组nums和一个整数k，判断数组中是否存在两个不同的索引i和j，满足nums[i] == nums[j]且
     * abs(i - j) <= k。如果存在，返回true；否则，返回false。
     *
     * @param nums
     * @param k
     * @return
     */
    public static boolean containsNearbyDuplicate(int[] nums, int k) {

//        for (int i = 0; i < nums.length - 1; i++) {
//            int j = i+1;
//            while (j < nums.length){
//                if (nums[i] == nums[j++]){
//                    int len = j - i - 1;
//                    if (len <= k){
//                        return true;
//                    }
//                }
//            }
//        }
//
//        return false;

        for (int i = 0; i < nums.length - k; i++) {
            for (int j = i + 1; j < i + k + 1; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 轮转数组
     * <p>
     * 给定一个整数数组nums，将数组中的元素向右轮转k个位置，其中k是非负数。
     *
     * @param nums
     * @param k
     */
    public static void rotate(int[] nums, int k) {

//        int[] temp = new int[nums.length];
//        System.arraycopy(nums,0,temp,0,nums.length);
//
//        for (int i = 0; i < temp.length; i++) {
//            int index = (i+k) % nums.length;
//            nums[index] = temp[i];
//        }

        int n = nums.length;
        int count = gcd(k, n); //计算出需要遍历的圈数，最小公约数
        for (int start = 0; start < count; ++start) { //按k的步数遍历直到回到起始点，完成一圈
            int current = start;
            int prev = nums[start];
            do {
                int next = (current + k) % n;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
            } while (start != current);
        }
    }

    /**
     * 欧几里得算法
     *
     * @param x
     * @param y
     * @return
     */
    public static int gcd(int x, int y) {
        return y > 0 ? gcd(y, x % y) : x;
    }

    /**
     * 分发糖果
     * <p>
     * n个孩子站成一排。给你一个整数数组ratings表示每个孩子的评分。你需要按照以下要求，给这些孩子分发糖果：
     * 【1】每个孩子至少分配到1个糖果
     * 【2】相邻两个孩子评分更高的孩子会获得更多的糖果。
     * 请你给每个孩子分发糖果，计算并返回需要准备的 最少糖果数目。
     *
     * @param ratings
     * @return
     */
    public static int candy(int[] ratings) {

//        int[] nums = new int[ratings.length];
//
//        for (int i = 0; i < ratings.length - 1; i++) {
//            if (ratings[i] < ratings[i+1]){
//                if (i == 0){
//                    nums[i] = 1;
//                }
//                nums[i+1] = nums[i] + 1;
//            }else if (ratings[i] == ratings[i+1]){
//                if (i == 0){
//                    nums[i] = 1;
//                }
//                nums[i+1] = nums[i];
//            }else if (ratings[i] > ratings[i+1]){
//                if (i == 0){
//                    nums[i] = 2;
//                }
//                nums[i+1] = nums[i] - 1;
//            }
//        }
//
//        System.out.println(Arrays.toString(nums));
//
//        int[] tem = new int[nums.length];
//        System.arraycopy(nums,0,tem,0,nums.length);
//
//        int sum = 0;
//        for (int i = nums.length - 1; i > 0; i--) {
//            if (nums[i] < nums[i-1]){
//                if (tem[i] < 1){
//                    tem[i] = 1;
//                }
//
//                tem[i-1] = tem[i] + 1;
//            }
//
//            sum += tem[i];
//        }
//
//        sum += tem[0];
//
//        System.out.println(Arrays.toString(tem));
//
//        return sum;

//        // 1、定义left[]数组，计算每个小朋友符合左侧规则时，能获取到的糖果
//        // 2、定义两个变量，第一个计算前一个小朋友的糖果，第二个计算总的糖果数量，从右侧开始计算
//        if (ratings.length == 0) {
//            return 0;
//        }
//        // 创建数组
//        int[] left = new int[ratings.length];
//        left[0] = 1;
//        for(int i = 1; i < ratings.length; i++) {
//            if (ratings[i] > ratings[i - 1]) {
//                left[i] = left[i - 1] + 1;
//            } else {
//                left[i] = 1;
//            }
//        }
//        // 先初始化最后一个小朋友的糖果
//        int next = 1, count = Math.max(1, left[ratings.length - 1]);
//        for(int i = ratings.length - 2; i >= 0; i--) {
//            if (ratings[i] > ratings[i + 1]) {
//                next += 1;
//            } else {
//                next = 1;
//            }
//            count += Math.max(next, left[i]);
//        }
//        return count;


        // 1、定义两个变量，第一个计算当前小朋友的糖果pre，第二个计算总的糖果数量count。
        // 2、左侧遍历时，如果时递减的情况，需要再创建一个变量，计算递减的次数 decr。
        // 3、特殊处理：递减的时候，如果我拥有的糖果和递减前小朋友的糖果个数相同时，需要++，举例：5321的时候，5有3个糖果，此时的3再递减中也会有5个糖果，所以就需要对5的糖果+1
        if (ratings.length == 0) {
            return 0;
        }

        // 先初始化最后一个小朋友的糖果
        int pre = 1, count = 1, decr = 0, inc = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] >= ratings[i - 1]) { //递增时，直接加
                pre = ratings[i] == ratings[i - 1] ? 1 : pre + 1;
                ;
                // 如果时递增的，当前递减序列结束
                decr = 0;
                count += pre;

                // pre表示当前小朋友用于的当过
                inc = pre;
            } else { //递减时，累加递减的次数，当递减次数=递减的最高点时，补上decr+1
                // 如果开始了递减序列，我们就开始记录递减序列的长度
                decr++;
                // 递减的时候，如果我拥有的糖果和递减的小朋友的个数相同时，需要++，举例：5321的时候，5有3个糖果，此时的3再递减中也会有5个糖果，所以就需要对5+1
                if (inc == decr) {
                    decr++;
                }
                // 重置糖果为1
                pre = 1;
                count += decr;
            }
        }
        return count;


    }


    /**
     * 计算数学表达式（自己的思路实现）
     *
     * @param str
     * @return
     */
    public static String calculate(String str) {

        Deque<String> deque1 = new LinkedList<>();
        Deque<String> deque2 = new LinkedList<>();

        List<String> strings = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (char c : str.toCharArray()) {
            if (c != '+' && c != '-' && c != '*' && c != '/') {
                stringBuilder.append(c);
            } else {
                String string = stringBuilder.toString();
                if (string.length() > 0) {
                    strings.add(string);
                    stringBuilder.setLength(0);
                }
                strings.add(String.valueOf(c));
            }
        }

        strings.add(stringBuilder.toString());

        for (int i = 0; i < strings.size(); i += 2) {
            if (i == strings.size() - 1) {
                if (strings.get(i - 1).equals("+") || strings.get(i - 1).equals("-")) {
                    deque1.add(strings.get(i));
                } else {
                    deque2.add(strings.get(i));
                }
            } else {
                String aChar = strings.get(i);
                String bChar = strings.get(i + 1);
                if (bChar.equals("+") || bChar.equals("-")) {
                    if (deque2.size() == 0) {
                        deque1.add(aChar);
                        deque1.add(bChar);
                    } else {
                        deque2.add(aChar);
                        String deque = deque(deque2);
                        deque1.add(deque);
                        deque1.add(bChar);
                    }
                } else {
                    deque2.add(aChar);
                    deque2.add(bChar);
                }
            }
        }

        if (deque2.size() > 0) {
            String deque = deque(deque2);
            deque1.add(deque);
        }

        return deque(deque1);
    }

    public static String deque(Deque<String> deque) {
        while (deque.size() > 1) {
            String[] chars = new String[3];
            chars[0] = deque.remove();
            chars[1] = deque.remove();
            chars[2] = deque.remove();
            Double cal = cal(chars);
            deque.push(cal.toString());
        }
        return deque.pop();
    }

    public static Double cal(String[] strings) {
        String param1 = strings[0];
        String operator = strings[1];
        String param2 = strings[2];
        if (operator.equals("+")) {
            return Double.valueOf(param1) + Double.valueOf(param2);
        } else if (operator.equals("-")) {
            return Double.valueOf(param1) - Double.valueOf(param2);
        } else if (operator.equals("*")) {
            return Double.valueOf(param1) * Double.valueOf(param2);
        } else if (operator.equals("/")) {
            return Double.valueOf(param1) / Double.valueOf(param2);
        }
        return 0.0;
    }


    enum SYMBOL {
        ADD("+", 1),
        MINUS("-", 1),
        MULTIPLY("*", 2),
        DIVIDE("/", 2);

        private String s;
        private int priority;

        SYMBOL(String s, int i) {
            this.s = s;
            this.priority = i;
        }

        static SYMBOL getBySymbol(String mark) {
            SYMBOL[] values = SYMBOL.values();
            for (SYMBOL symbol : values) {
                if (symbol.s.equals(mark)) {
                    return symbol;
                }
            }
            return null;
        }

        String calculate(String num1, String num2) {
            String result = null;
            if (this.equals(ADD)) {
                result = String.valueOf(Double.parseDouble(num1) + Double.parseDouble(num2));
            } else if (this.equals(MINUS)) {
                result = String.valueOf(Double.parseDouble(num1) - Double.parseDouble(num2));
            } else if (this.equals(MULTIPLY)) {
                result = String.valueOf(Double.parseDouble(num1) * Double.parseDouble(num2));
            } else if (this.equals(DIVIDE)) {
                result = String.valueOf(Double.parseDouble(num1) / Double.parseDouble(num2));
            }

            return result;
        }
    }

    /**
     * 计算数学表达式-不考虑括号的情况（按照https://it-blog-cn.com/blogs/algorithm/stack.html）
     * <p>
     * 算法：栈
     *
     * @param str
     * @return
     */
    public static String calculateNew(String str) {

        /**
         * 存放数字的栈
         */
        Deque<String> numStack = new LinkedList<>();
        /**
         * 存放计算符号的栈
         */
        Deque<String> symbolStack = new LinkedList<>();

        char[] charArray = str.toCharArray();

        StringBuilder number = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];

            /**
             * 如果遍历字符到最后一个，默认是数字，数字栈中有一个数字，运算符栈中有一个运算符
             */
            if (i == charArray.length - 1) {
                number.append(c);
                numStack.push(number.toString());
                number.setLength(0); //清空数字组装器

                if (!symbolStack.isEmpty()) {
                    while (symbolStack.peek() != null) { //获取先前入栈的运算符
                        String symbol = symbolStack.pop();
                        String num2 = numStack.pop();
                        String num1 = numStack.pop();
                        SYMBOL s = SYMBOL.getBySymbol(symbol);
                        String calculate = s.calculate(num1, num2);
                        numStack.push(calculate); //计算并将结果push到数字栈
                    }
                }
            }
            /**
             * 如果是数字字符，拼接数字
             */
            if (Character.isDigit(c) || '.' == c) {
                number.append(c);
            } else { //如果遍历到非数字字符
                if (number.length() > 0) { //如果之前读取的数字非空，入数字栈
                    numStack.push(number.toString());
                    number.setLength(0); //清空数字组装器
                }
                //如果发现是符号则判断当前的符号栈是否为空，不为空则先处理已存在的运算，直到运算符栈中的运算符优先于当前运算符
                if (!symbolStack.isEmpty()) {
                    SYMBOL nowSymbol = SYMBOL.getBySymbol(Character.toString(c));
                    String pre;
                    while (symbolStack.peek() != null) { //获取先前入栈的运算符
                        pre = symbolStack.pop();
                        SYMBOL preSymbol = SYMBOL.getBySymbol(pre);
                        //如果当前优先级低于栈顶优先级
                        if (preSymbol.priority >= nowSymbol.priority) {
                            String num2 = numStack.pop();
                            String num1 = numStack.pop();
                            String calculate = preSymbol.calculate(num1, num2);
                            numStack.push(calculate); //计算并将结果push到数字栈
                        } else {
                            symbolStack.push(pre);
                            break;
                        }
                    }
                }
                //当前运算符入栈
                symbolStack.push(Character.toString(c));
            }
        }

        //返回数字栈顶，即运算结果
        return numStack.pop();
    }

    /**
     * 计算数学表达式-考虑括号的情况（按照https://it-blog-cn.com/blogs/algorithm/stack.html）
     *
     * @param s
     * @return
     */
    public static String calculateBrackets(String s) {
        char[] charArray = s.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder1 = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if ('(' != c) {
                stringBuilder.append(c);
            } else {
                int j = i + 1;
                while (')' != charArray[j]) {
                    stringBuilder1.append(charArray[j++]);
                }
                String s1 = calculateNew(stringBuilder1.toString());
                stringBuilder.append(s1);
                stringBuilder1.setLength(0);
                i = j;
            }
        }

        return calculateNew(stringBuilder.toString());
    }

    /**
     * 计算数学表达式（考虑包含括号）
     * <p>
     * 思路：分解成符号树
     * 算法：后续遍历
     *
     * @param root 符号树根节点
     * @return
     */
    public static double calculateByTree(Node root) {

        String val = root.val;
        Node left = root.left;
        Node right = root.right;

        double result = 0;
        double leftResult = 0;
        double rightResult = 0;
        if (left == null && right == null) { //递归退出条件
            result = Double.parseDouble(val);
        }
        if (left != null) { //先处理左右子树
            leftResult = calculateByTree(left);
        }
        if (right != null) {
            rightResult = calculateByTree(right);
        }

        if ("+".equals(val)) { //最后处理当前节点
            result = leftResult + rightResult;
        } else if ("-".equals(val)) {
            result = leftResult - rightResult;
        } else if ("*".equals(val)) {
            result = leftResult * rightResult;
        } else if ("/".equals(val)) {
            result = leftResult / rightResult;
        } else if ("()".equals(val)) {
            result = leftResult;
        }

        return result;

    }

    /**
     * 根据表达式字符串构建运算符号树
     * <p>
     * 针对任意一个表达式，进行归纳定义
     * 1、要么最外层是一个括号+括号内的子表达式
     * 2、要么找到一个最后计算的运算符，分拆成左表达式、右表达式
     * 3、不断递归下去，直到表达式只有一个数字
     *
     * @param str
     * @return
     */
    public static Node buildCalculateTree(String str) {

        int n = str.length();
        char[] charArray = str.toCharArray();

        if (n == 1) { //递归出口
            assert ('0' <= charArray[0] && charArray[0] <= '9');
            return new Node(String.valueOf(charArray[0]), null, null);
        }

        // 标记每个位置的 “嵌套层数”
        int[] level = new int[n];
        int cur = 0;

        for (int i = 0; i < n; i++) {
            if (charArray[i] == '(') {
                cur++;
                continue;
            }
            if (charArray[i] == ')') {
                cur--;
                continue;
            }
            level[i] = cur;
        }

        // 如果最外层是配对的括号（表达式的情形1）
        if (charArray[0] == '(') {
            if (Arrays.stream(level).filter(d -> d > 0).count() == n - 2) {
                return new Node("()", buildCalculateTree(str.substring(1, n - 1)), null);
            }
        }

        // 否则，找到最后一个运算的符号（表达式的情形2）
        int pos = -1;
        for (int i = n - 1; i >= 0; i--) {
            if (level[i] == 0 && priority.keySet().contains(charArray[i])) {
                if (pos == -1 || priority.get(charArray[pos]) < priority.get(charArray[i])) {
                    pos = i;
                }
            }
        }

        assert (pos >= 0);


        Node lhs = buildCalculateTree(str.substring(0, pos));
        Node rhs = buildCalculateTree(str.substring(pos + 1));

        if (charArray[pos] == '+') return new Node("+", lhs, rhs);
        if (charArray[pos] == '*') return new Node("*", lhs, rhs);
        if (charArray[pos] == '-') return new Node("-", lhs, rhs);
        if (charArray[pos] == '/') return new Node("/", lhs, rhs);

        //如果以上情形都不满足，则抛出异常
        throw new RuntimeException("表达式有误");
    }

    public static class Node {
        String val;
        Node left;
        Node right;

        public Node(String val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "val='" + val + '\'' +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    static Map<Character, Integer> priority = new HashMap<>() {{
        put('+', 2);
        put('-', 2);
        put('*', 1);
        put('/', 1);
    }};


    /**
     * 给定n个正整数，将它们联成一排，相邻数字首尾相接，问能拼成的最大的多位整数
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


    public static void shuffleArray(Integer[] array) {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            int index = random.nextInt(array.length - i) + i;
            // Swap the current element with the random element
            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
        System.out.println(Arrays.toString(array));
    }

    public static void shuffleArray1(Integer[] array) {
        Arrays.sort(array, new Comparator<Integer>() {
            private Random random = new Random();

            @Override
            public int compare(Integer o1, Integer o2) {
                return random.nextBoolean() ? -1 : 1;
            }
        });
        System.out.println(Arrays.toString(array));
    }

    public static void shuffleArray2(Integer[] array) {
        Collections.shuffle(Arrays.asList(array));
        System.out.println(Arrays.toString(array));
    }


    /**
     * 校验两个字符串相加比较大小的数学性质
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

    public static void main(String[] args) {
//        System.out.println(pivotIndex(new int[]{1,7,3,6,5,6}));
//        System.out.println(pivotIndex1(new int[]{1,7,3,6,5,6}));
//        System.out.println(newton(20));
//        System.out.println(eratosthenes(100));

//        int[] nums1 = {-1, 0, 1, 2, -1, -4};
//        System.out.println(threeSum(nums1));
//
//        int[] nums2 = {0};
//        System.out.println(threeSum(nums2));
//
//        int[] nums3 = {1, 2, 3, 4, 5, 6};
//        System.out.println(threeSum(nums3));

//            System.out.println(simplifyPath("/./a/../b/c"));

//            rhombus(6);

//        System.out.println(rhombus(1));

//        System.out.println(fibonacci(10));


//        int[] array = {3, 1, 2, 4, 5, 7, 6, 9};
//        System.out.println(Arrays.toString(incrementalSub(array)));
//        System.out.println(Arrays.toString(incrementalSub1(array)));

//        int[] array = {1, 4, 3, 2, 2, 5, 9};
//        System.out.println(maxPerimeter(array));

//        int[] array = {1, 4, 3, 2, 2, 5, 9};
//        System.out.println(maxScore1(array,0,array.length-1));
//        System.out.println(dp(array));
//        System.out.println(dp1(array));

//        String str = "ABCABCAABCABCD";
//        String pattern = "ABCABCD";
//        int[] next = new int[pattern.length()];
//        getNext(pattern.toCharArray(),next);
//        System.out.println(search(str.toCharArray(),pattern.toCharArray(),next));

//        int[] array = new int[]{2,7,9,3,1};
//        System.out.println(rob(array,array.length - 1));
//        System.out.println(rob(array));

//        int[] arrayA = new int[]{2,5,7,9,8};
//        int[] arrayB = new int[]{10,2,5,6,7};
//        System.out.println(Arrays.toString(advantage(arrayA,arrayB)));
//        System.out.println(Arrays.toString(advantage1(arrayA,arrayB)));

//        System.out.println(Arrays.toString(moveZero(new int[]{0,0,1})));
//        System.out.println(solution2("1234567890Abcd"));
//        System.out.println(validateParenthesis("{a()}a"));

//        int[] nums1 = new int[]{1,99,200};
//        int[] nums2 = new int[]{4,101,1000};
//        merge(nums1,1,nums2,1);
//        System.out.println(Arrays.toString(nums1));

//        int i1 = removeElement1(new int[]{3, 1, 3}, 3);
//        int i2 = removeElement2(new int[]{3, 1, 3}, 3);

//        Integer[] array = new Integer[]{2,7,9,3,1};
//        shuffleArray(array);
//        shuffleArray1(array);
//        shuffleArray2(array);

//        int i = maxProfit(new int[]{7, 1, 5, 3, 6, 4});
//        System.out.println(i);

//        int i1 = majorityElement(new int[]{7, 7, 5, 7, 5, 1, 5, 7, 5, 5, 7, 7, 7, 4, 7, 7});
//        System.out.println(i1);


//        String s = longestCommonPrefix(new String[]{"aswer", "lewer", "answer"});
//        System.out.println(s);

//        boolean b = containsNearbyDuplicate(new int[]{1, 2, 3, 1}, 3);
//        System.out.println(b);

//        int i = removeTwiceDuplicates(new int[]{0,0,1,1,1,1,2,3,3});
//        System.out.println(i);

//        int[] nums = new int[]{1,2,3,4,5,6,7};
//        rotate(nums,7);
//        System.out.println(Arrays.toString(nums));

//        int maxMultiProfit = maxMultiProfit(new int[]{5, 4, 3, 2, 1});
//        System.out.println(maxMultiProfit);

//        int i1 = canCompleteCircuit(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2});
//        System.out.println(i1);

//        boolean b = canJump(new int[]{2, 3, 1, 1, 4});
//        System.out.println(b);

//        int jump = jump(new int[]{2, 3, 0, 1, 4});
//        System.out.println(jump);

//        int candy = candy(new int[]{1,2,3,2,1,0});
//        System.out.println(candy);

//        Deque<String> deque = new LinkedList<>();
//        deque.add("22");
//        deque.add("/");
//        deque.add("11");
//        deque.add("*");
//        deque.add("2");
//        System.out.println(deque(deque));
//
//        String calculate = calculate("3+4*1*2/3*3-10+3*3+10*1-10");
//        System.out.println(calculate);
//
//        String s = calculateNew("1-1*2");
//        String s = calculateBrackets("3+5/5+(1+2/4)*(1+1)");
//        String s = calculateBrackets("2*(3+1)/4*(3+5/5)");
//        System.out.println(s);

//        Node node = buildCalculateTree("2*(3+1)/4*(3+5/5)");
//        System.out.println(node);
//        double v = calculateByTree(node);
//        System.out.println(v);

//        Random random = new Random();
//        int i1 = random.nextInt(2);
//        System.out.println(i1);


        pieceNumber(new String[]{"223", "99", "232", "999", "0", "10"});
        pieceNumber1(new String[]{"223", "99", "232", "999", "0", "10"});
        pieceNumber2(new String[]{"223", "99", "232", "999", "0", "10"});
        /**
         * 严格证明比较困难，这里可以通过大量数据验证是否可以比较
         */
        for (int i = 0; i < 100; i++) {
            String a = gen();
            String b = gen();
            String c = gen();
            Boolean b1 = check_reflexive(a); //反身性
            Boolean b2 = check_reflexive(b);
            Boolean b3 = check_reflexive(c);
            Boolean b4 = check_antisymmetric(a, b); //非对称性
            Boolean b5 = check_antisymmetric(a, c);
            Boolean b6 = check_antisymmetric(b, c);
            Boolean b7 = check_transitive(a, b, c); //传递性
            if (!b1 | !b2 | !b3 | !b4 | !b5 | !b6 | !b7) { //查看什么情况下不满足“比较”的数学上的性质
//            if (b1 == false || b2 == false || b3 == false
//                    || b4 == false || b5 == false || b6 == false || b7 == false){
                System.out.println("\nsomething goes wrong");
            }
        }
        pieceNumber3(new String[]{"223", "99", "232", "999", "0", "10"});


    }
}

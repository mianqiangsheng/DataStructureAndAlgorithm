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
    public static int pivotIndex(int[] nums){
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
     * @param nums
     * @return
     */
    public static int pivotIndex1(int[] nums){
        int sum = Arrays.stream(nums).sum();
        int total = 0;
        for (int i = 0; i < nums.length; i++) {
           if (2 * total + nums[i] == sum){
               return i;
           }
            total += nums[i];
        }
        return -1;
    }


    /**
     * 使用牛顿递归计算某个整数的平方根
     * @param x
     * @return
     */
    public static double newton(int x){
        if (x == 0){
            return 0;
        }
        return sqrt(x,x);
    }

    private static double sqrt(double i, int x){
        double res = (i + x/i)/2; // i可以传任何已知的x的因数
        if (res == i){ //由于double存在精度，所以递归后一定出现相等的时候，如果相等，说明就是平方根
            return i;
        }else {
            return sqrt(res,x); //如果i≠i和x/i的平均值，则继续递归
        }
    }

    /**
     * 统计整数n以内的素数个数
     * 埃氏筛选法——以空间换时间的一种算法
     * @param n
     * @return
     */
    public static int eratosthenes(int n){
        boolean[] isComposite = new boolean[n]; //构造长度正好等于n的布尔数组，默认都表示是素数
        int count = 0; //素数计数器
        for (int i = 2; i < n; i++) { //从2开始遍历，0、1都不是素数，起始2是素数
            if (!isComposite[i]){ //正好i=2传入，2不是合数(即表示是宿素数)
                count++; //计数器+1
                for (int j = i * i; j < n; j+=i) { //从i²开始的所有小于n的数标记为合数，减少了外层遍历次数
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
     * @param nums
     * @return
     */
    public static int removeDuplicate(int[] nums){
        if (nums.length == 0)
            return 0;

        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]){
                i++;
                nums[i] = nums[j];
            }
        }
        return i;
    }

    /**
     * 求出 nums 数组中所有和为0的三元组（无重复）
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum(int[] nums){
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
            if (i != 0 && nums[i] == nums[i - 1])   continue;

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
     * @param path
     * @return
     */
    public static String simplifyPath(String path) {

        Deque<String> deque = new ArrayDeque<>();

        String[] spiltArr = path.split("/");

        for (int i = 0; i < spiltArr.length; i++){

            if ("".equals(spiltArr[i]))
                continue;
            else if ("..".equals(spiltArr[i])) {
                if (deque.size() > 0)
                    deque.poll();
            } else if (!".".equals(spiltArr[i])) {
                deque.offer(spiltArr[i]);
            }
        }

        if (deque.size() != 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("/");
            while (deque.size() != 0)
            {
                sb.append(deque.poll());
                if (deque.size() > 0){
                    sb.append("/");
                }
            }
            return sb.toString();
        } else {
            return "/";
        }


    }

    /**
     *  输出半个菱形
     * @param height 菱形高度
     */
    public static String rhombus(int height) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<height;i++){
            int i1 = height -i -1;
            for(int j=0;j<i1;j++){
                sb.append(" ");
            }
            int i2 = 2*i+1;
            for(int j=0;j<i2;j++){
                sb.append("*");
            }
            sb.append("\r\n");
        }

        return sb.toString();

    }

    /**
     * 返回指定下标的斐波那契数列值
     * 双指针
     * @param num 斐波那契数列下标
     * @return
     */
    public static int fibonacci(int num){

        if (num == 0)
            return 0;
        if (num == 1)
            return 1;

        int low = 0,high = 1;
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
     * @param nums 数组
     * @param k 子数组长度
     * @return
     */
    public static double findMaxAverage(int[] nums, int k){

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
            sum = sum - nums[i-k] + nums[i];
            if (sum > max)
                max = sum;
        }

        return 1.0 * max/k;
    }

    static class ListNode{
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
     * @param head
     * @return
     */
    public static boolean findCycle(ListNode head){
        if (head == null || head.next == null)
            return false;

        ListNode slow = head; //slow指针
        ListNode quick = head.next; //quick指针

        //如果快慢指针不重合，则遍历直到快指针到达链表末尾
        while (slow != quick){
            if (quick == null || quick.next == null){ //这种时候说明没有环
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
     * @param array
     * @return
     */
    public static int[] incrementalSub(int[] array){
        int maxLength = 0;
        int[] output = new int[0];
        for (int i = 0; i < array.length; ) {
            List<Integer> result = new ArrayList();
            result.add(array[i]);
            for (int j = i; j < array.length - 1; j++) {
                if (array[j+1] > array[j]){
                    result.add(array[j+1]);
                }else {
                    i = j+1+1;
                    break;
                }
            }
            if (result.size() > maxLength){
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
     * @param array
     * @return
     */
    public static int[] incrementalSub1(int[] array){
        int startIndex = 0;
        int maxLength = 1;
        int start = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i]<=array[i-1]){
                startIndex = i; //后一个元素小于前一个元素，重置子数组开始下标
            }
            if (i-startIndex+1 > maxLength){ //累计当前子数组长度，定位子数组开始下标、长度
                start = startIndex;
                maxLength = i-startIndex+1;
            }
        }
        int[] output = new int[maxLength];
        System.arraycopy(array,start,output,0,maxLength);
        return output;
    }

    /**
     * 给定由一些正数(代表长度)组成的数组array，返回由其中三个长度组成的、面积不为零的三角形的最大周长
     * @param array
     * @return
     */
    public static int maxPerimeter(int[] array){
        Arrays.sort(array);
        for (int i = array.length-1; i > 1 ; i--) {
            if (array[i] < array[i-1] + array[i-2]){
                return array[i] + array[i-1] + array[i-2];
            }
        }
        return 0;
    }

    /**
     * 给定一个表示分数的非负整数数组。 玩家1从数组任意一端拿取一个分数，随后玩家2继续从剩余数组任意一端拿取分数，然后玩家1。
     * 每次一个玩家只能拿取一个分数，分数被拿取之后不再可取。直到没有剩余分数可取时游戏结束。最终获得分数总和最多的玩家获胜。
     *
     * 假设每个玩家的玩法都会使他的分数最大化。
     *
     * 预测玩家1是否会成为赢家。
     *
     * 递归，得出按照以上游戏规则玩家可以获得的最高分数（假定对手也是按照一样的策略）
     *
     * @param array
     * @param left
     * @param right
     * @return
     */
    public static int maxScore(int[] array, int left, int right){
        if (left == right) //当数组只有一个元素，(左右)得分均为这个元素值
            return array[left];

        int leftResult = 0,rightResult = 0; //分别用leftResult记录选择左侧元素的得分，rightResult记录选择右侧元素的得分
        if (right - left == 1){ //当数组只有二个元素，左侧得分和右侧得分就是左右元素值
            leftResult = array[left];
            rightResult= array[right];
        }
        if(right - left >= 2){ //当数组剩余元素大于2
            //选择左侧元素的得分=左侧元素值+对手按照相同策略留下的数组的得分最小值（对手评估选择左或右，让你后续选择得分最低）
            leftResult = array[left] + Math.min(maxScore(array, left+2, right),maxScore(array, left+1, right-1));
            //选择右侧元素的得分=右侧元素值+对手按照相同策略留下的数组的得分最小值（对手评估选择左或右，让你后续选择得分最低）
            rightResult = array[right] + Math.min(maxScore(array, left, right-2),maxScore(array, left+1, right-1));
            //todo 可以发现以上存在重复递归，有改进余地
        }

        return Math.max(leftResult,rightResult);
    }

    /**
     * 思路2：每一轮玩家1和玩家2选择完成后，对玩家1来说，玩家1-玩家2存在一个差值，就是该轮玩家1领先的数值
     * 对于玩家1来说，最优策略是让数组元素选尽后，差值最大
     *
     * 递归：得出按照以上游戏规则玩家可以获得的最高分数差值（假定对手也是按照一样的策略）
     * @param array
     * @param left
     * @param right
     * @return
     */
    public static int maxScore1(int[] array, int left, int right){
        if (left == right) //当数组只有一个元素，差值就是这个元素值
            return array[left];

        //甲选择左元素，（甲-乙）差值 = （甲）左元素值 - （乙-甲）剩余元素差值 -> 甲选左元素时，甲-乙的差值
        int leftResult = array[left] - maxScore1(array,left+1,right);
        int rightResult = array[right] - maxScore1(array,left,right-1);
        //todo 可以发现以上存在重复递归，有改进余地

        return Math.max(leftResult,rightResult);
    }

    /**
     * 思路3：以上递归思路里都会存在重复计算递归的问题，比如计算了maxScore(array, 1, 10)，
     * 由于这个值没有存，导致后续递归可能还会重新计算maxScore(array, 1, 10)，导致效率低下。
     * 自然可以想到是否可以把每次计算的maxScore(array, left, right)值存储下来，针对相同的[left, right]直接查表获取。
     *
     * 动态规划 dp数组
     *
     * @param array
     * @return
     */
    public static int dp(int[] array){
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
        for (int i = length-2; i >= 0; i--) { //i表示数组左下标，玩家最多可以选择到待选数组length -2下标，必须从大到小进行推导
            for (int j = i+1; j < length; j++) { //j表示数组右下标，而且必须从i+1位置开始，从小到大进行推导
                dpArray[i][j] = Math.max(array[i] - dpArray[i+1][j], array[j] - dpArray[i][j-1]);
            }
        }

        return dpArray[0][length-1]; //返回整个待选数组array的玩家最大可能得分差值
    }

    /**
     * 动态规划-改进版本
     *
     * 其实我们要得到的是dp[0][length-1]的值，不关心dp[1][length-1]、dp[2][length-1]...的值
     * 所以我们可以简化dp为一维数组，默[][]前下标取0
     *
     * i=0,j=length-1 最终可以根据
     * dp[0][1] = max(array[0] - dp[1][1], array[1] - dp[0][0])
     * dp[1][2] = max(array[1] - dp[2][2], array[2] - dp[1][1])
     * ...
     * dp[length-1-1][length-1] = max(array[length-1-1] - dp[length-1][length-1], array[length-1] - dp[length-1-1][length-1-1])
     * 求得
     *
     * dp[0][2] = max(array[0] - dp[1,2], array[2] - dp[0,1])
     *
     * 由于i--，所以dp[1,2]先被算出来
     * 由于j++,所以dp[0,1]先被算出来
     *
     * 最终用dpArray[j]一维数组就可以存储出dp[0][j]的结果
     *
     *
     * @param array
     * @return
     */
    public static int dp1(int[] array){
        int length = array.length;
//        int[][] dpArray = new int[length][length]; //初始化dp二维数组，2个下标分别表示待选数组的左右下标
        int[] dpArray = new int[length];
        for (int i = 0; i < length; i++) {
//            dpArray[i][i] = array[i]; //针对左右下标相同的情况，差值就是该元素本身
            dpArray[i] = array[i]; //针对左右下标相同的情况，差值就是该元素本身
        }

        for (int i = length-2; i >= 0; i--) { //i表示数组左下标，玩家最多可以选择到待选数组length -2下标
            for (int j = i+1; j < length; j++) { //j表示数组右下标，针对某个可选择的左下标i，玩家可选择的右下标范围是[i+1,length -1]
//                dpArray[i][j] = Math.max(array[i] - dpArray[i+1][j], array[j] - dpArray[i][j-1]);
                dpArray[j] = Math.max(array[i] - dpArray[j], array[j] - dpArray[j-1]);
            }
        }

//        return dpArray[0][length-1]; //返回整个待选数组array的玩家最大可能得分差值
        return dpArray[length-1];
    }


    /**
     * KMP算法，求str中pattern字符串出现的下标，实现String.indexOf()功能，但是效率更高
     * @param str 待匹配字符串
     * @param pattern 目标字符串
     * @param next next数组
     * @return
     */
    public static int search(char[] str, char[] pattern, int[] next){
        int i = 0, j = 0;

        while (i < str.length && j < pattern.length){
            if (j == -1 || str[i] == pattern[j]){ //如果i元素和j元素相同，则继续移动i和j进行匹配
                i++;
                j++;
            }else {  //如果不等，则从前面推出的next[]中找到下标使得i元素和j元素相同，重新开始匹配
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
     *
     * 对于倒数第二位C，
     * 前缀：A,AB,ABC,ABCA,ABCAB
     * 后缀：B,BC,ABC,CABC,BCABC
     * 前缀和后缀都有ABC，所以PMT值是3
     *
     * PMT数组：  0 0 0 1 2 3 0
     * next数组：-1 0 0 0 1 2 3
     * @param pattern
     * @param next
     */
    public static void getNext(char[] pattern, int[] next){

        next[0] = -1;
        int i = 0, j = -1;

        while (i < pattern.length){
            if (j == -1 ){ //重置j，从头开始匹配
                i++;
                j++;
            }else if (pattern[i] == pattern[j]){ //如果i元素和j元素相同，则记录next[i+1]的值为当前累计的j
                i++;
                j++;
                next[i] = j;
            }else { //如果不等，则从前面推出的next[]中找到下标使得i元素和j元素相同
                j = next[j];
            }
        }
    }

    /**
     * 求一个数组中所有不相邻元素和最大的值
     *
     * 递归，针对第i个元素是否取，有2种结果，哪种结果大就选择取或不取
     *
     * @param array
     * @param end
     * @return
     */
    public static int rob(int[] array, int end){
       if (end == 0)
           return array[0];

       if (end == 1)
           return Math.max(array[0],array[1]);

        //针对位置i的元素，要不要选
        //子结构的最优解 -> 总体的最优解
        return Math.max(rob(array, end - 2) + array[end], rob(array,end-1));
        //todo 可以发现以上存在重复递归，有改进余地
    }

    /**
     * 求一个数组中所有不相邻元素和最大的值
     *
     * 动态规划 dp数组解法
     * @param array
     * @return
     */
    public static int rob(int[] array){
        int length = array.length;
        if (array == null || length == 0)
            return 0;
        if (length == 1)
            return array[0];

        //定义dp数组
        int[] dp = new int[length];
        //定义dp数组初始值
        dp[0] = array[0];
        dp[1] = Math.max(array[0],array[1]);

        for (int i = 2; i < length; i++) {
            dp[i] = Math.max(dp[i - 2] + array[i], dp[i-1]);
        }

        return dp[length-1];
    }

    /**
     * 田忌赛马，给定数组A和数组B，将数组A元素重新排序，使得数组A中比数组B对应位置大的元素个数最多
     *
     * @param arrayA
     * @param arrayB
     * @return
     */
    public static int[] advantage(int[] arrayA, int[] arrayB){

        //存储数组A需要移动到数组B对应位置的元素
        Map<Integer,Integer> aMap = new HashMap<>();

        //缓存数组B元素的原始下标
        Map<Integer,Integer> bCache = new HashMap<>();
        for (int i = 0; i < arrayB.length; i++) {
            bCache.put(arrayB[i],i);
        }

        //复制数组B到tempB
        int[] tempB = new int[arrayB.length];
        System.arraycopy(arrayB,0,tempB,0,arrayB.length);

        //对tempB进行排序
        Arrays.sort(tempB);
        Arrays.sort(arrayA);

        int j = 0;

        for (int i = 0; i < tempB.length; i++) {
            if (tempB[j] >= arrayA[i]){
                continue;
            }else if (tempB[j] < arrayA[i]){
                aMap.put(bCache.get(tempB[j]),arrayA[i]);
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
     *
     * 使用队列存储垃圾元素，链表存储排位元素
     *
     * @param arrayA
     * @param arrayB
     * @return
     */
    public static int[] advantage1(int[] arrayA, int[] arrayB){

        int[] sortB = arrayB.clone();
        Arrays.sort(sortB);
        Arrays.sort(arrayA);

        //给数组B每个位置添加队列，用于存放数组A中对应较大元素
        Map<Integer,Deque<Integer>> bMap = new HashMap<>();
        for (int b : arrayB) {
            bMap.put(b,new LinkedList<>());
        }
        //构造队列，用于存储数组A中无用的垃圾元素
        Deque<Integer> aq = new ArrayDeque<>();

        int j = 0;
        for (int a : arrayA) {
            if (a > sortB[j]){
                bMap.get(sortB[j++]).add(a); // 将数组A中较大元素放置到数组B对应位置元素的队列中，并同步后移数组B
            }else {
                aq.add(a); //将数组A中较小元素扔到垃圾队列中
            }
        }

        int[] result = new int[arrayA.length];

        for (int i = 0; i < arrayB.length; i++) {
            if (bMap.get(arrayB[i]).size() > 0){
                result[i] = bMap.get(arrayB[i]).poll();
            }else {
                result[i] = aq.poll();
            }
        }

        return result;

    }

    /**
     * 将数组中0元素移动到最后，保持其他剩余元素顺序不变
     * Input: [1,0,1,2,0,1,3]
     * Output: [1,1,2,1,3,0,0]
     * @param arr
     * @return
     */
    public static int[] moveZero(int[] arr){

        int[] result = new int[arr.length];

        int j = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0){
                result[j++] = arr[i];
            }else {
                result[arr.length - 1 - j] = arr[i];
            }
        }

        return result;
    }

    /**
     * https://blog.csdn.net/chenxy_bwave/article/details/123911967
     * @param password
     * @return
     */
    public static String solution2(String password) {
        // 1234567890Abcd
        //排序字符，进行重复字符计数+字符类型计数
        char[] charArray = password.toCharArray();

        boolean hasNumber =false;
        boolean hasLow =false;
        boolean hasUpper =false;

        if(charArray.length < 8 || charArray.length > 22) {
            throw new UnsupportedOperationException("length must between 8 to 22");
        }

        for(int i = 0;i<charArray.length - 2;i++){
            if(charArray[i] == charArray[i+1] && charArray[i+1] == charArray[i+2]){
                return "weak";
            }
            if(charArray[i] >= 97 && charArray[i] <= 122) {
                hasLow = true;
            }
            if(charArray[i] >= 65 && charArray[i] <= 90) {
                hasUpper = true;
            }
            if(charArray[i] >= 48 && charArray[i] <= 57) {
                hasNumber = true;
            }

            if(i == charArray.length - 3){
                if(charArray[i+1] > 97 && charArray[i+1] <= 122 || charArray[i+2] > 97 && charArray[i+2] <= 122) {
                    hasLow = true;
                }
                if(charArray[i+1] > 65 && charArray[i+1] <= 90 || charArray[i+2] > 65 && charArray[i+2] <= 90) {
                    hasUpper = true;
                }
                if(charArray[i+1] > 48 && charArray[i+1] <= 57 || charArray[i+2] > 48 && charArray[i+2] <= 57) {
                    hasNumber = true;
                }
            }

        }

        if(!(hasLow && hasUpper && hasNumber)) {
            return "weak";
        }

        return "strong";
    }

    /**
     *  判断括号是否使用正确
     *  () => true
     *  )(())) => false
     *
     *  左括号进栈，右括号判断是否与栈顶元素成对使用。
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

    public static void merge(int[] nums1, int m, int[] nums2, int n) {

        /**
         * 解法1 合并排序算法思想应用
         */
//        int index = 0,i = 0, j = 0;
//        int[] tempArray = new int[m+n];
//        while(i < m && j < n){
//            if(nums1[i] >= nums2[j]){
//                tempArray[index] = nums2[j];
//                index++;
//                j++;
//            }else{
//                tempArray[index] = nums1[i];
//                index++;
//                i++;
//            }
//        }
//        while(i < m){
//            tempArray[index++] = nums1[i++];
//        }
//        while(j < n){
//            tempArray[index++] = nums2[j++];
//        }
//
//        System.arraycopy(tempArray,0,nums1,0,tempArray.length);

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

    public static int removeElement(int[] nums, int val) {
        int result = nums.length;

        for (int i=0;i<result;i++) {
            if (nums[i] == val) {
                nums[i--] = nums[--result];
            }
        }

        return result;
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

//        removeElement(new int[]{3,1,3},3);

        Integer[] array = new Integer[]{2,7,9,3,1};
        shuffleArray(array);
        shuffleArray1(array);
        shuffleArray2(array);
    }
}

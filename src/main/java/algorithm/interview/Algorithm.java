package algorithm.interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

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
     * @param path
     * @return
     */
    public static String simplifyPath(String path) {

        Stack<String> stack = new Stack<>();

        String[] spiltArr = path.split("/");

        for (int i = 0; i < spiltArr.length; i++){

            if ("".equals(spiltArr[i]))
                continue;
            else if ("..".equals(spiltArr[i])) {
                if (stack.size() > 0)
                    stack.pop();
            } else if (!".".equals(spiltArr[i])) {
                stack.push(spiltArr[i]);
            }
        }

        if (stack.size() != 0)
        {
            StringBuilder sb = new StringBuilder();
            sb.append("/");
            while (stack.size() != 0)
            {
                sb.append(stack.pop());
                if (stack.size() > 0){
                    sb.append("/");
                }
            }
            return sb.toString();
        } else {
            return "/";
        }


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

            System.out.println(simplifyPath("/./a/../b"));
    }
}

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

    public static int removeDuplicates(int[] nums) {

        int pointeri = 0;
        int pointerj = 1;

        while(pointerj < nums.length){
            if(nums[pointeri] == nums[pointerj]){
                pointerj++;
            }else if(nums[pointeri] != nums[pointerj]){
                nums[pointeri+1] = nums[pointerj];
                pointeri++;
            }
        }

        return pointeri + 1;

    }

    public static int removeDuplicates1(int[] nums) {

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

        if (nums.length <=2) {
            return nums.length;
        }

        int slow = 2, fast = 2;

        while (fast < nums.length){
            if(nums[slow - 2] != nums[fast]){ // 这里是关键
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
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


    public static String calculate(String str){

        Deque<String> deque1 = new LinkedList<>();
        Deque<String> deque2 = new LinkedList<>();

        List<String> strings = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (char c : str.toCharArray()) {
            if (c != '+' && c != '-' && c != '*' && c != '/'){
                stringBuilder.append(c);
            }else {
                String string = stringBuilder.toString();
                if (string.length() > 0){
                    strings.add(string);
                    stringBuilder.setLength(0);
                }
                strings.add(String.valueOf(c));
            }
        }

        strings.add(stringBuilder.toString());

        for (int i = 0; i < strings.size(); i+=2) {
            if (i == strings.size() - 1){
                if (strings.get(i-1).equals("+") || strings.get(i-1).equals("-")){
                    deque1.add(strings.get(i));
                }else {
                    deque2.add(strings.get(i));
                }
            }else {
                String aChar = strings.get(i);
                String bChar = strings.get(i+1);
                if (bChar.equals("+") || bChar.equals("-")){
                    if (deque2.size() == 0){
                        deque1.add(aChar);
                        deque1.add(bChar);
                    }else{
                        deque2.add(aChar);
                        String deque = deque(deque2);
                        deque1.add(deque);
                        deque1.add(bChar);
                    }
                }else {
                    deque2.add(aChar);
                    deque2.add(bChar);
                }
            }
        }

        if (deque2.size() > 0){
            String deque = deque(deque2);
            deque1.add(deque);
        }

        return deque(deque1);
    }

    public static String deque(Deque<String> deque){
        while (deque.size() > 1){
            String[] chars = new String[3];
            chars[0] = deque.remove();
            chars[1] = deque.remove();
            chars[2] = deque.remove();
            Double cal = cal(chars);
            deque.push(cal.toString());
        }
        return deque.pop();
    }

    public static Double cal(String[] strings){
        String param1 = strings[0];
        String operator = strings[1];
        String param2 = strings[2];
        if (operator.equals("+")){
            return Double.valueOf(param1) + Double.valueOf(param2);
        }else if (operator.equals("-")){
            return Double.valueOf(param1) - Double.valueOf(param2);
        }else if (operator.equals("*")){
            return Double.valueOf(param1) * Double.valueOf(param2);
        }else if (operator.equals("/")){
            return Double.valueOf(param1) / Double.valueOf(param2);
        }
        return 0.0;
    }

    public static void main(String[] args) {
//        int[] array = new int[]{5,2,3,10,7,0};
//        insertionSort(array,0,array.length-1);
//        System.out.println(Arrays.toString(array));
//
//        int[] nums = new int[]{5,-6,3,-1,0};
//        int max = maxSubArray(nums);
//        System.out.println(max);
//        int max1 = maxSubArray1(nums);
//        System.out.println(max1);
//
//        System.out.println(solution(5));
//
//        int i = find(array, 5);
//        System.out.println(i);

//        System.out.println(solution("1234567890Abcd"));

//        removeDuplicates(new int[]{0,0,1,1,1,1,2,3,3});
//        removeDuplicates1(new int[]{0,0,1,1,1,1,2,3,3});
//        removeDuplicates1(new int[]{1,1,1,1,3});
//        removeDuplicates1(new int[]{1,2});

//        int[] nums = new int[]{5,-6,3,-1,0};
//        System.out.println(insertSort(nums));

        Deque<String> deque = new LinkedList<>();
        deque.add("22");
        deque.add("/");
        deque.add("11");
        deque.add("*");
        deque.add("2");
        System.out.println(deque(deque));

        String calculate = calculate("3+4*1*2/3*3-10+3*3+10");
        System.out.println(calculate);
    }
}

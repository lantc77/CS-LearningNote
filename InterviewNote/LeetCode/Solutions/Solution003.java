package leetcode;
import java.util.*;

/**
 * @ClassName: Solution003
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/4/22 17:44
 * @Version: TODO
 **/
public class Solution003 {
    /**
     * 通过使用 HashSet 作为滑动窗口，我们可以用 O(1)O(1) 的时间来完成对字符是否在当前的子字符串中的检查
     * @param
     * @return
     */
    public int lengthOfLongestSubstring1(String s) {
        int left = 0, right = 0, maxLen = 0;
        Set<Character> set = new HashSet();
        while(left < s.length() && right < s.length()){
            if(!set.contains(s.charAt(right))){
                set.add(s.charAt(right++));
                maxLen = Math.max(maxLen, right - left);
            }
            else{
                set.remove(s.charAt(left++));
            }
        }
        return maxLen;
    }

    /**
     * 优化的滑动窗口
     */
    public int lengthOfLongestSubstring2(String s) {
        int n = s.length(), maxLen = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int right = 0, left = 0; right < n; right++) {
            if (map.containsKey(s.charAt(right))) {
                left = Math.max(map.get(s.charAt(right)), left);
            }
            maxLen = Math.max(maxLen, right - left + 1);
            map.put(s.charAt(right), right + 1);
        }
        return maxLen;
    }

    public static void main(String args[]){
        String s = "abcabcbb";
        Solution003 test = new Solution003();
        System.out.println(test.lengthOfLongestSubstring2(s));
    }
}

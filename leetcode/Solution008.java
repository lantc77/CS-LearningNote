package leetcode;

/**
 * @ClassName: Solution008
 * @Description:
 * @Author: Eric Lan
 * @Date: 2020/4/26 10:24
 * @Version: TODO
 **/
public class Solution008 {
    /**
     * 要进行的判断：
     * 去掉首尾端空格 trim()
     * 第一个位置不是数字：
     *    第二个位置还不是数字：  return 0（主要可能是重复的正负号）
     *    第二个是数字：
     *      是正负号： 可以继续进行转换
     *      不是正负号： return0
     *
     * 数字的0都要一律忽略，但是如果只有0，也要返回0
     *
     * @param str
     * @return
     */
    public int myAtoi(String str) {
        if(str.length() == 0)
            return 0;
        str = str.trim();
        int sign = 1;
        if(!Character.isDigit(str.charAt(0))) {
            if(!Character.isDigit(str.charAt(1))) return 0;
            if (str.charAt(0) == '+') ;
            else if (str.charAt(0) == '-') sign = -1;
            else return 0;

            str = str.substring(1);
        }
        int ans = 0;
        for(int i = 0; i < str.length(); i++){
            if (Character.isDigit(str.charAt(i))) {
                int pop = str.charAt(i) - '0';
                if(sign == 1 && (ans>Integer.MAX_VALUE/10 || (ans==Integer.MAX_VALUE/10 && pop>7)))
                    return Integer.MAX_VALUE;
                if(sign == -1 && (ans>Integer.MAX_VALUE/10 || (ans==Integer.MAX_VALUE/10 && pop>8)))
                    return Integer.MIN_VALUE;
                ans = ans * 10 + pop;
            }
            else break;
        }
        return ans;
    }

    public static void main(String args[]){
        System.out.println(new Solution008().myAtoi("  +2147483649"));
    }
}

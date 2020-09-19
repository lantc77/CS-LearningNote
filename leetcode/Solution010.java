package leetcode;

import java.util.HashMap;

/**
 * @ClassName: Solution010
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/4/26 17:10
 * @Version: TODO
 **/
public class Solution010 {
    /**
     * 暴力递归法
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch1(String s, String p) {
        if(p.isEmpty()) return s.isEmpty();
        boolean first = (!s.isEmpty()) && (p.charAt(0)=='.'||p.charAt(0)==s.charAt(0));

        /*
        如果pattern的下一个字符是通配符'*'，那么接下来传入递归函数的text将移动，而
        pattern保持不变，使得始终检查text的第一个字符与pattern中*前的那个字符。当
        text的首字符和*前的字符不匹配时，接下来的递归中text不变而pattern将跳过两个
        字符
        举例：aabc 和 a*.c
        aabc a*.c
        abc  a*.c->跳过两个 剩下".c"
         */
        if(p.length() >= 2 && p.charAt(1) == '*')
            return (first && isMatch1(s.substring(1), p)) ||
                isMatch1(s, p.substring(2));
            //第二个条件不需要“与”first
        else
            return first && isMatch1(s.substring(1), p.substring(1));
    }

    enum Result {
        TRUE, FALSE
    }
    Result[][] memo;
    public boolean isMatch2(String s, String p){
        /*
        为什么要加1？
        aabc其实不止四个字符，我们还要考虑“空”这个情况，因此需要留出五个存储空间。
        text和pattern都有空字符的情况存在。
         */
        memo = new Result[s.length()+1][p.length()+1];
        return dp(0,0, s, p);
    }

    public boolean dp(int i, int j, String s, String p){
        if(memo[i][j] != null) return memo[i][j] == Result.TRUE;
        // p遍历完以后，s也应该遍历完
        if(j == p.length()) return i == s.length();

        boolean first = (i < s.length()) && (p.charAt(j)=='.'||p.charAt(j)==s.charAt(i));
        boolean ans;
        if( j <= p.length() - 2 && p.charAt(j+1) == '*')
            ans = dp(i, j+2, s, p) || (first && dp(i+1, j, s, p));

        else
            ans = first && dp(i+1, j+1, s, p);
        memo[i][j] = ans ? Result.TRUE : Result.FALSE;
        return ans;
    }


    public static void main(String args[]){
        String s = "aabc";
        String p = "a*.c";
        System.out.println(new Solution010().isMatch2(s,p));
    }
}

package leetcode;

/**
 * @ClassName: Solution009
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/4/26 15:41
 * @Version: TODO
 **/
public class Solution009 {
    public boolean isPalindrome(int x) {
        if(x < 0)
            return false;
        if(x>=0 && x<9)
            return true;

        int pop, rev = 0;
        while(x > 0){
            pop = x % 10;
            x /= 10;
            // 溢出，不是回文。
            if(rev>Integer.MAX_VALUE/10 || (rev==Integer.MAX_VALUE/10&&pop>7))
                return false;
            rev = rev*10 + pop;
        }
        return (x == rev);
    }

    public static void main(String args[]){
        System.out.println(new Solution009().isPalindrome(121));
    }
}

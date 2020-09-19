package leetcode;

/**
 * @ClassName: Solution007
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/4/26 11:36
 * @Version: TODO
 **/
public class Solution007 {
    public int reverse1(int x){
         int ans = 0, pop = 0;
         while(x != 0){
            pop = x % 10;
            x /= 10;
            if(ans > Integer.MAX_VALUE/ 10 || (ans == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            if(ans < Integer.MIN_VALUE/ 10 || (ans == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            ans = ans * 10 + pop;
         }
         return ans;
    }

    public static void main(String args[]){
        System.out.println(new Solution007().reverse1(2147483645));
    }
}


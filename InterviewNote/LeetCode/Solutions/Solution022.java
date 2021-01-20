package leetcode;
import java.util.*;
/**
 * @ClassName: Solution022
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/5/2 20:02
 * @Version: TODO
 **/
public class Solution022 {
    private List<String> ans = new ArrayList<String>();
    public List<String> generateParenthesis(int n) {
        if(n == 0)
            return ans;
        dfs("",0,0,n);
        return ans;
    }

    // know as backtrack
    public void dfs(String curStr, int left, int right, int n){
        if(left == n && right == n){
            ans.add(curStr);
            return;
        }

        // pruning
        if(left < right)
            return;

        if(left < n)
            dfs(curStr+'(', left+1, right, n);

        if(right < n)
            dfs(curStr+')', left, right+1, n);

    }

    public static void main(String args[]){
        Solution022 test = new Solution022();
        List<String> ans = test.generateParenthesis(3);
        for(String s: ans){
            System.out.println(s);
        }
    }

}

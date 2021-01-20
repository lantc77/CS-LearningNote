package leetcode;

import java.util.Stack;

/**
 * @ClassName: Solution032
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/5/29 11:23
 * @Version: TODO
 **/
public class Solution032 {
    public int longestValidParentheses(String s) {
        if (s.length() == 0 || s.length() == 1)
            return 0;
        int curNum = 0, maxNum = 0;
        Stack<Character> stack = new Stack<Character>();
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '('){
                stack.add(s.charAt(i));
            }
            else{
                if(!stack.isEmpty() && stack.pop()=='(')
                    curNum ++;
                else{
                    maxNum = (curNum > maxNum)?curNum:maxNum;
                    curNum = 0;
                    stack = new Stack<Character>();
                }
            }
        }
        if(maxNum == 0)
            maxNum = (curNum > maxNum)?curNum:maxNum;
        return maxNum * 2;
    }
    public static void main(String args[]){
        Solution032 test = new Solution032();
        int num = test.longestValidParentheses("()(()(");
        System.out.println(num);
    }
}

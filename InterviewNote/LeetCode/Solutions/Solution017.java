package leetcode;
import java.util.*;
/**
 * @ClassName: 17. Letter Combinations of a Phone Number
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/4/27 11:19
 * @Version: TODO
 **/
public class Solution017 {
    /**
     * 基础暴力解法
     */
    private String letterMap[] = {
            "",    //0
            "",     //1
            "abc",  //2
            "def",  //3
            "ghi",  //4
            "jkl",  //5
            "mno",  //6
            "pqrs", //7
            "tuv",  //8
            "wxyz"  //9
    };
    public List<String> letterCombinations(String digits) {
        ArrayList<String> strList = new ArrayList<String>();
        if(digits.length() == 0) return strList;
        // 如果不初始化strList，32行就无法开始第一次遍历。
        strList.add("");
        // 遍历 digits
        for(int i = 0; i< digits.length(); i++){
            ArrayList<String> tempList = new ArrayList<String>();
            String letters = letterMap[digits.charAt(i) - '0'];
            for(String str: strList)
                for(int j = 0; j < letters.length(); j++)
                    tempList.add(str+letters.charAt(j));
            // 更新strList
            strList = tempList;
        }
        return strList;
    }

    /**
     * 优化方法
     * 此类排列组合问题都可以用递归解决，当达到要求的组合长度的时候可以终止递归
     */
    public String[] map = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    List<String> strList;
    public void bt(String digits,int s,StringBuilder sb){
        // 递归终止条件
        if(s==digits.length()){
            strList.add(sb.toString());
            return;
        }
        int num=digits.charAt(s)-'0';
        for(int i=0;i<map[num].length();i++){
            sb.append(map[num].charAt(i));
            bt(digits,s+1,sb);
            sb.deleteCharAt(sb.length()-1);
        }
    }
    public List<String> letterCombinations_mod(String digits) {
        strList=new ArrayList();
        if(digits.length()==0)
            return strList;
        bt(digits,0,new StringBuilder());
        return strList;
    }
    public static void main(String args[]){
        Solution017 test = new Solution017();
        List<String> strList = test.letterCombinations("23");
        for(String str: strList)
            System.out.println(str);

    }
}

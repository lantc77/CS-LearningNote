package leetcode;
import java.util.*;
/**
 * @ClassName: Solution030
 * @Description:
 * 输入：
 *   s = "barfoothefoobarman",
 *   words = ["foo","bar"]
 * 输出：[0,9]
 * @Author: Eric Lan
 * @Date: 2020/5/4 21:41
 * @Version: TODO
 **/
public class Solution030 {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> ans = new ArrayList<>();
        int wordNums =words.length; //number of words
        // special case
        if (wordNums == 0) {
            return ans;
        }
        int wordLen = words[0].length(); //length of single word
        int windowSize = wordLen * wordNums; //sliding window size
        HashMap<String, Integer> allWords = new HashMap<String, Integer>();

        // the map1 stores <word, appear times>
        for(String word: words){
            int value = allWords.getOrDefault(word, 0);
            allWords.put(word, value + 1);
        }
        // iterate all the substring of windowSize
        for(int i = 0; i < s.length() - windowSize + 1; i++){
            HashMap<String, Integer> hasWord = new HashMap<String, Integer>();
            int num = 0;
            // judge the substring
            while(num < wordNums){
                String word = s.substring(i + num*wordLen, i + (num+1)*wordLen);
                // if word not in allWords then break immediately.
                if(allWords.containsKey(word)){
                    int value = hasWord.getOrDefault(word, 0);
                    hasWord.put(word, value + 1);
                    // if the nums of word more than in the allWords, it must not right.
                    if(hasWord.get(word) > allWords.get(word))  break;
                }
                else break;
                num++;
            }
            if(num == wordNums) ans.add(i);
        }
        return ans;
    }

    public static void main(String args[]){
        Solution030 test = new Solution030();
        String[] words = new String[]{"foo","bar"};
        List<Integer> ans = test.findSubstring("barfoothefoobarman", words);
        for(int num: ans){
            System.out.println(num);
        }
    }
}

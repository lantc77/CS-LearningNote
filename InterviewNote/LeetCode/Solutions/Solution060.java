package leetcode;
import java.util.*;
/**
 * @ClassName: Solution060
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/8/7 14:58
 * @Version: TODO
 **/
public class Solution060 {
    public String getPermutation(int n, int k) {
        int[] nums = new int[n];//生成nums数组
        for (int i = 0; i < n; i++) nums[i] = i + 1;
        boolean[] used = new boolean[n];//记录当前的索引的数是否被使用过
        return dfs(nums, new ArrayList<String>(), used, 0, n, k);
    }

    /**
     * @param nums      源数组
     * @param levelList 每一层的选择
     * @param used      数组的元素是否被使用过
     * @param depth     深度，也就是当前使用的元素的索引
     * @param n         上限值
     * @param k         第k个
     * @return 第k个排列
     */
    private String dfs(int[] nums, List<String> levelList, boolean[] used, int depth, int n, int k) {
        if (depth == n) {//触发出口条件，开始收集结果集
            StringBuilder res = new StringBuilder();
            for (String s : levelList) res.append(s);
            return res.toString();
        }
        for (int i = 0; i < n; i++) {
            if (used[i]) continue;//当前元素被使用过，做剪枝
            levelList.add(nums[i] + "");//list收的是string类型
            used[i] = true;//当前元素被使用过标记
            return dfs(nums, levelList, used, depth + 1, n, k);
        }
        return null;
    }

    public static void main(String args[]){
        Solution060 test = new Solution060();
        test.getPermutation(3,5);
    }
}

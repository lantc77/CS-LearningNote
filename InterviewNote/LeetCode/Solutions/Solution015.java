package leetcode;
import java.util.*;
/**
 * @ClassName: 3sum
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/4/26 23:49
 * @Version: TODO
 **/
public class Solution015 {
    public List<List<Integer>> threeSum(int[] nums) {
        // Arrays的method很重要
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        for(int i = 0; i < nums.length - 2; i++){
            // i < 0是为了防止越界，跳过重复的元素
            if(i > 0 && nums[i]==nums[i-1]) continue;
            int left = i+1, right = nums.length-1;
            while(left < right){
                int sum = nums[i] + nums[left] + nums[right];
                if(sum==0) {
                    ans.add(new ArrayList<>
                            (Arrays.asList(nums[i], nums[left], nums[right])));
                    // 跳过重复的元素
                    while (left < right && nums[left] == nums[++left]) ;
                    while (left < right && nums[right] == nums[--right]) ;
                }
                if(sum>0) while (left < right && nums[right] == nums[--right]) ;
                if(sum<0) while (left < right && nums[left] == nums[++left]) ;
            }
        }
        return ans;
    }
}


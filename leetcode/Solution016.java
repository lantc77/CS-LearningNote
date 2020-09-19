package leetcode;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @ClassName: 16. 3Sum Closest
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/4/27 10:34
 * @Version: TODO
 **/
public class Solution016 {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        // closet 最小差距值
        int closet = Integer.MAX_VALUE, ans = 0;
        for(int i = 0; i < nums.length - 2; i++){
            if(i>0 && nums[i] == nums[i-1]) continue;
            int left=i+1, right=nums.length-1;
            while(left < right){
                int sum = nums[i] + nums[left] + nums[right];

                if(Math.abs(sum - target) < closet) {
                    ans = sum;
                    closet = Math.abs(sum - target);
                }
                if(sum < target) left ++;
                if(sum > target) right --;
                if(sum == target) return target;
            }
        }
        return ans;
    }
}

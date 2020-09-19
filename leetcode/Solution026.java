package leetcode;

/**
 * @ClassName: Solution026
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/5/4 17:34
 * @Version: TODO
 **/
public class Solution026 {
    public int removeDuplicates(int[] nums) {
        int index = 0;
        for(int i = 1; i < nums.length; i++){
            if(nums[i] != nums[i-1]){
                index ++;
                nums[index] = nums[i];
            }
        }
        return index + 1;
    }
}

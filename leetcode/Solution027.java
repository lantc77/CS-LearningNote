package leetcode;

/**
 * @ClassName: Solution027
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/5/4 19:54
 * @Version: TODO
 **/
public class Solution027 {
    public int removeElement(int[] nums, int val) {
        int index = 0;
        for(int i = 0; i < nums.length; i++){
            if(nums[i] != val){
                nums[index++] = nums[i];
            }
        }
        return index;
    }


}

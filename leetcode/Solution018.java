package leetcode;
import java.util.*;
/**
 * @ClassName: 18. 4Sum
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/4/27 15:59
 * @Version: TODO
 **/
public class Solution018 {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        if(nums.length<4) return null;

        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        for(int i = 0; i < nums.length - 3; i++){
            if(i>0 && nums[i] == nums[i-1]) continue;
            int[] slice = Arrays.copyOfRange(nums,i+1,nums.length);
            // tempList 通过3sum算法得到的三个数的list
            List<List<Integer>> tempList = ThreeSum(slice, target-nums[i]);
            if(!tempList.isEmpty())
                // 遍历tempList 把每个节点加入nums[i]
                for(List<Integer> list: tempList){
                    List<Integer> curList = list;
                    curList.add(nums[i]);
                    ans.add(curList);
                }
        }
        return ans;
    }

    public List<List<Integer>> ThreeSum(int[] nums, int target){
        Arrays.sort(nums);
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        for(int i = 0; i < nums.length - 2; i++){
            if(i > 0 && nums[i]==nums[i-1]) continue;
            int left = i+1, right = nums.length-1;
            while(left < right){
                int sum = nums[i] + nums[left] + nums[right];
                if(sum==target) {
                    list.add(new ArrayList<>
                            (Arrays.asList(nums[i], nums[left], nums[right])));
                    // 跳过重复的元素
                    while (left < right && nums[left] == nums[++left]) ;
                    while (left < right && nums[right] == nums[--right]) ;
                }
                if(sum>target) while (left < right && nums[right] == nums[--right]) ;
                if(sum<target) while (left < right && nums[left] == nums[++left]) ;
            }
        }
        return list;
    }

    public static void main(String args[]){
        Solution018 test = new Solution018();
        int[] nums = new int[]{1,0,-1,0,-2,2};
        int target = 0;
        test.fourSum(nums, target);
    }
}

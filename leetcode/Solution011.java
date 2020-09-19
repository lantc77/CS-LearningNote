package leetcode;

/**
 * @ClassName: 11. Container With Most Water
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/4/26 22:14
 * @Version: TODO
 **/
public class Solution011 {
    public int maxArea(int[] height) {
        int area, maxArea=0, left=0, right=height.length-1;
        while(left != right){
            area = Math.min(height[left], height[right]) * (right - left);
            if(area > maxArea) maxArea = area;
            if(height[left]<height[right]) left++;
            else right--;
        }
        return maxArea;
    }

    public static void main(String args[]){
        int[] height = new int[]{1,8,6,2,5,4,8,3,7};
        System.out.println(new Solution011().maxArea(height));
    }
}

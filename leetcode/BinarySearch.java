package leetcode;
/**
 * @ClassName: BinarySearch
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/7/23 14:44
 * @Version: TODO
 **/
public class BinarySearch {
    public int findLastEqual(int[] array, int key) {
        int left = 0;
        int right = array.length - 1;

        // 这里必须是 <=
        while (left <= right) {
            int mid = (left + right) / 2;
            // 当array[mid] == key时不返回, 执行left = mid + 1, 向右收缩
            if (array[mid] <= key) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
            System.out.printf("left: array[%d]:%d ", left, array[left]);
            System.out.printf("right: array[%d]:%d\n", right, array[right]);
        }
        if (right >= 0 && array[right] == key) {
            return right;
        }

        return -1;
    }

    public static void main(String args[]){
        int[] array = {1,2,3,3,3,8,9,10};
        BinarySearch bs = new BinarySearch();
        int res = bs.findLastEqual(array, 3);
        System.out.println(res);
    }
}


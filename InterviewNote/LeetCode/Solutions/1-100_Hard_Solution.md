

# 4. [Median of Two Sorted Arrays](https://leetcode-cn.com/problems/median-of-two-sorted-arrays) 

**题目描述**

```
Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.

Follow up: The overall run time complexity should be O(log (m+n)).

Example 1:

Input: nums1 = [1,3], nums2 = [2]
Output: 2.00000
Explanation: merged array = [1,2,3] and median is 2.
Example 2:

Input: nums1 = [1,2], nums2 = [3,4]
Output: 2.50000
Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
```

**解题思路**

两个数组，各自找到一个分界线 partitionX, partionY，将两个数组分为两个部分

x1, x2, ... , maxLeftX | minRightX, .... , x_n

y1, y2, ... , maxLeftY | minRightY, .... , y_n

partitionX + partionY = (x + y + 1) / 2

其中 partitionX = ( start + end ) / 2

要找到

maxLeftX  <= minRightY

maxLeftY <= minRightX

这样能一定能保证左边的数字个数等于右边，此时 

数组个数为偶数：median = avg( max( maxLeftx, maxLeftY ), min( minRightX, minRightY ))

数组个数为奇数：median = max( maxLeftx, maxLeftY )

else if ( maxLeftX > minRightY )

​	move towards left in x

此时 maxLeftX > minRightY，说明需要把 partitionX 左移（partitionY自然跟着右移，使得 maxLeftY 增大）使得 maxLeftX 减小，即 end = partitionX - 1

else if ( maxLeftY > minRightX )

​	move towards right in x

此时 maxLeftY > minRightX，说明需要把 partitionY 左移（partitionX自然跟着右移，使得 maxLeftX 增大）使得 maxLeft 减小，使得 maxLeftY 减小，即 start = partitionX + 1

```java
public double findMedianSortedArrays(int[] input1, int[] input2) {
        //if input1 length is greater than switch them so that input1 is smaller than input2.
        if (input1.length > input2.length) {
            return findMedianSortedArrays(input2, input1);
        }
        int x = input1.length;
        int y = input2.length;

        int low = 0;
        int high = x;
        while (low <= high) {
            int partitionX = (low + high)/2;
            int partitionY = (x + y + 1)/2 - partitionX;

            //if partitionX is 0 it means nothing is there on left side. Use -INF for maxLeftX
            //if partitionX is length of input then there is nothing on right side. Use +INF for minRightX
            int maxLeftX = (partitionX == 0) ? Integer.MIN_VALUE : input1[partitionX - 1];
            int minRightX = (partitionX == x) ? Integer.MAX_VALUE : input1[partitionX];

            int maxLeftY = (partitionY == 0) ? Integer.MIN_VALUE : input2[partitionY - 1];
            int minRightY = (partitionY == y) ? Integer.MAX_VALUE : input2[partitionY];

            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {
                //We have partitioned array at correct place
                // Now get max of left elements and min of right elements to get the median in case of even length combined array size
                // or get max of left for odd length combined array size.
                if ((x + y) % 2 == 0) {
                    return ((double)Math.max(maxLeftX, maxLeftY) + Math.min(minRightX, minRightY))/2;
                } else {
                    return (double)Math.max(maxLeftX, maxLeftY);
                }
            } else if (maxLeftX > minRightY) { //we are too far on right side for partitionX. Go on left side.
                high = partitionX - 1;
            } else { //we are too far on left side for partitionX. Go on right side.
                low = partitionX + 1;
            }
        }

        //Only we we can come here is if input arrays were not sorted. Throw in that scenario.
        throw new IllegalArgumentException();
    }
```

```python
class Solution:
    def findMedianSortedArrays(self, nums1: List[int], nums2: List[int]) -> float:
        if len(nums1) > len(nums2):
            return self.findMedianSortedArrays(nums2, nums1)
            
        x = len(nums1)
        y = len(nums2)
        low = 0
        high = x
        
        while low <= high:
            partitionX = (low + high) // 2
            partitionY = (x + y + 1) // 2 - partitionX
            
            maxLeftX = float("-inf") if partitionX == 0 else nums1[partitionX - 1]
            minRightX = float("inf") if partitionX == x else nums1[partitionX]
            
            maxLeftY = float("-inf") if partitionY == 0 else nums2[partitionY - 1]
            minRightY = float("inf") if partitionY == y else nums2[partitionY]
            
            if maxLeftX <= minRightY and maxLeftY <= minRightX:
                if (x + y) % 2 == 0:
                    return (max(maxLeftX, maxLeftY) + min(minRightY, minRightX)) / 2
                else:
                    return max(maxLeftX, maxLeftY)
            elif maxLeftX > minRightY:
                high = partitionX - 1
            else:
                low = partitionX + 1
```



# 41. 缺失的第一个正数

[https://leetcode-cn.com/problems/first-missing-positive/](https://leetcode-cn.com/problems/first-missing-positive/)

**题目解析**

实际上，对于一个长度为 N 的数组，其中没有出现的最小正整数只能在 [1, N+1] 中。这是因为如果 [1, N] 都出现了，那么答案是 N+1，否则答案是 [1, N] 中没有出现的最小正整数。这样一来，我们将所有在 [1, N] 范围内的数放入哈希表，也可以得到最终的答案。而给定的数组恰好长度为 N ，这让我们有了一种将数组设计成哈希表的思路：

我们对数组进行遍历，对于遍历到的数 x ，如果它在 [1, N] 的范围内，那么就将数组中的第 x-1 个位置（注意：数组下标从 0 开始）打上「标记」。在遍历结束之后，如果所有的位置都被打上了标记，那么答案是 N+1 ，否则答案是最小的没有打上标记的位置加 1 。

那么如何设计这个「标记」呢？由于数组中的数没有任何限制，因此这并不是一件容易的事情。但我们可以继续利用上面的提到的性质：由于我们只在意 [1, N]  中的数，因此我们可以先对数组进行遍历，把不在 [1, N] 范围内的数修改成任意一个大于 N的数（例如 N+1 ）。这样一来，数组中的所有数就都是正数了，因此我们就可以将「标记」表示为「负号」。算法的流程如下：

我们将数组中所有小于等于 0 的数修改为 N+1 ；

我们遍历数组中的每一个数 x ，它可能已经被打了标记，因此原本对应的数为 |x|。如果∣x∣∈[1,N]，那么我们给数组中的第 |x| - 1 个位置的数添加一个负号。注意如果它已经有负号，不需要重复添加；

在遍历完成之后，如果数组中的每一个数都是负数，那么答案是 N+1 ，否则答案是第一个正数的位置加 1。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200725141919764.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0JlYW43NzE2MDY1NDA=,size_16,color_FFFFFF,t_70)

Java
```java
class Solution {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        // 返回的值最大不超过n
        for(int i = 0; i < n; i++){
            if(nums[i] <= 0)
                nums[i] = n+1;
        }

        for(int i = 0; i < n; i++){
            if(Math.abs(nums[i]) < n+1){
                int index = Math.abs(nums[i])-1;
                if(nums[index] > 0)
                    nums[index] = -nums[index];
            }
        }

        for(int i = 0; i < n; i++){
            if(nums[i] > 0)
                return i + 1;
        }
        return n+1;
    }
}
```

# 42. 接雨水
[https://leetcode-cn.com/problems/trapping-rain-water/](https://leetcode-cn.com/problems/trapping-rain-water/)
[leetcode 官方解法](https://leetcode-cn.com/problems/trapping-rain-water/solution/jie-yu-shui-by-leetcode/)
**解题思路**

数组必有一个 ”山顶“（若有多个高度相同山顶，任取一个即可）。

根据”木桶原理“，山顶左侧的元素的盛水量 ，由左侧最大值决定；山顶右侧元素的盛水量，由右侧最大值决定。

双指针法的两个指针最终会停在 “山顶” 处。
```
             top
              __
            _/  \       __
     __    /     \     /  \
_   /  \__/       \___/    \     __
 \_/                        \___/
```

# 45. 跳跃游戏 II
[https://leetcode-cn.com/problems/jump-game-ii/](https://leetcode-cn.com/problems/jump-game-ii/)
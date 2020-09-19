主要是考虑到easy题目没什么好分析的, 故统一总结在一篇文章里. 留作记录.

收录leetcode1-100题中easy难度的题目. 带星号的表示经典题型, 或者有衍生题.

@[toc]
# 1. Two Sum (Easy)
```python
class Solution(object):
    def twoSum(self, nums, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype: List[int]
        """
        sum = []
        i = 0
        while i < len(nums) :
            try:
                j = nums.index(target-nums[i])
            except ValueError,e:
                pass
            else: 
                if i!=j:
                    sum.extend([i,j])
                    return sum
            finally:
                i +=1 
```

# 7. Reverse Integer (Easy)
```python
# integer has a range.Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−2^31,  2^31 − 1].
class Solution:
    def reverse(self, x: int) -> int:
        if x == 0:
            return 0
        if x > 0:
            s = str(x)
            s = int(s[::-1])
        else:
            s = str(x)[1:]
            s = -int(s[::-1])
        if s > 2**31 - 1 or s < -(2**31):
            return 0
        return s
```

# 9. Palindrome Number (Easy)
```python
class Solution:
    def isPalindrome(self, x: int) -> bool:
        if x < 0:
            return False
        x = str(x)
        if x == x[::-1]:
            return True
        else:
            return False
```

# 13. Roman to Integer (Easy)
[https://leetcode.com/problems/roman-to-integer/](https://leetcode.com/problems/roman-to-integer/)
**题目描述**

把罗马字母转换成整型数字.

```python
"""
前面的罗马字符后面如果小于后面的罗马字符, 结果非但不能加上前面的罗马字, 还要减去它. 
所以num -= 2*pre. 如果前面的罗马字符大于后面的, 则可以直接加上后面的.
"""
class Solution:
    def romanToInt(self, s: str) -> int:
        value_roman = {"M":1000, "D":500, "C":100, "L":50, "X":10, "V":5, "I":1}
        pre = 0
        num = 0
        for i in s:
            num += value_roman[i]
            if value_roman[i] > pre:
                num -= 2 * pre
            pre = value_roman[i]
        return num
```
# 14. Longest Common Prefix (Easy)
[https://leetcode.com/problems/longest-common-prefix/](https://leetcode.com/problems/longest-common-prefix/)
**题目描述**

给定一个字符串数组, 输出最大共同前缀
Input: ["flower","flow","flight"]
Output: "fl"

```python
"""
从头遍历所有字符串, 直到不一样的字符出现.
"""
class Solution:
    def longestCommonPrefix(self, strs: List[str]) -> str:
        # 边界条件
        if len(strs) == 0:
            return ""
        if len(strs) == 1:
            return strs[0]
        # 这个写法很重要，得到最小元素长度
        minLen = min([len(s) for s in strs])
        for i in range(minLen):
            s = strs[0][i]
            for j in range(1, len(strs)):
                if strs[j][i] != s:
                    return strs[0][0:i]
        return strs[0][0:minLen]
```

# 20. Valid Parentheses (Easy)
[https://leetcode.com/problems/valid-parentheses/](https://leetcode.com/problems/valid-parentheses/)
**题目描述**

判断给定的括号字符串是否有效

```python
"""
用到栈, 先进后出, 左括号进栈, 如果遍历到右括号, 则弹出栈顶, 
判断是否和当前右括号匹配.
"""
class Solution:
    def isValid(self, s: str) -> bool:
        if s == "":
            return True
        left = ['(', '{', '[']
        right = {')':'(', '}':'{', ']':'['}
        stack = []
        for i in s:
            if i in left:
                stack.append(i)
            else:
                if stack == [] or right[i] != stack.pop():
                    return False
        if stack == []:
            return True
        else:
            return False
```
# 21. Merge Two Sorted Lists** (Easy)
[https://leetcode.com/problems/merge-two-sorted-lists/](https://leetcode.com/problems/merge-two-sorted-lists/)
**题目描述**

合并两个已经排序好的链表

**Python**
```python
"""
创建一个新的头结点, 逐个比较两个链表的元素, 把小的链接到新节点, 当一个链表被遍历
完了, 则把另一个链表剩下的部分全部并入.
"""
class Solution:
    def mergeTwoLists(self, l1, l2):
        """
        :type l1: ListNode
        :type l2: ListNode
        :rtype: ListNode
        """
        if not l1: return l2
        if not l2: return l1
        
        curr = head = ListNode(0)
        while l1 and l2:
            if(l1.val < l2.val):
                curr.next = l1 
                l1 = l1.next
            else:
                curr.next = l2
                l2 = l2.next
            curr = curr.next
        
        if l1:
            curr.next = l1 
        elif l2:
            curr.next = l2
        
        return head.next

```
# 26. Remove Duplicates from Sorted Array (Easy)
[https://leetcode.com/problems/remove-duplicates-from-sorted-array/](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)
**题目描述**

删除一个**有序**数组中重复的元素, 要求操作in-place, 使用O(1)空间.

**Python**
```python
"""
当前数字等于下一个数字时, 删除当前数字, 此时index需要减一(因为数字被删除)
当前数字不同于下一个数字时, index往前走1.
while中的len(nums)会实时更新.
"""
class Solution:
    def removeDuplicates(self, nums: List[int]) -> int:
        i = 0
        while i < len(nums)-1:
            if nums[i] == nums[i+1]:
                del nums[i]
                i = i-1
            i = i+1
        return len(nums)
```
# 27. Remove Element (Easy)
[https://leetcode.com/problems/remove-element/](https://leetcode.com/problems/remove-element/)
**题目描述**

删除一个数组中所有指定元素, 要求操作in-place, 使用O(1)空间.

**Python**
```python
class Solution:
    def removeElement(self, nums: List[int], val: int) -> int:
        i = 0
        while i < len(nums):
            if nums[i] == val:
                nums.pop(i)
            else:
                i += 1
```
# 28. Implement strStr() (Easy)
[https://leetcode.com/problems/implement-strstr/](https://leetcode.com/problems/implement-strstr/)
**题目描述**

在字符串中找子符串. 

**Python**
```python
class Solution:
    def strStr(self, haystack: str, needle: str) -> int:
        if needle in haystack:
            return haystack.index(needle)
        return -1      	
```
**Java**
```java
public class Solution {
    public int strStr(String haystack, String needle) {
        int l1 = haystack.length(), l2 = needle.length();
        if (l1 < l2) {
            return -1;
        } else if (l2 == 0) {
            return 0;
        }
        int threshold = l1 - l2;
        for (int i = 0; i <= threshold; ++i) {
            if (haystack.substring(i,i+l2).equals(needle)) {
                return i;
            }
        }
        return -1;
    }
}
```

# 35. Search Insert Position (Easy)
[https://leetcode.com/problems/search-insert-position/](https://leetcode.com/problems/search-insert-position/)
**题目描述**

输出一个数字在数组应该插入的位置
Input: [1,3,5,6], 2
Output: 1

**Python**
```python
"""
可以使用折半查找, 时间复杂度降低为O(logn).
"""
class Solution:
    def searchInsert(self, nums: List[int], target: int) -> int:
        if target <= nums[0]:
            return 0
        if target == nums[len(nums)-1]:
            return len(nums)-1
        if target > nums[len(nums)-1]:
            return len(nums)
        
        left = 0
        right = len(nums)-1
        while left <= right:
            mid = (left + right)// 2
            if nums[mid] == target: return mid
            if nums[mid] > target: right = mid - 1
            if nums[mid] < target: left = mid + 1
        return left
```
# 38. Count and Say (Easy)
[https://leetcode.com/problems/count-and-say/](https://leetcode.com/problems/count-and-say/)
**题目描述**

The count-and-say sequence is the sequence of integers with the first five terms as following:

	1.     1
	2.     11
	3.     21
	4.     1211
	5.     111221
1 is read off as "one 1" or 11.
11 is read off as "two 1s" or 21.
21 is read off as "one 2, then one 1" or 1211.

**Python**
```python
"""
say的特点就是若干重复数字的组合, 我们计算连续重复的数字, 把它"读出来",
如111221就是三个1, 两个2, 一个1, 即31.22.11.
那么下一轮就得到一个3, 一个1, 两个2, 一个1, 即13.11.22.11.
以此类推.
"""
class Solution:
    def countAndSay(self, n: int) -> str:
        if n == 1:
            return "1"
        if n == 2:
            return "11"
        say = "11"
        for i in range(n-2):
            count = 1 #计算某个数重复的次数
            newSay = "" #这一轮的say结果, 下一轮需要count这个say
            for j in range(len(say)-1):
                if say[j] == say[j+1]:
                    count += 1
                else:
                    newSay += (str(count) + str(say[j]))
                    count = 1
            newSay += (str(count) + str(say[len(say)-1]))
            
            say = newSay
        return say
```
# 53. Maximum Subarray ** (Easy)
[https://leetcode.com/problems/maximum-subarray/](https://leetcode.com/problems/maximum-subarray/)
**题目描述**

给一个数组, 求出和最大的连续子数组.

Input: [-2,1,-3,4,-1,2,1,-5,4],
Output: 6
Explanation: [4,-1,2,1] has the largest sum = 6.

**解题思路**

**解法一：动态规划**

用一个一维数组 dp [ i ] 表示以下标 i 结尾的子数组的元素的最大的和，
也就是
- 这个子数组最后一个元素是下边为 i 的元素，
- 而且这个子数组是所有以 i 结尾的子数组中，和最大的。

这样的话就有两种情况，

- 如果 dp [ i - 1 ] < 0，那么 dp [ i ] = nums [ i ]。 （ 加上 dp[i-1]  反而使得 nums[i] 变小 ）
- 如果 dp [ i - 1 ] >= 0，那么 dp [ i ] = dp [ i - 1 ] + nums [ i ]。

Java
```java
public int maxSubArray(int[] nums) {
    int n = nums.length;
    int[] dp = new int[n];
    int max = nums[0];
    dp[0] = nums[0];
    for (int i = 1; i < n; i++) {
        //两种情况更新 dp[i]
        if (dp[i - 1] < 0) {
            dp[i] = nums[i];
        } else {
            dp[i] = dp[i - 1] + nums[i];
        }
        //更新 max
        max = Math.max(max, dp[i]);
    }
    return max;
}
```

**解法二： 滑动窗口**
如果前面数的和preSum小于0，则从right处重新开始求和，并把left指针移动到right处。否则累加和。

Java
```java
class Solution {
    public int maxSubArray(int[] nums) {
        if(nums.length == 1)
            return nums[0];
        int left = 0, right = 1, preSum = nums[0], maxSum = preSum;
        while(right < nums.length){
            if(preSum < 0){
                left = right;
                preSum = nums[right];
            }
            else
                preSum += nums[right];
            if(preSum > maxSum)
                maxSum = preSum;
            right ++;
        }
        return maxSum;
    }
}
```
Python
```python
"""
每移动到一个新的位置, 计算当前位置能得到的最大值maxCurr, 
maxCurr=max(maxToCurr+nums[i], nums[i]),
上一个位置的最大值+当前数 和 当前数中取大者, 即是当前位置能得到的最大值.
然后同全局最大值比较
"""
class Solution:
    def maxSubArray(self, nums: List[int]) -> int:
        if len(nums) == 0:
            return 0
        if len(nums) == 1:
            return nums[0]
        
        maxToCurr = nums[0] # 每移动到一个新的位置, 计算当前位置能得到的最大值
        maxSum = nums[0] # 全局最大值
        
        for i in range(1, len(nums)):
            m = max(maxToCurr+nums[i], nums[i])
            maxToCurr = m
            if maxToCurr > maxSum:
                maxSum = maxToCurr
                
        return maxSum
```

# 58. Length of Last Word (Easy)
[https://leetcode.com/problems/length-of-last-word/](https://leetcode.com/problems/length-of-last-word/)
**题目描述**

返回一个句子最后一个单词的的长度. 如 "Hello World" 返回 5.

```python
class Solution:
    def lengthOfLastWord(self, s: str) -> int:
        s = s.split()
        if len(s) == 0:
            return 0
        return len(s[-1])
```

# 67. Add Binary (Easy)
[https://leetcode.com/problems/add-binary/](https://leetcode.com/problems/add-binary/)
**题目描述**

二进制加法

**Java**
```java
public class Solution {
    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1, j = b.length() -1, carry = 0;
        while (i >= 0 || j >= 0) {
            int sum = carry;
            if (j >= 0) sum += b.charAt(j--) - '0';
            if (i >= 0) sum += a.charAt(i--) - '0';
            sb.append(sum % 2);
            carry = sum / 2;
        }
        if (carry != 0) sb.append(carry);
        return sb.reverse().toString();
    }
}
```
# 69. Sqrt(x) (Easy)
[https://leetcode.com/problems/sqrt(x)/](https://leetcode.com/problems/sqrt%28x%29/)
```python

```
# 70. Climbing Stairs **(Easy)
[https://leetcode.com/problems/climbing-stairs/](https://leetcode.com/problems/climbing-stairs/)
**题目描述**

经典的青蛙跳台阶. 每次只能挑一个或者两个台阶, 跳到第n个台阶有几种跳法?

一个经典的做法是用递归的方法
```python
class Solution:
    def climbStairs(self, n: int) -> int:
        if n == 1:
            return 1
        if n == 2:
            return 2
        return self.climbStairs(n-1) + self.climbStairs(n-2)
```
但是, 显然递归十分耗时. 因此考虑使用迭代的方式做
```python
class Solution:
    def climbStairs(self, n: int) -> int:
        if n == 0:
            return 1
        step_2 = 0 # 到n-2台阶的走法数
        step_1 = 1 # 到n-1台阶的走法数
        for i in range(n):
            step_n = step_1 + step_2
            step_2 = step_1
            step_1 = step
        return step_n
```
# 83. Remove Duplicates from Sorted List (Easy)
[https://leetcode.com/problems/remove-duplicates-from-sorted-list/](https://leetcode.com/problems/remove-duplicates-from-sorted-list/)
**题目描述**

删除一个有序链表中重复的元素
Input: 1->1->2->3->3
Output: 1->2->3

```python
"""
如果子节点跟当前节点相等, 则将当前节点链接到下下个节点. 当前节点指针不移动.
不相等, 则指针往后移一位.
"""
class Solution:
    def deleteDuplicates(self, head: ListNode) -> ListNode:
        if not head:
            return None
        if head.next == None:
            return head
        
        node = head
        while node.next:
            if node.next.val == node.val:
                node.next = node.next.next
            else:
                node = node.next
        return head
```
# 88. Merge Sorted Array (Easy)
[https://leetcode.com/problems/merge-sorted-array/](https://leetcode.com/problems/merge-sorted-array/)
**题目描述**

合并两个有序数组到nums1. 已给出数组和其长度.
Input:
nums1 = [1,2,3,0,0,0], m = 3
nums2 = [2,5,6],       n = 3
Output: [1,2,2,3,5,6]

```python
class Solution:
    def merge(self, nums1: List[int], m: int, nums2: List[int], n: int) -> None:
        for i in range(len(nums1)-m):
            nums1[i+m] = nums2[i]
        nums1.sort()
```
# 100. 相同的树
```java
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p!=null && q!=null && p.val == q.val)
            return isSameTree(p.left , q.left) && isSameTree(p.right, q.right);
        if(p == null && q == null)
            return true;
        else
            return false;
    }
}
```
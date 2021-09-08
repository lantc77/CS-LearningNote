# 101. Symmetric Tree
https://leetcode.com/problems/symmetric-tree/

题目描述

```
Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).

For example, this binary tree [1,2,2,3,4,4,3] is symmetric:

    1
   / \
  2   2
 / \ / \
3  4 4  3
 

But the following [1,2,2,null,3,null,3] is not:

    1
   / \
  2   2
   \   \
   3    3
```

这道题很简单，用递归解决，自底向上判断两个对称位置的节点是否相等。

当然也可以用层序遍历（广度优先搜索）。

分情况讨论：

1. 当根节点为空时 返回true
2. 当只有一个节点为空，另一个不为空时，返回false
3. 当根节点相等且 ```isMirror(left.left, right.right) && isMirror(left.right, right.left) ```为true 时，返回true

需要创建一个helper函数 check()
````java
class Solution {
    public boolean isSymmetric(TreeNode root) {
        return isMirror(root, root);
    }

    public boolean isMirror(TreeNode left, TreeNode right){
        if(left == null && right == null)
            return true;
        if(left == null || right == null)
            return false;
        
        return left.val == right.val && isMirror(left.left, right.right) && isMirror(left.right, right.left);
    }
}
```

```python
class Solution:
    def isSymmetric(self, root: TreeNode) -> bool:
        if root == None:
            return True
        return self.isMirror(root.left, root.right)
    
    def isMirror(self, node1, node2):
        if node1 == None and node2 == None:
            return True
        if node1 == None or node2 == None:
            return False
        return node1.val == node2.val and self.isMirror(node1.left, node2.right) and self.isMirror(node1.right, node2.left)
```

# 102. Binary Tree Level Order Traversal

https://leetcode.com/problems/binary-tree-level-order-traversal/
很简单。
维护三个数组，ans存放答案，curQueue存放当前层需要遍历的结点，他们的子节点统一存放在nextQueue，当curQueue为空时，将nextStack的结点移入，同时ans开辟一个新的数组，nextQueue置空。
**Python**
```python
# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution:
    def levelOrder(self, root: TreeNode) -> List[List[int]]:
        if root == None:
            return []
        ans = [[]]
        curQueue = [root]
        nextQueue = []
        while curQueue:
            top = curQueue.pop(0)
            ans[-1].append(top.val)
            if top.left: nextQueue.append(top.left)
            if top.right: nextQueue.append(top.right)
            if(curQueue == []):
                curQueue = nextQueue
                if nextQueue: ans.append([])
                nextQueue = []
        return ans   
```
**Java**
```java
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans = new LinkedList<List<Integer>>();
        if(root==null)
            return ans;
		List<Integer> curVal = new LinkedList<Integer>();
		Queue<TreeNode> curQueue = new LinkedList<TreeNode>();
		Queue<TreeNode> nextQueue = new LinkedList<TreeNode>();
        curQueue.add(root);
		while(!curQueue.isEmpty()) {
			TreeNode top = curQueue.poll();
			curVal.add(top.val);
			if(top.left != null)
				nextQueue.add(top.left);
			if(top.right != null)
				nextQueue.add(top.right);
			if(curQueue.isEmpty()) {
				curQueue = nextQueue;
				nextQueue = new LinkedList<TreeNode>(); 
				ans.add(curVal);
				curVal = new LinkedList<Integer>();
			}
		}
		return ans;
    }
}
```
这里说明一下，不能随意使用clear，它将清除对应内存地址内的数据，此处如果想和python一样建立空数组，那么应该new一个新的内存地址。
```java
if(curQueue.isEmpty()) {
	curQueue = nextQueue;
	nextQueue.clear();
	ans.add(curVal);
	curVal.clear();
}
```

# 103. Binary Tree Zigzag Level Order Traversal

https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/
跟102题几乎一样，只不过加个反转。
```python
class Solution:
    def zigzagLevelOrder(self, root: TreeNode) -> List[List[int]]:
        if root == None:
            return []
        ans = []
        curVal = []
        curQueue = [root]
        nextQueue = []
        zig = 0
        while curQueue:
            top = curQueue.pop(0)
            curVal.append(top.val)
            if top.left: nextQueue.append(top.left)
            if top.right: nextQueue.append(top.right)
            if(curQueue == []):
                curQueue = nextQueue
                nextQueue = []
                if zig: 
                    ans.append(curVal[::-1])
                    zig = 0
                else: 
                    ans.append(curVal)
                    zig = 1
                curVal = []
        return ans
```

# 104. Maximum Depth of Binary Tree(easy)

非常简单，用递归，终止条件是当前结点为空的时候，返回0，每层返回能得到的最大深度+1。
```python
class Solution:
    def maxDepth(self, root: TreeNode) -> int:
        if root == None:
            return 0
        return max(self.maxDepth(root.left), self.maxDepth(root.right))+1
```

# 105. Construct Binary Tree from Preorder and Inorder Traversal (medium)

https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
preorder [ **3** / 9 / 20 15 7 / ]
inorder   [ / 9 /  **3** / 15 20 7 / ]
preorder[0]就是根节点，此时inorder中该节点的左部分是属于左子树，右部分属于右子树，根据该节点在inorder中的位置pos去划分inorder和preorder。
用递归，终止条件是当preorder为空，返回空，preorder只含一个元素，返回包含该元素的叶子节点TreeNode。根据传入的preorder，inorder重复做一样的事，得到根节点和孩子节点。
```python
class Solution:
    def buildTree(self, preorder: List[int], inorder: List[int]) -> TreeNode:
        if len(preorder) == 0:
            return 
        if len(preorder) == 1:
            return TreeNode(preorder[0])
        
        root = TreeNode(preorder[0])
        pos = inorder.index(root.val)
        if preorder[1:1+pos]:
            root.left = self.buildTree(preorder[1:1+pos], inorder[:pos])
        if preorder[1+pos:]:
            root.right = self.buildTree(preorder[1+pos:],inorder[pos+1:])
        return root
```
这段代码更复杂些，但是可以清楚看到preorder和inorder被分为四个部分，更好理解。
```python
Class Solution:
    def buildTree(self, preorder: List[int], inorder: List[int]) -> TreeNode:
        if preorder == []:
            return 
        root = TreeNode(preorder[0])
        self.getRoot(preorder, inorder, root)
        return root
        
    def getRoot(self, preorder, inorder, root):
        if len(preorder) == 1:
            return 
        pos = inorder.index(root.val)
        leftInorder = inorder[:pos]
        rightInorder = inorder[pos+1:]
        leftPreorder = preorder[1:1+len(leftInorder)]
        rightPreorder = preorder[1+len(leftInorder):]
        if leftPreorder:
            root.left = TreeNode(leftPreorder[0])
            self.getRoot(leftPreorder, leftInorder, root.left)
        if rightPreorder:
            root.right = TreeNode(rightPreorder[0])
            self.getRoot(rightPreorder,rightInorder, root.right)
```

106题与本题一样，只不过改为提供inorder和postorder。
只需要把```root = preorder[0]```改为```root = postorder[-1]```
```python
class Solution:
    def buildTree(self, inorder: List[int], postorder: List[int]) -> TreeNode:
        if len(postorder) == 0:
            return 
        if len(postorder) == 1:
            return TreeNode(postorder[-1])
        
        root = TreeNode(postorder[-1])
        pos = inorder.index(root.val)
        if postorder[:pos]:
            root.left = self.buildTree(inorder[:pos], postorder[:pos])
        if postorder[pos:len(postorder)-1]:
            root.right = self.buildTree(inorder[pos+1:], postorder[pos:len(postorder)-1])
        return root
```

# 108. Convert Sorted Array to Binary Search Tree

[108. Convert Sorted Array to Binary Search Tree](https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/)
**题目描述**

给定一个有序升序数组，输出可能的平衡二叉树
```

Example:

Given the sorted array: [-10,-3,0,5,9]

One possible answer is: [0,-3,9,-10,null,5]

      0
     / \
   -3   9
   /   /
 -10  5
```
**解题思路**

平衡二叉树就是左右子树高度不能超过1。从数组的角度来看，每次取数组中间数作为根节点，可保证左右剩下的元素个数相差不超过1。

继续左子树的中间数作为根节点，继而又保证其左右剩下的元素个数相差不超过1。以此递归，最终能组成平衡二叉树。

数组的中间数作为根节点，左节点为数组左半部分的中间值，右节点为数组右半部分的中间数。当传入的数组为空时结束递归。

**代码**
```python
class Solution:
    def sortedArrayToBST(self, nums: List[int]) -> TreeNode:
        return self.helper(nums, 0, len(nums)-1)
        
    def helper(self, nums, left, right):
        if left <= right:
            mid = (left + right)//2
            node = TreeNode(nums[mid])
            node.left = self.helper(nums, left, mid-1)
            node.right = self.helper(nums, mid+1, right)
            return node   
```

# 110. Balanced Binary Tree (easy)

https://leetcode.com/problems/balanced-binary-tree/
第一种方法，不但要保证当前节点左右子树平衡，而且他们的各自的孩子节点也要平衡。因此每一个节点都递归调用getDepth()，重复计算开销较大，时间复杂度为O(nlogn)。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190729161952186.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0JlYW43NzE2MDY1NDA=,size_16,color_FFFFFF,t_70)
```python
class Solution:
    def isBalanced(self, root: TreeNode) -> bool:
        if root == None:
            return True
        leftDepth = self.getDepth(root.left)
        rightDepth = self.getDepth(root.right)
        return abs(leftDepth-rightDepth) <= 1 and self.isBalanced(root.left) and self.isBalanced(root.right)
    def getDepth(self, node):
        if node == None:
            return 0
        return max(self.getDepth(node.left), self.getDepth(node.right))+1
```
第二种方法，利用一个标记变量，每次在递归函数里判断左右子树高度是否平衡，如果不平衡跳出递归，结束函数。因此不必一个节点遍历多遍，时间复杂度为O(n)。
```python
class Solution:
    def isBalanced(self, root: TreeNode) -> bool:
        if root == None:
            return True
        balanced = [True]
        self.getDepth(root, balanced)
        return balanced[0]
    
    def getDepth(self, node, balanced):
        if node == None:
            return 0
        leftDepth = self.getDepth(node.left, balanced)
        rightDepth = self.getDepth(node.right, balanced)
        if abs(leftDepth - rightDepth) > 1:
            balanced[0] = False
            return -1
        return max(leftDepth, rightDepth)+1
```

# 111. Minimum Depth of Binary Tree (easy)

https://leetcode.com/problems/minimum-depth-of-binary-tree/

题目强调“The minimum depth is the number of nodes **along the shortest path** from the root node down **to the nearest leaf node**.”题目强调必须找到叶子节点。递归的**终止条件**是当当前节点没有子节点或当前节点为空时，停止递归。当递归遇到节点一个左节点为空时，此时递归返回他的右节点深度；同理，遇到右节点为空时，递归他的左节点；当两个节点都存在时，返回两个节点深度更小的值。
```python
class Solution:
    def minDepth(self, root: TreeNode) -> int:
    	# 终止条件，结束递归
        if root == None:
            return 0
        if root.left == None and root.right == None:
            return 1
        # 开始递归    
        if root.left == None:
            return self.minDepth(root.right) + 1
        elif root.right == None:
            return self.minDepth(root.left) + 1
        else:
            return min(self.minDepth(root.left), self.minDepth(root.right)) + 1
```

# 112. Path Sum (easy)

思路和104,111，计算depth的题目递归思路一样。
每一次递归，减掉当前节点的值。很简单。
```python
class Solution:
    def hasPathSum(self, root: TreeNode, sum: int) -> bool:
        if root == None:
            return False
        
        if root.left == None and root.right == None:
            if sum - root.val == 0:
                return True
            return False
        
        if root.left == None:
            return self.hasPathSum(root.right, sum - root.val)
        elif root.right == None:
            return self.hasPathSum(root.left, sum - root.val)
        else:
            return self.hasPathSum(root.left, sum - root.val) or self.hasPathSum(root.right, sum - root.val)
```

# 113. Path Sum II

https://leetcode.com/problems/path-sum-ii/
基本思路和上一题一样，维护两个list，存储答案的ans和存储当前路径的path，用DFS优先搜索到叶子节点，如果符合sum要求，在ans中加入当前路径的list，**如果对路径path使用append会改变path内容，所以此类问题，传参的时候一定要注意用+，不会改变原内容**。
```python
	class Solution:
    def pathSum(self, root: TreeNode, sum: int) -> List[List[int]]:
        if root == None:
            return []
        
        if root.left == None and root.right == None:
            if sum == root.val:
                return [[root.val]]
        ans = []
        path = [root.val]
        if root.left:
            self.findPath(root.left, sum - root.val, ans, path)
        if root.right:
            self.findPath(root.right, sum - root.val, ans, path)
        return ans
    
    def findPath(self, root, sum, ans, curPath):
        if root.left == None and root.right == None:
            if sum == root.val:
                ans.append(curPath + [root.val])
        if root.left:
            self.findPath(root.left, sum-root.val, ans, curPath + [root.val])
        if root.right:
            self.findPath(root.right, sum-root.val, ans, curPath + [root.val])
```

# 114. Flatten Binary Tree to Linked List (medium)

https://leetcode.com/problems/flatten-binary-tree-to-linked-list/
用**分治**的思想。
对于一个节点，lastLeft表示节点最左的叶子节点，lastRight表示最右的叶子节点，对每个节点执行以下三步操作：
1. 因为左子树的链接优先级都是高于右子树的，若lastLeft存在，执行```lastLeft.left = root.right```，若不存在左叶子节点，则右子树保留
2. 将所有左子树调换到右```root.right = root.left```
3. 原先的左节点置空```root.left = null```

in-place链接完节点之后，需要根据
```python
if lastRight != None:   return lastRight
if lastLeft != None:   return lastLeft
return root
```
来返回节点。

**python**
```python
class Solution:
    def flatten(self, root: TreeNode) -> None:
        """
        Do not return anything, modify root in-place instead.
        """
        self.dfs(root)
    
    def dfs(self, root):
        # 如果节点为空，返回空
        if root == None:    return None
        
        lastLeft = self.dfs(root.left)
        lastRight = self.dfs(root.right)
        """
        1) link the right node of root to the last left node of root
        2) link the root.left to the root.right
        3) set root.left None
        """
        if lastLeft != None:
            lastLeft.right = root.right
            root.right = root.left
            root.left = None
        
        """
        we should return a node for next node to link
        """
        if lastRight != None:   return lastRight
        if lastLeft != None:   return lastLeft
        return root
```

# 115. Distinct Subsequences (hard)

https://leetcode.com/problems/distinct-subsequences/
经典DP，归结为给两个数组，执行某些操作后，需要怎样。

dp[i][j]表示目前为止 **s[1:j]** 中能匹配出多少个 **T[1:i]**，绿色表示 **t[i] = s[j]** 的情况下有多少种可行解，是 **s[1:j-1]** 中匹配出 **T[1:i-1]** 的数量+不使用当前 **s[j]** 而匹配的 **T[1:i-1]** 的数量。（图片出处见水印）
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190730153233635.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0JlYW43NzE2MDY1NDA=,size_16,color_FFFFFF,t_70)
举个例子，**dp[2][6] = dp[1][5] + dp[2][5]，T[2] = a，S[6] = a，dp[1][5]** 值表示的是 **S[1:5] **中已经匹配出了三个 **T[1:2]** 也就是 **b** ，因此加上当前的 T[2]，即 **a**就能组成 **ab**，另外原先在此之前已经匹配过一个 **ab**，因此还要加上这个值（即我们不适用当前 S[6] 来组）。如果遇到 T[i] != S[i]，则不会更新当前 dp[i][j] ，仅仅保留原值。

**这里注意**：Python如果直接用```[[0] * 5] * 5```这样来建立数组可能会出问题，虽然得到也是一个5X5的二维数组，但是如果给其中某个位置赋值，会使整列改变，具体详见博客https://blog.csdn.net/zzc15806/article/details/82629406，应该使用```arr = [[0]*5 for _ in range(5)]```。

**python**
```python
def numDistinct(self, s: str, t: str) -> int:
        dp = [[1] * (len(s) + 1)] + [[0] * (len(s) + 1) for _ in range(len(t)+1)]
        for i in range(1, len(t)+1):
            for j in range(1, len(s)+1):
                if s[j-1] == t[i-1]:
                    dp[i][j] = (dp[i - 1][j - 1] + dp[i][j - 1])
                else:
                    dp[i][j] = dp[i][j - 1]
        return dp[len(t)][len(s)]
```

# 116. Populating Next Right Pointers in Each Node

https://leetcode.com/problems/populating-next-right-pointers-in-each-node/

层序遍历，只不过在遍历同一层的时候，每次将当前节点的next指向队列中下一个即将遍历的节点。
```python
class Solution:
    def connect(self, root: 'Node') -> 'Node':
        if root == None:
            return 
        if root.left == None and root.right == None:
            root.next = None
            return root
        """就是层序遍历"""
        queue = [root] """需要取子节点的队列"""
        nextQueue = [] """子节点存放的队列，表示在同一层"""
        self.root = root
        while queue:
            cur = queue.pop(0) # 当前从queue中取出的节点
            if cur.left: nextQueue.append(cur.left)
            if cur.right: nextQueue.append(cur.right)
            if queue == []:
                cur.next = None
                queue += nextQueue
                nextQueue = []
            else:
                cur.next = queue[0]
        return self.root
```

# 120. Triangle

https://leetcode.com/problems/triangle/submissions/
**解题思路**

sumList用来保存上一层路径的求和结果，nextSumList保存当前层的求和结果，nextSumList[i] = row[i] + min(sumList[i-1], sumList[i])，当前值加上 上一层两个路径最小的和。

**代码**
Python
```python
class Solution:
    def minimumTotal(self, triangle: List[List[int]]) -> int:
        sumList = [0]
        nextSumList = []
        for row in triangle:
            for i in range(len(row)):
                if i == 0:
                    nextSumList.append(sumList[0] + row[i])
                elif i == len(row) - 1:
                    nextSumList.append(sumList[i-1] + row[i])
                else:
                    nextSumList.append(min(sumList[i-1], sumList[i])+row[i])
            sumList = nextSumList
            nextSumList = []
        return min(sumList)
```

# 124. Binary Tree Maximum Path Sum
[Binary Tree Maximum Path Sum](https://leetcode.com/problems/binary-tree-maximum-path-sum/)

**题目描述**

找出尽可能使得和最大的路径（所有点需要连接）

**解题思路**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191105141004780.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0JlYW43NzE2MDY1NDA=,size_16,color_FFFFFF,t_70)
设计递归函数findPathSum(node)，传入节点，节点为空时停止递归，返回到传入节点（左右子节点）为止积累的最大和。

如图左半部分，每次计算三个值：
sumLeft：根节点和左子树的和
sumRight：根节点和右子树的和
sumAll：根节点和左右子树的和
计算当前可能的最大和：
curMax = max(sumLeft, sumRight, sumAll, root.val)
maxSoFar = max(curMax, maxSoFar)

在图右半部分，递归调用findPathSum()只能返回max(sumLeft, sumRight, root.val)，不应包括蓝色的线路。

**代码**
```python
class Solution:
    def __init__(self):
        self.maxSoFar = float("-inf") #全局变量
    
    def maxPathSum(self, root: TreeNode) -> int:
        self.findPathSum(root)
        return self.maxSoFar
        
    def findPathSum(self, root):
        if(root == None):
            return 0
        left = self.findPathSum(root.left) # maxSum till left node
        right = self.findPathSum(root.right) # maxSum till right node
        sumLeft = left + root.val
        sumRight = right + root.val
        sumAll = left + right + root.val 
        
        curMax = max(root.val, max(sumAll, max(sumLeft, sumRight)))

        self.maxSoFar = max(curMax, self.maxSoFar)
            
        return max(max(sumLeft, sumRight), root.val)
```

# 134. Gas Station (Medium)

https://leetcode.com/problems/gas-station/

非常经典的一道题。解这道题的思路基于一个**数学定理**：

```如果一个数组的总和非负，那么一定可以找到一个起始位置，从他开始绕数组一圈，累加和一直都是非负的```

有了这个定理，判断到底是否存在这样的解非常容易，只需要把全部的油耗情况计算出来看看是否大于等于0即可。

那么如何求开始位置在哪？

注意到这样一个现象：

1. 假如从位置i开始，i+1，i+2...，一路开过来一路油箱都没有空。说明什么？说明从i到i+1，i+2，...肯定是正积累。
2. 现在突然发现开往位置j时油箱空了。这说明什么？说明从位置i开始没法走完全程(废话)。那么，我们要从位置i+1开始重新尝试吗？不需要！为什么？因为前面已经知道，位置i肯定是正积累，那么，如果从位置i+1开始走更加没法走完全程了，因为没有位置i的正积累了。同理，也不用从i+2，i+3，...开始尝试，**这样就比暴力解法能省去很多时间**。所以我们可以放心地从位置j+1开始尝试。
3. 每次油箱<=0的时候，要保存“欠下”的油量，当遍历完所有的i时，将当前余下的油减去所有之前欠下的油量，如果结果>=0才能说明能顺利跑完一轮。


```python
# Brute Force, 遇到数组较大会超时报错。效率也很低。
class Solution:
    def canCompleteCircuit(self, gas: List[int], cost: List[int]) -> int:
        start = 0   #起始位置
        remain = 0  #当前剩余燃料
        debt = 0    #前面没能走完的路上欠的债

        for i in range(len(gas)):
            remain += gas[i] - cost[i]
            if remain < 0:
                debt += remain
                start = i + 1
                remain = 0
        if remain + debt >= 0:
            return start
        else:
            return -1
```

# 136. Single Number

https://leetcode.com/problems/single-number/submissions/
**题目描述：**
Given an array of integers, every element appears twice except for one. Find that single one.

Note:
Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

**要完成的函数：**
def singleNumber(self, nums: List[int]) -> int:

**思路：**
1、给定一组数，这组数中除了有一个元素只出现一次，其他元素都出现了两次，要求输出这个只出现一次的元素的值。

2、时间复杂度O(n)，也就是只能从头到尾遍历一次；空间复杂度O(1)，只能使用常数级的空间。

3、照这道题目的要求来看，既不能常规做法的定义一个数组，记录每个数出现了几次；也不能双重循环找到出现两次的数，然后把它们都删掉，接着继续遍历，直到最后只剩下出现一次的数。所以这道题目要求只能遍历一遍，那就只能从数学上寻找方法了。

4、这道题目笔者本人想了很长时间，都不知道采用什么数学方法好，直到在discuss中看到大神采用异或（XOR）的做法……以下举例说明为什么异或能够处理这道问题。

举例说明：
先说明一点，所有整数在计算机中都采用二进制的表示方法。以下举两个例子：

1、数组为1、1、2，在计算机中表示为0001,0001,0010，那么0001\^0001=0000，接着0000^0010=0010。最后得到的结果是2，也就是2是只出现一次的数。

2、数组为1、2、1，在计算机中表示为0001,0010,0001，那么0001\^0010=0011，接着0011^0001=0010。最后得到的结果是2，也就是2是只出现一次的数。

**数学原理：**
**异或是两个相同的数则异或结果为0，两个不同的数异或结果为1，并且异或满足交换律和结合律，所以我们可以得到这样的结论：A\^B\^C\^B\^D\^C\^A=D。**

异或其实是“记录”了所有出现过的数，只要你第二遍出现，异或结果就会为0，直到一个只出现一次的数，那么异或结果最终为这个只出现一次的数的数值。

异或能够记录曾经出现过的数，然后一直在等待第二遍出现，来异或为0。如果一直没有第二遍出现，数组中全都是只出现一遍的数，那么最终结果会是很奇怪的。各位同学不妨试试。


**代码：**
```python
class Solution:
    def singleNumber(self, nums: List[int]) -> int:
        start = nums[0]
        for i in range(1, len(nums)):
            nums[0] ^= nums[i]
        return nums[0]
```

# 137. Single Number II

https://leetcode.com/problems/single-number-ii/
**题目描述：**
Given a non-empty array of integers, every element appears three times except for one, which appears exactly once. Find that single one.

Note:
要求时间复杂度O(n)，空间复杂的O(1)

**解题思路（方法一）：**
1、这道题和上一道题一样，要求时间复杂度是线性的，要求不能使用空间复杂度是O(1)的。但不同的是这次除了一个数只出现一次，其余所有数都出现了三次。我们又要怎么解决这道题呢？

2、同样直接上讨论区代码，再次膜拜大神。大神使用了一种统计的方法，不过不是我等平常思维的统计每个数出现了几次，而是开了一个长度为32的数组，统计每个二进制位出现了几次，最后对3取模（如果是出现了K次就对K取模），取模完哪一位不是3的整倍数，就说明只出现了一次的那个数，在这个位上为1，最终可以求出最后的结果。以下举例说明。

举例说明：
c++中存储一个int型整数，都是32位的空间，我们也开32位的数组。但以下为了表示简便，我们只用最后的4位，就足够了。

假定我们的array of integers为[1,2,2,1,1,2,4,4,5,4]，写成二进制位就是：

1：0001

2：0010

2：0010

1：0001

1：0001

2：0010

4：0100

4：0100

5：0101

4：0100

T：0434

R：0101

（T表示total，合计，每一列的和。R表示对3取模完之后的结果）

然后对T中的数值，每一位都对3取模，可以看到：出现了3的整数倍次的，取模完结果都是0；出现了非3的整数倍次的，即只出现了一次的那个数，取模完结果都为1，说明只出现一次的那个数，在当前这个位有出现过，最后也可以求出这个值。

不得不赞叹二进制位的神奇，可以发挥出“记录”的效果。这要是三进制位，就不能这样子处理了。二进制位为1，表示出现过，在这种“1个只出现1次，其余都出现了n次”的题目中，可以发挥出奇效。

不过似乎不是O(n)的时间复杂度？

**代码(cpp)：**
```cpp
class Solution {
public:
    int singleNumber(vector<int>& s) {
        vector<int> t(32);//开辟一个32位的数组 
        int i,j,n;
        for (i = 0; i < s.size(); ++i){
            n = s[i];
            for (j = 31; j >= 0; --j)
            {
                t[j]+=(n&1);//统计当前这个数的二进制位情况 
                n >>= 1;
                if (!n)
                    break;
            }
        }
        int result= 0;//表示最后的取模完的结果 
        for (j = 31; j >= 0; --j){
           n = t[j] % 3;
           if (n)
               result+=(1<<(31-j));
        }
        return result;
    }
};
```

**解题思路（方法二）：**
上一篇博客中提出的方法很容易理解，但是不是O(n)的时间复杂度，而是O(n^2)，这点应该很多朋友都能看出来。

今天给大家分享一个O(n)的方法，先贴出简洁的代码给大家欣赏一下。这个方法同样参考于discuss区。

**代码：(python)**
```python
class Solution:
    def singleNumber(self, nums: List[int]) -> int:
        a = 0
        b = 0
        for i in range(len(nums)):
            b = (b ^ nums[i]) & ~a
            a = (a ^ nums[i]) & ~b
        return b
```
短短几行代码，简洁扼要地完成了任务。以下举例详细说明为什么能这样子做，以及推测要如何产生这样子的想法。

举例说明：
数组为[2,2,2,3]，一共有四个元素，进行四次循环。

第一次循环，b=(0000\^0010)&1111=0010=2，a=(0000^0010)&1101=0000=0

第二次循环，b=(0010\^0010)&1111=0000=0，a=(0000^0010)&1111=0010=2

第三次循环，b=(0000\^0010)&1101=0000=0，a=(0010^0010)&1111=0000=0

第四次循环，b=(0000\^0011)&1111=0011=3，a=(0000^0011)&1100=0000=0

不知道大家有没有发现，某个值nums[i]第一次出现的时候，b把它记录了下来，这时候a=0；接着第二次出现的时候，b被清空了，记录到了a里面；接着第三次出现的时候，b和a都被清空了。

如果一个数组中，所有的元素除了一个特殊的只出现一次，其他都出现了三次，那么根据我们刚刚观察到的结论，最后这个特殊元素必定会被记录在b中。

有些朋友会说，但是不一定数组都是刚好3个2都在一起，3个4都在一起，都能够满足刚刚这样子的做法。

上上篇博客136题中，笔者本人提出了异或其实是满足交换律和结合律的，而且&这个操作也是满足交换律和结合律的，所以无论3个2会不会一起出现，结果都是会刚好抵消的。

所以上述的方法可以解决这个问题。

怎么想出这种方法的：
其实discuss区的大神是设计了一种方法，借由这种方法推出了a和b的变换方式…

我们想要达到的效果其实是——

|                     |      | a    | b                            | 作用 |
| ------------------- | ---- | ---- | ---------------------------- | ---- |
| 初始状态            | 0    | 0    |                              |      |
| 第一次碰见某个数x： | 0    | x    | 把x记录在b中                 |      |
| 第二次碰见某个数x： | x    | 0    | 把x记录在a中                 |      |
| 第三次碰见某个数x： | 0    | 0    | 把a和b都清空，可以处理其他数 |      |

还记得我们之前处理“所有元素都出现两次，只有一个特殊元素出现一次”的问题吗？其实我们那会想要达到的状态也是——
|                     |      | a    |      |
| ------------------- | ---- | ---- | ---- |
| 初始状态            | 0    |      |      |
| 第一次碰见某个数x： | x    |      |      |
| 第二次碰见某个数x： | 0    |      |      |
那么这次我们同样利用异或运算，看能不能设计出一种变换的方法让a和b按照上述变换规则，进行转换。

b=0时碰到x，就变成x；b=x时再碰到x，就变成0，这个不就是异或吗？所以我们也许可以设计b=b xor x。

但是当b=0时再再碰到x，这时候b还是要为0，但这时候不同的是a=x，而前两种情况都是a=0。所以我们可以设计成：b=(b xor x)&~a

同样道理，我们可以设计出：a=(a xor x)&~b

试着解释一下，就是当a为0时候(~a=1)，保留b的异或结果。当b非0时候，a的异或结果无效（b & ~b = 0），很有意思。


**感想：**
异或其实已经内含了“判断”的过程。想一下我们“所有元素都出现两次，只有一个特殊元素出现一次”的问题，两个相同的数字异或结果就是0，
其实就是一个判断过程，只要数字出现过一次，它就会永久记得你。**巧妙使用二进制以及组合逻辑操作符**可以解决这类问题。

# 139. Word Break

https://leetcode.com/problems/word-break/
**思路解析**

本题是一道很经典的动态规划题。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190722213610580.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0JlYW43NzE2MDY1NDA=,size_16,color_FFFFFF,t_70)
记忆数组f[i]表示s[0,i]是否可以被分割，f[0]=1表示“”空字符是可以被分割的。

两个循环，一个外循环，一个内循环。外循环表示目前分解s[0, i]的子字符串，内循环分解当前子字符串为左右子字符串，左边的f[j]直接从memo中查询，防止重复计算，右边的子字符串f[j, i]则判断是否在字典中，当这两个条件都成立的时候，f[i]置为1，表示s[0, i]是可以被字典中的单词分割。

另外这道题还可以这么想：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190722223808234.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0JlYW43NzE2MDY1NDA=,size_16,color_FFFFFF,t_70)
这张图详细表示了递归的内容，此时i遍历到8，我们要分割leetcode，此时j遍历到4的时候，左数组s[1,4]是leet，右数组s[5,8]是code，code直接在字典中可查到，而递归调用wordBreak传入左数组leet，可以看到leet也有四种分解法（该循环中i=4，j有四种取值），每次都计算wordBreak开销很大，因而还可以考虑用一个memo<string, boolean>来保存已经计算过的情况。

**代码实现：**

Java
```java
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        int[] f = new int[s.length()+1];
        f[0] = 1;
        for(int i=0; i < s.length(); i++)
             for(int j=0; j < i+1; j++)
                if(f[j]==1 & wordDict.contains(s.substring(j, i+1))){
                    f[i+1] = 1;
                    break;
                }
        return f[s.length()]==1;
    }
}
```
Python
```python
class Solution:
    def wordBreak(self, s: str, wordDict: List[str]) -> bool:
        f = [0] * (len(s)+1) # f[i] == 1 means s[0,i] can be break 
        f[0] = 1             # we take ""null string as "breakable"
        for i in range(len(s)):
            for j in range(i+1):
                if f[j] and s[j:i+1] in wordDict:
                    f[i+1] = 1
                    break
        return f[-1]
```

# 141. Linked List Cycle (easy)

<b>141. Linked List Cycle</b>
https://leetcode.com/problems/linked-list-cycle/

**题目描述**
Given a linked list, determine if it has a cycle in it.
Return True or False

**思路1**
**最常规的思路**，直接开辟一个list存放所有节点，每次都判断当前节点的子节点是否在list中，如果在的话说明存在loop，但是时空复杂度都为O(n)

**代码**
```python
# Definition for singly-linked list.
# class ListNode(object):
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution(object):
    def hasCycle(self, head):
        """
        :type head: ListNode
        :rtype: bool
        """
        nodeList = []
        while head:
            if head in nodeList:
                return True
            else:
                nodeList.append(head)
            head = head.next
        return False
```

**思路2**
因为**涉及到loop**, 可以将问题考虑成一个**追及问题**, 用Walker和runner表示不同前进速度的指针, 如果list有loop那么他们必然在某一点相遇. 
证明过程如下:
if there are loops:
2 * time - looplength *  cycles = 1 * time;
then we get 1 * time = looplength * cycles;
because time can be any number, so there must be a number equals to the value of looplength times cycles

**代码**
```python
# Definition for singly-linked list.
# class ListNode(object):
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution(object):
    def hasCycle(self, head):
        def hasCycle(self, head):
	        if head == None : return False
	        walker = head
	        runner = head
	        while runner.next!=None and runner.next.next!=None:
	            walker = walker.next
	            runner = runner.next.next
	            if walker == runner: return True
	        return False
```

# 142. Linked List Cycle II

<b>142. Linked List Cycle II</b>
https://leetcode.com/problems/linked-list-cycle-ii/

**题目描述**
Given a linked list, return the node where the cycle begins. If there is no cycle, return null.

和第一题不同的是要返回cycle的起点

**思路**
同样用两个快慢指针相遇来判断是否循环, 但是在两点相遇时, 此时需要加入一个新的指针, 从起点出发, 新指针和满指针同时以相同速度前进, 必然会在循环起点相遇, 数学证明如下:
When fast and slow meet at point p, the length they have run are 'a+2b+c' and 'a+b'.
Since the fast is 2 times faster than the slow. So a+2b+c == 2(a+b), then we get 'a==c'.
So when another slow2 pointer run from head to 'q', at the same time, previous slow pointer will run from 'p' to 'q', so they meet at the pointer 'q' together.
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190815105421823.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0JlYW43NzE2MDY1NDA=,size_16,color_FFFFFF,t_70)
**代码**
```python
# Definition for singly-linked list.
# class ListNode(object):
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution(object):
    def detectCycle(self, head):
        """
        :type head: ListNode
        :rtype: ListNode
        """
        if head == None: return None
        walker = head
        runner = head
        while walker.next != None and runner.next != None and runner.next.next != None:
            walker = walker.next
            runner = runner.next.next
            if walker == runner:
                walker2 = head
                while walker!= walker2:
                    walker = walker.next
                    walker2 = walker2.next
                return walker
        return None
        
```
# 143. Reorder List (medium)

**题目描述**

Given a singly linked list L: L0→L1→…→Ln-1→Ln,
reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…

You may not modify the values in the list's nodes, only nodes itself may be changed.

举个例子:
0 1 2 3 4 ----> 0 4 1 3 2
0 1 2 3 ----> 0 3 2 1

**解题思路**

最简单的方式就是通过一个列表保存所有node节点, 然后根据节点个数是奇数还是偶数, 按照题目规则得到一个序列, 根据这个序列重新链接整个链表.

**代码**
```python
class Solution(object):
    def reorderList(self, head):
        nodeList = [] #store all the node
        node = head
        while node != None:
            nodeList.append(node)
            node = node.next
        lenth = len(nodeList)
        # if the lenth is even: 0->3->1->2-null
        if lenth % 2 == 0:
            for i in range(lenth/2):
                nodeList[i].next = nodeList[lenth-1-i]
                if i+1 >= lenth/2:
                    nodeList[lenth-1-i].next = None
                else:
                    nodeList[lenth-1-i].next = nodeList[i+1]
        # else if the lenth is odd: 0->4->1->3->(2)->null
        else:
            for i in range(lenth//2+1):
                if i == lenth//2:
                    nodeList[i].next = None
                else:
                    nodeList[i].next = nodeList[lenth-1-i]
                    nodeList[lenth-1-i].next = nodeList[i+1]
```

# 144. Binary Tree Preorder Traversal (medium)
**144. Binary Tree Preorder Traversal**
https://leetcode.com/problems/binary-tree-preorder-traversal/

**思路**
递归方法
```python
class Solution:
    def preorderTraversal(self, root: TreeNode) -> List[int]:
        if not root:
            return []
        res = []
        self.preorder(root, res)
        return res
    
    def preorder(self, root, res):
        res.append(root.val)
        if root.left:
            self.preorder(root.left, res)
        if root.right:
            self.preorder(root.right, res)
```
可以使用栈, 先进后出, 每次出节点, 把其右子节点, 左节点入栈. 右边的节点在栈底层, 左边的节点在栈高层. 
迭代方法(使用栈)
```python
class Solution:
    def preorderTraversal(self, root: TreeNode) -> List[int]:
        if not root:
            return []
        res = []
        stack = [root]
        while stack:
            node = stack.pop()
            res.append(node.val)
            # 后加入的左节点将先出栈
            if node.right: stack.append(node.right)
            if node.left: stack.append(node.left)
        return res
```

# 145. Binary Tree Postorder Traversal

**145. Binary Tree Postorder Traversal 后序遍历的迭代解法**

因为后序遍历的顺序是 左->右->中, 其逆序遍历是 中->左->右(更容易求出), 类似于前序遍历, 我们得到逆后序遍历序列后只要反转即可得到后序遍历序列.

```python
"""
只需要将上一题中右节点先入栈改成左节点先入栈即可.
"""
class Solution:
    def postorderTraversal(self, root: TreeNode) -> List[int]:
        if not root:
            return []
        res = []
        stack = [root]
        while stack:
            node = stack.pop()
            res.append(node.val)
            # 后加入的左节点将先出栈
            if node.left: stack.append(node.left)
            if node.right: stack.append(node.right)
        return res[::-1]
```

**补充: 前序遍历的迭代解法**

# 146. LRU Cache (medium)

 https://leetcode.com/problems/lru-cache/
**题目描述**
Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and put.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.

模拟LRU存储过程, 以下是来自维基百科的解释. 总结起来就是当cache满了以后, 把最久未操作的地址内容清空出去, 以放置最新的数据. 
Least recently used (LRU)
Discards the least recently used items first. This algorithm requires keeping track of what was used when, which is expensive if one wants to make sure the algorithm always discards the least recently used item. General implementations of this technique require keeping "age bits" for cache-lines and track the "Least Recently Used" cache-line based on age-bits. In such an implementation, every time a cache-line is used, the age of all other cache-lines changes. LRU is actually a family of caching algorithms with members including 2Q by Theodore Johnson and Dennis Shasha, and LRU/K by Pat O'Neil, Betty O'Neil and Gerhard Weikum.

The access sequence for the below example is A B C D E D F.
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190815164259969.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0JlYW43NzE2MDY1NDA=,size_16,color_FFFFFF,t_70)
LRU working
In the above example once A B C D gets installed in the blocks with sequence numbers (Increment 1 for each new Access) and when E is accessed, it is a miss and it needs to be installed in one of the blocks. According to the LRU Algorithm, since A has the lowest Rank(A(0)), E will replace A.

**解题思路**

先考虑不使用有序字典,  建立三个列表, cache存放数据, cacheKey存放索引值, evictQ存放地址时序序列, 队首是最久未操作过的地址, 队尾是最新操作过的地址.

**本题的核心就是维护一个队列evictQ, 该队列保存的是各个地址的操作时序情况**. 每次操作过某一地址的数据 (无论get还是put), 就将当前地址值放入evictQ的队尾. cache满的时候, 弹出队首地址的内容, 放入数据.

get和put操作的时间复杂度都是O(1), 空间复杂的是O(n)

**代码**
```python
class LRUCache:

    def __init__(self, capacity: int):
        self.cache = [] # 存放数据
        self.cacheKey = [] # 存放索引值
        self.evictQ = [_ for _ in range(capacity)] # the order of evicting  #操作时序队列, 队首是最久未操作过的地址, 队尾是最新操作过的地址
        self.capacity = capacity
        self.used = 0
        
    def get(self, key: int) -> int:
        if key in self.cacheKey:
            index = self.cacheKey.index(key)
            self.evictQ.remove(index)
            self.evictQ.append(index)
            return self.cache[index]
        else:
            return -1
    def put(self, key: int, value: int) -> None:
        # if the key is already in the cache
        if key in self.cacheKey:
            index = self.cacheKey.index(key)
            self.evictQ.remove(index)
            self.evictQ.append(index)
            self.cache[index] = value
        # if the key is not exist
        # 1. if the cache is not full
        # 2. if the cache is full, evict the first of the cache
        else:
            if self.used != self.capacity:
                index = self.evictQ.pop(0)
                self.cacheKey.insert(index, key)
                self.cache.insert(index, value)
                self.evictQ.append(index) # index move to the tail
                self.used += 1
            # 
            elif self.used == self.capacity:
                index = self.evictQ.pop(0) # pop the head in the queue
                self.cacheKey[index] = key
                self.cache[index] = value
                self.evictQ.append(index)
```

# 150. Evaluate Reverse Polish Notation (medium)
https://leetcode.com/problems/evaluate-reverse-polish-notation/
**题目描述**

Evaluate the value of an arithmetic expression in Reverse Polish Notation.

Valid operators are +, -, *, /. Each operand may be an integer or another expression.

Note:

Division between two integers should truncate toward zero.
The given RPN expression is always valid. That means the expression would always evaluate to a result and there won't be any divide by zero operation.

**解题思路**

因为早出现的数字反而越靠后参与运算, 很容易想到用stack, 遍历tokens, 每次遇到数字, 进行入栈操作. 遇到运算符的时候, 弹出两个栈顶元素运算, 将结果入栈. 

**本题唯一要注意的是:**当两个符号相反的数且不能整除的数相除的时候, 代码的得出的结果要+1. 例如 3/-2=-2, -4/3=-2, 它是会向下取整的. 因此要+1才等于真实结果. 如果能整除, 4/-2=-2, 则不能+1. 

**代码**
```python
class Solution:
    def evalRPN(self, tokens):
        if len(tokens) == 0:
            return 0
        if len(tokens) == 1:
            return tokens[0]
        stack = []
        # 根据四种运算符,分四种情况讨论
        for token in tokens:
            if token == '+':
                res = 0
                res += stack.pop()
                res += stack.pop()
                stack.append(res)
            elif token == '-':
                res = 0
                res -= stack.pop()
                res += stack.pop()
                stack.append(res)
            elif token == '*':
                res = 1
                res *= stack.pop()
                res *= stack.pop()
                stack.append(res)
            elif token == '/':
                # 如果两数异号且不能整除, 结果需要+1
                div = stack.pop()
                res = stack.pop()
                if div * res < 0 and res % div != 0:
                    res = res // div + 1
                else:
                    res //= div
                stack.append(res)
            else:
                stack.append(int(token))
        return stack[0]
```

# 151. Reverse Words in a String

[https://leetcode.com/problems/reverse-words-in-a-string/](https://leetcode.com/problems/reverse-words-in-a-string/)
**题目描述**
Given an input string, reverse the string word by word.

Example 1:
Input: "the sky is blue"
Output: "blue is sky the"

**解题思路**
用split()将string分解成list, 然后反转list再组合.

**代码**
Python
```python
class Solution:
    def reverseWords(self, s: str) -> str:
        slist = s.strip().split()
        slist = slist[::-1]
        return " ".join(slist)
```
Java
```java
public class Solution {
    public String reverseWords(String s) {
        String [] words = s.split(" ");
        StringBuilder sb = new StringBuilder();
        int end = words.length - 1;
        for(int i = 0; i<= end; i++){
            if(!words[i].isEmpty()) {
                sb.insert(0, words[i]);
                if(i < end) sb.insert(0, " ");
            }
        }
        return sb.toString();
    }
}
```

# 152. Maximum Product Subarray (medium)

[https://leetcode.com/problems/maximum-product-subarray/](https://leetcode.com/problems/maximum-product-subarray/)
**题目描述**
Given an integer array nums, find the contiguous subarray within an array (containing at least one number) which has the largest product.

Example 1:

Input: [2,3,-2,4]
Output: 6
Explanation: [2,3] has the largest product 6.

**解题思路**
类似[53. Maximum Subarray](https://leetcode.com/problems/maximum-subarray/),但是考虑到乘数如果是负数会使得乘积为负数, 但是最小的负数如果遇到另一个负数乘积可能就变成最大的乘积. 所以除了设置当前最大数maxToCurr外还要设置一个当前最小数, 每次遇到新的乘数就从 (当前最大数\*乘数, 当前最小数\*乘数, 乘数) 中得到对应的最值.

maxToCurr表示当前位置下能取到的最大乘积结果
minToCurr表示当前位置下能取到的最小乘积结果
product表示最大的乘积结果

**代码**
Python
```python
class Solution:
    def maxProduct(self, nums: List[int]) -> int:
        if len(nums) == 0:
            return 0
        if len(nums) == 1:
            return nums[0]
        
        maxToCurr = nums[0]
        minToCurr = nums[0]
        product = nums[0]
        
        for i in range(1, len(nums)):
            currMax = max(maxToCurr*nums[i], minToCurr*nums[i], nums[i])
            currMin = min(maxToCurr*nums[i], minToCurr*nums[i], nums[i])
            maxToCurr = currMax
            minToCurr = currMin
            if maxToCurr > product:
                product = maxToCurr
        return product
```

# 153. Find Minimum in Rotated Sorted Array

[153. Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)
**题目描述**

Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).

Find the minimum element.

You may assume no duplicate exists in the array.

**解题思路**

一个简单的暴力解法就是遍历整个数组,  比较后一个数是否小于前一个数. 时间复杂度是**O(N)**. 贴一下代码:
```python
def findMin(nums):
        for i in range(len(nums)-1):
            if nums[i] > nums[i+1]:
                return nums[i+1]
        # 所有数都比下一个数小, 那么数列是递增, 返回第一个数
        return nums[0]
```
但是这里我们讨论的是使用二分查找法来更高效地解决问题, 时间复杂度为**O(logN)** .通过二分查找法找到中值并以此决定在左半部分还是右半部分继续搜索. 因为原数组是有序的, 因此可以使用二分查找法, 但是数组被旋转了, 所以要稍微改改.

首先我们判断nums的首尾元素, 如果nums[head] < nums[tail], 说明数组没有被旋转过. 直接返回第一个元素即可.
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190821221020509.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0JlYW43NzE2MDY1NDA=,size_16,color_FFFFFF,t_70)
如果nums[head] > nums[tail], 说明数组一定在某个位置存在inflection point, 在该值处数字由最大值到最小值(如图). 

该点左边的数都比该点右边的数大. 因此每次找到中间值, 只要和nums[head]比较即可, 如果大于它, 说明中间值在inflection point的左边, 小于说明在右边.

具体算法是

 1. Find the mid element of the array.
 2. If mid element > first element of array this means that we need to look for the inflection point on the right of mid.
 3. If mid element < first element of array this that we need to lookfor the inflection point on the left of mid.
 4. We stop our search when we find the inflection point, when
    either of the two conditions is satisfied:
    
	nums[mid] > nums[mid + 1] Hence, mid+1 is the smallest.
    nums[mid - 1] > nums[mid] Hence, mid is the smallest.

**代码**
Python
```python
class Solution:
    def findMin(self, nums: List[int]) -> int:
        if len(nums) == 1:
            return nums[0]
        # 比较首尾元素, 判断是否选择过.
        if nums[0] < nums[len(nums)-1]:
            return nums[0]
        left = 0
        right = len(nums)-1
        while right >= left:
            mid = left + (right - left) // 2
            # 终止条件是当前一个数大于后一个数, 返回后一个数
            if nums[mid] > nums[mid + 1]:
                return nums[mid + 1]
            if nums[mid-1] > nums[mid]:
                return nums[mid]
            # 将中间值同第一个数比较, 如果大于, 那么最小值在中间值右边, left=mid+1. 反之亦然.
            if nums[mid] > nums[0]:
                left = mid + 1
            else:
                right = mid - 1
```

# 154. Find Minimum in Rotated Sorted Array II (hard)

[154. Find Minimum in Rotated Sorted Array II](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/)
**题目描述**

Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).

Find the minimum element.

The array may contain duplicates.

**解题思路**

类似 [leetcode 153. Find Minimum in Rotated Sorted Array](https://blog.csdn.net/Bean771606540/article/details/100000308)
但是数组可以存在重复的数. 这样就增加判断的难度, 在153题中, 只要简单判断nums[mid]和nums[tail]的大小就可以决定left或者right往哪边收缩边界. 但是存在重复的情况下, 可能存在nums[mid] =\= nums[tail]或者nums[mid] \== nums[head]的情况.

这里总结一下比153题多需要考虑的几种情况:
()在判断收缩边界条件为 if nums[mid] == nums[tail]的情况下)
1. 数组全是重复数, 如 [2,2,2,2,2,2]
2. 旋转位置在重复数中, 其他数在左半部分: [1,1,0,1,1,1,1,1]
3. 旋转位置在重复数中, 其他数在右半部分: [1,1,1,1,1,0,1,1]
4. 旋转位置在重复数的末尾(这样判断仍然是nums[mid] == nums[tail]) : [2,3,0,1,1,1,1,1]
5. 因为我们这里考虑的是, nums[mid]和最右一个元素的大小, 因此不考虑旋转位置在重复数起始的情况: [3,3,3,3,0,1,1,2]

这里用到的判断一个数组是否重复的方法就是 判断if max(list) == min(list).

**代码**
```python
class Solution:
    def findMin(self, nums: List[int]) -> int:
        if len(nums) == 0:
            return 0
        if len(nums) == 1:
            return 1
        
        left = 0
        right = len(nums) - 1
        
        # 递增序列
        if nums[left] < nums[right]:
            return nums[0]
        # 1. 数组全是重复数
        if max(nums) == min(nums):
            return nums[0]
        
        while left <= right:
            mid = left + (right - left)//2
            if nums[mid] > nums[right]:
                left = mid + 1
            elif nums[mid] < nums[right]:
                right = mid - 1
                
            elif nums[mid] == nums[right]:
                # 旋转位置在重复数中
                if nums[mid] == nums[left]:
                	# 2. 其他数在左半部分
                    if max(nums[0:mid]) != min(nums[0:mid]):
                        right = mid - 1
                    # 3. 其他数在右半部分
                    else:
                        left = mid + 1
                # 4. 旋转位置在重复数的末尾
                else:
                    right = mid - 1
                    
            if nums[mid-1] > nums[mid]:
                return nums[mid]
            if nums[mid] > nums[mid+1]:
                return nums[mid+1] 
        
```

# 165. Compare Version Numbers

[165. Compare Version Numbers](https://leetcode.com/problems/compare-version-numbers/)
**题目描述**

Compare two version numbers version1 and version2.
If version1 > version2 return 1; if version1 < version2 return -1;otherwise return 0.

You may assume that the version strings are non-empty and contain only digits and the . character.

比价两个版本号的大小

**解题思路**

1. 将string以"."隔断拆分成list, 
2. 短的list的索引范围内逐个比较两个list中元素的大小
3. 还未比出大小的情况下, 说明两个list在一定范围内都相等, 但是(1) 可能存在长list长出的部分全为0(说明两个list大小相等). (2) 可能存在若干个0但是末尾元素大于0(说明此时长list一定大于短list).
4. 时间 空间复杂度均为O(n)

**判断数字要注意类似00123这样起始是0的数字, 把0去掉.**
**代码**
Python
```python
class Solution:
    def compareVersion(self, version1, version2):
        
        version1 = [int(_) for _ in version1.split(".")]
        version2 = [int(_) for _ in version2.split(".")]
        # print(version1, version2)
        len1 = len(version1)
        len2 = len(version2)
        
        for i in range(min(len1, len2)):
            if version1[i] > version2[i]:
                return 1
            elif version1[i] < version2[i]:
                return -1
        diff = len1 - len2
        if diff > 0 and version1[-1] != 0:
            return 1
        elif diff < 0 and version2[-1] != 0:
            return -1
        else:
            return 0
```

# 167. Two Sum II - Input array is sorted (easy)

[167. Two Sum II - Input array is sorted](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/)
**题目描述**

Given an array of integers that is already sorted in **ascending order**, find two numbers such that they add up to a specific target number.

Note:

默认只有一个解, 而且同一个数不能使用两次

Example:
Input: numbers = [2,7,11,15], target = 9
Output: [1,2]
Explanation: The sum of 2 and 7 is 9. Therefore index1 = 1, index2 = 2.

**解题思路**

1.暴力解法. 从头开始遍历数组, 判断target和当前数之差是否也在数组中

2.动态规划. 因为这是一个递增有序数组, numbers[0]最小, numbers[tail]最大. 
1. 令i = 0, j = tail, 计算s = numbers[i] + numbers[j]
2. 如果s > target, 则 j - 1(j左移). 能使s减小; 同理s < target, 则 i + 1(i右移), 使s增大. 直到遇到s == target. 

**代码**

1.暴力解法
```python
class Solution:
    def twoSum(self, numbers: List[int], target: int) -> List[int]:
        for i in range(len(numbers)):
            if target - numbers[i] in numbers[i+1:]:
                index = numbers[i+1:].index(target-numbers[i])+(i+1)
                return [i+1, index+1]          
```
2. 动态规划
```python
class Solution:
    def twoSum(self, numbers: List[int], target: int) -> List[int]:
        i = 0
        j = len(numbers)-1
        while i != j:
            s = numbers[i] + numbers[j]
            if s == target:
                return [i+1,j+1]
            elif s > target:
                j -= 1
            elif s < target:
                i += 1
```

# 179. Largest Number (medium)
[179. Largest Number](https://leetcode.com/problems/largest-number/)
**题目描述**

Given a list of non negative integers, arrange them such that they form the largest number.

Input: [3,30,34,5,9]
Output: "9534330"

Note: The result may be very large, so you need to return a string instead of an integer.

**解题思路**

**判断数字要注意类似00123这样起始是0的数字.**

需要把能使得组合数字高位数尽量大的数排到前面, 因此想到可以使用冒泡排序法, 比较好理解, 比较大小的方式就是比较两个数的组合, 如12和34, 得到1234, 3412, 由于12排前面使得组合变小, 因此12需要往后排, 34往前排. 

经过一轮比较, 每次排前都使得组合数小的数就到了数组最后, 比如12每次和其他数组合, 12排前面都会得到小的结果, 那么一轮之后12就到了末尾. 原理和冒泡法一样.

注意考虑特殊情况, 所有元素都相等的情况. 其中存在所有数都为0的情况, 输出不能使0000而是0.

换成其他高效的排序算法, 也可以. 本题核心就是 如何比较大小.

**代码**
Python
```python
class Solution:
    def largestNumber(self, nums: List[int]) -> str:
        if len(nums) == 0:
            return ""
        if len(nums) == 1:
            return str(nums[0])
        # 存在000000这种特殊情况
        if max(nums) == min(nums):
            if nums[0] == 0:
                return "0"
            else:
                return str(nums[0])*len(nums)
            
        # 用冒泡排序
        # 比较规则
        for i in range(len(nums)-1):
            for j in range(len(nums)-1-i):
                if str(nums[j])+str(nums[j+1]) < str(nums[j+1])+str(nums[j]):
                    nums[j], nums[j+1] = nums[j+1], nums[j]
        return "".join([str(_) for _ in nums])
```

# 187. Repeated DNA Sequences
[187. Repeated DNA Sequences](https://leetcode.com/problems/repeated-dna-sequences/)
**题目描述**

简单来说就是找出一个字符串中 所有重复一次以上 的长度为10的子串

Input: s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
Output: ["AAAAACCCCC", "CCCCCAAAAA"]

**解题思路**

这种在长字符串中找字符串的题目, 要使用dictionary或者hashmap来保存数据, 因为检索效率高, 能有效降低时间复杂度. 也可以使用hashset, 特点是其中的元素不会重复.

**代码**
Python
```python
class Solution:
    def findRepeatedDnaSequences(self, s: str) -> List[str]:
        if len(s) <= 10:
            return []
        dic = {}
        for i in range(len(s)-9):
            dic[s[i:i+10]] = dic.get(s[i:i+10], 0)+1
        return [res for res in dic.keys() if dic[res] > 1]
```
Java
```java
class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        Set seen = new HashSet(), repeated = new HashSet();
        for (int i = 0; i + 9 < s.length(); i++) {
            String sub = s.substring(i, i + 10);
            if (!seen.add(sub))
                repeated.add(sub);
        }
        return new ArrayList(repeated);
    }
}
```
# 198. House Robber (easy
[198. House Robber](https://leetcode.com/problems/house-robber/)
**题目描述**

从一个数组中取(rob)若干数, 使得结果(money)最大, 这些数不能相邻

Input: [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
Total amount you can rob = 1 + 3 = 4.

**解题思路**

无法直接确定哪些数要取, 所以要用到动态规划. 比较经典的思路.
T[k]表示的是在前k位置里能累计的最大分值(money)
递归方程是: T[k] = max(T[k-2]+nums[k], T[k-1])
如果上一个位置未被选择, 则当前位置k的得分为: T[k-1] (因为这个位置就不能选了)
如果上一个位置已经被选择, 则当前位置k的得分为: T[k-2]+nums[k]
取两者大值, 保存到T[k].

**代码**
Python
```python
class Solution:
    def rob(self, nums: List[int]) -> int:
        if len(nums) == 0:
            return 0
        rob = [0] * len(nums)
        for i in range(len(nums)):
            if i == 0:
                rob[i] = nums[0]
            elif i == 1:
                rob[i] = max(nums[0], nums[1])
            else:
                rob[i] = max(rob[i-2]+nums[i], rob[i-1])
        return rob[-1]
```

199. Binary Tree Right Side View
[199. Binary Tree Right Side View](https://leetcode.com/problems/binary-tree-right-side-view/)

**题目描述**

假设站在二叉树右侧, 求能看到的节点. 

**解题思路**

层序遍历, 每层的最后一个就是能被看到的.

**代码**
Python
```python
# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution:
    def rightSideView(self, root: TreeNode) -> List[int]:
        if not root:
            return []
        if root.left == None and root.right == None:
            return [root.val]
        # 层序遍历的最后一个
        queue = [root]
        cur = []
        res = [root.val]
        while queue:
            node = queue.pop(0)
            if node.left: cur.append(node.left)
            if node.right: cur.append(node.right)
            if not queue:
                queue += cur
                if cur: res.append(cur[-1].val)
                cur = []
        return res
```

# 200. Number of Islands
[200. Number of Islands](https://leetcode.com/problems/number-of-islands/)
**题目描述**

1 代表岛屿, 0 代表海洋, 1 被 0 包围算作一个岛屿. 只要1被连接, 都只能算一个岛屿.

**解题思路**

用DFS解题. 遍历grid所有点, 如果grid[i][j]==1, 则对grid[i][j]由四个方向进行DFS, 把所有经过的点标记为x(表示去过), 所以每次遇到grid[i][j]==1的点一定是一个新的岛屿.

**代码**
Python
```python
class Solution:
    def numIslands(self, grid: List[List[str]]) -> int:
        if not grid:
            return 0
        
        def explore(i,j):
            # i was here
            grid[i][j] = 'x' 
            if i > 0 and grid[i - 1][j] == '1':
                explore(i - 1,j)               
            if j > 0 and grid[i][j - 1] == '1':
                explore(i,j - 1)            
            if i < col - 1 and grid[i + 1][j] == '1':
                explore(i + 1,j)     
            if j < row - 1 and grid[i][j + 1] == '1':
                explore(i,j + 1)
        
        noOfIsland = 0      
        col = len(grid)
        row = len(grid[0])
 
        for i in range(col):
            for j in range(row):
                if grid[i][j] == '1':          
                    noOfIsland += 1      
                    # Mark all the points in this island i.e all the points that can be reached from this point
                    explore(i,j)
                
        return noOfIsland
```

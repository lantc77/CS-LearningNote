package leetcode;

/**
 * @ClassName: Solution023
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/5/2 22:09
 * @Version: TODO
 **/
public class Solution023 {
    /**
     * 1. 每次 O(K) 比较 K个指针求 min, 时间复杂度：O(NK)
     */
    public ListNode mergeKLists(ListNode[] lists){
        // 每个指针指向一个链表
        ListNode head = new ListNode(0);
        ListNode tail = head, minPoint = head;
        int minVal = Integer.MAX_VALUE, len = lists.length, minIndex = 0;
        while(true) {
            for (int i = 0; i < len; i++) {
                if (lists[i] != null && lists[i].val < minVal) {
                    minVal = lists[i].val;
                    minPoint = lists[i];
                    minIndex = i;
                }
            }
            // means all lists come to the end
            if (minVal == Integer.MAX_VALUE)
                break;
            tail.next = minPoint;
            tail = tail.next;
            // the node of minIndex th lists points to its next
            lists[minIndex] = lists[minIndex].next;
            // reinitialize the minVal
            minVal = Integer.MAX_VALUE;
        }
        return head.next;
    }
    /**
     * 2. 使用小根堆对 1 进行优化，每次 O(logK)O(logK) 比较 K个指针求 min, 时间复杂度：O(NlogK)O(NlogK)
     */
    /**
     * 3. 逐一合并两条链表, 时间复杂度：O(NK)O(NK)
     */
    /**
     * 4. 两两合并对 1 进行优化，时间复杂度：O(NlogK)O(NlogK)
     */
    public static void main(String args[]){
        Solution023 test = new Solution023();

    }
}

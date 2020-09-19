package leetcode;

import java.util.List;
import java.util.Stack;

/**
 * @ClassName: Solution025
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/5/4 16:23
 * @Version: TODO
 **/

public class Solution025 {
    /**
     * use stack to reverse the k node.
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        Stack<ListNode> stack = new Stack<ListNode>();
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode pre = dummyHead, temp = head, cur;
        while(true){
            /**
            once the loop is done, the temp points to the next of final node of kth node
            for example:
            pre -> 1 -> 2 -> 3 -> 4
            finally the node points to 4.
             **/
            for(int i = 0; i < k; i++){
                if(temp == null && stack.size() < k)
                    return dummyHead.next;
                stack.push(temp);
                temp = temp.next;
            }
            // pop all the node in stack, revese is done.
            cur = pre;
            for(int i = 0; i < k ; i++){
                cur.next = stack.pop();
                cur = cur.next;
            }
            // the last of reverse node points to the left node.
            pre = cur;
            cur.next = temp;
            if(temp == null)
                break;
        }
        return dummyHead.next;
    }
}

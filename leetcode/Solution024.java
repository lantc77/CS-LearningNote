package leetcode;
import java.util.*;
/**
 * @ClassName:  24. Swap Nodes in Pairs
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/5/4 15:50
 * @Version: TODO
 **/
public class Solution024 {
    /**
     * first -> second -> third -> third.next
     * 1 step: first.next = third;
     * 2 step: second.next = third.next;
     * 3 step: third.next = second;
     * finally:
     * first -> third -> second -> second.next(third.next)
     * if second.next or second.next.next is null: loop is over
     *
     * then:
     * first = first.next.next;
     * second = second.next;
     * third = second.next;
     * back to the 1 step
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode first = dummyHead, second = head, third = head.next;
        while(true){
            first.next = third;
            second.next = third.next;
            third.next = second;

            if(second.next == null || second.next.next == null)
                break;
            // set the new pointer
            first = first.next.next;
            second = second.next;
            third = second.next;
        }
        return dummyHead.next;
    }
}

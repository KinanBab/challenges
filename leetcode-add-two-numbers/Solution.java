// https://leetcode.com/problems/add-two-numbers/


/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode ptr = result;
        ListNode prev = null;

        while(l1 != null || l2 != null) {
            prev = ptr;
            if (l1 != null) {
                ptr.val += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                ptr.val += l2.val;
                l2 = l2.next;
            }

            int carry = ptr.val / 10;
            ptr.val = ptr.val % 10;
            ptr.next = new ListNode(carry);
            ptr = ptr.next;
        }
        
        if (ptr.val == 0) {
            prev.next = null;
        }
        return result;
    }
}

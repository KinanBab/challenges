// https://leetcode.com/problems/reverse-nodes-in-k-group/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseK(ListNode head, int k) {
        ListNode result = head;
        for (int i = 1; i < k; i++) {
            ListNode n = head.next;
            if (n == null)
                return reverseK(result, i);
            
            ListNode nn = n.next;
            head.next = nn;
            n.next = result;
            result = n;
        }
        
        return result;
    }
    
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) return head;

        ListNode result = reverseK(head, k);
        ListNode nhead = head.next;
        
        while(nhead != null) {
            ListNode nresult = reverseK(nhead, k);
            if(nresult == nhead) break;

            head.next = nresult;
            head = nhead;
            nhead = nhead.next;
        }
        
        
        return result;
    }
}

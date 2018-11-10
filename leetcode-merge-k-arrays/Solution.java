// https://leetcode.com/problems/merge-k-sorted-lists/
// Solution based on priority queus: TIME O(n log(k)), SPACE O(n).
// More optimal solution merges arrays in a tree like way in levels (divide and conquer)
// this is good practice on Priority Queues

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        int k = lists.length;
        if(k == 0) return null;

        PriorityQueue<ListNode> queue = new PriorityQueue<ListNode>(k, new Comparator<ListNode>() {
            public int compare(ListNode t1, ListNode t2) {
                return t1.val - t2.val;
            }
        });

        for(ListNode l : lists) {
            if(l != null) queue.add(l);
        }

        if (queue.size() == 0) return null;
        
        ListNode l = queue.poll();
        ListNode sorted = new ListNode(l.val);
        ListNode current = sorted;
        
        if(l.next != null) queue.add(l.next);
        
        while(queue.size() != 0) {
            l = queue.poll();
            current.next = new ListNode(l.val);
            current = current.next;
            if(l.next != null) queue.add(l.next);
        }
        
        return sorted;
    }
}

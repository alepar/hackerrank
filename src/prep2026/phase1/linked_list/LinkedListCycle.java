package prep2026.phase1.linked_list;

import java.util.HashSet;
import java.util.Set;

// https://leetcode.com/problems/linked-list-cycle/description/
public class LinkedListCycle {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public class Solution {
        public boolean hasCycle(ListNode head) {
            final Set<Integer> visited = new HashSet<>();

            ListNode slow = head;
            ListNode fast = head;
            while (fast != null) {
                fast = fast.next;
                if (fast == null) return false;
                if (fast == slow) return true;

                fast = fast.next;
                if (fast == slow) return true;

                slow = slow.next;
            }

            return false;
        }
    }

    // Helper: build list from values, with cycle at given index (-1 = no cycle)
    static ListNode buildList(int[] vals, int cyclePos) {
        if (vals.length == 0) return null;
        ListNode[] nodes = new ListNode[vals.length];
        for (int i = 0; i < vals.length; i++) nodes[i] = new ListNode(vals[i]);
        for (int i = 0; i < vals.length - 1; i++) nodes[i].next = nodes[i + 1];
        if (cyclePos >= 0) nodes[vals.length - 1].next = nodes[cyclePos];
        return nodes[0];
    }

    public static void main(String[] args) {
        LinkedListCycle outer = new LinkedListCycle();
        Solution sol = outer.new Solution();
        int pass = 0, fail = 0;

        // Test 1: Example 1 - [3,2,0,-4], cycle at pos 1 -> true
        {
            boolean result = sol.hasCycle(buildList(new int[]{3,2,0,-4}, 1));
            if (result == true) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL - got " + result); }
        }

        // Test 2: Example 2 - [1,2], cycle at pos 0 -> true
        {
            boolean result = sol.hasCycle(buildList(new int[]{1,2}, 0));
            if (result == true) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL - got " + result); }
        }

        // Test 3: Example 3 - [1], no cycle -> false
        {
            boolean result = sol.hasCycle(buildList(new int[]{1}, -1));
            if (result == false) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL - got " + result); }
        }

        // Test 4: Empty list - null -> false
        {
            boolean result = sol.hasCycle(null);
            if (result == false) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL - got " + result); }
        }

        // Test 5: Single node with self-cycle - [1], cycle at pos 0 -> true
        {
            boolean result = sol.hasCycle(buildList(new int[]{1}, 0));
            if (result == true) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL - got " + result); }
        }

        // Test 6: Long list with no cycle - [1,2,3,4,5] -> false
        {
            boolean result = sol.hasCycle(buildList(new int[]{1,2,3,4,5}, -1));
            if (result == false) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL - got " + result); }
        }

        // Test 7: Cycle at tail pointing to tail - [1,2,3], cycle at pos 2 -> true
        {
            boolean result = sol.hasCycle(buildList(new int[]{1,2,3}, 2));
            if (result == true) { pass++; System.out.println("Test 7: PASS"); }
            else { fail++; System.out.println("Test 7: FAIL - got " + result); }
        }

        // Test 8: Two nodes, no cycle - [1,2] -> false
        {
            boolean result = sol.hasCycle(buildList(new int[]{1,2}, -1));
            if (result == false) { pass++; System.out.println("Test 8: PASS"); }
            else { fail++; System.out.println("Test 8: FAIL - got " + result); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }
}

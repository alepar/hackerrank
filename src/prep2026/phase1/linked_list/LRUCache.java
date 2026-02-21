package prep2026.phase1.linked_list;


import java.util.HashMap;
import java.util.Map;

// https://leetcode.com/problems/lru-cache/description/
public class LRUCache {

    class Solution {

        class Node {
            Node prev;
            Node next;
            final int key;
            final int val;

            Node(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        final int capacity;
        final Map<Integer, Node> index = new HashMap<>();
        Node head;

        public Solution(int capacity) {
            this.capacity = capacity;
            this.head = null;
        }

        public int get(int key) {
            final Node node = this.index.get(key);

            if (node == null) return -1;
            this.put(key, node.val);

            return node.val;
        }

        public void put(int key, int value) {
            this.remove(key);

            final Node n = new Node(key, value);
            if (this.head == null) {
                n.next = n;
                n.prev = n;
            } else {
                n.prev = this.head.prev;
                n.next = this.head;
                this.head.prev = n;
                n.prev.next = n;
            }
            this.head = n;
            this.index.put(key, n);

            while (this.index.size() > capacity) {
                this.remove(this.head.prev.key);
            }
        }

        private void remove(int key) {
            final Node n = this.index.remove(key);
            if (n == null) return;

            if (n.prev == n) {
                this.head = null;
                return;
            }

            n.prev.next = n.next;
            n.next.prev = n.prev;

            if (n == this.head) this.head = n.next;
        }
    }

/*
    class Solution {

        final int capacity;
        final SequencedMap<Integer, Integer> index = new LinkedHashMap<>();

        public Solution(int capacity) {
            this.capacity = capacity;
        }

        public int get(int key) {
            final Integer val = this.index.get(key);

            if (val == null) return -1;
            this.put(key, val);

            return val;
        }

        public void put(int key, int value) {
            this.index.remove(key);
            this.index.putFirst(key, value);

            while (this.index.size() > capacity) {
                this.index.remove(this.index.lastEntry().getKey());
            }
        }
    }
*/

    public static void main(String[] args) {
        LRUCache outer = new LRUCache();
        int pass = 0, fail = 0;

        // Test 1: Example 1 - basic put/get sequence
        {
            Solution cache = outer.new Solution(2);
            cache.put(1, 1);
            cache.put(2, 2);
            int r1 = cache.get(1);       // -> 1
            cache.put(3, 3);             // evicts key 2
            int r2 = cache.get(2);       // -> -1
            cache.put(4, 4);             // evicts key 1
            int r3 = cache.get(1);       // -> -1
            int r4 = cache.get(3);       // -> 3
            int r5 = cache.get(4);       // -> 4
            if (r1 == 1 && r2 == -1 && r3 == -1 && r4 == 3 && r5 == 4) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL - got " + r1 + "," + r2 + "," + r3 + "," + r4 + "," + r5); }
        }

        // Test 2: Capacity 1 - single slot eviction
        {
            Solution cache = outer.new Solution(1);
            cache.put(1, 1);
            cache.put(2, 2);             // evicts key 1
            int r1 = cache.get(1);       // -> -1
            int r2 = cache.get(2);       // -> 2
            if (r1 == -1 && r2 == 2) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL - got " + r1 + "," + r2); }
        }

        // Test 3: Get refreshes recency - get(1) saves key 1 from eviction
        {
            Solution cache = outer.new Solution(2);
            cache.put(1, 1);
            cache.put(2, 2);
            cache.get(1);                // refreshes key 1
            cache.put(3, 3);             // evicts key 2 (not 1)
            int r1 = cache.get(2);       // -> -1
            int r2 = cache.get(1);       // -> 1
            if (r1 == -1 && r2 == 1) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL - got " + r1 + "," + r2); }
        }

        // Test 4: Put updates existing key value
        {
            Solution cache = outer.new Solution(2);
            cache.put(1, 1);
            cache.put(1, 10);            // update value
            int r1 = cache.get(1);       // -> 10
            if (r1 == 10) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL - got " + r1); }
        }

        // Test 5: Put on existing key refreshes recency
        {
            Solution cache = outer.new Solution(2);
            cache.put(1, 1);
            cache.put(2, 2);
            cache.put(1, 10);            // update key 1, refreshes it
            cache.put(3, 3);             // evicts key 2 (not 1)
            int r1 = cache.get(2);       // -> -1
            int r2 = cache.get(1);       // -> 10
            int r3 = cache.get(3);       // -> 3
            if (r1 == -1 && r2 == 10 && r3 == 3) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL - got " + r1 + "," + r2 + "," + r3); }
        }

        // Test 6: Get on missing key returns -1
        {
            Solution cache = outer.new Solution(2);
            int r1 = cache.get(99);      // -> -1
            if (r1 == -1) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL - got " + r1); }
        }

        // Test 7: Eviction order with interleaved ops
        {
            Solution cache = outer.new Solution(3);
            cache.put(1, 1);
            cache.put(2, 2);
            cache.put(3, 3);
            cache.get(1);                // refresh 1, LRU order: 2,3,1
            cache.put(4, 4);             // evicts key 2
            int r1 = cache.get(2);       // -> -1
            int r2 = cache.get(1);       // -> 1
            int r3 = cache.get(3);       // -> 3
            int r4 = cache.get(4);       // -> 4
            if (r1 == -1 && r2 == 1 && r3 == 3 && r4 == 4) { pass++; System.out.println("Test 7: PASS"); }
            else { fail++; System.out.println("Test 7: FAIL - got " + r1 + "," + r2 + "," + r3 + "," + r4); }
        }

        // Test 8: get refreshes before eviction - put(2,1),put(3,2),get(3)->2,get(2)->1,put(4,3) evicts 3,get(2)->1,get(3)->-1,get(4)->3
        {
            Solution cache = outer.new Solution(2);
            cache.put(2, 1);
            cache.put(3, 2);
            int r1 = cache.get(3);       // -> 2
            int r2 = cache.get(2);       // -> 1
            cache.put(4, 3);             // evicts key 3 (LRU)
            int r3 = cache.get(2);       // -> 1
            int r4 = cache.get(3);       // -> -1
            int r5 = cache.get(4);       // -> 3
            if (r1 == 2 && r2 == 1 && r3 == 1 && r4 == -1 && r5 == 3) { pass++; System.out.println("Test 8: PASS"); }
            else { fail++; System.out.println("Test 8: FAIL - got " + r1 + "," + r2 + "," + r3 + "," + r4 + "," + r5); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }
}

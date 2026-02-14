package prep2026.phase1.arrays_hashing;

import java.util.*;

// https://leetcode.com/problems/top-k-frequent-elements/description/
public class TopKFrequentElements {
    class Solution {
        public int[] topKFrequent(int[] nums, int k) {
            final Map<Integer, Integer> counts = new HashMap<>();
            for (var n: nums) {
                counts.compute(n, (Integer _, Integer v) -> {
                    if (v == null) v=0;
                    v++;
                    return v;
                });
            }

            final PriorityQueue<Integer> freqs = new PriorityQueue<>(Comparator.reverseOrder());
            freqs.addAll(counts.values());

            int cutoff = Integer.MAX_VALUE;
            for (int i=0; i<k; i++) {
                cutoff = freqs.poll();
            }

            final int[] result = new int[k];
            int j=0;
            for (var e : counts.entrySet()) {
                if (j == k) break;

                if (e.getValue() >= cutoff) {
                    result[j++] = e.getKey();
                }
            }
            return result;
        }
    }

    public static void main(String[] args) {
        TopKFrequentElements outer = new TopKFrequentElements();
        Solution sol = outer.new Solution();
        int pass = 0, fail = 0;

        // Test 1: Example 1 - [1,1,1,2,2,3], k=2 -> [1,2]
        {
            int[] result = sol.topKFrequent(new int[]{1,1,1,2,2,3}, 2);
            if (matchesUnordered(result, new int[]{1,2})) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 2: Example 2 - [1], k=1 -> [1]
        {
            int[] result = sol.topKFrequent(new int[]{1}, 1);
            if (matchesUnordered(result, new int[]{1})) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 3: All same element
        {
            int[] result = sol.topKFrequent(new int[]{5,5,5,5}, 1);
            if (matchesUnordered(result, new int[]{5})) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 4: k equals number of distinct elements
        {
            int[] result = sol.topKFrequent(new int[]{1,2,3}, 3);
            if (matchesUnordered(result, new int[]{1,2,3})) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 5: Negative numbers
        {
            int[] result = sol.topKFrequent(new int[]{-1,-1,2,2,3}, 2);
            if (matchesUnordered(result, new int[]{-1,2})) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 6: Larger input
        {
            int[] result = sol.topKFrequent(new int[]{4,1,-1,2,-1,2,3}, 2);
            if (matchesUnordered(result, new int[]{-1,2})) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL - got " + Arrays.toString(result)); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }

    private static boolean matchesUnordered(int[] actual, int[] expected) {
        if (actual.length != expected.length) return false;
        int[] a = actual.clone(), b = expected.clone();
        Arrays.sort(a);
        Arrays.sort(b);
        return Arrays.equals(a, b);
    }
}

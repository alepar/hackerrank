package prep2026.phase1.binary_search;

// https://leetcode.com/problems/binary-search/description/
public class BinarySearch {
    class Solution {
        public int search(int[] nums, int target) {
            int l=0, r=nums.length-1;
            while (l<=r) {
                final int m = (l+r)/2;

                if (nums[m] == target) {
                    return m;
                }

                if (nums[m] > target) {
                    r = m-1;
                } else {
                    l=m+1;
                }
            }

            return -1;
        }
    }

    public static void main(String[] args) {
        BinarySearch outer = new BinarySearch();
        Solution sol = outer.new Solution();
        int pass = 0, fail = 0;

        // Test 1: Example 1 - [-1,0,3,5,9,12], target=9 -> 4
        {
            int result = sol.search(new int[]{-1,0,3,5,9,12}, 9);
            if (result == 4) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL - got " + result); }
        }

        // Test 2: Example 2 - [-1,0,3,5,9,12], target=2 -> -1
        {
            int result = sol.search(new int[]{-1,0,3,5,9,12}, 2);
            if (result == -1) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL - got " + result); }
        }

        // Test 3: Single element found - [5], target=5 -> 0
        {
            int result = sol.search(new int[]{5}, 5);
            if (result == 0) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL - got " + result); }
        }

        // Test 4: Single element not found - [5], target=3 -> -1
        {
            int result = sol.search(new int[]{5}, 3);
            if (result == -1) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL - got " + result); }
        }

        // Test 5: Target is first element - [1,2,3,4,5], target=1 -> 0
        {
            int result = sol.search(new int[]{1,2,3,4,5}, 1);
            if (result == 0) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL - got " + result); }
        }

        // Test 6: Target is last element - [1,2,3,4,5], target=5 -> 4
        {
            int result = sol.search(new int[]{1,2,3,4,5}, 5);
            if (result == 4) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL - got " + result); }
        }

        // Test 7: Target smaller than all - [2,4,6], target=1 -> -1
        {
            int result = sol.search(new int[]{2,4,6}, 1);
            if (result == -1) { pass++; System.out.println("Test 7: PASS"); }
            else { fail++; System.out.println("Test 7: FAIL - got " + result); }
        }

        // Test 8: Target larger than all - [2,4,6], target=7 -> -1
        {
            int result = sol.search(new int[]{2,4,6}, 7);
            if (result == -1) { pass++; System.out.println("Test 8: PASS"); }
            else { fail++; System.out.println("Test 8: FAIL - got " + result); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }
}

package prep2026.phase1.binary_search;

// https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/description/
public class FindMinimumInRotatedSortedArray {

    class Solution {
        public int findMin(int[] nums) {
            int l = 0, r = nums.length - 1;
            while (l < r) {
                int m = (l + r) / 2;
                if (nums[m] > nums[r]) l = m + 1;  // min is in (m, r]
                else r = m;                          // min is in [l, m]
            }
            return nums[l];
        }
    }

    // note: if think you finally understand the above, try implementing findMax() ;)

    public static void main(String[] args) {
        FindMinimumInRotatedSortedArray outer = new FindMinimumInRotatedSortedArray();
        Solution sol = outer.new Solution();
        int pass = 0, fail = 0;

        // Test 0: Example 1 - [4,5,6,7,0,1,2] -> 0
        {
            int result = sol.findMin(new int[]{4,5,6,7,0,1,2});
            if (result == 0) { pass++; System.out.println("Test 0: PASS"); }
            else { fail++; System.out.println("Test 0: FAIL - got " + result); }
        }

        // Test 1: Example 1 - [3,4,5,1,2] -> 1
        {
            int result = sol.findMin(new int[]{3,4,5,1,2});
            if (result == 1) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL - got " + result); }
        }

        // Test 2: Example 2 - [4,5,6,7,0,1,2] -> 0
        {
            int result = sol.findMin(new int[]{4,5,6,7,0,1,2});
            if (result == 0) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL - got " + result); }
        }

        // Test 3: Example 3 - [11,13,15,17] -> 11 (no rotation)
        {
            int result = sol.findMin(new int[]{11,13,15,17});
            if (result == 11) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL - got " + result); }
        }

        // Test 4: Single element - [1] -> 1
        {
            int result = sol.findMin(new int[]{1});
            if (result == 1) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL - got " + result); }
        }

        // Test 5: Two elements, rotated - [2,1] -> 1
        {
            int result = sol.findMin(new int[]{2,1});
            if (result == 1) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL - got " + result); }
        }

        // Test 6: Two elements, not rotated - [1,2] -> 1
        {
            int result = sol.findMin(new int[]{1,2});
            if (result == 1) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL - got " + result); }
        }

        // Test 7: Rotation at second element - [5,1,2,3,4] -> 1
        {
            int result = sol.findMin(new int[]{5,1,2,3,4});
            if (result == 1) { pass++; System.out.println("Test 7: PASS"); }
            else { fail++; System.out.println("Test 7: FAIL - got " + result); }
        }

        // Test 8: Rotation at last element - [2,3,4,5,1] -> 1
        {
            int result = sol.findMin(new int[]{2,3,4,5,1});
            if (result == 1) { pass++; System.out.println("Test 8: PASS"); }
            else { fail++; System.out.println("Test 8: FAIL - got " + result); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }
}

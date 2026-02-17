package prep2026.phase1.binary_search;

// https://leetcode.com/problems/search-in-rotated-sorted-array/description/
public class SearchInRotatedSortedArray {
    class Solution {
        public int search(int[] nums, int target) {
            int l=0, r=nums.length-1;

            while (l<=r) {
                final int m = (l+r)/2;

                if (nums[m] == target) return m;
                if (nums[l] == target) return l;
                if (nums[r] == target) return r;

                if (nums[l] <= nums[m]) {
                    // left half is sorted
                    if (nums[l] < target && target < nums[m]) r = m - 1;
                    else l = m + 1;
                } else {
                    // right half is sorted
                    if (nums[m] < target && target < nums[r]) l = m + 1;
                    else r = m - 1;
                }
            }

            return -1;
        }
    }

    public static void main(String[] args) {
        SearchInRotatedSortedArray outer = new SearchInRotatedSortedArray();
        Solution sol = outer.new Solution();
        int pass = 0, fail = 0;

        // Test 1: Example 1 - [4,5,6,7,0,1,2], target=0 -> 4
        {
            int result = sol.search(new int[]{4,5,6,7,0,1,2}, 0);
            if (result == 4) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL - got " + result); }
        }

        // Test 2: Example 2 - [4,5,6,7,0,1,2], target=3 -> -1
        {
            int result = sol.search(new int[]{4,5,6,7,0,1,2}, 3);
            if (result == -1) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL - got " + result); }
        }

        // Test 3: Example 3 - [1], target=0 -> -1
        {
            int result = sol.search(new int[]{1}, 0);
            if (result == -1) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL - got " + result); }
        }

        // Test 4: No rotation - [1,2,3,4,5], target=3 -> 2
        {
            int result = sol.search(new int[]{1,2,3,4,5}, 3);
            if (result == 2) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL - got " + result); }
        }

        // Test 5: Target at pivot - [3,4,5,1,2], target=1 -> 3
        {
            int result = sol.search(new int[]{3,4,5,1,2}, 1);
            if (result == 3) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL - got " + result); }
        }

        // Test 6: Target is largest element - [3,4,5,1,2], target=5 -> 2
        {
            int result = sol.search(new int[]{3,4,5,1,2}, 5);
            if (result == 2) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL - got " + result); }
        }

        // Test 7: Two elements, rotated - [2,1], target=1 -> 1
        {
            int result = sol.search(new int[]{2,1}, 1);
            if (result == 1) { pass++; System.out.println("Test 7: PASS"); }
            else { fail++; System.out.println("Test 7: FAIL - got " + result); }
        }

        // Test 8: Target is first element - [6,7,1,2,3,4,5], target=6 -> 0
        {
            int result = sol.search(new int[]{6,7,1,2,3,4,5}, 6);
            if (result == 0) { pass++; System.out.println("Test 8: PASS"); }
            else { fail++; System.out.println("Test 8: FAIL - got " + result); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }
}

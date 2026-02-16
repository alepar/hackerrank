package prep2026.phase1.arrays_hashing;

import java.util.*;

// https://leetcode.com/problems/product-of-array-except-self
public class ProductOfArrayExceptSelf {
    class Solution {
        public int[] productExceptSelf(int[] nums) {
            final int n = nums.length;

            final int[] prefixes = new int[n];
            prefixes[0] = 1;
            for (int i = 1; i< n; i++) {
                prefixes[i] = prefixes[i-1] * nums[i-1];
            }

            final int[] suffixes = new int[n];
            suffixes[n-1] = 1;
            for (int i = n-2; i>=0; i--) {
                suffixes[i] = suffixes[i+1]*nums[i+1];
            }

            final int[] res = new int[n];
            for (int i=0; i<n; i++) {
                res[i] = prefixes[i] * suffixes[i];
            }

            return res;
        }
    }

    public static void main(String[] args) {
        ProductOfArrayExceptSelf outer = new ProductOfArrayExceptSelf();
        Solution sol = outer.new Solution();
        int pass = 0, fail = 0;

        // Test 1: Example 1 - [1,2,3,4] -> [24,12,8,6]
        {
            int[] result = sol.productExceptSelf(new int[]{1,2,3,4});
            if (Arrays.equals(result, new int[]{24,12,8,6})) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 2: Example 2 - [-1,1,0,-3,3] -> [0,0,9,0,0]
        {
            int[] result = sol.productExceptSelf(new int[]{-1,1,0,-3,3});
            if (Arrays.equals(result, new int[]{0,0,9,0,0})) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 3: Two elements
        {
            int[] result = sol.productExceptSelf(new int[]{3,5});
            if (Arrays.equals(result, new int[]{5,3})) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 4: Contains one zero
        {
            int[] result = sol.productExceptSelf(new int[]{2,0,4});
            if (Arrays.equals(result, new int[]{0,8,0})) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 5: Contains two zeros
        {
            int[] result = sol.productExceptSelf(new int[]{0,2,0});
            if (Arrays.equals(result, new int[]{0,0,0})) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 6: All ones
        {
            int[] result = sol.productExceptSelf(new int[]{1,1,1,1});
            if (Arrays.equals(result, new int[]{1,1,1,1})) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 7: Negative numbers
        {
            int[] result = sol.productExceptSelf(new int[]{-1,-2,-3});
            if (Arrays.equals(result, new int[]{6,3,2})) { pass++; System.out.println("Test 7: PASS"); }
            else { fail++; System.out.println("Test 7: FAIL - got " + Arrays.toString(result)); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }
}

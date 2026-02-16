package prep2026.phase1.two_pointers;

import java.util.*;

// https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
public class TwoSumII {
    class Solution {
        public int[] twoSum(int[] numbers, int target) {
            int l=0, r=numbers.length-1;

            while (l<r) {
                final int s = numbers[l] + numbers[r];
                if (s == target) {
                    return new int[]{l+1, r+1};
                }

                if (s < target) {
                    l++;
                } else if (s > target) {
                    r--;
                }
            }

            return null;
        }
    }

    public static void main(String[] args) {
        TwoSumII outer = new TwoSumII();
        Solution sol = outer.new Solution();
        int pass = 0, fail = 0;

        // Test 1: Example 1 - [2,7,11,15], target=9 -> [1,2]
        {
            int[] result = sol.twoSum(new int[]{2,7,11,15}, 9);
            if (Arrays.equals(result, new int[]{1,2})) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 2: Example 2 - [2,3,4], target=6 -> [1,3]
        {
            int[] result = sol.twoSum(new int[]{2,3,4}, 6);
            if (Arrays.equals(result, new int[]{1,3})) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 3: Example 3 - [-1,0], target=-1 -> [1,2]
        {
            int[] result = sol.twoSum(new int[]{-1,0}, -1);
            if (Arrays.equals(result, new int[]{1,2})) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 4: First and last element - [1,2,3,4,5], target=6 -> [1,5]
        {
            int[] result = sol.twoSum(new int[]{1,2,3,4,5}, 6);
            if (Arrays.equals(result, new int[]{1,5})) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 5: Adjacent elements - [1,2,3,4], target=3 -> [1,2]
        {
            int[] result = sol.twoSum(new int[]{1,2,3,4}, 3);
            if (Arrays.equals(result, new int[]{1,2})) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 6: Negative numbers - [-3,-1,0,2,4], target=1 -> [2,4]
        {
            int[] result = sol.twoSum(new int[]{-3,-1,0,2,5}, 1);
            if (Arrays.equals(result, new int[]{2,4})) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 7: Duplicate values - [1,1,2,3], target=2 -> [1,2]
        {
            int[] result = sol.twoSum(new int[]{1,1,2,3}, 2);
            if (Arrays.equals(result, new int[]{1,2})) { pass++; System.out.println("Test 7: PASS"); }
            else { fail++; System.out.println("Test 7: FAIL - got " + Arrays.toString(result)); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }
}

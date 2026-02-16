package prep2026.phase1.two_pointers;

import java.util.*;
import java.util.stream.Collectors;

// https://leetcode.com/problems/3sum/description/
public class ThreeSum {
    class Solution {
        public List<List<Integer>> threeSum(int[] nums) {
            final List<List<Integer>> result = new ArrayList<>();
            final int n = nums.length;

            Arrays.sort(nums);

            for (int i=0; i<n-2; i++) {
                if (i>0 && nums[i]==nums[i-1]) {
                    continue;
                }

                final int target = -nums[i];
                int l=i+1, r=n-1;
                while (l<r) {
                    final int s = nums[l] + nums[r];

                    if (s == target) {
                        result.add(List.of(nums[i], nums[l], nums[r]));
                        l++; while (l<r && nums[l] == nums[l-1]) l++;
                        r--; while (r>l && nums[r] == nums[r+1]) r--;
                    } else if (s < target) {
                        l++;
                    } else {
                        r--;
                    }
                }
            }

            return result;
        }
    }

    public static void main(String[] args) {
        ThreeSum outer = new ThreeSum();
        Solution sol = outer.new Solution();
        int pass = 0, fail = 0;

        // Test 1: Example 1 - [-1,0,1,2,-1,-4] -> [[-1,-1,2],[-1,0,1]]
        {
            List<List<Integer>> result = sol.threeSum(new int[]{-1,0,1,2,-1,-4});
            List<List<Integer>> expected = List.of(List.of(-1,-1,2), List.of(-1,0,1));
            if (matches(result, expected)) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL - got " + result); }
        }

        // Test 2: Example 2 - [0,1,1] -> []
        {
            List<List<Integer>> result = sol.threeSum(new int[]{0,1,1});
            if (result.isEmpty()) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL - got " + result); }
        }

        // Test 3: Example 3 - [0,0,0] -> [[0,0,0]]
        {
            List<List<Integer>> result = sol.threeSum(new int[]{0,0,0});
            List<List<Integer>> expected = List.of(List.of(0,0,0));
            if (matches(result, expected)) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL - got " + result); }
        }

        // Test 4: No valid triplets - [1,2,3]
        {
            List<List<Integer>> result = sol.threeSum(new int[]{1,2,3});
            if (result.isEmpty()) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL - got " + result); }
        }

        // Test 5: Multiple triplets - [-2,-1,0,1,2]
        {
            List<List<Integer>> result = sol.threeSum(new int[]{-2,-1,0,1,2});
            List<List<Integer>> expected = List.of(List.of(-2,0,2), List.of(-1,0,1));
            if (matches(result, expected)) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL - got " + result); }
        }

        // Test 6: Duplicates must be skipped - [-1,-1,-1,0,1,2]
        {
            List<List<Integer>> result = sol.threeSum(new int[]{-1,-1,-1,0,1,2});
            List<List<Integer>> expected = List.of(List.of(-1,-1,2), List.of(-1,0,1));
            if (matches(result, expected)) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL - got " + result); }
        }

        // Test 7: All negatives - [-3,-2,-1]
        {
            List<List<Integer>> result = sol.threeSum(new int[]{-3,-2,-1});
            if (result.isEmpty()) { pass++; System.out.println("Test 7: PASS"); }
            else { fail++; System.out.println("Test 7: FAIL - got " + result); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }

    private static boolean matches(List<List<Integer>> actual, List<List<Integer>> expected) {
        if (actual.size() != expected.size()) return false;
        List<List<Integer>> aSorted = actual.stream()
            .map(l -> l.stream().sorted().collect(Collectors.toList()))
            .sorted(Comparator.comparing(Object::toString))
            .collect(Collectors.toList());
        List<List<Integer>> eSorted = expected.stream()
            .map(l -> l.stream().sorted().collect(Collectors.toList()))
            .sorted(Comparator.comparing(Object::toString))
            .collect(Collectors.toList());
        return aSorted.equals(eSorted);
    }
}

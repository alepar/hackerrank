package prep2026.phase1.two_pointers;

// https://leetcode.com/problems/container-with-most-water
public class ContainersWithMostWater {
    class Solution {
        public int maxArea(int[] height) {
            int max = 0;
            int l=0, r=height.length-1;
            while(l<r) {
                final int h = Math.min(height[l], height[r]);
                final int w = r-l;
                if (h*w > max) {
                    max = h*w;
                }

                if (height[l] < height[r]) {
                    l++;
                } else {
                    r--;
                }
            }

            return max;
        }
    }

    public static void main(String[] args) {
        ContainersWithMostWater outer = new ContainersWithMostWater();
        Solution sol = outer.new Solution();
        int pass = 0, fail = 0;

        // Test 1: Example 1 - [1,8,6,2,5,4,8,3,7] -> 49
        {
            int result = sol.maxArea(new int[]{1,8,6,2,5,4,8,3,7});
            if (result == 49) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL - got " + result); }
        }

        // Test 2: Example 2 - [1,1] -> 1
        {
            int result = sol.maxArea(new int[]{1,1});
            if (result == 1) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL - got " + result); }
        }

        // Test 3: Descending heights - [5,4,3,2,1] -> 6
        {
            int result = sol.maxArea(new int[]{5,4,3,2,1});
            if (result == 6) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL - got " + result); }
        }

        // Test 4: Ascending heights - [1,2,3,4,5] -> 6
        {
            int result = sol.maxArea(new int[]{1,2,3,4,5});
            if (result == 6) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL - got " + result); }
        }

        // Test 5: Tall edges, short middle - [10,1,1,1,10] -> 40
        {
            int result = sol.maxArea(new int[]{10,1,1,1,10});
            if (result == 40) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL - got " + result); }
        }

        // Test 6: All same height - [3,3,3,3] -> 9
        {
            int result = sol.maxArea(new int[]{3,3,3,3});
            if (result == 9) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL - got " + result); }
        }

        // Test 7: Spike in the middle - [1,1,100,1,1] -> 4
        {
            int result = sol.maxArea(new int[]{1,1,100,1,1});
            if (result == 4) { pass++; System.out.println("Test 7: PASS"); }
            else { fail++; System.out.println("Test 7: FAIL - got " + result); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }
}

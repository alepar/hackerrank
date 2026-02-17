package prep2026.phase1.binary_search;

import java.util.Arrays;

// https://leetcode.com/problems/koko-eating-bananas/description/
public class KokoEatingBananas {
    class Solution {
        public int minEatingSpeed(int[] piles, int h) {
            int max = piles[0];
            for (int i=1; i<piles.length; i++) {
                if (piles[i] > max) max = piles[i];
            }

            int l=1, r=max;
            while (l<r) {
                final int k = (l+r)/2;
                int needed = 0;
                for (int i=0; i<piles.length; i++) {
                    needed += Math.ceilDiv(piles[i], k);
                }

                if (needed > h) l = k+1;
                else r=k;
            }

            return l;
        }
    }

    // note: important to recognize when you can bin search for the answer:
    // - given a guess of the answer, you can easily test whether real answer is bigger or smaller
    // "answer space is monotonic"

    public static void main(String[] args) {
        KokoEatingBananas outer = new KokoEatingBananas();
        Solution sol = outer.new Solution();
        int pass = 0, fail = 0;

        // Test 1: Example 1 - [3,6,7,11], h=8 -> 4
        {
            int result = sol.minEatingSpeed(new int[]{3,6,7,11}, 8);
            if (result == 4) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL - got " + result); }
        }

        // Test 2: Example 2 - [30,11,23,4,20], h=5 -> 30
        {
            int result = sol.minEatingSpeed(new int[]{30,11,23,4,20}, 5);
            if (result == 30) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL - got " + result); }
        }

        // Test 3: Example 3 - [30,11,23,4,20], h=6 -> 23
        {
            int result = sol.minEatingSpeed(new int[]{30,11,23,4,20}, 6);
            if (result == 23) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL - got " + result); }
        }

        // Test 4: Single pile - [10], h=5 -> 2
        {
            int result = sol.minEatingSpeed(new int[]{10}, 5);
            if (result == 2) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL - got " + result); }
        }

        // Test 5: h equals number of piles (must eat each in one hour) -> max pile
        {
            int result = sol.minEatingSpeed(new int[]{5,10,15}, 3);
            if (result == 15) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL - got " + result); }
        }

        // Test 6: Plenty of time - [1,1,1,1], h=100 -> 1
        {
            int result = sol.minEatingSpeed(new int[]{1,1,1,1}, 100);
            if (result == 1) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL - got " + result); }
        }

        // Test 7: All same size - [8,8,8], h=6 -> 4
        {
            int result = sol.minEatingSpeed(new int[]{8,8,8}, 6);
            if (result == 4) { pass++; System.out.println("Test 7: PASS"); }
            else { fail++; System.out.println("Test 7: FAIL - got " + result); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }
}

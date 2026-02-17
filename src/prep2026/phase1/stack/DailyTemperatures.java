package prep2026.phase1.stack;

import java.util.*;

// https://leetcode.com/problems/daily-temperatures/description/
public class DailyTemperatures {
    class Solution {
        public int[] dailyTemperatures(int[] temperatures) {
            final int n = temperatures.length;
            final int[] answer = new int[n];
            final Deque<Integer> nonIncreasing = new ArrayDeque<>();

            answer[n-1]=0;
            nonIncreasing.push(n-1);

            for (int i=n-2; i>=0; i--) {
                if (temperatures[i]<temperatures[i+1]) {
                    answer[i]=1;
                } else {
                    while(!nonIncreasing.isEmpty() && temperatures[nonIncreasing.peek()] <= temperatures[i]) {
                        nonIncreasing.pop();
                    }

                    if (!nonIncreasing.isEmpty()) {
                        answer[i] = nonIncreasing.peek()-i;
                    } else {
                        answer[i] = 0;
                    }
                }

                nonIncreasing.push(i);
            }

            return answer;
        }
    }

    public static void main(String[] args) {
        DailyTemperatures outer = new DailyTemperatures();
        Solution sol = outer.new Solution();
        int pass = 0, fail = 0;

        // Test 1: Example 1 - [73,74,75,71,69,72,76,73] -> [1,1,4,2,1,1,0,0]
        {
            int[] result = sol.dailyTemperatures(new int[]{73,74,75,71,69,72,76,73});
            if (Arrays.equals(result, new int[]{1,1,4,2,1,1,0,0})) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 2: Example 2 - [30,40,50,60] -> [1,1,1,0]
        {
            int[] result = sol.dailyTemperatures(new int[]{30,40,50,60});
            if (Arrays.equals(result, new int[]{1,1,1,0})) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 3: Example 3 - [30,60,90] -> [1,1,0]
        {
            int[] result = sol.dailyTemperatures(new int[]{30,60,90});
            if (Arrays.equals(result, new int[]{1,1,0})) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 4: Descending - no warmer days -> [0,0,0,0]
        {
            int[] result = sol.dailyTemperatures(new int[]{90,80,70,60});
            if (Arrays.equals(result, new int[]{0,0,0,0})) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 5: Single element -> [0]
        {
            int[] result = sol.dailyTemperatures(new int[]{50});
            if (Arrays.equals(result, new int[]{0})) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 6: All same temperature -> [0,0,0,0]
        {
            int[] result = sol.dailyTemperatures(new int[]{70,70,70,70});
            if (Arrays.equals(result, new int[]{0,0,0,0})) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL - got " + Arrays.toString(result)); }
        }

        // Test 7: Valley then peak - [80,70,60,70,80,90] -> [5,3,1,1,1,0]
        {
            int[] result = sol.dailyTemperatures(new int[]{80,70,60,70,80,90});
            if (Arrays.equals(result, new int[]{5,3,1,1,1,0})) { pass++; System.out.println("Test 7: PASS"); }
            else { fail++; System.out.println("Test 7: FAIL - got " + Arrays.toString(result)); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }
}

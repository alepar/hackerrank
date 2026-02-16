package prep2026.phase1.sliding_window;

import java.util.*;

// https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
public class LongestSubstringWithoutRepeatingCharacters {
    class Solution {
        public int lengthOfLongestSubstring(String s) {
            final int[] lastSeen = new int[256];
            Arrays.fill(lastSeen, -1);

            int l=0, r=0, max=0;

            while(r < s.length()) {
                final char c = s.charAt(r++);
                if (lastSeen[c] >= l) {
                    l = lastSeen[c]+1;
                }

                lastSeen[c] = r-1;

                if (r-l>max) max = r-l;
            }

            return max;
        }

// works, but slow:
/*
        public int lengthOfLongestSubstring(String s) {
            final Map<Character, Integer> freqs = new HashMap<>();
            int l=0, r=0, max=0;

            while (r < s.length()) {
                final char c = s.charAt(r++);
                freqs.merge(c, 1, Integer::sum);

                while (l<r && freqs.get(c) > 1) {
                    freqs.merge(s.charAt(l), -1, Integer::sum);
                    l++;
                }

                if (r-l > max) {
                    max = r-l;
                }
            }

            return max;
        }
*/
    }

    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingCharacters outer = new LongestSubstringWithoutRepeatingCharacters();
        Solution sol = outer.new Solution();
        int pass = 0, fail = 0;

        // Test 1: Example 1 - "abcabcbb" -> 3 ("abc")
        {
            int result = sol.lengthOfLongestSubstring("abcabcbb");
            if (result == 3) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL - got " + result); }
        }

        // Test 2: Example 2 - "bbbbb" -> 1
        {
            int result = sol.lengthOfLongestSubstring("bbbbb");
            if (result == 1) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL - got " + result); }
        }

        // Test 3: Example 3 - "pwwkew" -> 3 ("wke")
        {
            int result = sol.lengthOfLongestSubstring("pwwkew");
            if (result == 3) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL - got " + result); }
        }

        // Test 4: Empty string -> 0
        {
            int result = sol.lengthOfLongestSubstring("");
            if (result == 0) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL - got " + result); }
        }

        // Test 5: All unique - "abcdef" -> 6
        {
            int result = sol.lengthOfLongestSubstring("abcdef");
            if (result == 6) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL - got " + result); }
        }

        // Test 6: Single character - "a" -> 1
        {
            int result = sol.lengthOfLongestSubstring("a");
            if (result == 1) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL - got " + result); }
        }

        // Test 7: Spaces and special chars - "a b c" -> 3
        {
            int result = sol.lengthOfLongestSubstring("a b c");
            if (result == 3) { pass++; System.out.println("Test 7: PASS"); }
            else { fail++; System.out.println("Test 7: FAIL - got " + result); }
        }

        // Test 8: Repeat at the end - "abcda" -> 4
        {
            int result = sol.lengthOfLongestSubstring("abcda");
            if (result == 4) { pass++; System.out.println("Test 8: PASS"); }
            else { fail++; System.out.println("Test 8: FAIL - got " + result); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }
}

package prep2026.phase1.sliding_window;

// https://leetcode.com/problems/minimum-window-substring/description/
public class MinimumWindowSubstring {
    class Solution {
        public String minWindow(String s, String t) {
            final int[] targetFreqs = new int[256];
            int distinct = 0;
            for (int i=0; i<t.length(); i++) {
                final char c = t.charAt(i);
                if (targetFreqs[c] == 0) {
                    distinct++;
                }
                targetFreqs[c]++;
            }

            final int[] currentFreqs = new int[256];
            int satisfied = 0;
            int l=0, r=0;
            String min = null;
            while (r<s.length()) {
                final char c = s.charAt(r++);
                currentFreqs[c]++;
                if (currentFreqs[c] == targetFreqs[c]) satisfied++;

                while (satisfied == distinct) {
                    if (min == null || r-l < min.length()) {
                        min = s.substring(l, r);
                    }

                    final char cr = s.charAt(l++);
                    if (currentFreqs[cr] == targetFreqs[cr]) satisfied--;
                    currentFreqs[cr]--;
                }
            }

            if (min == null) return "";
            return min;
        }
    }

    public static void main(String[] args) {
        MinimumWindowSubstring outer = new MinimumWindowSubstring();
        Solution sol = outer.new Solution();
        int pass = 0, fail = 0;

        // Test 1: Example 1 - s="ADOBECODEBANC", t="ABC" -> "BANC"
        {
            String result = sol.minWindow("ADOBECODEBANC", "ABC");
            if ("BANC".equals(result)) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL - got \"" + result + "\""); }
        }

        // Test 2: Example 2 - s="a", t="a" -> "a"
        {
            String result = sol.minWindow("a", "a");
            if ("a".equals(result)) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL - got \"" + result + "\""); }
        }

        // Test 3: Example 3 - s="a", t="aa" -> "" (not enough chars)
        {
            String result = sol.minWindow("a", "aa");
            if ("".equals(result)) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL - got \"" + result + "\""); }
        }

        // Test 4: No valid window - s="abc", t="d" -> ""
        {
            String result = sol.minWindow("abc", "d");
            if ("".equals(result)) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL - got \"" + result + "\""); }
        }

        // Test 5: Entire string is the window - s="abc", t="abc" -> "abc"
        {
            String result = sol.minWindow("abc", "abc");
            if ("abc".equals(result)) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL - got \"" + result + "\""); }
        }

        // Test 6: Duplicate chars in t - s="aabbc", t="abc" -> "abbc"
        {
            String result = sol.minWindow("aabbc", "abc");
            if ("abbc".equals(result)) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL - got \"" + result + "\""); }
        }

        // Test 7: t has repeated chars - s="aaabbc", t="aab" -> "aab"
        {
            String result = sol.minWindow("aaabbc", "aab");
            if ("aab".equals(result)) { pass++; System.out.println("Test 7: PASS"); }
            else { fail++; System.out.println("Test 7: FAIL - got \"" + result + "\""); }
        }

        // Test 8: Window at the start - s="abcd", t="ab" -> "ab"
        {
            String result = sol.minWindow("abcd", "ab");
            if ("ab".equals(result)) { pass++; System.out.println("Test 8: PASS"); }
            else { fail++; System.out.println("Test 8: FAIL - got \"" + result + "\""); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }
}

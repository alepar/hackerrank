package prep2026.phase1.arrays_hashing;

import java.util.*;
import java.util.stream.Collectors;

// https://leetcode.com/problems/group-anagrams/description/
public class GroupAnagrams {
    class Solution {
        public List<List<String>> groupAnagrams(String[] strs) {
            final Map<String, List<String>> groups = new HashMap<>();

            for (var s: strs) {
                final char[] chars = s.toCharArray();
                Arrays.sort(chars);
                final String key = new String(chars);

                groups.computeIfAbsent(key, (String k) -> new ArrayList<>());
                groups.get(key).add(s);
            }

            return groups.values().stream().toList();
        }
    }

    public static void main(String[] args) {
        GroupAnagrams outer = new GroupAnagrams();
        Solution sol = outer.new Solution();
        int pass = 0, fail = 0;

        // Test 1: Example 1
        {
            List<List<String>> result = sol.groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"});
            List<Set<String>> expected = List.of(
                Set.of("eat","tea","ate"),
                Set.of("tan","nat"),
                Set.of("bat")
            );
            if (matches(result, expected)) { pass++; System.out.println("Test 1: PASS"); }
            else { fail++; System.out.println("Test 1: FAIL - got " + result); }
        }

        // Test 2: Example 2 - single empty string
        {
            List<List<String>> result = sol.groupAnagrams(new String[]{""});
            List<Set<String>> expected = List.of(Set.of(""));
            if (matches(result, expected)) { pass++; System.out.println("Test 2: PASS"); }
            else { fail++; System.out.println("Test 2: FAIL - got " + result); }
        }

        // Test 3: Example 3 - single element
        {
            List<List<String>> result = sol.groupAnagrams(new String[]{"a"});
            List<Set<String>> expected = List.of(Set.of("a"));
            if (matches(result, expected)) { pass++; System.out.println("Test 3: PASS"); }
            else { fail++; System.out.println("Test 3: FAIL - got " + result); }
        }

        // Test 4: No anagrams
        {
            List<List<String>> result = sol.groupAnagrams(new String[]{"abc","def","ghi"});
            List<Set<String>> expected = List.of(Set.of("abc"), Set.of("def"), Set.of("ghi"));
            if (matches(result, expected)) { pass++; System.out.println("Test 4: PASS"); }
            else { fail++; System.out.println("Test 4: FAIL - got " + result); }
        }

        // Test 5: All anagrams of each other
        {
            List<List<String>> result = sol.groupAnagrams(new String[]{"abc","bca","cab"});
            List<Set<String>> expected = List.of(Set.of("abc","bca","cab"));
            if (matches(result, expected)) { pass++; System.out.println("Test 5: PASS"); }
            else { fail++; System.out.println("Test 5: FAIL - got " + result); }
        }

        // Test 6: Multiple empty strings
        {
            List<List<String>> result = sol.groupAnagrams(new String[]{"",""});
            boolean ok = result.size() == 1 && result.get(0).size() == 2
                && result.get(0).get(0).isEmpty() && result.get(0).get(1).isEmpty();
            if (ok) { pass++; System.out.println("Test 6: PASS"); }
            else { fail++; System.out.println("Test 6: FAIL - got " + result); }
        }

        // Test 7: Duplicate words
        {
            List<List<String>> result = sol.groupAnagrams(new String[]{"eat","eat"});
            boolean ok = result.size() == 1 && result.get(0).size() == 2;
            if (ok) { pass++; System.out.println("Test 7: PASS"); }
            else { fail++; System.out.println("Test 7: FAIL - got " + result); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }

    private static boolean matches(List<List<String>> actual, List<Set<String>> expected) {
        if (actual.size() != expected.size()) return false;
        List<Set<String>> actualSets = actual.stream()
            .map(HashSet::new)
            .collect(Collectors.toList());
        for (Set<String> exp : expected) {
            if (!actualSets.remove(exp)) return false;
        }
        return actualSets.isEmpty();
    }
}

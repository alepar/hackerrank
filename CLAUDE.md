# Hackerrank / LeetCode Practice Repo

## LeetCode Problem Test Harness Convention

When creating a test harness + solution stub for a LeetCode problem in `src/prep2026/`:

### File structure
```java
package prep2026.phase1.<topic>;

import java.util.*;

// https://leetcode.com/problems/<problem-slug>/description/
public class ProblemName {
    class Solution {
        // Method stub matching LeetCode signature exactly
        // Return a default/empty value (e.g., return 0; return new int[]{};)
    }

    public static void main(String[] args) {
        ProblemName outer = new ProblemName();
        Solution sol = outer.new Solution();
        int pass = 0, fail = 0;

        // Test N: Description - input -> expected
        {
            var result = sol.method(...);
            if (/* check */) { pass++; System.out.println("Test N: PASS"); }
            else { fail++; System.out.println("Test N: FAIL - got " + result); }
        }

        System.out.println("\n" + pass + "/" + (pass+fail) + " tests passed");
    }

    // Optional: helper methods for comparison (matchesUnordered, matches, etc.)
}
```

### Test case selection (aim for 6-8 tests)
1. **LeetCode examples first** (Tests 1-2 or 1-3) - copy directly from the problem
2. **Edge cases** - minimum input size, empty/single element
3. **Problem-specific tricky cases** - zeros for product problems, duplicates, negative numbers
4. **Boundary cases** - first/last elements, adjacent elements, all same values

### Key conventions
- Inner `class Solution` (non-static) matches LeetCode's class structure
- Instantiate via `outer.new Solution()` pattern
- Each test in its own `{}` block for variable scoping
- Test comments: `// Test N: Brief description - input -> expected`
- Single-line if/else for pass/fail to keep tests compact
- Use `Arrays.equals` for ordered arrays, add `matchesUnordered` helper for unordered results
- No test framework dependencies - just `main()` with stdout

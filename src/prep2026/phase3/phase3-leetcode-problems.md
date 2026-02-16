# Phase 3: Dynamic Programming & Optimization - LeetCode Links

Problems for Phase 3 of SDE interview prep. This phase focuses on dynamic programming - the most pattern-driven topic in interviews.

---

## 13. 1-D Dynamic Programming

- **Climbing Stairs** - [LeetCode #70](https://leetcode.com/problems/climbing-stairs/) (Easy)
- **House Robber** - [LeetCode #198](https://leetcode.com/problems/house-robber/) (Medium)
- **Coin Change** - [LeetCode #322](https://leetcode.com/problems/coin-change/) (Medium)
- **Longest Increasing Subsequence** - [LeetCode #300](https://leetcode.com/problems/longest-increasing-subsequence/) (Medium)
- **Word Break** - [LeetCode #139](https://leetcode.com/problems/word-break/) (Medium)
- **Decode Ways** - [LeetCode #91](https://leetcode.com/problems/decode-ways/) (Medium)

**Key concepts:** Climbing stairs pattern, house robber pattern, coin change, LIS  
**Approach:** Define state → recurrence → base case. Start with top-down (memoization), then convert to bottom-up if needed.

---

## 14. 2-D Dynamic Programming

- **Unique Paths** - [LeetCode #62](https://leetcode.com/problems/unique-paths/) (Medium)
- **Longest Common Subsequence** - [LeetCode #1143](https://leetcode.com/problems/longest-common-subsequence/) (Medium)
- **Edit Distance** - [LeetCode #72](https://leetcode.com/problems/edit-distance/) (Medium)
- **Interleaving String** - [LeetCode #97](https://leetcode.com/problems/interleaving-string/) (Medium)
- **Longest Palindromic Substring** - [LeetCode #5](https://leetcode.com/problems/longest-palindromic-substring/) (Medium)

**Key concepts:** Grid traversal, string matching (LCS, edit distance)  
**When to use:** Two sequences, 2D grid, choices along two dimensions

---

## Summary

**Total Problems:** 11  
**Breakdown:**
- Easy: 1 problem
- Medium: 10 problems
- Hard: 0 problems

**Suggested Approach:**
- DP is pattern-driven - learn the patterns, not individual problems
- For each problem:
  1. **Define the state** - What does dp[i] or dp[i][j] represent?
  2. **Find the recurrence** - How does current state relate to previous states?
  3. **Identify base cases** - What are the initial/trivial cases?
  4. **Determine iteration order** - Which direction to fill the DP table?
- Start with **top-down (memoization)** - easier to reason about
- Convert to **bottom-up (tabulation)** for better space/time optimization
- Draw the decision tree or DP table to visualize

**Duration:** Approximately 1.5 weeks if solving 1-2 problems per day (DP takes longer!)

**Key Patterns:**

### 1-D DP Patterns:
- **Fibonacci-like:** dp[i] depends on dp[i-1], dp[i-2]
- **Decision:** Include/exclude current element (House Robber)
- **Unbounded choices:** Coin Change (can reuse elements)
- **Bounded choices:** Coin Change II (use each once)

### 2-D DP Patterns:
- **Grid paths:** dp[i][j] = dp[i-1][j] + dp[i][j-1]
- **String matching:** Compare s[i] with t[j]
- **Subsequence:** LCS, Edit Distance
- **Substring:** Longest Palindromic Substring

**Common Mistakes to Avoid:**
- Not clearly defining what the DP state represents
- Forgetting to handle base cases
- Off-by-one errors in indices
- Not considering all possible transitions
- Premature optimization (start with memoization, not tabulation)

**Pro Tips:**
- If you can solve the brute force recursively, you're 80% done
- Memoization = recursion + cache (usually easier to code)
- Tabulation = iteration + table (usually faster/less space)
- Space optimization often possible: if dp[i] only needs dp[i-1], use 2 variables instead of array

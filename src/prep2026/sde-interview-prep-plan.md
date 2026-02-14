# SDE Interview Prep Plan

## Overview

A phased, structured plan for brushing up on coding interview skills. Designed to start with fundamentals to shake off rust, then systematically cover all major topic areas that appear in SDE interviews at top companies.

**Primary practice resource:** [NeetCode 150](https://neetcode.io/practice/practice/neetcode150) — a curated, ordered list of 150 LeetCode problems covering all key patterns. Free video solutions for each. If you want even more beginner-friendly ramp-up, start with the [NeetCode 250](https://neetcode.io/practice/practice/neetcode250) which adds easier warm-up problems per topic.

**Supplemental:** [Tech Interview Handbook](https://www.techinterviewhandbook.org/algorithms/study-cheatsheet/) for quick concept review per topic.

---

## Phase 0: Warm-Up — Shake Off the Rust (3–5 days)

**Goal:** Get comfortable writing code again. Fluency with your language's stdlib, basic syntax, and thinking through simple problems out loud.

Pick your interview language (likely Java or Python given your background) and do these:

- **Basic iteration & string manipulation** — reverse a string, check palindrome, count character frequencies
- **Simple recursion** — factorial, fibonacci, power function, flatten a nested list
- **Basic data structure usage** — use a HashMap/dict to solve Two Sum, use a Set for dedup, iterate a linked list
- **Sorting** — implement merge sort and quicksort from scratch (these use recursion + array manipulation and are a great warm-up)

Don't look at LeetCode yet. Just write code in an editor, run it, debug it. The point is to get your fingers and brain back in sync.

**Deliverable:** You can write a clean recursive function, iterate arrays/strings, and use hash maps without looking anything up.

---

## Phase 1: Foundations — Core Patterns (Weeks 1–3)

These are the highest-frequency interview topics and the building blocks for everything else. Do them in this order — each builds on the previous.

### 1. Arrays & Hashing
- Hash maps, hash sets, frequency counting
- Two Sum pattern, grouping, encoding/decoding
- **Key problems:** Two Sum, Group Anagrams, Top K Frequent Elements, Product of Array Except Self
- **Complexity:** Know O(1) average lookup, O(n) space tradeoff

### 2. Two Pointers
- Left/right pointers on sorted arrays, fast/slow pointers
- **Key problems:** Valid Palindrome, Two Sum II, 3Sum, Container With Most Water
- **When to use:** Sorted input, searching for pairs, reducing O(n²) to O(n)

### 3. Sliding Window
- Fixed and variable-length windows on arrays/strings
- **Key problems:** Best Time to Buy and Sell Stock, Longest Substring Without Repeating Characters, Minimum Window Substring
- **When to use:** Contiguous subarray/substring optimization

### 4. Stack
- LIFO for matching, parsing, monotonic stacks
- **Key problems:** Valid Parentheses, Min Stack, Evaluate Reverse Polish Notation, Daily Temperatures
- **When to use:** Nested structures, nearest greater/smaller element

### 5. Binary Search
- Classic search, search on answer space, rotated arrays
- **Key problems:** Binary Search, Search in Rotated Sorted Array, Find Minimum in Rotated Sorted Array, Koko Eating Bananas
- **When to use:** Sorted data, monotonic condition, minimizing/maximizing with a check function

### 6. Linked List
- Reversal, fast/slow pointers (cycle detection), merge
- **Key problems:** Reverse Linked List, Merge Two Sorted Lists, Linked List Cycle, LRU Cache
- **When to use:** In-place node manipulation, detecting cycles

---

## Phase 2: Trees, Graphs & Recursion (Weeks 3–5)

This is where recursion gets serious. Trees and graphs are extremely common in interviews.

### 7. Trees (Binary Trees & BSTs)
- DFS (preorder, inorder, postorder), BFS (level-order)
- BST properties (inorder = sorted), validate BST
- **Key problems:** Invert Binary Tree, Maximum Depth, Same Tree, Lowest Common Ancestor, Binary Tree Level Order Traversal, Validate BST, Kth Smallest Element in BST
- **Pattern:** Most tree problems are "recursive function that returns something from children"

### 8. Heap / Priority Queue
- Min-heap, max-heap, top-K patterns
- **Key problems:** Kth Largest Element, Last Stone Weight, K Closest Points to Origin, Find Median from Data Stream, Merge K Sorted Lists
- **When to use:** Repeatedly finding min/max, streaming data, merging sorted sequences

### 9. Backtracking
- Generate all combinations/permutations/subsets, constraint satisfaction
- **Key problems:** Subsets, Combination Sum, Permutations, Word Search, N-Queens
- **Pattern:** Choose → Explore → Unchoose. Draw the decision tree.

### 10. Tries
- Prefix trees for string problems
- **Key problems:** Implement Trie, Design Add and Search Words, Word Search II
- **When to use:** Prefix matching, autocomplete, word dictionaries

### 11. Graphs
- BFS, DFS on adjacency lists/matrices, connected components
- Topological sort (course scheduling)
- **Key problems:** Number of Islands, Clone Graph, Pacific Atlantic Water Flow, Course Schedule, Word Ladder
- **When to use:** Grid traversal, dependency ordering, shortest path in unweighted graphs

### 12. Advanced Graphs
- Dijkstra's (weighted shortest path), Union-Find, Prim's/Kruskal's (MST)
- **Key problems:** Network Delay Time, Cheapest Flights Within K Stops, Min Cost to Connect All Points, Swim in Rising Water
- **When to use:** Weighted edges, dynamic connectivity, minimum spanning tree

---

## Phase 3: Dynamic Programming & Optimization (Weeks 5–7)

DP is the most feared topic but also pattern-driven. Learn the patterns, not individual problems.

### 13. 1-D Dynamic Programming
- Climbing stairs pattern, house robber pattern, coin change, LIS
- **Key problems:** Climbing Stairs, House Robber, Coin Change, Longest Increasing Subsequence, Word Break, Decode Ways
- **Approach:** Define state → recurrence → base case. Start with top-down (memoization), then convert to bottom-up if needed.

### 14. 2-D Dynamic Programming
- Grid traversal, string matching (LCS, edit distance)
- **Key problems:** Unique Paths, Longest Common Subsequence, Edit Distance, Interleaving String, Longest Palindromic Substring
- **When to use:** Two sequences, 2D grid, choices along two dimensions

---

## Phase 4: Remaining Patterns (Weeks 7–8)

These come up less frequently but can be the difference between solving and not solving a problem.

### 15. Greedy
- Local optimal → global optimal, interval scheduling, jump game
- **Key problems:** Maximum Subarray, Jump Game, Gas Station, Hand of Straights, Partition Labels
- **When to use:** Sorting + greedy choice, interval problems, "can you do this without DP?"

### 16. Intervals
- Merge, insert, intersection of intervals
- **Key problems:** Merge Intervals, Insert Interval, Non-overlapping Intervals, Meeting Rooms I & II
- **Pattern:** Sort by start time, then sweep

### 17. Math & Geometry
- Modular arithmetic, GCD, matrix operations, number theory basics
- **Key problems:** Rotate Image, Spiral Matrix, Set Matrix Zeroes, Pow(x,n), Multiply Strings
- **When to use:** Matrix manipulation, number-based puzzles

### 18. Bit Manipulation
- XOR tricks, counting bits, single number
- **Key problems:** Single Number, Number of 1 Bits, Counting Bits, Reverse Bits, Sum of Two Integers
- **When to use:** Space optimization, XOR for finding unique elements, powers of 2

---

## Cross-Cutting Skills (Ongoing)

These aren't separate topics but should be practiced throughout every phase:

**Big-O Analysis** — For every solution, state time and space complexity. Know the common complexities: O(1), O(log n), O(n), O(n log n), O(n²), O(2ⁿ), O(n!). Understand amortized analysis for things like dynamic arrays and hash tables.

**Problem-Solving Framework** — For every problem, practice this flow:
1. Clarify constraints and edge cases (ask questions!)
2. Think out loud — walk through examples
3. Identify the pattern (which of the 18 categories?)
4. Code the brute force, state its complexity
5. Optimize, explain the tradeoff
6. Test with edge cases

**Communication** — Practice explaining your thought process out loud, even when solving alone. Interviewers care about your reasoning as much as the code.

---

## Suggested Schedule Summary

| Phase | Topics | Duration | Problems |
|-------|--------|----------|----------|
| Phase 0 | Warm-up: recursion, strings, hash maps, sorting | 3–5 days | Freeform coding |
| Phase 1 | Arrays, Two Pointers, Sliding Window, Stack, Binary Search, Linked List | ~2.5 weeks | ~44 from NeetCode 150 |
| Phase 2 | Trees, Heaps, Backtracking, Tries, Graphs, Adv. Graphs | ~2 weeks | ~54 from NeetCode 150 |
| Phase 3 | 1-D DP, 2-D DP | ~1.5 weeks | ~22 from NeetCode 150 |
| Phase 4 | Greedy, Intervals, Math, Bit Manipulation | ~1 week | ~30 from NeetCode 150 |
| **Total** | **All 18 topic categories** | **~8 weeks** | **150 problems** |

If you're short on time, prioritize Phases 0–2. Arrays/Hashing, Trees, and Graphs alone cover the majority of what you'll see.

---

## System Design (If Applicable)

For senior SDE roles, you'll also need system design. This is a separate track but worth noting:

- Start with [NeetCode System Design for Beginners](https://neetcode.io/courses/system-design-for-beginners/0) (free)
- Key topics: load balancing, caching, databases (SQL vs NoSQL), message queues, consistent hashing, rate limiting, CAP theorem
- Practice designing: URL shortener, chat system, news feed, notification system

---

## Tips

- **Don't just read solutions.** If stuck after 20 minutes, read the approach (not the code), then implement yourself.
- **Re-solve problems.** Come back to problems you found hard after 3–7 days. Spaced repetition is powerful.
- **Track your progress.** Use NeetCode's built-in tracker or a spreadsheet.
- **Mock interviews.** Do at least 2–3 timed mock interviews (with a friend or on platforms like Pramp/interviewing.io) before real interviews.

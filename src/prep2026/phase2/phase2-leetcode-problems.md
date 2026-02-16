# Phase 2: Trees, Graphs & Recursion - LeetCode Links

Problems for Phase 2 of SDE interview prep. This phase focuses on recursion-heavy topics: trees, graphs, backtracking, heaps, and tries.

---

## 7. Trees (Binary Trees & BSTs)

- **Invert Binary Tree** - [LeetCode #226](https://leetcode.com/problems/invert-binary-tree/) (Easy)
- **Maximum Depth of Binary Tree** - [LeetCode #104](https://leetcode.com/problems/maximum-depth-of-binary-tree/) (Easy)
- **Same Tree** - [LeetCode #100](https://leetcode.com/problems/same-tree/) (Easy)
- **Lowest Common Ancestor of a Binary Search Tree** - [LeetCode #235](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/) (Medium)
- **Binary Tree Level Order Traversal** - [LeetCode #102](https://leetcode.com/problems/binary-tree-level-order-traversal/) (Medium)
- **Validate Binary Search Tree** - [LeetCode #98](https://leetcode.com/problems/validate-binary-search-tree/) (Medium)
- **Kth Smallest Element in a BST** - [LeetCode #230](https://leetcode.com/problems/kth-smallest-element-in-a-bst/) (Medium)

**Key concepts:** DFS (preorder, inorder, postorder), BFS (level-order), BST properties  
**Pattern:** Most tree problems are "recursive function that returns something from children"

---

## 8. Heap / Priority Queue

- **Kth Largest Element in an Array** - [LeetCode #215](https://leetcode.com/problems/kth-largest-element-in-an-array/) (Medium)
- **Last Stone Weight** - [LeetCode #1046](https://leetcode.com/problems/last-stone-weight/) (Easy)
- **K Closest Points to Origin** - [LeetCode #973](https://leetcode.com/problems/k-closest-points-to-origin/) (Medium)
- **Find Median from Data Stream** - [LeetCode #295](https://leetcode.com/problems/find-median-from-data-stream/) (Hard)
- **Merge k Sorted Lists** - [LeetCode #23](https://leetcode.com/problems/merge-k-sorted-lists/) (Hard)

**Key concepts:** Min-heap, max-heap, top-K patterns  
**When to use:** Repeatedly finding min/max, streaming data, merging sorted sequences

---

## 9. Backtracking

- **Subsets** - [LeetCode #78](https://leetcode.com/problems/subsets/) (Medium)
- **Combination Sum** - [LeetCode #39](https://leetcode.com/problems/combination-sum/) (Medium)
- **Permutations** - [LeetCode #46](https://leetcode.com/problems/permutations/) (Medium)
- **Word Search** - [LeetCode #79](https://leetcode.com/problems/word-search/) (Medium)
- **N-Queens** - [LeetCode #51](https://leetcode.com/problems/n-queens/) (Hard)

**Key concepts:** Generate all combinations/permutations/subsets, constraint satisfaction  
**Pattern:** Choose → Explore → Unchoose. Draw the decision tree.

---

## 10. Tries

- **Implement Trie (Prefix Tree)** - [LeetCode #208](https://leetcode.com/problems/implement-trie-prefix-tree/) (Medium)
- **Design Add and Search Words Data Structure** - [LeetCode #211](https://leetcode.com/problems/design-add-and-search-words-data-structure/) (Medium)
- **Word Search II** - [LeetCode #212](https://leetcode.com/problems/word-search-ii/) (Hard)

**Key concepts:** Prefix trees for string problems  
**When to use:** Prefix matching, autocomplete, word dictionaries

---

## 11. Graphs

- **Number of Islands** - [LeetCode #200](https://leetcode.com/problems/number-of-islands/) (Medium)
- **Clone Graph** - [LeetCode #133](https://leetcode.com/problems/clone-graph/) (Medium)
- **Pacific Atlantic Water Flow** - [LeetCode #417](https://leetcode.com/problems/pacific-atlantic-water-flow/) (Medium)
- **Course Schedule** - [LeetCode #207](https://leetcode.com/problems/course-schedule/) (Medium)
- **Word Ladder** - [LeetCode #127](https://leetcode.com/problems/word-ladder/) (Hard)

**Key concepts:** BFS, DFS on adjacency lists/matrices, connected components, topological sort  
**When to use:** Grid traversal, dependency ordering, shortest path in unweighted graphs

---

## 12. Advanced Graphs

- **Network Delay Time** - [LeetCode #743](https://leetcode.com/problems/network-delay-time/) (Medium)
- **Cheapest Flights Within K Stops** - [LeetCode #787](https://leetcode.com/problems/cheapest-flights-within-k-stops/) (Medium)
- **Min Cost to Connect All Points** - [LeetCode #1584](https://leetcode.com/problems/min-cost-to-connect-all-points/) (Medium)
- **Swim in Rising Water** - [LeetCode #778](https://leetcode.com/problems/swim-in-rising-water/) (Hard)

**Key concepts:** Dijkstra's (weighted shortest path), Union-Find, Prim's/Kruskal's (MST)  
**When to use:** Weighted edges, dynamic connectivity, minimum spanning tree

---

## Summary

**Total Problems:** 29  
**Breakdown:**
- Easy: 3 problems
- Medium: 20 problems
- Hard: 6 problems

**Suggested Approach:**
- Trees and Heaps are foundational - master these first
- Backtracking requires drawing decision trees - visualize before coding
- Graph problems often need both BFS and DFS - know when to use each
- Advanced Graphs introduce algorithms (Dijkstra, Union-Find) - learn the template

**Duration:** Approximately 2 weeks if solving 2-3 problems per day

**Key Skills:**
- Recursive thinking and base cases
- Graph traversal (BFS vs DFS trade-offs)
- Backtracking template (choose, explore, unchoose)
- Heap operations and when to use min vs max heap

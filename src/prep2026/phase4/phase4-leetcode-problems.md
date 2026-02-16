# Phase 4: Remaining Patterns - LeetCode Links

Problems for Phase 4 of SDE interview prep. These patterns come up less frequently but are important for comprehensive coverage.

---

## 15. Greedy

- **Maximum Subarray** - [LeetCode #53](https://leetcode.com/problems/maximum-subarray/) (Medium)
- **Jump Game** - [LeetCode #55](https://leetcode.com/problems/jump-game/) (Medium)
- **Gas Station** - [LeetCode #134](https://leetcode.com/problems/gas-station/) (Medium)
- **Hand of Straights** - [LeetCode #846](https://leetcode.com/problems/hand-of-straights/) (Medium)
- **Partition Labels** - [LeetCode #763](https://leetcode.com/problems/partition-labels/) (Medium)

**Key concepts:** Local optimal → global optimal, interval scheduling, jump game  
**When to use:** Sorting + greedy choice, interval problems, "can you do this without DP?"

---

## 16. Intervals

- **Merge Intervals** - [LeetCode #56](https://leetcode.com/problems/merge-intervals/) (Medium)
- **Insert Interval** - [LeetCode #57](https://leetcode.com/problems/insert-interval/) (Medium)
- **Non-overlapping Intervals** - [LeetCode #435](https://leetcode.com/problems/non-overlapping-intervals/) (Medium)
- **Meeting Rooms** - [LeetCode #252](https://leetcode.com/problems/meeting-rooms/) (Easy) - *LeetCode Premium*
- **Meeting Rooms II** - [LeetCode #253](https://leetcode.com/problems/meeting-rooms-ii/) (Medium) - *LeetCode Premium*

**Key concepts:** Merge, insert, intersection of intervals  
**Pattern:** Sort by start time, then sweep  
**Note:** Meeting Rooms problems require LeetCode Premium. Alternative free problems: [LintCode 920](https://www.lintcode.com/problem/920/) and [LintCode 919](https://www.lintcode.com/problem/919/)

---

## 17. Math & Geometry

- **Rotate Image** - [LeetCode #48](https://leetcode.com/problems/rotate-image/) (Medium)
- **Spiral Matrix** - [LeetCode #54](https://leetcode.com/problems/spiral-matrix/) (Medium)
- **Set Matrix Zeroes** - [LeetCode #73](https://leetcode.com/problems/set-matrix-zeroes/) (Medium)
- **Pow(x, n)** - [LeetCode #50](https://leetcode.com/problems/powx-n/) (Medium)
- **Multiply Strings** - [LeetCode #43](https://leetcode.com/problems/multiply-strings/) (Medium)

**Key concepts:** Modular arithmetic, GCD, matrix operations, number theory basics  
**When to use:** Matrix manipulation, number-based puzzles

---

## 18. Bit Manipulation

- **Single Number** - [LeetCode #136](https://leetcode.com/problems/single-number/) (Easy)
- **Number of 1 Bits** - [LeetCode #191](https://leetcode.com/problems/number-of-1-bits/) (Easy)
- **Counting Bits** - [LeetCode #338](https://leetcode.com/problems/counting-bits/) (Easy)
- **Reverse Bits** - [LeetCode #190](https://leetcode.com/problems/reverse-bits/) (Easy)
- **Sum of Two Integers** - [LeetCode #371](https://leetcode.com/problems/sum-of-two-integers/) (Medium)

**Key concepts:** XOR tricks, counting bits, single number  
**When to use:** Space optimization, XOR for finding unique elements, powers of 2

---

## Summary

**Total Problems:** 20  
**Breakdown:**
- Easy: 5 problems
- Medium: 15 problems
- Hard: 0 problems
- Premium: 2 problems (Meeting Rooms I & II)

**Suggested Approach:**
- These topics are more niche but still important
- Greedy problems: prove why greedy works (or use DP if greedy fails)
- Intervals: always sort first, then decide how to merge/process
- Math/Geometry: practice matrix manipulation patterns
- Bit manipulation: master XOR, AND, OR, bit shifting operations

**Duration:** Approximately 1 week if solving 2-3 problems per day

**Key Patterns:**

### Greedy Patterns:
- **Proof by exchange:** Show that swapping choices doesn't improve solution
- **Earliest deadline first:** Interval scheduling
- **Furthest reach:** Jump game variants
- **Local maximum:** Often combined with sorting

### Interval Patterns:
- **Merge overlapping:** Sort by start, merge consecutive
- **Count overlaps:** Sweep line algorithm or heap
- **Insert new interval:** Find position, merge neighbors
- **Remove minimum:** Greedy choice based on end time

### Math & Geometry:
- **Matrix rotation:** Transpose + reverse
- **Spiral traversal:** Maintain boundaries, shrink inward
- **In-place modification:** Use matrix itself to track state
- **Binary exponentiation:** Divide and conquer for pow()

### Bit Manipulation Tricks:
- **XOR:** `a ^ a = 0`, `a ^ 0 = a` (find unique element)
- **AND:** Check if bit is set: `n & (1 << i)`
- **OR:** Set bit: `n | (1 << i)`
- **NOT + AND:** Clear bit: `n & ~(1 << i)`
- **Count bits:** Brian Kernighan's algorithm: `n & (n-1)` clears lowest set bit
- **Check power of 2:** `n & (n-1) == 0`

**Common Gotchas:**
- Greedy: Make sure greedy choice is actually optimal (prove it or test edge cases)
- Intervals: Edge cases with touching intervals (do [1,2] and [2,3] overlap?)
- Bit manipulation: Sign extension, overflow when left shifting
- Math: Integer overflow, negative numbers, division by zero

**Pro Tips:**
- For greedy, if you can't prove it works, it might need DP instead
- Most interval problems are solved with sorting + one pass
- Bit manipulation problems often have O(1) space solutions
- Math problems may require simulating the process step-by-step

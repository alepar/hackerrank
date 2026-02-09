package leetcode.word_ladder_ii

import java.util.Deque
import java.util.LinkedList
import java.util.NavigableMap
import java.util.TreeMap
import kotlin.collections.emptyList

// https://leetcode.com/problems/word-ladder-ii/
//
// Given two words, beginWord and endWord, and a dictionary wordList, return all the shortest
// transformation sequences from beginWord to endWord, or an empty list if no such sequence exists.
// Each sequence consists of words where:
//   - Every adjacent pair of words differs by a single letter.
//   - Every word in the sequence is in wordList (beginWord does not need to be in wordList).
//   - endWord must be in wordList.
//
// Example 1:
//   beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
//   Output: [["hit","hot","dot","dog","cog"], ["hit","hot","lot","log","cog"]]
//
// Example 2:
//   beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
//   Output: []  (endWord "cog" is not in wordList)
class PrefixNode(parent : PrefixNode?) {

    val children: NavigableMap<Char, PrefixNode> = TreeMap()
    val parent : PrefixNode? = parent
    var final = false

    fun addWord(word: String) {
        var cur = this
        for (c in word) {
            cur = cur.children.getOrPut(c) { PrefixNode(cur) }
        }
        cur.final = true
    }

    fun findAdjacent(word: String, d: Int) : List<String> {
        var cur: PrefixNode? = this
        var curd = 0
        val prefixList = LinkedList<Char>()
        var startFrom = 'a'
        val result = ArrayList<String>()

        outer@
        while(true) {
            cur!!
            if (cur == this && cur.children.tailMap(startFrom).isEmpty()) break

            if (cur.final && prefixList.size == word.length) {
                result.add(prefixList.joinToString(""))
            } else {
                for (e in cur.children.tailMap(startFrom)) {
                    if (e.key == word[prefixList.size] || curd+1<=d) {
                        if (e.key != word[prefixList.size]) { curd++ }

                        prefixList.add(e.key)
                        cur=e.value
                        startFrom = 'a'

                        continue@outer
                    }
                }
            }

            cur = cur!!.parent
            val removed = prefixList.removeLast()
            if (removed != word[prefixList.size]) { curd-- }
            startFrom = removed+1
        }

        return result
    }

}

class Graph {

    val edges = HashMap<String, MutableSet<String>>()

    fun addEdge(l: String, r: String) {
        edges.getOrPut(l, { mutableSetOf() }).add(r)
        edges.getOrPut(r, { mutableSetOf() }).add(l)
    }

    fun getAdjacent(cur: String) : Set<String> {
        return edges[cur] ?: emptySet()
    }

    fun findPathsBack(word: String, visited: MutableMap<String, Int>) : List<List<String>> {
        val d = visited[word]!!
        if (d == 0) {
            return listOf(listOf(word))
        }

        val paths = ArrayList<List<String>>()

        for (prev in this.getAdjacent(word)) {
            if (visited[prev] == d-1) {
                for (subpath in this.findPathsBack(prev, visited)) {
                    paths.add(subpath.toMutableList() + listOf(word))
                }
            }
        }

        return paths
    }

}

class Solution {

    fun findLadders(beginWord: String, endWord: String, wordList: List<String>): List<List<String>> {
        val prefixRoot = PrefixNode(null)
        val wordGraph = Graph()

        prefixRoot.addWord(beginWord)
        for (word in wordList) {
            for (adjacent in prefixRoot.findAdjacent(word, 1)) {
                wordGraph.addEdge(adjacent, word)
            }
            prefixRoot.addWord(word)
        }


        val visited = mutableMapOf<String, Int>()
        visited[beginWord] = 0

        val queue : Deque<String> = LinkedList()
        queue.add(beginWord)

        while (queue.isNotEmpty()) {
            val cur = queue.removeFirst()
            if (cur == endWord) {
                break
            }

            val depth = visited[cur]!!
            for (next in wordGraph.getAdjacent(cur)) {
                if (visited.contains(next)) continue

                visited[next] = depth + 1
                queue.addLast(next)
            }
        }

        if (visited[endWord] == null) {
            return emptyList()
        }


        return wordGraph.findPathsBack(endWord, visited)
    }
}

fun main() {
    val s = Solution()

    // Example 1: two shortest paths of length 5
    assertEquals(1,
        listOf(listOf("hit","hot","dot","dog","cog"), listOf("hit","hot","lot","log","cog")),
        s.findLadders("hit", "cog", listOf("hot","dot","dog","lot","log","cog")))

    // Example 2: endWord not in wordList → no path
    assertEquals(2,
        listOf(),
        s.findLadders("hit", "cog", listOf("hot","dot","dog","lot","log")))

    // Test 3: direct single-step transformation
    assertEquals(3,
        listOf(listOf("a", "b")),
        s.findLadders("a", "b", listOf("b")))

    // Test 4: endWord in list but unreachable (2 diffs, no intermediate)
    assertEquals(4,
        listOf(),
        s.findLadders("ab", "cd", listOf("cd")))

    // Test 5: single path, linear chain of 4
    // cat→cot→dot→dog
    assertEquals(5,
        listOf(listOf("cat","cot","dot","dog")),
        s.findLadders("cat", "dog", listOf("cot","dot","dog")))

    // Test 6: diamond — two paths converge at "tex" then reach "tax"
    // red→ted→tex→tax
    // red→rex→tex→tax
    assertEquals(6,
        listOf(listOf("red","ted","tex","tax"), listOf("red","ted","tad","tax"), listOf("red","rex","tex","tax")),
        s.findLadders("red", "tax", listOf("ted","tex","tax","tad","rex")))

    // Test 7: four shortest paths through a 3-letter combinatorial fan
    // aaa→aac→acc→ccc
    // aaa→aac→cac→ccc
    // aaa→aca→acc→ccc
    // aaa→aca→cca→ccc
    assertEquals(7,
        listOf(
            listOf("aaa","aac","acc","ccc"),
            listOf("aaa","aac","cac","ccc"),
            listOf("aaa","aca","acc","ccc"),
            listOf("aaa","aca","cca","ccc")),
        s.findLadders("aaa", "ccc", listOf("aac","acc","ccc","cac","cca","aca")))

    println("all tests passed")
}

fun assertEquals(id: Int, expected: List<List<String>>, actual: List<List<String>>) {
    val exp = expected.sortedBy { it.toString() }
    val act = actual.sortedBy { it.toString() }
    if (exp != act) {
        throw RuntimeException("Test $id: expected $exp but got $act")
    }
}
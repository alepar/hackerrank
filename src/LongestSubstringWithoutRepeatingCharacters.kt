class LongestSubstringWithoutRepeatingCharacters {

    fun lengthOfLongestSubstring(s: String): Int {
        var l = 0
        val counts = HashMap<Char, Int>()
        var max = 0

        for (r in 1..s.length) {
            val charAdd = s[r - 1]
            counts.compute(charAdd) { _, v -> (v?:0)+1 }

            while (hasRepeating(counts)) {
                val charDel = s[l]
                counts.compute(charDel) { _, v ->
                    val v2 = (v?:throw IllegalStateException())-1
                    if (v2 == 0) null else v2
                }
                l++
            }

            if (r-l > max) {
                max = r-l
            }
        }

        return max
    }

    private fun hasRepeating(counts: Map<Char, Int>): Boolean {
        for ((k, v) in counts) {
            when {
                v>1 -> return true
                v<0 -> throw IllegalStateException("$k == $v")
            }
        }
        return false
    }
}

fun main() {
    println(LongestSubstringWithoutRepeatingCharacters().lengthOfLongestSubstring(""))
}
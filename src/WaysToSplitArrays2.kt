class WaysToSplitArrays2 {
    fun waysToSplit(nums: IntArray): Int {
        var sumLeft = 0
        var sumRemain = nums.sum().toLong()

        var endMid = 2
        var sumMid = nums[0]+nums[1]

        var endMidMax = 2
        var sumRightMax = sumRemain - sumMid

        var result = 0
        for (endLeft in 1..nums.size-2) {
            val value = nums[endLeft - 1]
            sumLeft += value
            sumRemain -= value
            sumMid -= value

            for (i in endMid..nums.size-1) {
                if (sumLeft <= sumMid) {
                    break
                }
                sumMid += nums[i]
                endMid = i+1
            }

            for (i in endMidMax..nums.size-1) {
                sumRightMax -= nums[i]
                endMidMax = i+1

                if (sumRightMax < sumRemain-sumRightMax) {
                    sumRightMax += nums[i]
                    endMidMax = i
                    break
                }
            }

            if (endMid <= endMidMax) {
                println("$endLeft | $endMid - $endMidMax")
                result += (endMidMax-endMid)+1
            } else {
                break
            }
        }

        return result
    }
}

fun main(args: Array<String>) {
//    println(WaysToSplitArrays2().waysToSplit(intArrayOf(7,2,5,5,6,2,10,9)))
    println(WaysToSplitArrays2().waysToSplit(intArrayOf(8892,2631,7212,1188,6580,1690,5950,7425,8787,4361,9849,4063,9496,9140,9986,1058,2734,6961,8855,2567,7683,4770,40,850,72,2285,9328,6794,8632,9163,3928,6962,6545,6920,926,8885,1570,4454,6876,7447,8264,3123,2980,7276,470,8736,3153,3924,3129,7136,1739,1354,661,1309,6231,9890,58,4623,3555,3100,3437)))
}
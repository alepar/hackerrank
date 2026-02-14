class WaysToSplitArrays {
    fun waysToSplit(nums: IntArray): Int {
        var sumLeft = nums[0]
        var endLeft = 1

        var sumMidMin = nums[1]
        var endMidMin = 2

        var sumMidMax = 0
        var endMidMax = 0

        var sumRightMin = 0

        for (i in 2 until nums.size) {
            if (sumMidMin >= sumLeft) {
                break
            }
            sumMidMin += nums[i]
            endMidMin = i+1
        }
        if (sumMidMin < sumLeft) {
            return 0
        }

        for (i in endMidMin until nums.size) {
            sumRightMin += nums[i]
        }

        if (sumMidMin > sumRightMin) {
            return 0
        }

        var sumCur = sumMidMin
        for (i in endMidMin until nums.size) {
            sumCur += nums[i]
            if (sumCur > sumRightMin+sumMidMin-sumCur) {
                sumMidMax = sumCur-nums[i]
                endMidMax = i
                break
            }
        }

        var result = 0
        for (i in 1..nums.size-2) {
            if (endMidMin > endMidMax) {
                println("-1")
                break
            }
            println("$i: $endLeft | $endMidMin - $endMidMax")
            result += (endMidMax-endMidMin)+1

            sumMidMin -= nums[endLeft]
            sumMidMax -= nums[endLeft]
            sumLeft += nums[endLeft]
            endLeft++

            for (j in endMidMin..nums.size-1) {
                if (sumMidMin >= sumLeft) {
                    break
                }
                sumMidMin += nums[j]
                sumRightMin -= nums[j]
                endMidMin = j+1
            }
            if (sumMidMin < sumLeft) {
                println("-2")
                break
            }
            if (sumMidMin > sumRightMin) {
                println("-3 $endMidMin")
                break;
            }

            for (j in endMidMax..nums.size-1) {
                sumMidMax += nums[j]
                if (sumMidMax > sumMidMin+sumRightMin-sumMidMax) {
                    sumMidMax -= nums[j]
                    endMidMax = j
                    break
                }
            }
        }

        return result
    }
}

fun main(args: Array<String>) {
    println(WaysToSplitArrays().waysToSplit(intArrayOf(7,2,5,5,6,2,10,9)))
//    println(WaysToSplitArrays().waysToSplit(intArrayOf(8892,2631,7212,1188,6580,1690,5950,7425,8787,4361,9849,4063,9496,9140,9986,1058,2734,6961,8855,2567,7683,4770,40,850,72,2285,9328,6794,8632,9163,3928,6962,6545,6920,926,8885,1570,4454,6876,7447,8264,3123,2980,7276,470,8736,3153,3924,3129,7136,1739,1354,661,1309,6231,9890,58,4623,3555,3100,3437)))
//    println(92668+6545+6920+926)
}

/*
 7     |  2,5  5,6,2,10,9 - 2,5,5,6  2,10,9    == 3
 7,2   |  5,5  6,2,10,9 - 5,5,6,2  10,9        == 3
 7,2,5 |  5,6,2,10  9 NO
*/

/*
  87264 = 8892,2631,7212,1188,6580,1690,5950,7425,8787,4361,9849,4063,9496,9140,
  |
  92668 = 9986,1058,2734,6961,8855,2567,7683,4770,40,850,72,2285,9328,6794,8632,9163,3928,6962
  -
  107059 = 6545,6920,926,
  |
  113380 = 8885,1570,4454,6876,7447,8264,3123,2980,7276,470,8736,3153,3924,3129,7136,1739,1354,661,1309,6231,9890,58,4623,3555,3100,3437
 */
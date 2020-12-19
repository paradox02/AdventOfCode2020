package adventofcode2020.day10

import adventofcode2020.readFileLineByLine
import kotlin.math.abs

@ExperimentalStdlibApi
fun main(args: Array<String>) {
//    taskA()
    taskB()
}

private fun taskA() {
    val nums = mutableListOf<Long>(0L)
    readFileLineByLine("input10") {
        nums.add(it.toLong())
    }
    nums.sort()
    nums.add(nums.last() + 3L)

    var c3 = 0
    var c2 = 0
    var c1 = 0
    for (i in 1..nums.size - 1 step 1) {
        if (abs(nums.get(i - 1) - nums.get(i)) == 3L) {
            c3++
        }
        if (abs(nums.get(i - 1) - nums.get(i)) == 2L) {
            c2++
        }
        if (abs(nums.get(i - 1) - nums.get(i)) == 1L) {
            c1++
        }
    }
    println("$c1 $c2 $c3")
    println("Answer is ${c1 * c3}")

}

private fun taskB() {
    val nums = mutableListOf<Long>(0L)
    readFileLineByLine("input10") {
        nums.add(it.toLong())
    }
    nums.sort()
    nums.add(nums.last() + 3L)

    val diffs: MutableMap<Long,Long> = mutableMapOf(0L to 1L)

    nums.drop(1).forEach { num ->
        diffs[num] = (1 .. 3).map { lookBack ->
            diffs.getOrDefault(num - lookBack, 0)
        }.sum()
    }

    println("Answer is ${diffs.getValue(nums.last())}")


}
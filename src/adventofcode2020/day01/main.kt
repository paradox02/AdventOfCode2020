package adventofcode2020.day01

import adventofcode2020.readFileLineByLine


private val TARGET_SUM = 2020

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    taskA()
    taskB()
}

private fun taskA() {
    val inputs = mutableListOf<Int>()
    readFileLineByLine("input01") {
        it.toIntOrNull()?.let { it1 -> inputs.add(it1) }
    }
    val out = findPairBySum(inputs, TARGET_SUM)?.let {
        it.first * it.second
    }
    print("Answer is $out")
}

private fun taskB() {
    val inputs = mutableListOf<Int>()

    readFileLineByLine("input01") {
        it.toIntOrNull()?.let { it1 -> inputs.add(it1) }
    }

    var nums: Triple<Int, Int, Int>? = null
    while (inputs.isNotEmpty() && nums == null) {
        val tempNum = inputs.removeFirst()
        findPairBySum(inputs.toMutableList(), TARGET_SUM - tempNum)?.let {
            nums = Triple(tempNum, it.first, it.second)
        }
    }
    println(nums)
    print("Answer is ${nums?.first?.times(nums?.second ?: 0)?.times(nums?.third ?: 0)}")
}


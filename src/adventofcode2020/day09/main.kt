package adventofcode2020.day09

import adventofcode2020.readFileLineByLine

private val PREAMBLE_LENGTH = 25
private val VARIANT_A_RESULT = 257342611L

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    taskA()
    taskB()
}

private fun taskA() {
    val preNums = mutableListOf<Long>()
    readFileLineByLine("input09") {
        if (preNums.size < PREAMBLE_LENGTH) {
            preNums.add(it.toLong())
        } else {
            val actualNum = it.toLong()
            var isData = true
            val tempNums = preNums.toMutableList()
            tempNums.toMutableList().forEach {
                val diff = actualNum - it
//                if (diff == it)
//                    return@readFileLineByLine
                if (tempNums.contains(diff)) {
                    preNums.add(actualNum)
                    preNums.removeAt(0)
                    isData = false
                    return@readFileLineByLine
                }
            }

            if (isData) {
                println("Answer is $actualNum")
            }
        }
    }
}

private fun taskB() {
    val data = mutableListOf<Long>()
    readFileLineByLine("input09") {
        data.add(it.toLong())
    }

    val temp = mutableListOf<Long>()
    data.forEach {
        if (temp.sum() == VARIANT_A_RESULT) {
            temp.sort()
            println("answer is ${temp.first() + temp.last()}")
        }
        temp.add(it)
        while (temp.sum() > VARIANT_A_RESULT) {
            temp.removeAt(0)
        }
    }
}
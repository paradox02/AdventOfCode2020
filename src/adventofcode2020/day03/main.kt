package adventofcode2020.day03

import adventofcode2020.readFileLineByLine

@ExperimentalStdlibApi
fun main(args: Array<String>) {
//    taskA()
    taskB()
}

private fun taskA() {
    println("Answer is ${countTree(3)}")
}

private fun taskB() {
    val out = countTree(1).toLong() * countTree(3) * countTree(5) * countTree(7) * countTree(1, 2)
    println("answer is $out")
}

fun countTree(step: Int, line: Int = 1): Int {
    var count = 0
    var lastIndex = 0
    var actualLine = 0
    readFileLineByLine("input03") {
        if (actualLine++ % line == 0) {
            if (it.length <= lastIndex) {
                lastIndex -= it.length
            }
            if (it.get(lastIndex) == '#')
                count++
            lastIndex += step
        }
    }
    return count
}


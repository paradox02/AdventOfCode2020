package adventofcode2020.day06

import adventofcode2020.readFileLineByLine

@ExperimentalStdlibApi
fun main(args: Array<String>) {
//    taskA()
    taskB()
}

private fun taskA() {
    var count = 0
    val questions = mutableSetOf<Char>()
    readFileLineByLine("input06") {
        if (it.isEmpty()) {
            count += questions.count()
            questions.clear()
        } else {
            questions.addAll(it.toList())
        }
    }
    println("Answer is $count")
}

private fun taskB() {
    var count = 0
    var questions = 0
    val answers = mutableListOf<Char>()
    readFileLineByLine("input06") {
        if (it.isEmpty()) {
            count += answers.groupBy { it }.filter { it.value.size == questions }.count()
            questions = 0
            answers.clear()
        } else {
            val data = it.toList()
            questions++
            answers.addAll(data)
        }
    }
    println("Answer is $count")
}


package adventofcode2020.day02

import adventofcode2020.readFileLineByLine

@ExperimentalStdlibApi
fun main(args: Array<String>) {
//    taskA()
    taskB()
}

private fun taskA() {

    val validPasswords = mutableListOf<String>()
    readFileLineByLine("input02") {
        inputString(it)?.let { res ->
            validPasswords.add(res)
        }
    }
    print("Answer is ${validPasswords.size}")
}

private fun taskB() {

    val validPasswords = mutableListOf<String>()
    readFileLineByLine("input02") {
        inputString(it)?.let { res ->
            validPasswords.add(res)
        }
    }
    print("Answer is ${validPasswords.size}")
}

fun inputString(line: String): String? {
    val data = line.split("-", ":", " ")
    val range = Pair(data.first().toInt(), data.get(1).toInt())
    val char = data.get(2)

    return if ((data.last().get(range.first - 1).toString() == char) xor (data.last().get(range.second - 1)
            .toString() == char)
    ) {
        data.last()
    } else {
        null
    }
}


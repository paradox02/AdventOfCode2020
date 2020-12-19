package adventofcode2020.day08

import adventofcode2020.readFileLineByLine

@ExperimentalStdlibApi
fun main(args: Array<String>) {
//    taskA()
    taskB()
}

private fun taskA() {

    val instructions = mutableListOf<Pair<CMD, Int>>()
    readFileLineByLine("input08") {
        val lineData = it.split(" ")
        val cmd = CMD.values().first { it.value == lineData.first() }
        val num = lineData.last().toInt()
        instructions.add(Pair(cmd, num))
    }

    val value = getValue(instructions)
    println("Answer is $value")
}

private fun taskB() {
    val instructions = mutableListOf<Pair<CMD, Int>>()
    readFileLineByLine("input08") {
        val lineData = it.split(" ")
        val cmd = CMD.values().first { it.value == lineData.first() }
        val num = lineData.last().toInt()
        instructions.add(Pair(cmd, num))
    }
    val count = instructions.count { it.first != CMD.ACC }

    var value = 0
    for (i in 1..count * 2) {
        value = getValueWithoutCycling(instructions, i)
        if (value != 0) {
            println("Answer is $value")
            return
        }
    }
}

fun getValue(instructions: MutableList<Pair<CMD, Int>>): Int {
    val history = mutableListOf<Int>()

    var value = 0
    var actualIndex = 0
    var actualCmdStep = instructions.get(0)
    while (!history.contains(actualIndex)) {
        history.add(actualIndex)
        when (actualCmdStep.first) {
            CMD.NOP -> {
                actualIndex++
            }
            CMD.ACC -> {
                value += actualCmdStep.second
                actualIndex++
            }
            CMD.JMP -> {
                actualIndex += actualCmdStep.second
            }
        }
        actualCmdStep = instructions.get(actualIndex)
    }
    return value
}

fun getValueWithoutCycling(instructions: MutableList<Pair<CMD, Int>>, change: Int): Int {
    val history = mutableListOf<Int>()

    var posibilities = 0
    var changed = false

    var value = 0
    var actualIndex = 0
    var actualCmdStep = instructions.get(0)
    while (!history.contains(actualIndex)) {
        history.add(actualIndex)
        if (actualCmdStep.first != CMD.ACC && !changed) {
            posibilities++
        }
        if (posibilities == change && !changed) {
            changed = true
            actualIndex = change(actualIndex, actualCmdStep)
        } else {
            when (actualCmdStep.first) {
                CMD.NOP -> {
                    actualIndex++
                }
                CMD.ACC -> {
                    value += actualCmdStep.second
                    actualIndex++
                }
                CMD.JMP -> {
                    actualIndex += actualCmdStep.second
                }
            }
        }
        if (actualIndex == instructions.size){
            return value
        }
        if (!(actualIndex in 0..instructions.size - 1))
            return 0
        actualCmdStep = instructions.get(actualIndex)
    }
    if (actualIndex != instructions.size - 1)
        return 0
    return 0
}

private fun change(actualIndex: Int, actualCmdStep: Pair<CMD, Int>): Int {
    return if (actualCmdStep.first == CMD.JMP) {
        actualIndex + 1
    } else {
        actualIndex + actualCmdStep.second
    }
}

enum class CMD(val value: String) {
    NOP("nop"),
    ACC("acc"),
    JMP("jmp")
}
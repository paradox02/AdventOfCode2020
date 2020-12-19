package adventofcode2020.day05

import adventofcode2020.readFileLineByLine

@ExperimentalStdlibApi
fun main(args: Array<String>) {
//    taskA()
    taskB()
}

private fun taskA() {
    var maxId: Int? = null
    readFileLineByLine("input05") {
        var rowInterval = Pair(0, 127)
//        println(columnInterval)

        var row = 0
        it.subSequence(0, 7).forEach {
            rowInterval = if (it == 'F') {
                intervalHalvingLower(rowInterval.first, rowInterval.second)
            } else {
                intervalHalvingUpper(rowInterval.first, rowInterval.second)
            }
            println(rowInterval)
            //row = rowInterval.second
        }
        row = rowInterval.first
        println(row)

        var columnInterval = Pair(0, 7)

        var column = 0
        it.subSequence(7, 9).forEach {
            columnInterval = if (it == 'L') {
                intervalHalvingLower(columnInterval.first, columnInterval.second)
            } else {
                intervalHalvingUpper(columnInterval.first, columnInterval.second)
            }
            println(columnInterval)
        }
        println(columnInterval)
        column = if (it.last()=='L') columnInterval.first else columnInterval.second
        println(column)
        val value = row * 8 + column
        if (value > maxId ?: 0) maxId = value
    }
    println("Answer is $maxId")
}

private fun taskB() {
    val allIds = mutableListOf<Int>()
    for (i in 0..127){
        for (j in 0..7){
            allIds.add(i*8+j)
        }
    }
    readFileLineByLine("input05") {
        var rowInterval = Pair(0, 127)
//        println(columnInterval)

        var row = 0
        it.subSequence(0, 7).forEach {
            rowInterval = if (it == 'F') {
                intervalHalvingLower(rowInterval.first, rowInterval.second)
            } else {
                intervalHalvingUpper(rowInterval.first, rowInterval.second)
            }
            println(rowInterval)
            //row = rowInterval.second
        }
        row = rowInterval.first
        println(row)

        var columnInterval = Pair(0, 7)

        var column = 0
        it.subSequence(7, 9).forEach {
            columnInterval = if (it == 'L') {
                intervalHalvingLower(columnInterval.first, columnInterval.second)
            } else {
                intervalHalvingUpper(columnInterval.first, columnInterval.second)
            }
            println(columnInterval)
        }
        println(columnInterval)
        column = if (it.last()=='L') columnInterval.first else columnInterval.second
        println(column)
        val value = row * 8 + column
        allIds.remove(value)
    }
    println("Answer is $allIds")
}

private fun intervalHalvingUpper(min: Int, max: Int): Pair<Int, Int> {
    return Pair((min + max + 1) / 2, max)
}

private fun intervalHalvingLower(min: Int, max: Int): Pair<Int, Int> {
    return Pair(min, (min + max + 1) / 2 - 1)
}


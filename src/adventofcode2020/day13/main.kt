package adventofcode2020.day13

import adventofcode2020.readFileLineByLine

@ExperimentalStdlibApi
fun main(args: Array<String>) {
//    taskA()
    taskB()
}

private fun taskA() {
    var myDepart: Int? = null
    readFileLineByLine("input13") { line ->
        if (myDepart == null) {
            myDepart = line.toInt()
        } else {
            val myBus = line.replace(",x", "").replace("x,", "").split(",").map { it.toInt() }
                .map {
                    val wait = ((myDepart!! / it) + 1) * it - myDepart!!
                    Pair(it, wait)
                }.minBy { it.second }
            println("${myBus?.first?.times(myBus.second)}")

        }
    }
}

private fun taskB() {
    var myDepart: Int? = null
    var time = 0L
    readFileLineByLine("input13") { line ->
        if (myDepart == null) {
            myDepart = line.toInt()
        } else {
            val lines = line.split(",")
            val data = mutableListOf<Pair<Long, Int>>()
            lines.forEachIndexed { index, s ->
                val num = s.toLongOrNull()
                if (num != null)
                    data.add(Pair(num, index))
            }
            var stepSize = data.removeAt(0).first
            data.forEach {
                while ((time + it.second) % it.first != 0L) {
                    time += stepSize
                }
                stepSize *= it.first // New Ratio!
            }
        }
    }
    println(time)
}
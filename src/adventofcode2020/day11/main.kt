package adventofcode2020.day11

import adventofcode2020.readFileLineByLine

private var maxX: Int? = null
private var maxY: Int? = null

@ExperimentalStdlibApi
fun main(args: Array<String>) {
//    taskA()
    taskB()
}

private fun taskA() {
    val hall = MutableList<MutableList<SeatState>>(0) { mutableListOf() }
    readFileLineByLine("input11") {
        val line = it.toCharArray().map { mark -> SeatState.values().first { it.mark == mark } }.toMutableList()
        hall.add(line)
    }
    maxX = hall.size
    maxY = hall.firstOrNull()?.size ?: 0

    var newHall = getNewHall(hall)

    while (newHall.first != 0) {

        newHall.second.snapshot()
        newHall = getNewHall(newHall.second)
    }

    println("Answer is ${newHall.second.occupiedSeats()}")
}

private fun List<List<SeatState>>.snapshot() {
    println()
    this.forEach { line ->
        println(line.joinToString("") { it.mark.toString() })
    }
}

private fun List<List<SeatState>>.occupiedSeats(): Int {
    var seats = 0
    this.forEach { line ->
        seats += line.count { it == SeatState.OCCUPIED }
    }
    return seats
}

private fun taskB() {
    val lines = mutableListOf<String>()
    readFileLineByLine("input11") { line ->
        lines.add(line)
    }

    val process = Day11(lines.toList())
    println("Answer is ${process.solvePart2()}")
}

fun getNewHall(hall: List<List<SeatState>>): Pair<Int, List<MutableList<SeatState>>> {
    var changes = 0
    val newHall = hall.toMutableList().map { it.toMutableList() }

    newHall.forEachIndexed { y, line ->
        line.forEachIndexed { x, seatState ->
            val adjustmentsOccupationsCount = getCountOfOccupiedAdjucments(hall, x, y)
            when (seatState) {
                SeatState.EMPTY -> {
                    if (adjustmentsOccupationsCount == 0) {
                        newHall[y][x] = SeatState.OCCUPIED
                        changes++
                    }
                }
                SeatState.OCCUPIED ->
                    // in A variant change 5 to 4
                    if (adjustmentsOccupationsCount >= 5) {
                        newHall[y][x] = SeatState.EMPTY
                        changes++
                    }
                else -> {
                }
            }
        }
    }
    return Pair(changes, newHall)
}

fun getCountOfOccupiedAdjucments(hall: List<List<SeatState>>, y: Int, x: Int): Int {
    val mX = maxX ?: 0
    val mY = maxY ?: 0
    var occupiedCount = 0
//    X__
//    _0_
//    ___
    if (x - 1 >= 0 && y - 1 >= 0 && hall[x - 1][y - 1] == SeatState.OCCUPIED)
        occupiedCount++

//    _X_
//    _0_
//    ___
    if (x >= 0 && y - 1 >= 0 && hall[x][y - 1] == SeatState.OCCUPIED)
        occupiedCount++
//    __X
//    _0_
//    ___
    if (x + 1 < mX && y - 1 >= 0 && hall[x + 1][y - 1] == SeatState.OCCUPIED)
        occupiedCount++

//    ___
//    X0_
//    ___
    if (x - 1 >= 0 && hall[x - 1][y] == SeatState.OCCUPIED)
        occupiedCount++
//    ___
//    _0X
//    ___
    if (x + 1 < mX && hall[x + 1][y] == SeatState.OCCUPIED)
        occupiedCount++

//    ___
//    _0_
//    X__
    if (x - 1 >= 0 && y + 1 < mY && hall[x - 1][y + 1] == SeatState.OCCUPIED)
        occupiedCount++

//    ___
//    _0_
//    _X_
    if (x >= 0 && y + 1 < mY && hall[x][y + 1] == SeatState.OCCUPIED)
        occupiedCount++
//    ___
//    _0_
//    __X
    if (x + 1 < mX && y + 1 < mY && hall[x + 1][y + 1] == SeatState.OCCUPIED)
        occupiedCount++

    return occupiedCount
}

enum class SeatState(val mark: Char) {
    EMPTY('L'),
    OCCUPIED('#'),
    FLOOR('.'),
}
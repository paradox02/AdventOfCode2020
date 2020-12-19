package adventofcode2020.day12

import adventofcode2020.readFileLineByLine
import kotlin.math.abs

@ExperimentalStdlibApi
fun main(args: Array<String>) {
    taskA()
//    taskB()
}

private fun taskA() {
    var actualPosition = Triple(0, 0, Direction.EAST)
    readFileLineByLine("input12") { line ->
        val direction = Direction.values().first { line[0] == it.value }
        val num = line.removeRange(0, 1).toInt()
        handleDirections(direction, num, actualPosition.third).let {
            val x = actualPosition.first + it.first
            val y = actualPosition.second + it.second
            actualPosition = Triple(x, y, it.third)
            println("Answer is ${actualPosition.first} ${actualPosition.second}")
        }
    }
    println("Answer is ${actualPosition.first} ${actualPosition.second}")
    println("Answer is ${abs(actualPosition.first) + abs(actualPosition.second)}")
}

private fun handleDirections(
    direction: Direction,
    value: Int,
    actualDirection: Direction
): Triple<Int, Int, Direction> {
    var x = 0
    var y = 0
    var lastDir = actualDirection
    when (direction) {
        Direction.NORTH -> {
            y += value
        }
        Direction.SOUTH -> {
            y -= value
        }
        Direction.EAST -> {
            x += value
        }
        Direction.WEST -> {
            x -= value
        }
        Direction.LEFT -> {
            if (value != 0) {
                val rotate = value / 90
                val ordinal = Math.floorMod(rotate - actualDirection.ordinal, 4)
                lastDir = Direction.values()[ordinal]
            }
        }
        Direction.RIGHT -> {
            if (value != 0) {
                val rotate = value / 90
                val ordinal = Math.floorMod(rotate + actualDirection.ordinal, 4)
                lastDir = Direction.values()[ordinal]
            }
        }
        Direction.FORWARD -> {
            handleDirections(actualDirection, value, actualDirection).let {
                x += it.first
                y += it.second
            }
        }
    }
    return Triple(x, y, lastDir)
}

private fun taskB() {
}

private enum class Direction(val value: Char) {
    NORTH('N'),
    EAST('E'),
    SOUTH('S'),
    WEST('W'),
    LEFT('L'),
    RIGHT('R'),
    FORWARD('F'),
}
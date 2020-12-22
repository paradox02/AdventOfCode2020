package adventofcode2020.day12

import adventofcode2020.readFileLineByLine
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@ExperimentalStdlibApi
fun main(args: Array<String>) {
//    taskA()
    taskB()
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
                val rotate = (360 - value) / 90
                val ordinal = Math.floorMod(rotate + actualDirection.ordinal, 4)
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
    var boja = Triple(10L, 1L, Direction.NORTH)
    var ship = Triple(0L, 0L, Direction.EAST)

    readFileLineByLine("input12") { line ->
        val direction = Direction.values().first { line[0] == it.value }
        val num = line.removeRange(0, 1).toInt()
        println(line)
        handleShipDirections(direction, boja, ship, num, ship.third).let {
            boja = it.first
            ship = it.second

            println("ship is ${ship.first} ${ship.second}")
            println("waypoint is ${boja.first} ${boja.second}")
        }
    }

    println(
        "Answer is ${
            abs(ship.first).toULong() + abs(ship.second).toULong()
        }"
    )

}

private fun handleShipDirections(
    direction: Direction,
    boja: Triple<Long, Long, Direction>,
    ship: Triple<Long, Long, Direction>,
    value: Int,
    actualDirection: Direction
): Pair<Triple<Long, Long, Direction>, Triple<Long, Long, Direction>> {
    var tBoja = boja
    var tShip = ship
    var lastDir = actualDirection
    when (direction) {
        Direction.NORTH -> {
            tBoja = (Triple(tBoja.first, tBoja.second + value, lastDir))
//            tShip = (Triple(tShip.first, tShip.second + value, lastDir))
        }
        Direction.SOUTH -> {
            tBoja = (Triple(tBoja.first, tBoja.second - value, lastDir))
//            tShip = (Triple(tShip.first, tShip.second - value, lastDir))
        }
        Direction.EAST -> {
            tBoja = (Triple(tBoja.first + value, tBoja.second, lastDir))
//            tShip = (Triple(tShip.first + value, tShip.second, lastDir))
        }
        Direction.WEST -> {
            tBoja = (Triple(tBoja.first - value, tBoja.second, lastDir))
//            tShip = (Triple(tShip.first - value, tShip.second, lastDir))
        }
        Direction.LEFT -> {
            if (value != 0) {
                val rotate = (360 - value) % 360
                val x =
                    ((tBoja.first - tShip.first) * cos(Math.toRadians(rotate.toDouble())) + (tBoja.second - tShip.second) * sin(
                        Math.toRadians(rotate.toDouble())
                    )).toInt()
                val y =
                    (-(tBoja.first - tShip.first) * sin(Math.toRadians(rotate.toDouble())) + (tBoja.second - tShip.second) * cos(
                        Math.toRadians(rotate.toDouble())
                    )).toInt()
                val ordinal = Math.floorMod(rotate + actualDirection.ordinal, 4)
                lastDir = Direction.values()[ordinal]
                tBoja = Triple(ship.first + x, ship.second + y, lastDir)
            }
        }
        Direction.RIGHT -> {
            if (value != 0) {
                val rotate = value % 360
                val x =
                    ((tBoja.first - tShip.first) * cos(Math.toRadians(rotate.toDouble())) + (tBoja.second - tShip.second) * sin(
                        Math.toRadians(rotate.toDouble())
                    )).toInt()
                val y =
                    (-(tBoja.first - tShip.first) * sin(Math.toRadians(rotate.toDouble())) + (tBoja.second - tShip.second) * cos(
                        Math.toRadians(rotate.toDouble())
                    )).toInt()
                val ordinal = Math.floorMod(rotate + actualDirection.ordinal, 4)
                lastDir = Direction.values()[ordinal]
                tBoja = Triple(ship.first + x, ship.second + y, lastDir)
            }
        }
        Direction.FORWARD -> {

            val diffX = tBoja.first - tShip.first
            val diffY = tBoja.second - tShip.second
            tBoja = (Triple(tBoja.first + diffX * value, tBoja.second + diffY * value, lastDir))
            tShip = (Triple(tShip.first + diffX * value, tShip.second + diffY * value, lastDir))
        }
    }
    return Pair(tBoja, tShip)
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
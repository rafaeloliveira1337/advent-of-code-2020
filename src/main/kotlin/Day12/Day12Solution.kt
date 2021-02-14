package Day12

import java.util.*
import kotlin.math.abs

val shipPos = mutableMapOf(
    'N' to 0,
    'E' to 0,
    'S' to 0,
    'W' to 0
)

val pathToWaypoint = mutableMapOf(
    'N' to 1,
    'E' to 10,
    'S' to 0,
    'W' to 0
)

val baseDirections = shipPos.keys.toList()

enum class OppositeDirection(val opposite: Char) {
    E('W'),
    S('N'),
    W('E'),
    N('S');

    companion object {
        fun oppositeOf(direction: Char) = valueOf(direction.toString()).opposite
    }
}


fun Day12Solution() {
    println("Day 12 Solution")

    val input = Day12Input.input

    val directions = ArrayDeque<Char>(4)
    directions.add('E')
    directions.add('S')
    directions.add('W')
    directions.add('N')

    for (instructionLine in input) {
        val instruction = instructionLine[0]
        val value = instructionLine.substring(1).toInt()

        when (instruction) {
            'E', 'S', 'W', 'N' -> move(instruction, value, shipPos)
            'F' -> move(directions.peekFirst(), value, shipPos)
            'L' -> rotateShip(
                directions = directions,
                clockwise = false,
                steps = value / 90
            )
            'R' -> rotateShip(
                directions = directions,
                clockwise = true,
                steps = value / 90
            )
            else -> Unit
        }
    }

    println(shipPos)
    println("Manhattan Position: ${shipPos.values.sum()}")
}

fun Day12SolutionPart2() {
    println("Day 12 Solution - Part 2")

    val input = Day12Input.input

    println(shipPos)

    for (instructionLine in input) {
        val instruction = instructionLine[0]
        val value = instructionLine.substring(1).toInt()

        when (instruction) {
            'E', 'S', 'W', 'N' -> move(instruction, value, pathToWaypoint)
            'F' -> moveToWaypoint(value)
            'L' -> rotateWaypoint(
                clockwise = false,
                steps = value / 90
            )
            'R' -> rotateWaypoint(
                clockwise = true,
                steps = value / 90
            )
        }
    }

    println(shipPos)
    println("Manhattan Position: ${shipPos.values.sum()}")
}

fun moveToWaypoint(multiplier: Int) {
    pathToWaypoint.forEach { direction, units ->
        move(direction, units * multiplier, shipPos)
    }
}

fun move(direction: Char, units: Int, objectToMove: MutableMap<Char, Int>) {
    val opposite = OppositeDirection.oppositeOf(direction)
    val oppositePosValue = objectToMove[opposite]!!

    val diff = oppositePosValue - units
    if (diff >= 0) {
        objectToMove[opposite] = diff
    } else {
        objectToMove[opposite] = 0
        objectToMove.merge(direction, abs(diff), Int::plus)
    }
}

fun rotateShip(directions: ArrayDeque<Char>, clockwise: Boolean, steps: Int) {
    if (clockwise) {
        repeat(steps) { directions.addLast(directions.pollFirst()) }
    } else {
        repeat(steps) { directions.addFirst(directions.pollLast()) }
    }
}

fun rotateWaypoint(clockwise: Boolean, steps: Int) {
    val nextState: MutableMap<Char, Int> = mutableMapOf()

    if (clockwise) {
        pathToWaypoint.forEach { (direction, value) ->
            val curIndex = baseDirections.indexOf(direction)

            var nextDirectionIdx = curIndex + steps
            while (nextDirectionIdx >= baseDirections.size) {
                nextDirectionIdx -= baseDirections.size
            }

            nextState[baseDirections[nextDirectionIdx]] = value
        }
    } else {
        pathToWaypoint.forEach { (direction, value) ->
            val curIndex = baseDirections.indexOf(direction)

            var nextDirectionIdx = curIndex - steps
            while (nextDirectionIdx < 0) {
                nextDirectionIdx += baseDirections.size
            }

            nextState[baseDirections[nextDirectionIdx]] = value
        }
    }

    nextState.forEach { (direction, value) ->
        pathToWaypoint[direction] = value
    }
}

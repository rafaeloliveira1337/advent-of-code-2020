package Day15

import java.util.*

fun Day15Solution() {
    println("Day 15 Solution")

    val numberToTurns = mutableMapOf<Int, TreeSet<Int>>()

    var turnNumber = 1
    var lastNum = -1
    Day15Input.input.forEach { num ->
        lastNum = num
        numberToTurns.put(num, sortedSetOf(turnNumber))
        turnNumber++
    }

    println(numberToTurns)

    while (turnNumber < 2021) {
        val nextNumber = numberToTurns[lastNum].let {
            if (it?.size!! > 1) {
                if (it.size > 2) it.remove(it.first())

                val reversed = it!!.reversed()
                reversed[0] - reversed[1]
            } else null
        } ?: 0

        println("turn $turnNumber, num: $nextNumber")

        numberToTurns.getOrPut(nextNumber) { sortedSetOf() }.add(turnNumber)

        lastNum = nextNumber
        turnNumber++
    }

    println("2020th Number: $lastNum")
}




fun Day15SolutionPart2() {
    val history = mutableMapOf<Long, TreeSet<Long>>()

    var turnNumber = 1L
    var lastNum = -1L
    Day15Input.input.forEach { num ->
        lastNum = num.toLong()
        history.put(num.toLong(), sortedSetOf(turnNumber))
        turnNumber++
    }

    println(history)

    while (turnNumber < 30000001) {
        val nextNumber = history[lastNum].let {
            if (it?.size!! > 1) {
                if (it.size > 2) it.remove(it.first())

                val reversed = it!!.reversed()
                reversed[0] - reversed[1]
            } else null
        } ?: 0

        println("turn $turnNumber, num: $nextNumber")

        history.getOrPut(nextNumber) { sortedSetOf() }.add(turnNumber)

        lastNum = nextNumber
        turnNumber++
    }

    println("30000000th Number: $lastNum")
}
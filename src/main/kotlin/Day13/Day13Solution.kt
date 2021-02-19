package Day13

import java.math.BigInteger

fun Day13Solution() {
    println("Day 13 Solution")

    val input = Day13Input.input
    val earliestPossibleDeparture = input[0].toInt()
    val busses = input[1].split(",").filterNot { it == "x" }.map { it.toInt() }

    var busId = Int.MIN_VALUE
    var earliestBusDeparture = Int.MAX_VALUE
    var waitingTime = Int.MAX_VALUE


    busses.forEach {
        var departureTimestamp = it
        while (departureTimestamp < earliestPossibleDeparture) departureTimestamp += it

        if (departureTimestamp < earliestBusDeparture) {
            earliestBusDeparture = departureTimestamp
            busId = it
        }
    }

    waitingTime = earliestBusDeparture - earliestPossibleDeparture
    val result = waitingTime * busId

    println("""Earliest departure: $earliestPossibleDeparture
    |Earliest bus departure: $earliestBusDeparture
    |Bus ID: $busId
    |Waiting Time: $waitingTime
    |Result: $result""".trimMargin())
}

/*
 * I could only understand this algorithm with this video:
 * https://www.youtube.com/watch?v=4_5mluiXF5I
 * */
fun Day13SolutionPart2() {
    println("Day 13 Solution - Part 2")

    val input = Day13Input.input[1].split(",").toMutableList()

    var timestamp = BigInteger.ZERO
    var step = BigInteger.valueOf(input[0].toLong())

    input.forEachIndexed { offset, value ->
        if (value != "x" && offset != 0) {
            println("doing n. $value with step: $step and offset $offset")

            var mod = timestamp.plus(BigInteger.valueOf(offset.toLong()))
                .mod(BigInteger.valueOf(value.toLong()))

            while (mod != BigInteger.ZERO) {
                println("timestamp: $timestamp")
                timestamp += BigInteger.valueOf(step.toLong())

                mod = timestamp.plus(BigInteger.valueOf(offset.toLong()))
                    .mod(BigInteger.valueOf(value.toLong()))
            }

            step *= BigInteger.valueOf(value.toLong())
        }
    }

    println("Result: $timestamp")
}
package Day16

import java.io.File

fun Day16Solution() {
    println("Day 16 Solution")

    val filePath = "${System.getProperty("user.dir")}/src/main/kotlin/Day16/Day16Input.txt"
    val (rules, _, nearbyTickets) = File(filePath).readText(Charsets.UTF_8) // load entire file
        .split(Regex("(?m)^\\s*$")) // split on blank lines

    val ruleRanges = rules.trim() // remove leading line break
        .split("\n") // remove line breaks
        .map { it.substringAfter(": ") }
        .flatMap { it.split(" or ") }

    println(ruleRanges)

    val result = nearbyTickets
        .substringAfter("nearby tickets:\n") // ignore header line
        .replace("\n", ",") // remove line breaks
        .split(",")
        .map { it.toInt() }
        .filter { !isValid(it, ruleRanges) } // keep only invalid values
        .sum()

    println("Result: $result")
}

fun isValid(number: Int, ruleRanges: List<String>): Boolean {
    for (rule in ruleRanges) {
        val (left, right) = rule.split("-").map { it.toInt() }

        if (number in left..right) return true
    }

    return false
}

fun Day16SolutionPart2() {
    println("Day 16 Solution - Part 2")

    val filePath = "${System.getProperty("user.dir")}/src/main/kotlin/Day16/Day16Input.txt"
    val (rules, myTicket, nearbyTickets) = File(filePath).readText(Charsets.UTF_8) // load entire file
        .split(Regex("(?m)^\\s*$")) // split on blank lines

    val ruleMap = rules.trim()
        .split("\n")
        .associate { it.substringBefore(": ") to it.substringAfter(": ").split(" or ") }
        .toMutableMap()

    val nearbyTicketMap = mutableMapOf<Int, MutableList<Int>>()
    nearbyTickets
        .substringAfter("nearby tickets:\n")
        .split("\n")
        .map { line -> line.split(",").map { it.toInt() } }
        .filter { list ->
            list.all {
                isValid(
                    it,
                    ruleMap.values.flatten()
                )
            }
        } // keep only tickets with all valid values
        .forEach {
            it.mapIndexed { idx, value ->
                nearbyTicketMap.getOrPut(idx) { mutableListOf() }.add(value)
            }
        }

    val dataToValidPosition = mutableMapOf<String, MutableList<Int>>()

    for ((position, values) in nearbyTicketMap) {
        for ((data, dataRules) in ruleMap) {
            if (values.all { isValid(it, dataRules) }) {
                dataToValidPosition.getOrPut(data) { mutableListOf() }.add(position)
            }
        }
    }

    println(dataToValidPosition)
    val finalDataToPosition = findCorrectPositions(dataToValidPosition)

    val myTicketValues = myTicket.substringAfter("your ticket:\n")
        .trim()
        .split(",")

    val departureValuePositions = finalDataToPosition.filter { it.key.startsWith("departure") }.values

    println(departureValuePositions)
    println(myTicketValues)

    val res = departureValuePositions.fold(1L, { acc, nextPos -> acc * myTicketValues[nextPos].toLong() })
    println("Result: $res")
}

fun findCorrectPositions(dataToPosition: MutableMap<String, MutableList<Int>>): MutableMap<String, Int> {
    val correctPositions = mutableMapOf<String, Int>()

    while (correctPositions.size < dataToPosition.size) {
        dataToPosition.filter { it.value.size == 1 }
            .forEach { (data, columnValues) ->
                val columnNumber = columnValues.first()
                correctPositions[data] = columnNumber
                dataToPosition.values.forEach { it.remove(columnNumber) }
            }
    }

    return correctPositions
}

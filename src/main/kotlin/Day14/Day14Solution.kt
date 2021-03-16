package Day14

import java.lang.IllegalArgumentException

fun Day14Solution() {
    println("Day 14 Solution")

    val memoryMap = mutableMapOf<Int, Long>()
    var mask = ""

    for (line in Day14Input.input) {
        if (line.startsWith("mask")) mask = line.split(" = ")[1]
        else processLine(line, mask, memoryMap)
    }

    println(memoryMap)

    val result = memoryMap.values.sum()
    println("Result: $result")
}

fun Day14SolutionPart2() {
    println("Day 14 Solution - Part 2")

    val memoryMap = mutableMapOf<Long, Long>()
    var mask = ""

    for (line in Day14Input.input) {
        if (line.startsWith("mask")) mask = line.split(" = ")[1]
        else processLinePart2(line, mask, memoryMap)
    }

    val result = memoryMap.values.sum()
    println("Result: $result")
}

// Part 1
private fun processLine(line: String, mask: String, memoryMap: MutableMap<Int, Long>) {
    val (memCommand, decimalValue) = line.split(" = ")
    val memAddr = memCommand.removeSurrounding("mem[", "]").toInt()
    val binaryValue = decimalValue.toUInt().toString(radix = 2).padStart(36, '0')

    var valueWithMask = ""


    for ((i, c) in mask.withIndex()) {
        valueWithMask += when(c) {
            'X' -> binaryValue[i]
            '0' -> 0
            '1' -> 1
            else -> throw IllegalArgumentException("Something wen wrong when applying mask $mask to $binaryValue")
        }
    }

    memoryMap[memAddr] = valueWithMask.toLong(radix = 2)

    println("Value with mask: $valueWithMask, as decimal: ${valueWithMask.toLong(radix = 2)}")
}


// Part 2
private fun processLinePart2(line: String, mask: String, memoryMap: MutableMap<Long, Long>) {
    val (memCommand, decimalValue) = line.split(" = ")
    val memAddr = memCommand.removeSurrounding("mem[", "]").toInt()
    val binaryValue = memAddr.toUInt().toString(radix = 2).padStart(36, '0')

    var valueWithMask = ""

    for ((i, c) in mask.withIndex()) {
        valueWithMask += when(c) {
            'X' -> 'X'
            '0' -> binaryValue[i]
            '1' -> 1
            else -> throw IllegalArgumentException("Something wen wrong when applying mask $mask to $binaryValue")
        }
    }

    val floatingAddresses = getFloatingAddresses(valueWithMask.trimStart('0'))
    floatingAddresses.map { it.toLong(radix = 2) }.forEach { memoryMap[it] = decimalValue.toLong() }
}

private fun getFloatingAddresses(address: String): List<String> {
    if (address.isEmpty()) return listOf("")

    val firstChar = address[0]
    val remainder = address.substring(1)
    val partialAddresses = getFloatingAddresses(remainder)

    return if (firstChar == 'X') {
        partialAddresses.map { "0$it" }.plus(partialAddresses.map { "1$it" })
    } else {
        partialAddresses.map { "$firstChar$it" }
    }

}
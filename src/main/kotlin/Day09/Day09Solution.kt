package Day09


fun hasSumInPreamble(start: Int, end: Int, sum: Long, arr: Array<Long>): Boolean {
    val subtrahendSet = mutableSetOf<Long>()
    for (i in start..end) {
        val item = arr[i]
        val remainder = sum - item

        if (!subtrahendSet.contains(remainder)) {
            subtrahendSet.add(item)
        } else {
            return true
        }
    }

    return false
}

fun findInvalidNumberIndex(preambleSize: Int, input: Array<Long>): Int {
    for (i in 0 until input.size - preambleSize) {
        val sum = input[preambleSize + i]

        val start = if (i < preambleSize) i else i
        val end = i + preambleSize - 1

        if (!hasSumInPreamble(start, end, sum, input)) {
            return preambleSize + i
        }
    }

    return -1
}

fun sumInContiguousArray(data: Array<Long>, expectedSum: Long): Long {
    (0..data.lastIndex - 2).forEach { start ->
        var min = data[start]
        var max = data[start]
        var sum = data[start]
        var end = start+1

        while (sum < expectedSum || end < start + 2) {
            sum += data[end]
            min = min.coerceAtMost(data[end])
            max = max.coerceAtLeast(data[end])
            end++
        }

        if (sum == expectedSum) {
            println("The sum of the smallest and biggest number in a contiguous sum array is: ${min + max}")
            return min + max
        }
    }

    return -1
}

fun Day09Solution() {
    println("Day 09 Solution")

    val input = Day09Input.input
    val preambleSize = 25

    for (i in 0 until input.size - preambleSize) {
        val sum = input[preambleSize + i]

        val start = if (i < preambleSize) i else i
        val end = i + preambleSize - 1

        if (!hasSumInPreamble(start, end, sum, input)) {
            println("The first item without the sum in the preamble is: ${input[preambleSize + i]}")
            break
        }
    }
}

fun Day09SolutionPart2() {
    println("Day 09 Solution - Part 2")
    val preambleSize = 25

    val input = Day09Input.input

    val invalidNumberIndex = findInvalidNumberIndex(preambleSize, input)
    println("invalid number: ${input[invalidNumberIndex]}, with index: $invalidNumberIndex")
    sumInContiguousArray(input, input[invalidNumberIndex])
}
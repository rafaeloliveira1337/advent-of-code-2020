package Day18

val OPERATORS = listOf("+", "*", "-", "/")

fun Day18Solution() {
    println("Day 18 Solution")

    val result = Day18Input.input
        .map { parseLine(it) }
        .fold(0L, {acc, e -> acc + e } )

    println("Result: $result")
}

fun Day18SolutionPart2() {
    println("Day 18 Solution - Part 2")

    val result = Day18Input.input
        .map { it.replace(" ", "") }
        .sumOf { solvePart2(it.iterator()) }

    println("Result: $result")
}

fun Iterable<Long>.product(): Long =
    this.reduce { a, b -> a * b }

fun Char.asLong(): Long =
    this.toString().toLong()

private fun solvePart2(line: CharIterator): Long {
    val multiplyList = mutableListOf<Long>()
    var sum = 0L
    while (line.hasNext()) {
        val next = line.nextChar()
        when {
            next == ')' -> break
            next == '(' -> sum += solvePart2(line)
            next == '*' -> {
                multiplyList += sum
                sum = 0L
            }
            next.isDigit() -> sum += next.asLong()
        }
    }
    return (multiplyList + sum).product()
}

private fun parseLine(lineInput: String): Long {
    println("==== | Parsing line: $lineInput | =====")
    var line = lineInput.replace(" ",  "")
    val reg = """\([^()]+\)""".toRegex()
    var matchingSequence = reg.findAll(line)
    while (matchingSequence.count() > 0) {
        matchingSequence.forEach {
            val block = it.value.removeSurrounding(prefix = "(", suffix = ")")

            val blockValue = parseBlock(block)
            line = line.replace(it.value, blockValue.toString())
            println(line)
        }

        matchingSequence = reg.findAll(line)
    }

    return parseBlock(line)
}

private fun parseBlock(line: String): Long {
    val args = mutableListOf<String>()

    var argumentCandidate = ""
    for (i in 0 until line.length) {
        val element = line[i].toString()
        println("elem: $element")
        if (element in OPERATORS) {
            if (argumentCandidate.isNotBlank()) {
                args.add(argumentCandidate)
                argumentCandidate = ""
            }

            args.add(element)
        } else {
            argumentCandidate += element
        }
    }

    if (argumentCandidate.isNotBlank()) args.add(argumentCandidate)

    return calculate(args)
}

private fun calculate(args: List<String>): Long {
    println("Calculate This: $args")
    var res = args[0].toLong()

    var operator = "";
    for (i in 1 until args.size) {
        if (args[i] in OPERATORS) {
            operator = args[i]
            continue
        }

        res = res.operate(operator, args[i].toLong())
    }

    return res
}

private fun Long.operate(operator: String, operand: Long): Long {
    return when(operator) {
        "+" -> this + operand
        "-" -> this - operand
        "*" -> this * operand
        "/" -> this / operand
        else -> error("Unknown operator $operator")
    }
}

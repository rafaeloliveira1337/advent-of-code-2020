package Day08

fun String.toOperandValuePair(): Pair<Char, Int> {
    val value = substring(1).toInt()
    val operand = get(0)

    return Pair(operand, value)
}

fun Int.applyArguments(operandValuePair: Pair<Char, Int>): Int {
    return if (operandValuePair.first == '-') {
        this - operandValuePair.second
    } else {
        this + operandValuePair.second
    }
}

fun runInstructions(idx: Int, visitedIndexes: MutableSet<Int>, program: Array<String>, accumulated: Int) {
    var currentValue = accumulated
    var nextIndex = idx+1

    if (idx == program.size-1) {
        println("End of program reached, value: $currentValue")
        return
    }

    if (idx in visitedIndexes) {
        println("Infinite loop detected, value before first repetition: $currentValue")
        return
    } else {
        visitedIndexes.add(idx)
        val (instruction, argument) = program[idx].split(" ")

        when (instruction) {
            "acc" -> {
                currentValue = currentValue.applyArguments(argument.toOperandValuePair())
            }
            "jmp" -> {
                nextIndex = nextIndex.applyArguments(argument.toOperandValuePair()) - 1
            }
        }

    }
        runInstructions(nextIndex, visitedIndexes, program, currentValue)
}

fun Day08Solution() {
    println("Day 08 Solution")

    val input = Day08Input.input
    val visitedIndexes = mutableSetOf<Int>()

    runInstructions(0, visitedIndexes, input, 0)
}
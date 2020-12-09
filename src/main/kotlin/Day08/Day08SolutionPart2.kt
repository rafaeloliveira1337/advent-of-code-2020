package Day08

fun String.swapNopJmp(): String {
    val (operand, value) = split(" ")
    return if (operand == "nop") "jmp $value"
    else "nop $value"
}

fun doesTheProgramFinish(idx: Int, visitedIndexes: MutableSet<Int>, program: Array<String>, accumulated: Int): Boolean {
    var currentValue = accumulated
    var nextIndex = idx + 1

    if (idx == program.size - 1) {
        // apply last program line if it's 'acc'
        if (program[idx].startsWith("acc")) {
            val (instruction, argument) = program[idx].split(" ")
            currentValue = currentValue.applyArguments(argument.toOperandValuePair())
        }

        println("End of program reached, value: $currentValue")
        return true
    }

    if (idx in visitedIndexes) {
        println("Infinite loop detected, value before first repetition: $currentValue")
        return false
    } else {
        visitedIndexes.add(idx)
        val (instruction, argument) = program[idx].split(" ")

        when (instruction) {
            "acc" -> {
                currentValue = currentValue.applyArguments(argument.toOperandValuePair())
            }
            "jmp" -> {
                val previous = nextIndex
                nextIndex = nextIndex.applyArguments(argument.toOperandValuePair())

                if (nextIndex != previous) nextIndex--
            }
        }

    }

    return doesTheProgramFinish(nextIndex, visitedIndexes, program, currentValue)
}

fun Day08SolutionPart2() {
    println("Day 08 Solution - Part 2")

    val nopAndJmpIndexList = mutableListOf<Int>()
    Day08Input.input.forEachIndexed { index, element ->
        if (element.startsWith("nop") || element.startsWith("jmp")) nopAndJmpIndexList.add(index)
    }

    val visitedIndexes = mutableSetOf<Int>()
    var program = Day08Input.input

    var programFinishes = doesTheProgramFinish(0, visitedIndexes, program, 0)

    if (!programFinishes) {
        for (i in nopAndJmpIndexList) {
            program = Day08Input.input.copyOf()
            program[i] = program[i].swapNopJmp()
            programFinishes = doesTheProgramFinish(0, mutableSetOf(), program, 0)
            if (programFinishes) break
        }
    }
}
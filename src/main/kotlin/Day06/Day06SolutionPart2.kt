package Day06

import java.io.File

fun Day06SolutionPart2() {
    println("Day 06 Solution - Part 2")

    val filePath = "${System.getProperty("user.dir")}/src/main/kotlin/Day06/Day06Input.txt"
    val input = File(filePath).readText(Charsets.UTF_8) // load entire file

    val sum =
        input
            .split(Regex("(?m)^\\s*$")) // split on blank lines
            .map {group ->
                var count = 0;
                val availableAnswers = group.replace("\n","")
                    .toCharArray()
                    .toSet()

                val groupAnswers = group.split("\n").filter { it.isNotEmpty() }

                for (answer in availableAnswers) {
                    if (groupAnswers.all { it.contains(answer) }) count++
                }

                count
            }.sum()

    println("The sum of questions everyone answered 'yes' is: $sum")
}
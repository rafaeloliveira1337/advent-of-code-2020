package Day06

import java.io.File

fun Day06Solution() {
    println("Day 06 Solution")

    val filePath = "${System.getProperty("user.dir")}/src/main/kotlin/Day06/Day06Input.txt"
    val input = File(filePath).readText(Charsets.UTF_8) // load entire file

    val sum =
        input
            .split(Regex("(?m)^\\s*$")) // split on blank lines
            .map {
                it.replace("\n","") // put all answers on same line
                    .toCharArray()
                    .toSet()
                    .count()
            }.sum()

    println("The sum of 'yes' answers is: $sum")
}
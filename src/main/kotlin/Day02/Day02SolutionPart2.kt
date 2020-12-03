package adventofcode2020.Day02

fun Day02SolutionPart2() {
    println("Day 02 Solution - Part 2")

    var validPasswordCount = 0;

    Day02Input.input.forEach {
        val (criteria, password) = it.split(": ")
        val (minMax, requiredChar) = criteria.split(" ")
        val (index1, index2) = minMax.split("-")

        val indices = listOf(index1.toInt() - 1 , index2.toInt() - 1)


        val requiredCharInValidPositionCount = password.toCharArray()
            .withIndex()
            .filter{ (idx, c) -> idx in indices && "$c" == requiredChar }
            .count()

        if (requiredCharInValidPositionCount == 1) validPasswordCount++
    }

    println("Total of valid passwords: $validPasswordCount")
}
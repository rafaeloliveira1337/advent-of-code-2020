package adventofcode2020.Day02

fun Day02Solution() {
    println("Day 02 Solution")

    var validPasswordCount = 0;

    Day02Input.input.forEach {
        val (criteria, password) = it.split(": ")
        val (minMax, requiredChar) = criteria.split(" ")
        val (min, max) = minMax.split("-")

        val requiredCharCount = password.toCharArray()
            .filter { "$it" == requiredChar }
            .count()

        if (requiredCharCount >= min.toInt() && requiredCharCount <= max.toInt()) validPasswordCount++
    }

    println("Total of valid passwords: $validPasswordCount")
}
package Day04

import java.io.File

fun Day04SolutionPart2() {
    println("Day 04 Solution - Part 2")

    val filePath = "${System.getProperty("user.dir")}/src/main/kotlin/Day04/Day04Input.txt"
    val input = File(filePath).readText(Charsets.UTF_8)

    val validPassports =
        input
            .split(Regex("(?m)^\\s*$")) // split entire content on blank lines
            .map {
                it.replace("\n", " ") // remove line breaks inside the passport data
                    .trim() // trim leading and trailing spaces
                    .split(" ") // split on spaces to get list of field:value
                    .map { it.split(":") } // split field and value
                    .filter { (field, value) -> field != "cid" && isValid(field, value) } // ignore "cid" field and drop invalid data
            }.filter {
                it.size == 7 // make sure all the required fields are present
            }.count()

    println("The number of valid passports is: $validPassports")
}

fun isValid(field: String, value: String): Boolean {
    return when (field) {
        "byr" -> value.toInt() in 1920..2002
        "iyr" -> value.toInt() in 2010..2020
        "eyr" -> value.toInt() in 2020..2030
        "hgt" -> validHeight(value)
        "hcl" -> "^#[0-9a-f]{6}".toRegex() matches value
        "ecl" -> value in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
        "pid" -> "^\\d{9}".toRegex() matches value
        "cid" -> true
        else -> false
    }
}

fun validHeight(height: String): Boolean {
    val regex = "^(\\d+)(in|cm)".toRegex()

    return regex.matches(height) && run {
        val (_, heightValue, unit) = regex.find(height)!!.groupValues

        if (unit == "cm") heightValue.toInt() in 150..193 else heightValue.toInt() in 59..76
    }
}
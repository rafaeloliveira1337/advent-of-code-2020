package Day04

import java.io.File

fun Day04Solution() {
    println("Day 04 Solution")

    val filePath = "${System.getProperty("user.dir")}/src/main/kotlin/Day04/Day04Input.txt"
    val input = File(filePath).readText(Charsets.UTF_8)

    val validPassports =
        input
            .split(Regex("(?m)^\\s*$")) // split entire content on blank lines
            .map {
                it.replace("\n"," ") // remove line breaks inside the passport data
                    .trim() // trim leading and trailing spaces
                    .split(" ") // split on spaces to get list of field:value
                    .map { it.substringBefore(":") } // keep only field
                    .filterNot { it == "cid" } // ignore "cid" field, as it doesn't matter for us
            }.filter {
                it.size == 7 // keep only the entries that have all 7 required fields (byr, iyr, eyr, hgt, hcl, ecl and pid)
            }.count()


    println("The number of valid passports is: $validPassports")
}

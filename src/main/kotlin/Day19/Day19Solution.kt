package Day19

import java.io.File

fun Day19Solution() {
    println("Day 19 Solution")

    val filePath = "${System.getProperty("user.dir")}/src/main/kotlin/Day19/Day19Input.txt"
    val (rulesTxt, messagesTxt) = File(filePath).readText(Charsets.UTF_8).split("\n\n") // load entire file

    val rules: RuleMap = rulesTxt.split("\n").map {
        val (ruleId, ruleText) = it.split(":")
        ruleId.toInt() to ruleText.trim()
    }.toMap()

    val messages = messagesTxt.split("\n")

    val result = messages.count { rules.matchesRules(it, 0, listOf(0)) }
    println("Result: $result")
}

fun Day19SolutionPart2() {
    println("Day 19 Solution - Part 2")
    val filePath = "${System.getProperty("user.dir")}/src/main/kotlin/Day19/Day19Input.txt"
    val (rulesTxt, messagesTxt) = File(filePath).readText(Charsets.UTF_8).split("\n\n") // load entire file

    val rules: RuleMap = rulesTxt.split("\n").map {
        val (ruleId, ruleText) = it.split(":")
        ruleId.toInt() to ruleText.trim()
    }.toMap()

    val messages = messagesTxt.split("\n")

    val updatedRules = rules.toMutableMap()
    updatedRules[8] = "42 | 42 8"
    updatedRules[11] = "42 31 | 42 11 31"

    val result = messages.count { updatedRules.matchesRules(it, 0, listOf(0)) }
    println("Result: $result")
}

typealias RuleMap = Map<Int,String>

fun RuleMap.matchesRules(message: String, ptr: Int,  ruleQueue: List<Int>): Boolean {
    println("msg: $message - ptr: $ptr - ruleQueue: $ruleQueue")
    return when {
        ptr >= message.length -> ruleQueue.isEmpty() // message must end as the rules end
        ruleQueue.isEmpty() -> false // exhausted rules without reaching end of message
        else -> {
            val ruleText = this[ruleQueue.first()]!!
            println("ruleText: $ruleText")
            when {
                ruleText.startsWith('"') -> (message[ptr] == ruleText[1]) &&
                        matchesRules(message, ptr+1, ruleQueue.drop(1)) // move to next char + rule
                else -> ruleText.split("|").map { it.trim() }
                    .firstOrNull { subRules ->  // make sure at least 1 sub-rule matches
                        // replace 1st rule's ID with the IDs from sub-rules and check again
                        matchesRules(message, ptr, subRules.split(" ").map { it.toInt() } + ruleQueue.drop(1))
                    } != null
            }
        }
    }
}
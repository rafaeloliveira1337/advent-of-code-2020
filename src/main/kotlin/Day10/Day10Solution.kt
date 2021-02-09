package Day10

fun Day10Solution() {
    println("Day 10 Solution")

    val input = Day10Input.input.sorted()
    var diff1 = 0
    var diff3 = 0

    var lastJolt = 0L
    for (jolt in input) {
        when(jolt - lastJolt) {
            1L -> diff1++
            3L -> diff3++
        }

        lastJolt = jolt
    }

    diff3++

    println("Diff 1: $diff1, diff 3: $diff3, product: ${diff1 * diff3}")
}

fun Day10SolutionPart2() {
    println("Day 10 Solution - Part 2")

    val input = Day10Input.input.sorted()

    val result = mutableMapOf(0L to 1L)

    for (jolt in input) {
        result[jolt] = (1..3).map { result.getOrDefault(jolt-it, 0) }.sum()
    }

    val combinations = result.getValue(input.last())

    println("Combinations: $combinations")
}
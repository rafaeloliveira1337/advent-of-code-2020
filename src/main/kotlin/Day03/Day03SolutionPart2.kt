package Day03

fun Day03SolutionPart2() {
    println("Day 03 Solution - Part 2")

    val input = Day03Input.input
    var trees = 0
    var totalProduct = 1L


    listOf(
        Pair(1, 1),
        Pair(3, 1),
        Pair(5, 1),
        Pair(7, 1),
        Pair(1, 2)
    ).forEach { (right, down) ->
        var col = right
        trees = 0

        for (row in down until input.size step down) {
            val chars = input[row].toCharArray()

            if (col >= chars.size) col -= chars.size

            if (chars.get(col) == '#') trees++

            col += right
        }
        totalProduct *= trees
        println("Trees found on the way: $trees. Product: $totalProduct")
    }

    println("Total product of Trees: $totalProduct")

}
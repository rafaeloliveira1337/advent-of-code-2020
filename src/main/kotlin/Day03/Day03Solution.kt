package Day03

fun Day03Solution() {
    println("Day 03 Solution")

    val input = Day03Input.input
    var trees = 0

    var col = 3;
    for (row in input.indices.drop(1)) {
        val chars = input[row].toCharArray()

        if (col >= chars.size) col -= chars.size

        if (chars.get(col) == '#') trees++

        col += 3
    }

    println("Trees found on the way: $trees")
}
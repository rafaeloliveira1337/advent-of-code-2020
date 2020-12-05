package Day05

fun Day05Solution() {
    println("Day 05 Solution")

    val seatIDs = mutableListOf<Int>()

    Day05Input.input.forEach { boardingPass ->
        println("Boarding Pass: $boardingPass")

        val row = binaryLookup(boardingPass.toCharArray(), 0, 127, 0)
        val col = binaryLookup(boardingPass.toCharArray(), 0, 7, 7)
        val seatId = row * 8 + col
        seatIDs.add(seatId)

        println("Seat ID: $row * 8 + $col = $seatId")
    }

    println("The highest Seat ID is: ${seatIDs.maxOrNull()}")
}

fun binaryLookup(boardingPass: CharArray, left: Int, right: Int, i: Int): Int {
    val mid = left + (right - left) / 2

    if (left == right) {
        return left
    }

    val char = boardingPass[i]

    // take left subarray if char is 'F' or 'L',
    // take right otherwise
    return if (char == 'F' || char == 'L') {
        binaryLookup(boardingPass, left, mid, i + 1)
    } else {
        binaryLookup(boardingPass, mid + 1, right, i + 1)
    }
}
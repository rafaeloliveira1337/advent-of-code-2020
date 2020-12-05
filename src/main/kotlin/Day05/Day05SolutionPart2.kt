package Day05


/*
 * The requirements of this problem got me confused and I only understood what was being asked when I read the explanation
 * on this reddit thread: https://www.reddit.com/r/adventofcode/comments/k727v4/2020_day_5_part_2_im_not_sure_what_this_one_is/
 */
fun Day05SolutionPart2() {
    println("Day 05 Solution - Part 2")

    val seatIDs = sortedSetOf<Int>()

    Day05Input.input.forEach { boardingPass ->
        println("Boarding Pass: $boardingPass")

        val row = binaryLookupPart2(boardingPass.toCharArray(), 0, 127, 0)
        val col = binaryLookupPart2(boardingPass.toCharArray(), 0, 7, 7)
        val seatId = row * 8 + col
        seatIDs.add(seatId)
    }

    val seatIDArray = seatIDs.toIntArray()

    for (i in 0..seatIDs.size - 2) {
        if (seatIDArray[i+1] - seatIDArray[i] == 2) {
            println("Your Seat ID is: ${seatIDArray[i] + 1}")
            break
        }
    }
}

fun binaryLookupPart2(boardingPass: CharArray, left: Int, right: Int, i: Int): Int {
    val mid = left + (right - left) / 2

    if (left == right) {
        return left
    }

    val char = boardingPass[i]

    // take left subarray if char is 'F' or 'L',
    // take right otherwise
    return if (char == 'F' || char == 'L') {
        binaryLookupPart2(boardingPass, left, mid, i + 1)
    } else {
        binaryLookupPart2(boardingPass, mid + 1, right, i + 1)
    }
}
package Day11

fun printMatrix(matrix: Array<CharArray>) {
    matrix.forEach { println(it) }
}

fun Array<CharArray>.copy() = Array(size) { get(it).clone() }

fun Day11Solution() {
    println("Day 11 Solution")

    var seats = Day11Input.input.map { it.toCharArray() }.toTypedArray()

    printMatrix(seats)

    var seatsChanged = true
    while (seatsChanged) {
        val (nextIteration, _seatsChanged) = getNextIterationPart1(seats)
        seats = nextIteration
        seatsChanged = _seatsChanged
    }

    val count = seats.map { it.count { it.equals('#') } }.sum()

    println("Count of #: $count")
}

fun Day11SolutionPart1Alternative() {
    println("Day 11 Solution - Part 1 Alternative")

    var seats = Day11Input.input.map { it.toCharArray() }.toTypedArray()

    printMatrix(seats)

    var seatsChanged = true
    while (seatsChanged) {
        val (nextIteration, _seatsChanged) = getNextIterationPart1Alternative(seats)
        seats = nextIteration
        seatsChanged = _seatsChanged
    }

    val count = seats.map { it.count { it.equals('#') } }.sum()

    println("Count of #: $count")
}

fun Day11SolutionPart2() {
    println("Day 11 Solution - Part 2")

    var seats = Day11Input.input.map { it.toCharArray() }.toTypedArray()

    printMatrix(seats)

    var seatsChanged = true
    while (seatsChanged) {
        val (nextIteration, _seatsChanged) = getNextIterationPart2(seats)
        seats = nextIteration
        seatsChanged = _seatsChanged
    }

    val count = seats.map { it.count { it.equals('#') } }.sum()

    println("Count of #: $count")
}

// Part 1
fun getNextIterationPart1(matrix: Array<CharArray>): Pair<Array<CharArray>, Boolean> {
    val nextIteration: Array<CharArray> = matrix.copy()
    val leftWall = 0
    val ceiling = 0
    val rightWall = matrix[0].size - 1
    val floor = matrix.size - 1
    var seatsChanged = false

    // count adjacent taken seats
    for (i in 0..floor) {
        for (j in 0..rightWall) {
            var takenSeats = 0
            val currentSeatState = matrix[i][j]

            if (i-1 >= ceiling) {
                if (matrix[i-1][j] == '#') takenSeats++

                if (j-1 >= leftWall && matrix[i-1][j-1] == '#') takenSeats++

                if (j+1 <= rightWall && matrix[i-1][j+1] == '#') takenSeats++
            }

            if (j-1 >= leftWall && matrix[i][j-1] == '#') takenSeats++

            if (i+1 <= floor) {
                if (j-1 >= leftWall && matrix[i+1][j-1] == '#') takenSeats++

                if (matrix[i+1][j] == '#') takenSeats++
            }

            if (j+1 <= rightWall) {
                if (i+1 <= floor && matrix[i+1][j+1] == '#') takenSeats++

                if (matrix[i][j+1] == '#') takenSeats++
            }

            val newSeatState = when {
                takenSeats == 0 && currentSeatState == 'L' -> {
                    seatsChanged = true
                    '#'
                }
                takenSeats >= 4 && currentSeatState == '#' -> {
                    seatsChanged = true
                    'L'
                }
                else -> currentSeatState
            }

            nextIteration[i][j] = newSeatState

        }
    }

    println("=============== Changed? $seatsChanged")
    printMatrix(nextIteration)
    return Pair(nextIteration, seatsChanged)
}

// Part 1 Alternative
fun getNextIterationPart1Alternative(matrix: Array<CharArray>): Pair<Array<CharArray>, Boolean> {
    val leftWall = 0
    val ceiling = 0
    val rightWall = matrix[0].size - 1
    val floor = matrix.size - 1
    var seatsChanged = false

    val nextIteration = matrix.mapIndexed { i, row ->
        row.mapIndexed { j, currentSeat ->
            if (currentSeat == '.') currentSeat

            var takenSeats = 0
            for (delta_i in -1..1) {
                for (delta_j in -1..1) {
                    if (delta_i == 0 && delta_j == 0) continue

                    val adjacentI = i + delta_i
                    val adjacentJ = j + delta_j

                    if ((adjacentI >= ceiling && adjacentI <= floor) && (adjacentJ >= leftWall && adjacentJ <= rightWall)
                        && matrix[adjacentI][adjacentJ] == '#'
                    ) {
                        takenSeats++
                    }
                }
            }

            when {
                takenSeats == 0 && currentSeat == 'L' -> {
                    seatsChanged = true
                    '#'
                }
                takenSeats >= 4 && currentSeat == '#' -> {
                    seatsChanged = true
                    'L'
                }
                else -> currentSeat
            }

        }.toCharArray()
    }.toTypedArray()

    println("=============== Changed? $seatsChanged")
    printMatrix(nextIteration)
    return Pair(nextIteration, seatsChanged)
}

// Part 2
fun getNextIterationPart2(matrix: Array<CharArray>): Pair<Array<CharArray>, Boolean> {
    val nextIteration: Array<CharArray> = matrix.copy()
    val leftWall = 0
    val ceiling = 0
    val rightWall = matrix[0].size - 1
    val floor = matrix.size - 1
    var seatsChanged = false

    // count adjacent taken seats
    for (i in 0..floor) {
        for (j in 0..rightWall) {
            var visibleTakenSeats = 0
            val currentSeatState = matrix[i][j]

            if (currentSeatState == '.') continue

            visibleTakenSeats += countTakenSeatInDirection(i, j, -1, 0,
                { a, _ -> a >= ceiling },
                matrix
            ) // up

            visibleTakenSeats += countTakenSeatInDirection(i, j, -1, -1,
                { a, b -> a >= ceiling && b >= leftWall },
                matrix
            ) // up Left

            visibleTakenSeats += countTakenSeatInDirection(i, j, 0, -1,
                { _, b -> b >= leftWall },
                matrix
            ) // left

            visibleTakenSeats += countTakenSeatInDirection(i, j, 1, -1,
                { a, b -> a <= floor && b >= leftWall },
                matrix
            ) // down left

            visibleTakenSeats += countTakenSeatInDirection(i, j, 1, 0,
                { a, _ -> a <= floor },
                matrix
            ) // down

            visibleTakenSeats += countTakenSeatInDirection(i, j, 1, 1,
                { a, b -> a <= floor && b <= rightWall },
                matrix
            ) // down right

            visibleTakenSeats += countTakenSeatInDirection(i, j, 0, 1,
                { _, b -> b <= rightWall },
                matrix
            ) // right

            visibleTakenSeats += countTakenSeatInDirection(i, j, -1, 1,
                { a, b -> a >= ceiling && b <= rightWall },
                matrix
            ) // up right

            val newSeatState = when {
                visibleTakenSeats == 0 && currentSeatState == 'L' -> {
                    seatsChanged = true
                    '#'
                }
                visibleTakenSeats >= 5 && currentSeatState == '#' -> {
                    seatsChanged = true
                    'L'
                }
                else -> currentSeatState
            }

            nextIteration[i][j] = newSeatState

        }
    }

    println("=============== Changed? $seatsChanged")
    printMatrix(nextIteration)
    return Pair(nextIteration, seatsChanged)
}

fun countTakenSeatInDirection(currentI: Int,
                              currentJ: Int,
                              iIncrement: Int,
                              jIncrement: Int,
                              isWithinBounds: (a: Int, b: Int) -> Boolean,
                              seats: Array<CharArray>): Int {

    val i = currentI + iIncrement
    val j = currentJ + jIncrement

    if (isWithinBounds(i, j)) {
        return when (seats[i][j]) {
            '#' -> 1
            'L' -> 0
            else -> countTakenSeatInDirection(i, j, iIncrement, jIncrement, isWithinBounds, seats)
        }
    }

    return 0
}
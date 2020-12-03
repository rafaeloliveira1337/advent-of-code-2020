package Day01

fun Day01SolutionPart2() {
    println("Day 01 Solution - Part 2")

    val input = Day01Input.entries.sorted()

    loop@for (i in 0..input.size - 2) {
        var left = i + 1
        var right = input.size - 1
        while (left < right) {
            val currentSum = input.get(i) + input.get(left) + input.get(right)
            if (currentSum == 2020) {
                println("The three numbers that equal 2020  are ${input.get(i)}, ${input.get(left)} and ${input.get(right)}.\nTheir product is: ${input.get(i) * input.get(left) * input.get(right)}")
                break@loop
            } else if (currentSum < 2020) {
                left++;
            } else {
                right--;
            }
        }
    }
}
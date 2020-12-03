package Day01

fun Day01Solution() {
    println("Day 01 Solution")

    val subtrahendSet = mutableSetOf<Int>()

    for (i in Day01Input.entries) {
        val remainder = 2020 - i

        if (!subtrahendSet.contains(remainder)) {
            subtrahendSet.add(i)
        } else {
            println("The two numbers that equal 2020 are $remainder and $i.\nTheir product is: ${remainder * i}")
            break
        }
    }
}
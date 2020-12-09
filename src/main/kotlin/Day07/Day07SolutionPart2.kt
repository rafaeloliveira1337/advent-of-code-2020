package Day07

fun sumChildrenOf(child: String, parentToChildrenMap: Map<String, List<ContainedBag>>): Int {
    return parentToChildrenMap[child]!!
        .map { it.num * (1 + sumChildrenOf(it.name, parentToChildrenMap)) }
        .sum()
}

fun Day07SolutionPart2() {
    println("Day 07 Solution - Part 2")

    val parentToChildrenMap = Day07Input.input.map { it.toRule() }.toList()
        .associate { it.containing to it.contained }

    val answer = sumChildrenOf("shiny gold", parentToChildrenMap)
    println("The number bags required inside a 'shiny gold' bag is: $answer ")
}
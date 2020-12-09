package Day07

data class ContainedBag(val num: Int, val name: String)
data class Rule(val containing: String, val contained: List<ContainedBag>)

fun String.toRule(): Rule {
    val (containing, contained) = split(" contain ")
    val containingBag = containing.substringBefore(" bags")
    if (contained == "no other bags.") return Rule(containingBag, emptyList())
    val containedBags = contained.split(", ", ".").filter { it.isNotEmpty() }.map {
        val containedBagAndAmount = it.substringBefore(" bag")
        val (amount, bag) = containedBagAndAmount.split(" ", limit=2)

        ContainedBag(amount.toInt(), bag)
    }
    return Rule(containingBag, containedBags)
}

fun findParentsOf(child: String, parents: MutableSet<String>, childToImmediateParentMap: Map<String, List<String>>) {
    for (bag in childToImmediateParentMap[child] ?: return) {
        if (bag in parents) continue
        parents.add(bag)
        findParentsOf(bag, parents, childToImmediateParentMap)
    }
}

fun Day07Solution() {
    println("Day 07 Solution")

    val childToImmediateParentMap = mutableMapOf<String, MutableList<String>>()

    Day07Input.input.map { it.toRule() }.toList().forEach { rule ->
        rule.contained.forEach {
            childToImmediateParentMap.getOrPut(it.name) { mutableListOf() }.add(rule.containing)
        } }

    val parentSet = mutableSetOf<String>()
    findParentsOf("shiny gold", parentSet, childToImmediateParentMap)

    println("The number of bag colors that can eventually contain a 'shiny gold' bag is: ${parentSet.size} ")
}
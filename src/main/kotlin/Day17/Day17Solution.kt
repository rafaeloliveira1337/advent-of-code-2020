package Day17

import java.io.File

fun Day17Solution() {
    println("Day 17 Solution")

    val filePath = "${System.getProperty("user.dir")}/src/main/kotlin/Day17/Day17Input.txt"
    val lines = File(filePath).readText(Charsets.UTF_8) // load entire file
        .split("\n") // split on new line character

    var state = getInitialActiveNodes(lines, hypercube = false)
    for (i in 0 until 6) {
        state = runSimulation(state, hypercube = false)
    }

    println(state)
    println("Result: ${state.size}")
}

fun Day17SolutionPart2() {
    println("Day 17 Solution - Part 2")

    val filePath = "${System.getProperty("user.dir")}/src/main/kotlin/Day17/Day17Input.txt"
    val lines = File(filePath).readText(Charsets.UTF_8) // load entire file
        .split("\n") // split on new line character

    var state = getInitialActiveNodes(lines, hypercube = true)
    for (i in 0 until 6) {
        state = runSimulation(state, hypercube = true)
    }

    println(state)
    println("Result: ${state.size}")
}

private fun runSimulation(activeNodes: Set<String>, hypercube: Boolean): Set<String> {
    val newActiveNodes = mutableSetOf<String>()
    val inactiveToActiveNeighborCount = mutableMapOf<String, Int>()

    for(node in activeNodes) {
        val neighbors = if (hypercube) {
            get4DNeighborCoordinates(node)
        } else get3DNeighborCoordinates(node)

        val (active, inactiveNodesWithActiveNeighbor) = neighbors.partition { activeNodes.contains(it) }
        val activeNeighborCount = active.size
        inactiveNodesWithActiveNeighbor.groupingBy { it }.eachCountTo(inactiveToActiveNeighborCount)

        if (activeNeighborCount == 2 || activeNeighborCount == 3) newActiveNodes.add(node)
    }

    // make "active" the inactive nodes that have exactly 3 active neighbors
    newActiveNodes.addAll(inactiveToActiveNeighborCount.filterValues { it == 3 }.keys)

    return newActiveNodes
}


private fun getInitialActiveNodes(lines: List<String>, hypercube: Boolean): Set<String> {
    val nodes = mutableSetOf<String>()

    for (x in lines.indices) {
        for (y in lines[x].indices) {
            if (lines[x][y] == '#') nodes.add(if (hypercube) "$x,$y,0,0" else "$x,$y,0")
        }
    }

    return nodes
}

private fun get3DNeighborCoordinates(node: String): Set<String> {
    val neighbors = mutableSetOf<String>()
    val (x, y, z) = node.split(",").map { it.toInt() }

    for (deltaX in -1..1) {
        for (deltaY in -1..1) {
            for (deltaZ in -1..1) {
                neighbors.add("${x + deltaX},${y + deltaY},${z + deltaZ}")
            }
        }
    }

    neighbors.remove(node)
    return neighbors
}

private fun get4DNeighborCoordinates(node: String): Set<String> {
    val neighbors = mutableSetOf<String>()
    val (x, y, z, w) = node.split(",").map { it.toInt() }

    for (deltaX in -1..1) {
        for (deltaY in -1..1) {
            for (deltaZ in -1..1) {
                for (deltaW in -1..1) {
                    neighbors.add("${x + deltaX},${y + deltaY},${z + deltaZ},${w + deltaW}")
                }
            }
        }
    }

    neighbors.remove(node)
    return neighbors
}
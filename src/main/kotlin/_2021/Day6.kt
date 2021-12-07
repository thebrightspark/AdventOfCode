package _2021

import aocRun
import splitToInts
import splitToLongs

fun main() {
    aocRun(puzzleInput) { input ->
        return@aocRun simulate(input, 80)
    }
    aocRun(puzzleInput) { input ->
        return@aocRun simulate(input, 256)
    }
}

private fun simulate(input: String, iterations: Int): Long {
    val fish = input.splitToInts(",")
    val fishByAge = (0..8).mapTo(mutableListOf()) { age -> fish.count { it == age }.toLong() }
    repeat(iterations) {
        fishByAge.add(fishByAge.removeFirst())
        fishByAge[6] = fishByAge[6] + fishByAge[8]
    }
    return fishByAge.sum()
}

private val testInput = "3,4,3,1,2"

private val puzzleInput =
    "5,3,2,2,1,1,4,1,5,5,1,3,1,5,1,2,1,4,1,2,1,2,1,4,2,4,1,5,1,3,5,4,3,3,1,4,1,3,4,4,1,5,4,3,3,2,5,1,1,3,1,4,3,2,2,3,1,3,1,3,1,5,3,5,1,3,1,4,2,1,4,1,5,5,5,2,4,2,1,4,1,3,5,5,1,4,1,1,4,2,2,1,3,1,1,1,1,3,4,1,4,1,1,1,4,4,4,1,3,1,3,4,1,4,1,2,2,2,5,4,1,3,1,2,1,4,1,4,5,2,4,5,4,1,2,1,4,2,2,2,1,3,5,2,5,1,1,4,5,4,3,2,4,1,5,2,2,5,1,4,1,5,1,3,5,1,2,1,1,1,5,4,4,5,1,1,1,4,1,3,3,5,5,1,5,2,1,1,3,1,1,3,2,3,4,4,1,5,5,3,2,1,1,1,4,3,1,3,3,1,1,2,2,1,2,2,2,1,1,5,1,2,2,5,2,4,1,1,2,4,1,2,3,4,1,2,1,2,4,2,1,1,5,3,1,4,4,4,1,5,2,3,4,4,1,5,1,2,2,4,1,1,2,1,1,1,1,5,1,3,3,1,1,1,1,4,1,2,2,5,1,2,1,3,4,1,3,4,3,3,1,1,5,5,5,2,4,3,1,4"

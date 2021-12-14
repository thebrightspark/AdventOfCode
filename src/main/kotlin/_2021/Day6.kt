package _2021

import aoc
import splitToInts

fun main() {
	aoc(2021, 6) {
		aocRun {
			simulate(it, 80)
		}
		aocRun {
			simulate(it, 256)
		}
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

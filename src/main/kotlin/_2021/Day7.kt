package _2021

import aoc
import range
import splitToInts
import kotlin.math.max
import kotlin.math.min

fun main() {
	aoc(2021, 7) {
		aocRun {
			calcBestPosition(it) { pos, target -> max(pos, target) - min(pos, target) }
		}
		aocRun {
			calcBestPosition(it) { pos, target ->
				val distance = max(pos, target) - min(pos, target)
				(distance * (distance + 1)) / 2
			}
		}
	}
}

private fun calcBestPosition(input: String, fuelCalc: (pos: Int, target: Int) -> Int): String {
	val positions = input.splitToInts(",")
	return range(positions.minOrNull()!!, positions.maxOrNull()!!)
		.associateWith { target -> positions.sumOf { fuelCalc(it, target) } }
		.minByOrNull { it.value }
		?.let { "Pos: ${it.key}, Fuel: ${it.value}" }
		?: "Wut"
}

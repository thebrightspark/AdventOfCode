package _2021

import aoc
import parseInput
import range
import java.util.regex.Pattern

private val REGEX = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)")

fun main() {
	aoc(2021, 5) {
		aocRun {
			countOverlappingPoints(it, true)
		}
		aocRun {
			countOverlappingPoints(it, false)
		}
	}
}

private fun parseLines(input: String): List<Line> = parseInput(REGEX, input) {
	Line(it.group(1).toInt() to it.group(2).toInt(), it.group(3).toInt() to it.group(4).toInt())
}

private fun countOverlappingPoints(input: String, horizontalOrVerticalOnly: Boolean): Int {
	val posCounts = mutableMapOf<Pair<Int, Int>, Int>()
	parseLines(input)
		.let { lines -> if (horizontalOrVerticalOnly) lines.filter { it.isHorizontalOrVertical } else lines }
		.map { it.getAllPositions(horizontalOrVerticalOnly) }
		.forEach { positions ->
			positions.forEach { posCounts.compute(it) { _, v -> if (v == null) 1 else v + 1 } }
		}
	return posCounts.values.count { it >= 2 }
}

private class Line(val pos1: Pair<Int, Int>, val pos2: Pair<Int, Int>) {
	private val diffX: Int = pos1.first.compareTo(pos2.first)
	private val diffY: Int = pos1.second.compareTo(pos2.second)
	val isHorizontalOrVertical: Boolean
		get() = (diffX == 0 || diffY == 0) && diffX != diffY

	fun getAllPositions(horizontalOrVerticalOnly: Boolean): List<Pair<Int, Int>> = when {
		isHorizontalOrVertical -> {
			when {
				diffX != 0 -> {
					val y = pos1.second
					range(pos1.first, pos2.first).map { it to y }
				}
				diffY != 0 -> {
					val x = pos1.first
					range(pos1.second, pos2.second).map { x to it }
				}
				else -> emptyList()
			}
		}
		!horizontalOrVerticalOnly -> {
			val rangeX = range(pos1.first, pos2.first)
			val rangeY = range(pos1.second, pos2.second)
			rangeX.zip(rangeY)
		}
		else -> emptyList()
	}

	override fun toString(): String = "${pos1.first},${pos1.second} -> ${pos2.first},${pos2.second}"
}

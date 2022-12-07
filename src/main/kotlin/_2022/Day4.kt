package _2022

import aoc
import parseInput
import java.util.regex.Pattern

private val REGEX = Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)")

fun main() {
	aoc(2022, 4) {
		aocRun { input ->
			process(input, ::hasFullyContainedSections)
		}
		aocRun { input ->
			process(input, ::hasOverlappedSections)
		}
	}
}

private fun process(input: String, comparison: (elf1: Pair<Int, Int>, elf2: Pair<Int, Int>) -> Boolean): Long =
	parseInput(REGEX, input) {
		(it.group(1).toInt() to it.group(2).toInt()) to (it.group(3).toInt() to it.group(4).toInt())
	}.sumOf { pair -> if (comparison(pair.first, pair.second)) 1L else 0L }

private fun hasFullyContainedSections(elf1: Pair<Int, Int>, elf2: Pair<Int, Int>): Boolean =
	(elf1.first >= elf2.first && elf1.second <= elf2.second) || (elf2.first >= elf1.first && elf2.second <= elf1.second)

private fun hasOverlappedSections(elf1: Pair<Int, Int>, elf2: Pair<Int, Int>): Boolean =
	hasFullyContainedSections(elf1, elf2)
		|| isBetween(elf1.first, elf2) || isBetween(elf1.second, elf2)
		|| isBetween(elf2.first, elf1) || isBetween(elf2.second, elf1)

private fun isBetween(num: Int, pair: Pair<Int, Int>): Boolean = num >= pair.first && num <= pair.second

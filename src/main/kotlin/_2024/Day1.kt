package _2024

import REGEX_LINE_SEPARATOR
import aoc
import kotlin.math.abs

fun main() {
	fun parseToLists(input: String): Pair<MutableList<Int>, MutableList<Int>> {
		val list1 = mutableListOf<Int>()
		val list2 = mutableListOf<Int>()
		input.splitToSequence(REGEX_LINE_SEPARATOR)
			.map { "(\\d+)\\s+(\\d+)".toRegex().find(it)!! }
			.forEach {
				list1 += it.groups[1]!!.value.toInt()
				list2 += it.groups[2]!!.value.toInt()
			}
		return list1 to list2
	}

	aoc(2024, 1) {
		aocRun { input ->
			val (list1, list2) = parseToLists(input)
			list1.sort()
			list2.sort()

			return@aocRun list1.indices.sumOf { abs(list1[it] - list2[it]) }
		}
		aocRun { input ->
			val (list1, list2) = parseToLists(input)
			val similarityMap = mutableMapOf<Int, Int>()
			return@aocRun list1.sumOf { num -> similarityMap.computeIfAbsent(num) { list2.count { it == num } * num } }
		}
	}
}

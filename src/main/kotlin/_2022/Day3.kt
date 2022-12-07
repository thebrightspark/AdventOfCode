package _2022

import REGEX_LINE_SEPARATOR
import aoc

const val LOWER_CODE_OFFSET = 'a'.code - 1
const val UPPER_CODE_OFFSET = 'A'.code - 27

fun main() {
	aoc(2022, 3) {
		aocRun { input ->
			input.split(REGEX_LINE_SEPARATOR).sumOf { contents ->
				findCommonItemsInRucksack(contents).sumOf { itemPriority(it) }
			}
		}
		aocRun { input ->
			input.split(REGEX_LINE_SEPARATOR).chunked(3).sumOf { group ->
				itemPriority(findCommonItemInGroup(group))
			}
		}
	}
}

private fun findCommonItemsInRucksack(contents: String): Set<Char> {
	val indexMid = contents.length / 2
	val first = contents.substring(0, indexMid).toSet()
	val second = contents.substring(indexMid).toSet()
	return first.intersect(second)
}

private fun findCommonItemInGroup(group: List<String>): Char {
	var commonItems = group[0].toSet()
	group.subList(1, group.size).forEach {
		commonItems = commonItems.intersect(it.toSet())
	}
	return commonItems.single()
}

private fun itemPriority(item: Char): Int =
	if (item in ('a'..'z')) item.code - LOWER_CODE_OFFSET else item.code - UPPER_CODE_OFFSET

package _2022

import REGEX_LINE_SEPARATOR
import aoc

const val LOWER_CODE_OFFSET = 'a'.code - 1
const val UPPER_CODE_OFFSET = 'A'.code - 27

fun main() {
	aoc(2022, 3) {
		aocRun { input ->
			process(input.split(REGEX_LINE_SEPARATOR))
		}
//        aocRun(testInput) { input ->
//        }
	}
}

private fun process(input: List<String>): Int = input.sumOf { contents ->
	findCommonItems(contents).sumOf { itemPriority(it) }
}

private fun findCommonItems(contents: String): Set<Char> {
	val indexMid = contents.length / 2
	val first = contents.substring(0, indexMid).toSet()
	val second = contents.substring(indexMid).toSet()
//	println("$contents\n\t$first\n\t$second")
	val common = first.intersect(second)
//	println("Common: $common")
	return common
}

private fun itemPriority(item: Char): Int =
	if (item in ('a'..'z')) item.code - LOWER_CODE_OFFSET else item.code - UPPER_CODE_OFFSET

private val testInput = """
vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw
""".trimIndent()

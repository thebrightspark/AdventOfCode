package _2021

import REGEX_LINE_SEPARATOR
import REGEX_WHITESPACE
import aoc

fun main() {
	aoc(2021, 8) {
		aocRun { input ->
			input.split(REGEX_LINE_SEPARATOR).sumOf { line ->
				line.substringAfter(" | ").split(REGEX_WHITESPACE).count { digit ->
					digit.length.let { it == 2 || it == 3 || it == 4 || it == 7 }
				}
			}
		}
		aocRun { input ->
			input.split(REGEX_LINE_SEPARATOR)
				.mapTo(mutableListOf()) { line ->
					lateinit var signals: List<String>
					lateinit var code: List<String>
					line.split(" | ").let { lineParts ->
						signals = lineParts[0].split(REGEX_WHITESPACE).map { it.sortChars() }
						code = lineParts[1].split(REGEX_WHITESPACE).map { it.sortChars() }
					}

					val output = run {
						val digits = mutableMapOf<String, Int>()

						lateinit var one: String
						lateinit var four: String
						lateinit var seven: String
						val fiveSegmentDigits = mutableListOf<String>()
						val sixSegmentDigits = mutableListOf<String>()
						// Find the digits with unique amounts of segments, and collect digits with 5 and 6 segments
						signals.forEach {
							when (it.length) {
								2 -> {
									one = it
									digits[it] = 1
								}
								3 -> {
									seven = it
									digits[it] = 7
								}
								4 -> {
									four = it
									digits[it] = 4
								}
								// 2, 3, 5
								5 -> fiveSegmentDigits += it
								// 0, 6, 9
								6 -> sixSegmentDigits += it
								7 -> digits[it] = 8
							}
						}

						if (code.none { it.length == 5 || it.length == 6 }) {
							// If there's no 5 or 6 segment digits in the code, then there's no need to figure them out
							return@run code.map { digits[it] }.joinToString("")
						}

						// Determine the 5 segment digits
						// 3 is the only digit that has all the segments in 7
						// 2 is the only digit that doesn't have two of the segments in 4
						// 5 is just the remaining digit
						val sevenSegments = seven.toCharArray()
						val three = fiveSegmentDigits.single { it.containsAllSegments(sevenSegments) }
						digits[three] = 3
						val fourSegments = four.toCharArray()
						val two = fiveSegmentDigits.single { it.countMissingSegments(fourSegments) == 2 }
						digits[two] = 2
						val five = fiveSegmentDigits.single { it != three && it != two }
						digits[five] = 5

						// Determine the 6 segment digits
						// 6 is the only digit that doesn't have one of the segments in 1
						// 0 is the only digit that doesn't have one of the segments in 5
						// 9 is just the remaining digit
						val oneSegments = one.toCharArray()
						val six = sixSegmentDigits.single { it.countMissingSegments(oneSegments) == 1 }
						digits[six] = 6
						val fiveSegments = five.toCharArray()
						val zero = sixSegmentDigits.single { it.countMissingSegments(fiveSegments) == 1 }
						digits[zero] = 0
						val nine = sixSegmentDigits.single { it != six && it != zero }
						digits[nine] = 9

						return@run code.map { digits[it] }.joinToString("")
					}

//					println("${code.joinToString(" ")} = $output")
					return@mapTo output
				}
				.sumOf { it.toInt() }
		}
	}
}

private fun String.sortChars(): String = this.toCharArray().sorted().joinToString("")

private fun String.containsAllSegments(otherDigitChars: CharArray): Boolean =
	this.toCharArray().let { chars -> otherDigitChars.all { chars.contains(it) } }

private fun String.countMissingSegments(otherDigitChars: CharArray): Int =
	otherDigitChars.toMutableSet().also { it.removeAll(this.toCharArray().toSet()) }.size

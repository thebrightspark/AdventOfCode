package _2021

import REGEX_LINE_SEPARATOR
import aoc
import kotlin.math.ceil

private val CHUNK_MAP = mapOf(
	')' to '(',
	']' to '[',
	'}' to '{',
	'>' to '<'
)
private val CHUNK_MAP_REVERSED = CHUNK_MAP.entries.associateBy({ it.value }, { it.key })

fun main() {
	aoc(2021, 10) {
		aocRun { input ->
			input.split(REGEX_LINE_SEPARATOR).sumOf { processLine(it).first }
		}
		aocRun { input ->
			val scores = input.split(REGEX_LINE_SEPARATOR).asSequence()
				.map { processLine(it) }
				.filter { it.first == 0 }
				.map { chunks -> chunks.second.reversed().map { CHUNK_MAP_REVERSED[it]!! } }
				.map { chunks ->
					chunks
						.map {
							when (it) {
								')' -> 1L
								']' -> 2L
								'}' -> 3L
								'>' -> 4L
								else -> error("Pls why")
							}
						}
						.reduce { acc, chunk -> (acc * 5L) + chunk }
//						.also { println("${chunks.joinToString("")} -> $it") }
				}
				.sorted()
				.toList()
			return@aocRun scores[ceil(scores.size.toFloat() / 2F).toInt() - 1]
		}
	}
}

private fun processLine(line: String): Pair<Int, List<Char>> {
	val chunks = mutableListOf<Char>()
	for (char in line.toCharArray()) {
		when (char) {
			'(', '[', '{', '<' -> chunks += char
			')', ']', '}', '>' -> {
				val openChar = CHUNK_MAP[char]!!
				val lastChunk = chunks.last()
				if (lastChunk != openChar) {
					// Illegal! Can't close a chunk with open chunks inside!
					return when (char) {
						')' -> 3
						']' -> 57
						'}' -> 1197
						'>' -> 25137
						else -> error("This shouldn't happen -> '$char'")
					} to chunks
				}
				chunks.removeLast()
			}
		}
	}
	return 0 to chunks
}

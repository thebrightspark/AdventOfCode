package _2022

import aoc
import java.util.LinkedList

fun main() {
	aoc(2022, 6) {
		aocRun { process(it, 4) }
        aocRun { process(it, 14) }
	}
}

private fun process(input: String, markerSize: Int): Int {
	val slice = LinkedList(input.substring(0, markerSize).toList())
	if (slice.distinct().size == markerSize)
		return markerSize

	(markerSize until input.length).forEach {
		slice.removeFirst()
		slice.add(input[it])

		if (slice.distinct().size == markerSize)
			return it + 1
	}

	error("wat")
}

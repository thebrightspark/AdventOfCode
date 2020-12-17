package _2020

import aocRun
import splitToIntsMut

fun main() {
	aocRun(puzzleInput) { input ->
		runGame(input, 2020)
	}
	aocRun(puzzleInput) { input ->
		runGame(input, 30_000_000)
	}
}

private fun runGame(input: String, turns: Int): Int {
	val numbers = input.splitToIntsMut(",")
	val lastPositions = mutableMapOf<Int, Int>()
	(0 until numbers.size - 1).map { numbers[it] }.forEachIndexed { index, num ->
		lastPositions[num] = index + 1
	}
	var num = numbers.last()
	((numbers.size + 1)..turns).forEach {
		val lastNum = num
		val index = lastPositions.getOrDefault(num, it - 1)
		num = it - 1 - index
		lastPositions[lastNum] = it - 1
//		println("$it: $lastNum -> $index ($num)")
	}
	return num
}

private const val puzzleInput = "9,19,1,6,0,5,4"

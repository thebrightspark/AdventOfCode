package _2021

import REGEX_LINE_SEPARATOR
import aoc

fun main() {
	aoc(2021, 9) {
		aocRun { input ->
			val grid = parseGrid(input)
			val deepestDepths = findDeepestDepths(grid)
			return@aocRun deepestDepths.map { (x, y) -> grid[y][x] }.sumOf { it + 1 }
		}
		aocRun { input ->
			val grid = parseGrid(input)
			val deepestDepths = findDeepestDepths(grid)
			return@aocRun deepestDepths.map { findBasin(grid, it.first, it.second).size }
				.sortedDescending()
				.take(3)
				.reduce { acc, v -> acc * v }
		}
	}
}

private fun parseGrid(input: String): List<List<Int>> =
	input.split(REGEX_LINE_SEPARATOR).map { row -> row.map { it.toString().toInt() } }

private fun findDeepestDepths(grid: List<List<Int>>): List<Pair<Int, Int>> = mutableListOf<Pair<Int, Int>>().apply {
	grid.forEachIndexed { y, row ->
		row.forEachIndexed { x, depth ->
			if (isDeepestDepth(grid, x, y, depth))
				this += x to y
		}
	}
}

private fun getAdjacentPositions(grid: List<List<Int>>, x: Int, y: Int): List<Pair<Int, Int>> {
	val gridHeight = grid.size
	val gridWidth = grid[0].size
	return arrayOf(x to y - 1, x to y + 1, x - 1 to y, x + 1 to y)
		.filter { it.first in 0 until gridWidth && it.second in 0 until gridHeight }
}

private fun isDeepestDepth(grid: List<List<Int>>, x: Int, y: Int, depth: Int): Boolean =
	getAdjacentPositions(grid, x, y).map { grid[it.second][it.first] }.all { it > depth }

private fun findBasin(grid: List<List<Int>>, x: Int, y: Int): List<Pair<Int, Int>> =
	mutableListOf<Pair<Int, Int>>().also { list ->
		getAdjacentPositions(grid, x, y).forEach {
			findBasinInternal(grid, it.first, it.second, list)
		}
	}

private fun findBasinInternal(grid: List<List<Int>>, x: Int, y: Int, basin: MutableList<Pair<Int, Int>>) {
	val pos = x to y
	if (grid[y][x] == 9 || basin.contains(pos))
		return
	basin += pos
	getAdjacentPositions(grid, x, y).filter { !basin.contains(it) }.forEach {
		findBasinInternal(grid, it.first, it.second, basin)
	}
}

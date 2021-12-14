package _2021

import aoc

fun main() {
	aoc(2021, 9) {
		aocRun { input ->
			val deepestDepths = mutableListOf<Int>()
			val grid = input.split("\n").map { row -> row.map { it.toString().toInt() } }
			grid.forEachIndexed { y, row ->
				row.forEachIndexed { x, depth ->
					if (isDeepestDepth(grid, x, y, depth))
						deepestDepths += depth
				}
			}
			return@aocRun deepestDepths.sumOf { it + 1 }
		}
//    aocRun(testInput) { input ->
//    }
	}
}

private fun isDeepestDepth(grid: List<List<Int>>, x: Int, y: Int, depth: Int): Boolean {
	val gridHeight = grid.size
	val gridWidth = grid[0].size
	return arrayOf(x to y - 1, x to y + 1, x - 1 to y, x + 1 to y).asSequence()
		.filter { it.first in 0 until gridWidth && it.second in 0 until gridHeight }
		.map { grid[it.second][it.first] }
		.all { it > depth }
}

private val testInput = """
2199943210
3987894921
9856789892
8767896789
9899965678
""".trimIndent()

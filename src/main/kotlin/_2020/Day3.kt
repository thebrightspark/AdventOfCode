package _2020

import aocRun
import toMut

fun main() {
	aocRun(puzzleInput) { input ->
		return@aocRun countTrees(parseInput(input), 3, 1)
	}
	aocRun(puzzleInput) { input ->
		val trees = parseInput(input)
		return@aocRun countTrees(trees, 1, 1) *
			countTrees(trees, 3, 1) *
			countTrees(trees, 5, 1) *
			countTrees(trees, 7, 1) *
			countTrees(trees, 1, 2)
	}
}

private fun parseInput(input: String): List<List<Char>> = input.split("\n").map { it.toList() }

private fun countTrees(trees: List<List<Char>>, velX: Int, velY: Int): Long {
	val velocity = velY to velX
	val pos = 0 toMut 0
	pos.first += velocity.first
	pos.second += velocity.second
	val height = trees.size
	val width = trees[0].size
	var treeCount = 0L
	while (pos.first < height) {
		val row = trees[pos.first]
		val actualX = pos.second - (pos.second / row.size) * width
		val c = row[actualX]
		if (c == '#')
			treeCount++

		pos.first += velocity.first
		pos.second += velocity.second
	}
	return treeCount
}

private val puzzleInput = """
.....#.##......#..##..........#
##.#.##..#............##....#..
......###...#..............#.##
.....#..##.#..#......#.#.#..#..
..#.......###..#..........#.#..
..#..#.##.......##.....#....#..
.##....##....##.###.....###..#.
..##....#...##..#....#.#.#.....
.....##..###.##...............#
#.....#..#....#.##...####..#...
#......#.#....#..#.##....#..#.#
##.#...#.#............#......#.
.#####.......#..#.#....#......#
..#.#....#.#.##...#.##...##....
.....#.#...#..####.##..#.......
#....#...##.#.#.##.#..##.....#.
##.##...#....#...#......#..##..
....##...#..#.#...#.#.#.....##.
..#....##......##....#.#....#..
#..#....#....###..#.##....#.#.#
..#.#####..##....#....#.....##.
.#...##.......#...#....#.#...##
#.#.#.##.......#.....#.#.#....#
.#.#.....#.......#.......##....
.#......#....#....#.......##...
#......#.....#......#..#..#....
#.#...#...#....##....#.#...#..#
....#.....##...#...#..#.#......
..#......#..........#...#.#....
..#..#......####..##...###.....
.#.....#...##...#.##........###
#.#....#..#....#..#.....#.#..#.
...##.##.#.#.##...#.....#......
##....#.#.#...####.#.#.#.#.....
.##.........#..#..###..........
..##.###.#..#..#....##.....#...
##........#..###....#.#..#..#..
....#.#.......##..#.#.#.#......
....##.....#.........##.......#
..#........##.#.........###..##
....#..................##..#...
#...#.#..###..#.....#..#..#...#
..#..#.##..#..#.......#.......#
.....#..##..#....##...........#
..##...#........#...#.#.......#
.........#.#..#.#..#.##.#.###..
....#...#..#..#......##....#.#.
..#..#.#....#....#..#.####..##.
##....#.....#......##.###.#..#.
#..#..##..###......#.#.#.#...#.
.......#..##..##...#...#..#....
..#.###.#...#....#.##.#.....##.
.#.#.......##...##...##....#...
#...#.#.#...#.####..#..##......
###..#.##..#..........#...#....
##.#.........#..##......####...
..##.#..#....#.##..............
...#....#.......###............
...#.....##....#.#.#.#.......##
###.###...#...#...###.##...##..
#.#....#.##..#.....#.....##.#..
...#....#....#.........#....#.#
##.#....#........#..#..##.#....
.#.#..#.......#...##.......#...
.##...##........#....#.#..#....
....#..#.##.###.....#.#........
.#.#...#.#..#.....#.........#..
.......#.#.#..##....#.........#
.##...#....#..#...#........#..#
....#....#..#.#..#.#.#....##.##
..##....#.....##..#.#...#...#..
#.##.........#.....#.......#.##
...#...##.#.#..........#......#
###...#.....#..#.......#####..#
#.####...##.#.#..#...#.........
.##.....#.....##..#...##.##....
.........###...#......##....###
.#....##...###.#..#...##..#.#.#
.......#.......#.#...##.#......
.....#.#........#..##.....##...
....#.#.........##.#...##..#.#.
#..#..#.##..#.##.##.....##.###.
..##.........###...#....#....#.
.###...#..#.##...........#.....
#..##..........#..........#....
.....#.#....#..##..#...#.#....#
..#.....#.#....#...##.##.......
##.....##........#....#..##....
.#..#.#.........#..#..#........
.............##....#....#..#...
....##....#..#.#.##....###.##.#
.###..#.....#..#..##..#..##..#.
...#..###.......#.#....#..###..
#.#..#.....#...#......#........
#..#..............###.#......#.
..#....##.#....#.##.#.#...#....
.........##..#...#.#.......#...
........#...#.#....#.....##..#.
...#.##..#..#..###..#..#......#
.....####......#...#....#...#.#
...###.#.#......#....#.......#.
#...##.#....#....##....##.###..
.......##...##.....#.##.#..#..#
.....#.#............##...#.####
.##..#.#.#.#..#.#.#.....#.##...
.#..####...#.#....#.....#..#...
....##..#.#...#..#....#.#......
...#......###..#..###..#.....#.
.#.#.#..##....#...##..#.....#..
###....#....#...##.....#...#...
#.##....#......#...###.........
.#..#.#...#..#....#....#....#..
...............##...####..#..#.
#.#...........####..#...##.....
##.#....#........#......#...##.
......#...#...#....#....#.....#
#......#.............#....###..
.#...#...##.....#...##.##..#...
..#.#......#.#........#........
.......#..#.#...##..#.#.#......
..##...#.##........#....#.#...#
.....#..#..#........#.#......##
....#.#...##............##....#
.#.#....#.#.#...#...#.##.....#.
#.#.##...#....#.#.#..#.##..#.#.
.........####..#...#...#.......
#..#..####......#..##..#...#...
.........##..................#.
.....##.#..##.#.#...#......##..
...#....#....#.#.....#...#..#.#
#...##.#...##...........#..#...
#..........#.#..#..#.##..#..#.#
.#...#.##...#.#.#..#.......##..
.........#...........#..#..#...
.##...##....#.#......#........#
#.#...........#....#.......#...
##.#.#.......#...###......##..#
...###..#.##..##.#.#.......#...
.#...#..##.#...#........#.....#
...#.......#..#..........#.#...
..#.#.#.#.....#.#.......#..#..#
#.##.....#..##...#..###.#....#.
.......#...........#...#....###
.......#..#...#.............#..
#.....###.......#...#........#.
.#..#..#..#...........#........
....#.#...#.#.##.#.#....#.##..#
.......#..##...##...#...#......
...#.....##.###...#.#...##....#
#..#....#...##......#....##....
#.#.......#....#.###.##..#..#..
..##...........#...#....#......
.#........#.....#..#..#...#..##
.....#.#.#..#.......#....#.....
#..#.#......#......##....#.....
##.....................##......
.##........###..#.........#...#
........#.........#..#.........
.#.##....#.....#...#.........##
....##......#.........#........
...#.#..#...##.##.#.#..####....
..##...........##.#.#....#.....
.#.....#.#...#..#.......#....#.
....#...#......##...#...##.#..#
....#..##....#..#.........##.#.
..##...##.##....#....##.###...#
..#....##..##.#.#.#...#......#.
##...#.........#...........#...
.##....##.#.....#...#.......#..
..........##.###.##....###....#
..........#..##..#....#.#.##.##
........##.#...#.#.#.#...###.#.
.#......#.#.#...###.#.#.#......
.........#......#......#...#..#
......#.....#.##....##.#####..#
..#..##...###.#..........#.#.#.
.#..#....###.#...#..#....#...##
...................#..........#
....###.....#...##......#.....#
#.....#..##.....#.#..........#.
..#.......##.#....#..#.##.#...#
........##.#..###..#......##...
#...........##.#...###..#....#.
....#...........#.....#.#...#..
.##..#.#...#...#.##...#..#.....
#........#.#.#.#.#.#...........
#..#.....#..#..#.##....#....#.#
..#............##....#.#.##...#
.....###.#....#.#......#.###...
...#.....#.#.................#.
..#...##..#.#...#...#...#.....#
.##.#........#..#....##..#..##.
.#..........#...#.#..#..#.#....
#.......##.........#.##..#.####
.#..............#.......##.....
#......#.##..........#..#......
..##...#...#.#...#............#
.##.##..##..##........##.....#.
.....#..#.....##...............
.#..#...##...#...#.....#.......
#......#...#.......#..##.###.##
###..##......##......###....#..
....#..........#...#.##.#.....#
.........#....#..#..#.#..##....
.....#.....#...........#......#
.#.......#...#....##...#.##...#
..##.#..............#..#...#.#.
.#..####.#.........#....#....#.
..###.#...#..#......#.......###
.#.#..##...###...#...#.#...#.#.
...#..##..###.#..#.....#.##....
#...###.#...##.....####.....#..
.#.##...#..#.#..##.....#.......
...#.##.....##.....#....#......
.#...##.....#..###..#..........
..........#...#.....#....##.#..
.......#...#...#...#........#..
#....##..#...#..##.#.#.....#...
.#.#..............#..#....#....
.####.#.#.###......#...#.#....#
.#...#...##.#...............#.#
...#.......##...#...#....##....
#..........###.##..........##.#
.......#...#....#.#..#.#....#..
....#.##.#...###..#..##.##.....
..#.#.#......#.#.......###.....
#..................#.##....#...
#.....#..#.#.#..#...#.........#
..#..#...#.#.##........#.......
#..#.#..#..........###...#.#...
.......#.##....#........##.#...
.####.#.#...#.#...##.##.....###
........#.#...#.#..##...##.....
....##.##......#.##.........#..
.#..#...#.#...........#........
.......#..........#....#...#...
..###.#.###..#..#.....#..##....
.#..........#.......##...#.....
.#.....#...#........#...#.##..#
.#..#.......#..#.......#.#.#...
....#..##.#...##...#.#....#....
.....#.........#..#..#....#....
..#.#..##....#..#..##.#.#.....#
........#.#...###....#.#.#.....
.#.....#.......#..###.#........
.......#...#.#...#...##........
##.............#.#.....#.#..#..
.#....#.......#.#.......#..##..
#.....#........#..##..##.......
...........#.........###......#
....#.##...#.#...#...#....#..##
......#..##......#......#.##.#.
......##....####...###...#.....
#....#..........#.#.##.....#..#
....#.#...........#.#.#.#.#...#
....####.....##...#..##..#...#.
#....#.###..###.....#..###.....
..##.........#......#...##.#...
..#.....#.#...#.##.#...#..###.#
..#.##..##........#.......#.###
.....#..........#.....#....#...
.......##..##..###.......#####.
..###......#.#....###....##...#
#..##.....#..###...#.....##.##.
#..#..##.##.###.####.##.#......
.#.#......#.##......#..#......#
..###.....#.#......#.#.####....
#..............#..#.#...#.###..
...#..#.##..........##.#...#.##
.#.#.#.........#....#.#..#.....
..#.##..#...#..#...#......#....
.......#...#.##.#.#..#...##..#.
..........#.####...#........#.#
....#...#....##.#.........#.#..
##.#..#.......###....#..#..#.#.
..##.....#..#.#.#.####......#..
.#.....#..........#..#..#.#....
......#.#.......#.#...#..#..#..
...#...............#....#...#..
##.......#.........#.......#...
...#.......###...#.#...#.......
#...###....#....##....#....#...
...#....##..#.#.............##.
.....#.#.#..#......#...#.#..#..
.##....#..##..#####..##.....##.
....##.#.#..#.....#.#...#......
...#.....##.#.#..##..#.#.......
.......#..#..#..........#......
.......#...#..#.........#.##...
..#..#..#...##..#.#....#......#
..#....#...#.#......#........#.
.#...#..#...#.#..........#.....
#..#...####..#......##.##.#.#..
.#...#.#...#.#.....#..##.#.....
..#.##.#......#.........##...#.
###..............#.............
...#...###....#..#.............
.##....#......#..#.....#..#..#.
.#..........#.....##...#..#....
....##..#.#......###.##......#.
.#..##.#.##.#...##.#......###.#
#..###.#...###..#........#.#...
#..#.#.#..#...###.##.##..#..#..
#.#..#....#.........##......#..
....###.....###....#...........
....#..##.##....##..#.....#....
.#.....#....####...#..##.#..###
.........##..#......#.#...##...
.##.......#.....#.###.#..#.#..#
.....#.#...###.....#......####.
##.#...#......#.#.#..#.####...#
.#.##.....#..#..#.............#
.#..###..#..#......#..##......#
.......#.#........##.....#.#...
#....#...#..###..#.#.....#.##..
.##.....#.#....###..#.....##...
...##....#....#...#....#.#.#...
#####..#...........###....#...#
.#.......##.##.....#....#......
.#..#.#...#..#......#...#..#.#.
....#.....##...#####..#...#...#
###.##...#.#............#....#.
.....#...#........##.........#.
""".trimIndent()

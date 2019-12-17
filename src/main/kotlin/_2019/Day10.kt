package _2019

import aocRun
import between
import kotlin.math.round

fun main() {
    aocRun(testInput1) { calcBestAsteroid(it) }
    aocRun(testInput2) { calcBestAsteroid(it) }
    aocRun(testInput3) { calcBestAsteroid(it) }
    aocRun(testInput4) { calcBestAsteroid(it) }
    aocRun(testInput5) { calcBestAsteroid(it) }
    aocRun(puzzleInput) { calcBestAsteroid(it) }
}

private fun calcBestAsteroid(input: String): String {
    println(input)
    val asteroidGrid = input.split("\n").map { line -> line.toCharArray().map { it == '#' } }
    val asteroids = mutableMapOf<Pair<Int, Int>, Int>()
    asteroidGrid.forEachIndexed y1@{ y1, line1 ->
        line1.forEachIndexed x1@{ x1, isAsteroid1 ->
            if (!isAsteroid1) return@x1
            var numCanSee = 0
            asteroidGrid.forEachIndexed y2@{ y2, line2 ->
                line2.forEachIndexed x2@{ x2, isAsteroid2 ->
                    if (!isAsteroid2 || (x1 == x2 && y1 == y2)) return@x2
                    val canSee = when {
                        x1 == x2 -> between(y1, y2).map { x1 to it }.none { asteroidGrid[it.second][it.first] }
                        y1 == y2 -> between(x1, x2).map { it to y1 }.none { asteroidGrid[it.second][it.first] }
                        else -> {
                            val gradient = (y1.toFloat() - y2.toFloat()) / (x1.toFloat() - x2.toFloat())
                            val offset = y1.toFloat() - (gradient * x1.toFloat())
                            between(x1, x2)
                                .mapNotNull {
                                    val y = (gradient * it.toFloat()) + offset
                                    if (round(y) == y)
                                        it to y.toInt()
                                    else
                                        null
                                }
                                .none { asteroidGrid[it.second][it.first] }
                        }
                    }
                    if (canSee)
                        numCanSee++
                }
            }
            asteroids[x1 to y1] = numCanSee
        }
    }
    /*println(asteroids)
    asteroidGrid.forEachIndexed { y, line ->
        println(line.mapIndexed { x, _ -> asteroids[x to y]?.toString() ?: "." }.joinToString(separator = " "))
    }*/
    return asteroids.maxBy { it.value }!!.toString()
}

private val testInput1 = """
.#..#
.....
#####
....#
...##
""".trimIndent()

private val testInput2 = """
......#.#.
#..#.#....
..#######.
.#.#.###..
.#..#.....
..#....#.#
#..#....#.
.##.#..###
##...#..#.
.#....####
""".trimIndent()

private val testInput3 = """
#.#...#.#.
.###....#.
.#....#...
##.#.#.#.#
....#.#.#.
.##..###.#
..#...##..
..##....##
......#...
.####.###.
""".trimIndent()

private val testInput4 = """
.#..#..###
####.###.#
....###.#.
..###.##.#
##.##.#.#.
....###..#
..#.#..#.#
#..#.#.###
.##...##.#
.....#.#..
""".trimIndent()

private val testInput5 = """
.#..##.###...#######
##.############..##.
.#.######.########.#
.###.#######.####.#.
#####.##.#.##.###.##
..#####..#.#########
####################
#.####....###.#.#.##
##.#################
#####.##.###..####..
..######..##.#######
####.##.####...##..#
.#####..#.######.###
##...#.##########...
#.##########.#######
.####.#.###.###.#.##
....##.##.###..#####
.#.#.###########.###
#.#.#.#####.####.###
###.##.####.##.#..##
""".trimIndent()

private val puzzleInput = """
.............#..#.#......##........#..#
.#...##....#........##.#......#......#.
..#.#.#...#...#...##.#...#.............
.....##.................#.....##..#.#.#
......##...#.##......#..#.......#......
......#.....#....#.#..#..##....#.......
...................##.#..#.....#.....#.
#.....#.##.....#...##....#####....#.#..
..#.#..........#..##.......#.#...#....#
...#.#..#...#......#..........###.#....
##..##...#.#.......##....#.#..#...##...
..........#.#....#.#.#......#.....#....
....#.........#..#..##..#.##........#..
........#......###..............#.#....
...##.#...#.#.#......#........#........
......##.#.....#.#.....#..#.....#.#....
..#....#.###..#...##.#..##............#
...##..#...#.##.#.#....#.#.....#...#..#
......#............#.##..#..#....##....
.#.#.......#..#...###...........#.#.##.
........##........#.#...#.#......##....
.#.#........#......#..........#....#...
...............#...#........##..#.#....
.#......#....#.......#..#......#.......
.....#...#.#...#...#..###......#.##....
.#...#..##................##.#.........
..###...#.......#.##.#....#....#....#.#
...#..#.......###.............##.#.....
#..##....###.......##........#..#...#.#
.#......#...#...#.##......#..#.........
#...#.....#......#..##.............#...
...###.........###.###.#.....###.#.#...
#......#......#.#..#....#..#.....##.#..
.##....#.....#...#.##..#.#..##.......#.
..#........#.......##.##....#......#...
##............#....#.#.....#...........
........###.............##...#........#
#.........#.....#..##.#.#.#..#....#....
..............##.#.#.#...........#.....
""".trimMargin()
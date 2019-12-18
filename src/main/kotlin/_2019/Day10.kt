package _2019

import aocRun
import between
import kotlin.math.abs

fun main() {
    aocRun(puzzleInput) { calcBestAsteroid(it) }
}

private fun calcBestAsteroid(input: String): String {
    val asteroidGrid = input.split("\n").map { line -> line.toCharArray().map { it == '#' } }
    val asteroids = mutableMapOf<Pair<Int, Int>, Int>()
    asteroidGrid.forEachIndexed y1@{ y1, line1 ->
        line1.forEachIndexed x1@{ x1, isAsteroid1 ->
            if (!isAsteroid1) return@x1
            var numCanSee = 0
            asteroidGrid.forEachIndexed y2@{ y2, line2 ->
                line2.forEachIndexed x2@{ x2, isAsteroid2 ->
                    if (!isAsteroid2 || (x1 == x2 && y1 == y2)) return@x2
                    if (canSee(x1, y1, x2, y2, asteroidGrid))
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

private fun canSee(x1: Int, y1: Int, x2: Int, y2: Int, asteroidGrid: List<List<Boolean>>): Boolean = when {
    x1 == x2 -> between(y1, y2).map { x1 to it }.none { asteroidGrid[it.second][it.first] }
    y1 == y2 -> between(x1, x2).map { it to y1 }.none { asteroidGrid[it.second][it.first] }
    else -> {
        val xDiff = x2 - x1
        val yDiff = y2 - y1
        val gcd = gcd(abs(xDiff), abs(yDiff))
        val xVec = xDiff / gcd
        val yVec = yDiff / gcd
        var xPos = x1 + xVec
        var yPos = y1 + yVec
        var r = true
        while (xPos in between(x1, x2) && yPos in between(y1, y2)) {
            if (asteroidGrid[yPos][xPos]) {
                r = false
                break
            }
            xPos += xVec
            yPos += yVec
        }
        r
    }
}

private tailrec fun gcd(int1: Int, int2: Int): Int = if (int2 == 0) int1 else gcd(int2, int1 % int2)

private val testInput1 = """
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
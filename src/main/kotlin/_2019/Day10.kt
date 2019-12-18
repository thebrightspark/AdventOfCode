package _2019

import aocRun
import between
import kotlin.math.abs
import kotlin.math.atan2

fun main() {
    aocRun(puzzleInput) { calcBestAsteroid(parseAsteroidGrid(it)) }

    aocRun(puzzleInput) { input ->
        val asteroidGrid = parseAsteroidGrid(input)
        val gunPos = calcBestAsteroid(asteroidGrid)
        println("Pos: $gunPos")
        var remaining = asteroidGrid.stream().mapToInt { line -> line.count { it } }.sum() - 1
        var rotationNum = 1
        var nextAsteroidNum = 1
        lateinit var _200th: Pair<Int, Int>
        while (remaining > 0) {
            asteroidGrid.forEachIndexed { y, line ->
                val l = line.mapIndexed { x, b ->
                    when {
                        y == gunPos.second && x == gunPos.first -> "X"
                        b -> "#"
                        else -> "."
                    }
                }.joinToString(separator = "")
                println(l)
            }
            println("$remaining remaining at start of rotation $rotationNum")
            getVisibleAsteroids(gunPos.first, gunPos.second, asteroidGrid).forEach { pos ->
                if (nextAsteroidNum == 200)
                    _200th = pos
                println("${nextAsteroidNum++}\t- ${pos.first},${pos.second}")
                asteroidGrid[pos.second][pos.first] = false
                remaining--
            }
            rotationNum++
        }
        return@aocRun "200th: $_200th -> ${_200th.first * 100 + _200th.second}"
    }
}

private fun parseAsteroidGrid(input: String): MutableList<MutableList<Boolean>> =
    input.split("\n").mapTo(mutableListOf()) { line -> line.toCharArray().mapTo(mutableListOf()) { it == '#' } }

private fun calcBestAsteroid(asteroidGrid: List<List<Boolean>>): Pair<Int, Int> {
    val asteroids = mutableMapOf<Pair<Int, Int>, Int>()
    asteroidGrid.forEachIndexed y1@{ y1, line1 ->
        line1.forEachIndexed x1@{ x1, isAsteroid1 ->
            if (!isAsteroid1) return@x1
            var numCanSee = 0
            asteroidGrid.forEachIndexed y2@{ y2, line2 ->
                line2.forEachIndexed x2@{ x2, isAsteroid2 ->
                    if (isAsteroid2 && (x1 != x2 && y1 != y2) && canSee(x1, y1, x2, y2, asteroidGrid))
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
    return asteroids.maxBy { it.value }!!.key
}

private fun getVisibleAsteroids(x1: Int, y1: Int, asteroidGrid: List<List<Boolean>>): List<Pair<Int, Int>> {
    val visible = mutableListOf<Triple<Int, Int, Double>>()
    asteroidGrid.forEachIndexed { y2, line ->
        line.forEachIndexed x@{ x2, isAsteroid ->
            if (isAsteroid && (x1 != x2 || y1 != y2) && canSee(x1, y1, x2, y2, asteroidGrid)) {
                val angle = Math.PI - atan2(x2.toDouble() - x1.toDouble(), y2.toDouble() - y1.toDouble())
                visible += Triple(x2, y2, angle)
            }
        }
    }
    return visible.sortedBy { it.third }.map { it.first to it.second }
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

private val testInput2 = """
.#....#####...#..
##...##.#####..##
##...#...#.#####.
..#.....X...###..
..#.#.....#....##
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
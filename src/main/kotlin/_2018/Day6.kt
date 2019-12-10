package _2018

import aocRun
import forEachPoint
import splitInput
import java.awt.Point
import java.awt.Rectangle
import kotlin.math.abs

fun main() {
    aocRun(puzzleInput) { input ->
        val locations = processLocations(input)

        // Create borders - we'll use border1 but then check against border2 later
        val border1 = createBorder(locations)
        println("Created border $border1")
        border1.grow(2, 2)
        println("Expanded border $border1")
        val border2 = Rectangle(border1)
        border1.grow(10, 10)

        // Give the coord for each point an ID
        var nextId = 0
        val locationsWithId = locations.associateWith { nextId++ }
        val grid = mutableMapOf<Point, Int>()
        val sizes = mutableMapOf<Int, Int>()

        // Calculate manhattan distance for all points within border1
        border1.forEachPoint { x, y ->
            val p = Point(x, y)
            val distances = locationsWithId.map { it.value to manhattanDistance(it.key, p) }
            val nearest = distances.minBy { it.second }!!
            if (distances.count { it.second == nearest.second } == 1) {
                grid[p] = nearest.first
                sizes.compute(nearest.first) { _, count -> (count ?: 0) + 1 }
            }
        }
        println("Calculated closest locations for all grid coords")

        // Remove outliers that expand infinitely
        val smallerGrid = grid.filterKeys { border2.contains(it) }
        val smallerSizes = smallerGrid.entries.map { it.value }.groupingBy { it }.eachCount()
        val filteredSizes = sizes.toMutableMap()
        filteredSizes.entries.removeIf { smallerSizes[it.key] != it.value }
        println("Removed ${sizes.size - filteredSizes.size} outliers that expand infinitely")
        println("Sizes of remaining IDs:\n${filteredSizes.entries.sortedBy { it.value }.joinToString("\n", transform = { "${it.key} -> ${it.value}" })}")

        return@aocRun filteredSizes.maxBy { it.value }!!.value
    }

    aocRun(puzzleInput) { input ->
        val locations = processLocations(input)
        val border = createBorder(locations)
        var count = 0
        border.forEachPoint { x, y ->
            val point = Point(x, y)
            val sumOfDistances = locations.map { manhattanDistance(it, point) }.sum()
            if (sumOfDistances < 10000)
                count++
        }
        return@aocRun count
    }
}

private fun processLocations(input: String): List<Point> = input.splitInput().map {
    val parts = it.split(",")
    return@map Point(parts[0].trim().toInt(), parts[1].trim().toInt())
}

private fun createBorder(locations: List<Point>): Rectangle {
    val locXs = locations.map { it.x }
    val minX = locXs.min()!!
    val maxX = locXs.max()!!
    val locYs = locations.map { it.y }
    val minY = locYs.min()!!
    val maxY = locYs.max()!!
    return Rectangle(minX, minY, maxX - minX, maxY - minY)
}

private fun manhattanDistance(point1: Point, point2: Point): Int = abs(point1.x - point2.x) + abs(point1.y - point2.y)

private val puzzleInput = """
137, 282
229, 214
289, 292
249, 305
90, 289
259, 316
134, 103
96, 219
92, 308
269, 59
141, 132
71, 200
337, 350
40, 256
236, 105
314, 219
295, 332
114, 217
43, 202
160, 164
245, 303
339, 277
310, 316
164, 44
196, 335
228, 345
41, 49
84, 298
43, 51
158, 347
121, 51
176, 187
213, 120
174, 133
259, 263
210, 205
303, 233
265, 98
359, 332
186, 340
132, 99
174, 153
206, 142
341, 162
180, 166
152, 249
221, 118
95, 227
152, 186
72, 330
""".trimIndent()

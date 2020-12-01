package _2018

import aocRun

fun main() {
    aocRun(puzzleInput) { input ->
        val grid = calcPowerLevels(input)
        //grid.forEach { println(it.joinToString()) }

        val totals = calcTotals(grid, 3)
        return@aocRun totals.maxByOrNull { it.value }.let { "(${it!!.key.first + 1},${it.key.second + 1}) = ${it.value}" }
    }

    aocRun(puzzleInput) { input ->
        val grid = calcPowerLevels(input)
        println("Calculated power levels")
        val totals = (0..300).toList().stream().parallel().map { it to calcTotals(grid, it) }
        val largestTotalsPerSize = totals.map { sizeTotals ->
            val largestForSize = sizeTotals.second.maxByOrNull { it.value }!!
            return@map Triple(largestForSize.key, largestForSize.value, sizeTotals.first)
        }
        val largestTotal = largestTotalsPerSize.max { o1, o2 -> o1.second.compareTo(o2.second) }.get()
        return@aocRun "(${largestTotal.first.first + 1},${largestTotal.first.second + 1},${largestTotal.third}) = ${largestTotal.second}"
    }
}

private fun calcPowerLevels(serialNum: Int): Array<Array<Int>> =
    Array(300) { x -> Array(300) { y -> calcPowerLevel(x + 1, y + 1, serialNum) } }

private fun calcPowerLevel(x: Int, y: Int, serialNum: Int): Int {
    val rackId = x + 10
    var powerLevel = rackId
    powerLevel *= y
    powerLevel += serialNum
    powerLevel *= rackId
    val powerString = powerLevel.toString()
    val strLength = powerString.length
    powerLevel = if (strLength >= 3) powerString[strLength - 3].toString().toInt() else 0
    powerLevel -= 5
    return powerLevel
}

private fun calcTotals(grid: Array<Array<Int>>, squareSize: Int): Map<Pair<Int, Int>, Int> {
    println("Calculating total for square size $squareSize")
    val max = 300 - squareSize
    val totalsMap = mutableMapOf<Pair<Int, Int>, Int>()
    (0..max).forEach { x ->
        (0..max).forEach { y ->
            totalsMap[x to y] = calc3x3TotalPower(grid, squareSize, x, y)
        }
    }
    return totalsMap
}

private fun calc3x3TotalPower(grid: Array<Array<Int>>, squareSize: Int, x: Int, y: Int): Int {
    var total = 0
    (x until x + squareSize).forEach { gridX ->
        (y until y + squareSize).forEach { gridY ->
            total += grid[gridX][gridY]
        }
    }
    return total
}

private const val puzzleInput = 9445

package _2020

import aocRun

fun main() {
	aocRun(puzzleInput) { input ->
		val split = input.split("\n")
		val timestamp = split[0].toLong()
		val buses = split[1].split(",").mapNotNull { if (it == "x") null else it.toLong() }
		val earliestTimes = buses.map { it to calcEarliestTime(timestamp, it) }
		val earliestBus = earliestTimes.minByOrNull { it.second }!!
		println(earliestBus)
		return@aocRun earliestBus.first * (earliestBus.second - timestamp)
	}
	aocRun(testInput) { input ->
		val buses = input.substringAfter("\n").split(",").mapIndexedNotNull { i, s ->
			if (s == "x") null else i.toLong() to s.toLong()
		}
		// This isn't going to work...
		val lcm = buses.map { it.second - it.first }.reduce { acc, bus -> lcm(acc, bus) }
		return@aocRun lcm
	}
}

private fun calcEarliestTime(timestamp: Long, bus: Long): Long = ((timestamp / bus) * bus + bus)

private fun gcd(num1: Long, num2: Long): Long = if (num2 == 0L) num1 else gcd(num2, num1 % num2)

private fun lcm(num1: Long, num2: Long): Long = num1 / gcd(num1, num2) * num2

private val testInput = """
939
7,13,x,x,59,x,31,19
""".trimIndent()

private val puzzleInput = """
1004098
23,x,x,x,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,x,509,x,x,x,x,x,x,x,x,x,x,x,x,13,17,x,x,x,x,x,x,x,x,x,x,x,x,x,x,29,x,401,x,x,x,x,x,37,x,x,x,x,x,x,x,x,x,x,x,x,19
""".trimIndent()

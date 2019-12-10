package _2019

import aocRun
import java.util.stream.Stream
import kotlin.streams.toList

fun main() {
    aocRun(puzzleInput) { input ->
        return@aocRun getRangeStream(input)
            .filter { hasAscendingDigits(it) }
            .filter { hasAdjacentDigits(it) }
            .count()
    }

    aocRun(puzzleInput) { input ->
        val result = getRangeStream(input)
            .filter { hasAscendingDigits(it) }
            .filter { hasAdjacentDigitsDoubles(it) }
            .toList()
//            .count()
        repeat(20) { println(result.random()) }
        return@aocRun result.size
    }
}

private fun hasAscendingDigits(number: String): Boolean {
    (1 until number.length).forEach { i ->
        if (number[i].toString().toInt() < number[i - 1].toString().toInt())
            return false
    }
    return true
}

private fun hasAdjacentDigits(number: String): Boolean {
    (1 until number.length).forEach { i ->
        if (number[i] == number[i - 1])
            return true
    }
    return false
}

private fun hasAdjacentDigitsDoubles(number: String): Boolean {
    val adjacentCounts = mutableMapOf<Char, Int>()
    (1 until number.length).forEach { i ->
        if (number[i] == number[i - 1])
            adjacentCounts.compute(number[i]) { _, v -> v?.plus(1) ?: 2 }
    }
    return adjacentCounts.containsValue(2) || adjacentCounts.isEmpty()
}

private fun getRangeStream(input: String): Stream<String> = input.split('-').let {
    (it[0].toInt()..it[1].toInt()).toSet().stream().map { v -> v.toString() }
}

private const val puzzleInput = "271973-785961"
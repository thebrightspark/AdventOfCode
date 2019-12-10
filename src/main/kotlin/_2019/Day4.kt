package _2019

import aocRun
import java.util.stream.Stream
import kotlin.streams.toList

fun main() {
    aocRun(puzzleInput) { input ->
        return@aocRun getRangeStream(input)
            // Has two adjacent same digits
            .filter {
                (1 until it.length).forEach { i ->
                    if (it[i] == it[i - 1])
                        return@filter true
                }
                return@filter false
            }
            // Digits ascend
            .filter {
                (1 until it.length).forEach { i ->
                    if (it[i].toString().toInt() < it[i - 1].toString().toInt())
                        return@filter false
                }
                return@filter true
            }
            .count()
    }

    aocRun(puzzleInput) { input ->
        val result = getRangeStream(input)
            // Has two adjacent same digits but not part of larger group
            .filter {
                val adjacentCounts = mutableMapOf<Char, Int>()
                (1 until it.length).forEach { i ->
                    if (it[i] == it[i - 1])
                        adjacentCounts.compute(it[i]) { _, v -> v?.plus(1) ?: 2 }
                }
                return@filter adjacentCounts.containsValue(2) || adjacentCounts.isEmpty()
            }
            // Digits ascend
            .filter {
                (1 until it.length).forEach { i ->
                    if (it[i].toString().toInt() < it[i - 1].toString().toInt())
                        return@filter false
                }
                return@filter true
            }
            .toList()
//            .count()
        repeat(20) { println(result.random()) }
        return@aocRun result.size
    }
}

private fun getRangeStream(input: String): Stream<String> = input.split('-').let {
    (it[0].toInt()..it[1].toInt()).toSet().stream().map { v -> v.toString() }
}

private const val puzzleInput = "271973-785961"
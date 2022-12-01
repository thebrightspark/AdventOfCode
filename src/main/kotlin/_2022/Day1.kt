package _2022

import PATTERN_LINE_SEPARATOR
import aoc
import splitToInts

fun main() {
    aoc(2022, 1) {
        aocRun { input ->
            input.split(Regex("$PATTERN_LINE_SEPARATOR{2}")).maxOf { it.splitToInts().sum() }
        }
        aocRun { input ->
            input.split(Regex("$PATTERN_LINE_SEPARATOR{2}"))
                .map { it.splitToInts().sum() }
                .sortedDescending()
                .take(3)
                .sum()
        }
    }
}

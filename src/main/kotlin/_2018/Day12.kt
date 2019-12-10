package _2018

import aocRun
import java.util.regex.Pattern

private val notePattern = Pattern.compile("(?<predicate>[.#]){5} => (?<result>[.#])")

fun main() {
    aocRun(testInput) { input ->
        val state = parseInitialState(input)
        val notes = parseNotes(input)
        println("Initial state:\n${stateToString(state)}")
        repeat(20) {
            notes.forEach { note ->

            }
        }
    }
}

private fun parseInitialState(input: String) =
    input.substringAfter("initial state: ").substringBefore("\n").toCharArray().map { it == '#' }

private fun parseNotes(input: String) =
    input.split(Regex("\n"), 3)[2].split("\n").map { Note(it) }

private fun stateToString(state: List<Boolean>) = state.joinToString("") { if (it) "#" else "." }

private class Note(input: String) {
    private val predicate: (List<Boolean>) -> Boolean
    private val result: Boolean

    init {
        val matcher = notePattern.matcher(input)
        if (!matcher.find())
            throw RuntimeException("Invalid note: $input")
        val predicateBools = matcher.group("predicate").toCharArray().map { it == '#' }
        predicate = { it == predicateBools }
        result = matcher.group("result") == "#"
    }
}

private val testInput = """
initial state: #..#.#..##......###...###

...## => #
..#.. => #
.#... => #
.#.#. => #
.#.## => #
.##.. => #
.#### => #
#.#.# => #
#.### => #
##.#. => #
##.## => #
###.. => #
###.# => #
####. => #
""".trimIndent()

private val puzzleInput = """
initial state: ###.......##....#.#.#..###.##..##.....#....#.#.....##.###...###.#...###.###.#.###...#.####.##.#....#

..... => .
#..## => .
..### => #
..#.# => #
.#.#. => .
####. => .
##.## => #
#.... => .
#...# => .
...## => .
##..# => .
.###. => #
##### => #
#.#.. => #
.##.. => #
.#.## => .
...#. => #
#.##. => #
..#.. => #
##... => #
....# => .
###.# => #
#..#. => #
#.### => #
##.#. => .
###.. => #
.#### => .
.#... => #
..##. => .
.##.# => .
#.#.# => #
.#..# => .
""".trimIndent()

package _2022

import aoc
import parseInput
import java.util.regex.Pattern

private val INPUT_REGEX = Pattern.compile("([A-C]) ([X-Z])")

fun main() {
    aoc(2022, 2) {
        aocRun { processPart1(it) }
        aocRun { processPart2(it) }
    }
}

private fun processPart1(input: String): Long {
    val parsedInput = parseInput(INPUT_REGEX, input) { RPS.getOpponentRPS(it.group(1)) to RPS.getYourRPS(it.group(2)) }
    var score = 0L
    parsedInput.forEach { (other, yours) ->
        val result = yours.calculateResult(other)
        score += result.calculateScore(yours)
    }
    return score
}

private fun processPart2(input: String): Long {
    val parsedInput =
        parseInput(INPUT_REGEX, input) { RPS.getOpponentRPS(it.group(1)) to DesiredResult.get(it.group(2)) }
    var score = 0L
    parsedInput.forEach { (other, desiredResult) ->
        val yours = other.getOtherFromDesiredResult(desiredResult)
        val result = yours.calculateResult(other)
        score += result.calculateScore(yours)
    }
    return score
}

private enum class RPS(val opponentChar: Char, val yourChar: Char, val score: Long) {
    ROCK('A', 'X', 1),
    PAPER('B', 'Y', 2),
    SCISSORS('C', 'Z', 3);

    companion object {
        val OPPONENT_CHAR_MAP: Map<Char, RPS> = values().associateBy { it.opponentChar }
        val YOUR_CHAR_MAP: Map<Char, RPS> = values().associateBy { it.yourChar }

        fun getOpponentRPS(char: String): RPS = OPPONENT_CHAR_MAP[char[0]]!!

        fun getYourRPS(char: String): RPS = YOUR_CHAR_MAP[char[0]]!!
    }

    private val winsAgainst: RPS by lazy {
        when (this) {
            ROCK -> SCISSORS
            PAPER -> ROCK
            SCISSORS -> PAPER
        }
    }

    private val losesAgainst: RPS by lazy {
        when (this) {
            ROCK -> PAPER
            PAPER -> SCISSORS
            SCISSORS -> ROCK
        }
    }

    fun calculateResult(other: RPS): RPSResult = when {
        this == other -> RPSResult.DRAW
        winsAgainst == other -> RPSResult.WIN
        else -> RPSResult.LOSE
    }

    fun getOtherFromDesiredResult(result: DesiredResult): RPS = when (result) {
        DesiredResult.WIN -> losesAgainst
        DesiredResult.DRAW -> this
        DesiredResult.LOSE -> winsAgainst
    }
}

private enum class RPSResult(val score: Long) {
    WIN(6),
    DRAW(3),
    LOSE(0);

    fun calculateScore(yourRps: RPS): Long = yourRps.score + score
}

private enum class DesiredResult(val char: Char) {
    WIN('Z'),
    DRAW('Y'),
    LOSE('X');

    companion object {
        val CHAR_MAP: Map<Char, DesiredResult> = values().associateBy { it.char }

        fun get(char: String): DesiredResult = CHAR_MAP[char[0]]!!
    }
}

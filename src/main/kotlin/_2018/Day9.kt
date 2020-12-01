package _2018

import aocRun
import java.text.NumberFormat
import java.util.regex.Pattern
import kotlin.system.measureNanoTime

private val pattern = Pattern.compile("(?<players>\\d+) players; last marble is worth (?<last>\\d+) points")
private val numFormat = NumberFormat.getNumberInstance()
private val pctFormat = NumberFormat.getPercentInstance()

fun main() {
    aocRun(puzzleInput) { input ->
        val (players, last) = parseInput(input)
        return@aocRun calculateHighestScore(players, last)
    }

    aocRun(puzzleInput) { input ->
        val (players, last) = parseInput(input)
        return@aocRun calculateHighestScore(players, last * 100, true)
    }
}

private fun parseInput(input: String): Pair<Int, Int> {
    val matcher = pattern.matcher(input)
    if (!matcher.find())
        throw RuntimeException("Not a valid input! -> '$input'")
    return Pair(matcher.group("players").toInt(), matcher.group("last").toInt())
}

private fun next(max: Int, current: Int, increment: Int): Int {
    var next = current + increment
    while (next > max)
        next -= max
    return next
}

private fun prev(max: Int, current: Int, decrement: Int): Int {
    var prev = current - decrement
    while (prev < 0)
        prev += max
    return prev
}

private fun calculateHighestScore(players: Int, last: Int, printCompletionPct: Boolean = false): Long {
    val scores = mutableMapOf<Int, Long>()
    val marbles = ArrayList<Long>(last)
    marbles += 0
    var curPlayer = 0
    var curPos = 0
    val timePeriodMillis = 10000
    var nextTime = System.currentTimeMillis() + timePeriodMillis
    var doStats = false
    var lastStatNum: Long = 1
    (1.toLong()..last.toLong()).forEach { nextMarbleNum ->
        //println("\nMarbles: $marbles")
        //println("\nCurPos: $curPos, Next num: $nextMarbleNum")

        if (printCompletionPct) {
            val curTime = System.currentTimeMillis()
            if (curTime >= nextTime) {
                nextTime = curTime + timePeriodMillis
                println(
                    "${numFormat.format(nextMarbleNum)} / ${numFormat.format(last)} (+${numFormat.format(
                        nextMarbleNum - lastStatNum
                    )}) -> ${pctFormat.format(nextMarbleNum.toFloat() / last.toFloat())} complete"
                )
                lastStatNum = nextMarbleNum
                doStats = true
            }
        }
        if (nextMarbleNum.rem(23) == 0.toLong()) {
            var scoreGained = nextMarbleNum
            val removePos = prev(marbles.size, curPos, 7)
            val removedMarble = marbles.removeAt(removePos)
            scoreGained += removedMarble
            //println("Pos $removePos removed")
            curPos = removePos
            if (removePos == marbles.size)
                curPos = 0
            scores.compute(curPlayer) { _, curScore -> curScore?.plus(scoreGained) ?: scoreGained }
            //println("Added $scoreGained to player $curPlayer ($nextMarbleNum + $removedMarble)")
        } else {
            var startNanos: Long = 0
            measureNanoTime {

            }
            if (doStats) startNanos = System.nanoTime()
            val nextPos = next(marbles.size, curPos, 2)
            //println("Next pos: $nextPos")
            marbles.add(nextPos, nextMarbleNum)
            curPos = nextPos
            if (doStats) {
                val timeTaken = System.nanoTime() - startNanos
                println("Time stats: ${numFormat.format(timeTaken)} ms")
                doStats = false
            }
        }
        //println("Cur pos now: $curPos")
        curPlayer = next(players, curPlayer, 1)
    }
    return scores.values.maxOrNull()!!
}

private const val testInput = "10 players; last marble is worth 1618 points"

private const val puzzleInput = "458 players; last marble is worth 72019 points"

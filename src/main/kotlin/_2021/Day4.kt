package _2021

import MutablePair
import PATTERN_LINE_SEPARATOR
import REGEX_WHITESPACE
import aoc
import splitToInts

fun main() {
	aoc(2021, 4) {
		aocRun { input ->
			val bingo = parseBingo(input)
			var result: PlayResult = PlayResult.NO_WINNER
			while (result == PlayResult.NO_WINNER)
				result = bingo.play()
			return@aocRun bingo.calculateWinningScore()
		}
		aocRun { input ->
			val bingo = parseBingo(input)
			var result: PlayResult = PlayResult.NO_WINNER
			while (result != PlayResult.FINISHED)
				result = bingo.play()
			return@aocRun bingo.calculateWinningScore()
		}
	}
}

private fun parseBingo(input: String): Bingo {
	val splitInput = input.split(Regex("$PATTERN_LINE_SEPARATOR{2}"))
	val numbers = splitInput[0].splitToInts(",")
	val boards = splitInput.subList(1, splitInput.size).map { Board(it) }
	return Bingo(numbers, boards)
}

private class Bingo(val numbers: List<Int>, val boards: List<Board>) {
	private var nextNumber = 0
	private val activeBoards = boards.toMutableList()
	var lastWinner: Board? = null
	var lastWinningNumber: Int? = null

	private fun boardI(board: Board): Int = boards.indexOf(board)

	fun play(): PlayResult {
		if (nextNumber >= numbers.size || activeBoards.isEmpty())
			return PlayResult.FINISHED
		val number = numbers[nextNumber++]
		val marked = activeBoards.filter { b ->
			b.markIfExists(number)/*.also { println("Marked #${boardI(b)} ($number)") }*/
		}
		if (marked.isNotEmpty()) {
			val winners = marked.filter { it.checkForWin() }
			if (winners.isNotEmpty()) {
				winners.forEach {
//                    println("Winner #${boardI(it)} ($number)")
					lastWinner = it
					lastWinningNumber = number
					activeBoards.remove(it)
				}
			} else {
//                println("No winner ($number)")
			}
		} else {
//            println("None marked ($number)")
		}
		return if (lastWinner == null) PlayResult.NO_WINNER else PlayResult.WINNER
	}

	fun calculateWinningScore(): Int = lastWinner!!.calculateScore(lastWinningNumber!!)
}

private class Board(input: String) {
	private val grid: Array<Array<BoardEntry>> = input.split("\n").map { row ->
		row.trim().split(REGEX_WHITESPACE).map { BoardEntry(it.toInt()) }.toTypedArray()
	}.toTypedArray()
	private var lastPos: MutablePair<Int, Int>? = null

	fun markIfExists(number: Int): Boolean {
		grid.forEachIndexed { i, row ->
			row.forEachIndexed { j, entry ->
				if (entry.number == number) {
					entry.marked = true
					lastPos?.set(i, j) ?: run { lastPos = MutablePair(i, j) }
					return true
				}
			}
		}
		// Board doesn't have number
		return false
	}

	fun checkForWin(): Boolean = lastPos?.let { checkRow(it.first) || checkColumn(it.second) } ?: false

	fun calculateScore(winningNum: Int): Int =
		grid.sumOf { row -> row.sumOf { if (!it.marked) it.number else 0 } } * winningNum

	private fun checkRow(row: Int): Boolean = grid[row].all { it.marked }

	private fun checkColumn(column: Int): Boolean = grid.all { it[column].marked }

	override fun toString(): String =
		grid.joinToString("\n") { row -> row.joinToString("\t") { it.number.toString() } }
}

private data class BoardEntry(val number: Int, var marked: Boolean = false)

private enum class PlayResult {
	WINNER,
	NO_WINNER,
	FINISHED
}

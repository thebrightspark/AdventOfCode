package _2021

import aoc
import parseInput
import java.util.regex.Pattern

private val PATTERN = Pattern.compile("(\\w+) (\\d+)")

fun main() {
	aoc(2021, 2) {
		aocRun {
			process(it) { dir, submarine, amount -> dir.execute1(submarine, amount) }
		}
		aocRun {
			process(it) { dir, submarine, amount -> dir.execute2(submarine, amount) }
		}
	}
}

private fun process(input: String, inputHandler: (Direction, Submarine, Int) -> Unit): Int {
	val submarine = Submarine()
	parseInput(PATTERN, input) { Direction.valueOf(it.group(1).uppercase()) to it.group(2).toInt() }
		.forEach { (dir, amount) -> inputHandler(dir, submarine, amount) }
	return submarine.horizontalPos * submarine.verticalPos
}

private data class Submarine(
	var horizontalPos: Int = 0,
	var verticalPos: Int = 0,
	var aim: Int = 0
)

private enum class Direction {
	FORWARD {
		override fun execute1(submarine: Submarine, amount: Int) {
			submarine.horizontalPos += amount
		}

		override fun execute2(submarine: Submarine, amount: Int) {
			submarine.horizontalPos += amount
			submarine.verticalPos += (submarine.aim * amount)
		}
	},
	UP {
		override fun execute1(submarine: Submarine, amount: Int) {
			submarine.verticalPos -= amount
		}

		override fun execute2(submarine: Submarine, amount: Int) {
			submarine.aim -= amount
		}
	},
	DOWN {
		override fun execute1(submarine: Submarine, amount: Int) {
			submarine.verticalPos += amount
		}

		override fun execute2(submarine: Submarine, amount: Int) {
			submarine.aim += amount
		}
	};

	abstract fun execute1(submarine: Submarine, amount: Int)

	abstract fun execute2(submarine: Submarine, amount: Int)
}

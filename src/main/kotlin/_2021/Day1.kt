package _2021

import aoc
import splitToInts

fun main() {
	aoc(2021, 1) {
		aocRun {
            countIncreasingDepths(it.splitToInts())
        }
		aocRun {
            countIncreasing3MeasureDepths(it.splitToInts())
        }
	}
}

private fun countIncreasingDepths(list: List<Int>): Int {
	val diffList = mutableListOf<Int>()
	repeat(list.size - 1) {
		diffList += list[it].compareTo(list[it + 1])
	}
	return diffList.count { it < 0 }
}

private fun countIncreasing3MeasureDepths(list: List<Int>): Int {
	val threeMeasureSums = mutableListOf<Int>()
	repeat(list.size - 2) {
		threeMeasureSums += list[it] + list[it + 1] + list[it + 2]
	}
	val diffList = mutableListOf<Int>()
	repeat(threeMeasureSums.size - 1) {
		diffList += threeMeasureSums[it].compareTo(threeMeasureSums[it + 1])
	}
	return diffList.count { it < 0 }
}

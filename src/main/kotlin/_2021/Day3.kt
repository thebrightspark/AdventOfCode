package _2021

import REGEX_LINE_SEPARATOR
import aoc

fun main() {
	aoc(2021, 3) {
		aocRun { input ->
			val bitList = input.split(REGEX_LINE_SEPARATOR).map { it.toBinaryByteArray() }
			val numOfOnes = numberOfOnes(bitList)
			val listSizeHalf = bitList.size / 2
			val mostSigBits = numOfOnes.map { if (it >= listSizeHalf) '1' else '0' }
				.joinToString("")
				.toBinaryByteArray()
			val leastSigBits = ByteArray(mostSigBits.size) { if (mostSigBits[it] == 1.toByte()) 0 else 1 }
			return@aocRun mostSigBits.toDecimalInt() * leastSigBits.toDecimalInt()
		}
		aocRun { input ->
			val bitList = input.split(REGEX_LINE_SEPARATOR).map { it.toBinaryByteArray() }
			val oxygenRating = oxygenRating(bitList)
			val co2Rating = co2Rating(bitList)
			return@aocRun oxygenRating.toDecimalInt() * co2Rating.toDecimalInt()
		}
	}
}

private fun Char.toBinaryByte(): Byte = if (this == '1') 1 else 0

private fun Byte.toBinaryChar(): Char = if (this == 1.toByte()) '1' else '0'

private fun String.toBinaryByteArray(): ByteArray = ByteArray(this.length) { this[it].toBinaryByte() }

private fun ByteArray.toDecimalInt(): Int =
	StringBuilder().also { sb -> this.forEach { sb.append(it.toBinaryChar()) } }.toString().toInt(2)

private fun numberOfOnes(bitList: List<ByteArray>): IntArray = IntArray(bitList.first().size) { i ->
	bitList.sumOf { it[i].toInt() }
}

private fun bitCriteria(
	bitList: List<ByteArray>,
	bitFilter: (zeros: Int, ones: Int) -> Byte,
	bitPos: Int = 0
): ByteArray {
	if (bitPos >= bitList.first().size)
		error("Bit pos $bitPos is larger than the number of bits (${bitList.first().size})!")

	val numOfOnes = bitList.sumOf { it[bitPos].toInt() }
	val numOfZeros = bitList.size - numOfOnes
	val bitFilterNum = bitFilter(numOfZeros, numOfOnes)
	val filteredBitList = bitList.filter { it[bitPos] == bitFilterNum }
	return when (filteredBitList.size) {
		0 -> error("Empty bit list!?")
		1 -> filteredBitList.single()
		else -> bitCriteria(filteredBitList, bitFilter, bitPos + 1)
	}
}

private fun oxygenRating(bitList: List<ByteArray>): ByteArray =
	bitCriteria(bitList, { zeros, ones -> if (ones >= zeros) 1.toByte() else 0.toByte() })

private fun co2Rating(bitList: List<ByteArray>): ByteArray =
	bitCriteria(bitList, { zeros, ones -> if (zeros <= ones) 0.toByte() else 1.toByte() })

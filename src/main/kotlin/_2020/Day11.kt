package _2020

import aocRun

fun main() {
	aocRun(puzzleInput) { input ->
		return@aocRun runSeatModel(
			parseSeatLayout(input),
			{ seating, x, y -> hasNoAdjacentOccupiedSeats(seating, x, y) },
			{ seating, x, y -> has4OrMoreAdjacentOccupiedSeats(seating, x, y) }
		)
	}
	aocRun(puzzleInput) { input ->
		return@aocRun runSeatModel(
			parseSeatLayout(input),
			{ seating, x, y -> hasNoOccupiedSeatInSight(seating, x, y) },
			{ seating, x, y -> has5OrMoreOccupiedSeatsInSight(seating, x, y) }
		)
	}
}

private fun parseSeatLayout(input: String): List<List<Seat>> =
	input.split("\n").map { row -> row.toCharArray().map { Seat.getForChar(it) } }

private fun printSeatLayout(seating: List<List<Seat>>) = seating.forEach { row ->
	row.forEach { print(it.character) }
	println()
}

private fun runSeatModel(
	initialSeating: List<List<Seat>>,
	shouldEmptyBeOccupied: (seating: List<List<Seat>>, x: Int, y: Int) -> Boolean,
	shouldOccupiedBeEmpty: (seating: List<List<Seat>>, x: Int, y: Int) -> Boolean
): Int {
//	printSeatLayout(initialSeating)
	var seating = initialSeating
	var lastSeating: List<List<Seat>>
	var round = 0
	do {
		lastSeating = seating
		seating = runSeatModelRound(seating, shouldEmptyBeOccupied, shouldOccupiedBeEmpty)
		round++
//		println("----- Round $round -----")
//		printSeatLayout(seating)
	} while (seating != lastSeating)
	return seating.sumBy { row -> row.sumBy { if (it == Seat.OCCUPIED) 1 else 0 } }
}

private fun runSeatModelRound(
	seating: List<List<Seat>>,
	shouldEmptyBeOccupied: (seating: List<List<Seat>>, x: Int, y: Int) -> Boolean,
	shouldOccupiedBeEmpty: (seating: List<List<Seat>>, x: Int, y: Int) -> Boolean
): List<List<Seat>> {
	// Create deep copy of the seating
	val newSeating = mutableListOf<MutableList<Seat>>().apply { seating.forEach { add(it.toMutableList()) } }

	// Run the seat model once for every position
	seating.forEachIndexed { y, row ->
		row.forEachIndexed { x, seat ->
			when (seat) {
				Seat.FLOOR -> Unit
				Seat.EMPTY -> if (shouldEmptyBeOccupied(seating, x, y))
					newSeating[y][x] = Seat.OCCUPIED
				else
					Unit
				Seat.OCCUPIED -> if (shouldOccupiedBeEmpty(seating, x, y))
					newSeating[y][x] = Seat.EMPTY
				else
					Unit
			}
		}
	}

	return newSeating
}

private inline fun <T> List<List<T>>.forEachAround(
	x: Int,
	y: Int,
	consumer: (value: T, vecX: Int, vecY: Int) -> Unit
) {
	for (vecY in -1..1) {
		val checkY = y + vecY
		if (checkY < 0 || checkY > this.size - 1)
			continue
		val row = this[checkY]
		for (vecX in -1..1) {
			val checkX = x + vecX
			if (checkX < 0 || checkX > row.size - 1)
				continue
			if (checkY != y || checkX != x)
				consumer(row[checkX], vecX, vecY)
		}
	}
}

private inline fun <T> List<List<T>>.forEachInLineUntil(
	xStart: Int,
	yStart: Int,
	vecX: Int,
	vecY: Int,
	function: (value: T) -> Unit
) {
	val maxX = this[0].size - 1
	val maxY = this.size - 1
	var x = xStart
	var y = yStart
	while (true) {
		x += vecX
		y += vecY
		if (x < 0 || x > maxX || y < 0 || y > maxY)
			return
		function(this[y][x])
	}
}

private fun hasNoAdjacentOccupiedSeats(seating: List<List<Seat>>, x: Int, y: Int): Boolean {
//	println("hasNoAdjacentOccupiedSeats -> x: $x, y: $y")
	seating.forEachAround(x, y) { seat, _, _ ->
		if (seat == Seat.OCCUPIED)
			return false
	}
	return true
}

private fun hasNoOccupiedSeatInSight(seating: List<List<Seat>>, x: Int, y: Int): Boolean {
	seating.forEachAround(x, y) { _, vecX: Int, vecY: Int ->
		seating.forEachInLineUntil(x, y, vecX, vecY) { seat ->
			when (seat) {
				Seat.FLOOR -> Unit
				Seat.EMPTY -> return@forEachAround
				Seat.OCCUPIED -> return false
			}
		}
	}
	return true
}

private fun has4OrMoreAdjacentOccupiedSeats(seating: List<List<Seat>>, x: Int, y: Int): Boolean {
//	println("has4OrMoreAdjacentOccupiedSeats -> x: $x, y: $y")
	var count = 0
	seating.forEachAround(x, y) { seat, vecX, vecY ->
		if (seat == Seat.OCCUPIED)
			count++
		if (count >= 4)
			return true
	}
	return false
}

private fun has5OrMoreOccupiedSeatsInSight(seating: List<List<Seat>>, x: Int, y: Int): Boolean {
	var count = 0
	seating.forEachAround(x, y) { _, vecX, vecY ->
		seating.forEachInLineUntil(x, y, vecX, vecY) { seat ->
			when (seat) {
				Seat.FLOOR -> Unit
				Seat.EMPTY -> return@forEachAround
				Seat.OCCUPIED -> {
					count++
					if (count >= 5)
						return true
					return@forEachAround
				}
			}
		}
	}
	return false
}

private enum class Seat(val character: Char) {
	FLOOR('.'),
	EMPTY('L'),
	OCCUPIED('#');

	companion object {
		private val map = mutableMapOf<Char, Seat>().apply {
			values().forEach { put(it.character, it) }
		}.toMap()

		fun getForChar(char: Char): Seat = map[char]!!
	}
}

private val testInput = """
L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL
""".trimIndent()

private val puzzleInput = """
LLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLLLLLL.LLLLLLLLLLL.LLLLLLLLLLLLLL.LLLL.LLLL.LLLLLL
LLLLL.LLLLLLLLLLLLLLLL.LLLLL.LLL.LLLLLLL.LLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLL.LLLL.LLLL.LLLL.LLLLLL
LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLL.LLLLLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLL.LLLLLLLLL.LLLLLLLLLLL
LLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLL.LLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLL.LLLL.LLLLLL
LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLL.LLLLLLL.LLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLL.LLLL.LLLLLL
LLLLL.LLLL.LLLLLLLLLLL.LLLLLLLLL.LLLLLLL.LLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLL.LLLL.LLLLLL
LLLLLLLLLL.LLLLLLLLLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLL.LLLL.LLLL.LLLL.LLLLLL
LLLLLLLLLL.LLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLL.LLLL.LLLL.LLLL.LLLLLL
L.L...L...LL.LL.......LL...LL.L...LL..LL..L.......LLLLL.....LL..LLLL.L....L..L...L.LL....LL...L
LLLLL.LLLLL.LLLLLLLLLL.LLLLLLLLL.LLLLLLL.LLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLL.LLLL.LLLL.LLLL.LLLLLL
LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLLLLLLL.LL.LLLLLLLLL.LLLL.LLLL.LLLLLLLLLLL
LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLL.LL.LLLLLLLLLL.LLLL.LLLLLLLLLLLLLLLL.LLLL.LLLL.LLLLLLLLL.LLLLLL
LLLLLLLLLL.LLLLLLLLLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLLL.LLL.LL.LLLLLLLLL.LLLL.LLLL.LLLL.L.LLLL
LLLLL.LLLL.LLLL.LLL.LLLLLLLLLLLLLLLLLLLL.LLLLLLLLLL.LLLLLLLLLLL.LLLLLLLLL.LLLL.LLLL.LLLLLLLLLLL
LLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLL.LLLL.LLL.LL.LLLLLLLLLLLLLLLLLLL.LLLL.LLLLLL
.L.....L.LL..LLL.L..L...L.LLL.L...L.L.L.L.....L..L.......L.LLL...L.......L.LLLL......L.L.L...LL
LLLLLLLLLL.LLLL.LLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLL..LLLLLL.LLLL.LLLL.L.LL.LLLLLLLLL.LLLLLL
LLLLLLLLLL.LLLL.LLLLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLL.LLLLLLLLL.LLLLLLLLLLL
LLLLL.LLLL.LLLLLLLLLLL.LLLLLLLLL.LLLLLLLLLLLLL.LL.LLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLL.LLLL.LLLLLL
LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLLLLLLLLLL.LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLLLLLLL.LLLL.LLLL.LLLLLL
.L..LLL.L...LL.....LL......LL...L...LL...L.L..L....L.L.L.LL.L........L....LL......L..LL..LL....
LLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLL.LLLLLLL.LLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLLLLLLL.LLLL.LLLLLLLLLLL
LLLLLLLLLL.LLLLLLLLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLLLL.LLLLLLLLL.LLLLLLLLL.LLLL.LLLLLL
LLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLL.LLLLL.LLL..LLLL.LLLLLL.LLLLLLLLLLL.LL.LLLL.LLLLLLLLLLL
LLLLL.LLLL.LLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLL.LLLL.LLLL..L.LLL.LLLLLLLLL.LLLL.LLLLLLLLLLLLLLLL
LLLLLLLLLL.LLLL.LLLLLL.LLLLLL.LLLLLLLLLL.LLLLL.LLLL.LLLLLLLLLLL.LLLLLLLLLLLLLL.LLLL.LLLLLLLLLLL
L.LLL.LLLLLLLLL.LLLLLL.LLLLLLLLL.LLLLLLL.LLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLLLLLLL
LLLL....L.......L..LLL...........L..L...LL..L.L.LLL...L.....LL..LL..L....L....L..LL..LL.L....L.
LLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLL.L.LLLLLLLLLLLL.LLLLLL
LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLL.LLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLLLL
LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLLL.LLLLLLL.LLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLL.LLLL.LLLLL.LLLLLLLLLL
LLLLLLLLLL.LLLL.LLLLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLL.LLLL.LLLLLL..LLL.LLLL.LLLL.LLLL.LLLLLLLLLLL
LLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLL.L.LLLL.LL.L.LL.LLLLLLLLLLLLL.LLLL.LLLL.LLLL.LLLLLL
.LL........L.....L.L.L.......LL.L.L.......LLL.........L....LL........L.L..L......L.LL......L..L
LLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLL.LLLL.LLLL.LLLL.LLLLLL
LLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLLLLLLL.L.LLLLLLLLLLLLLL
LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLL.LLLLLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLLLLLLLLLL
LLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.L.LLLL.LLLLLL.LLLLLLLLL.LLLL.LLLLLLLLL.LLLLLL
LLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLLLL.LLL..LLLLLL.LLLLLLLLL.LLLLLLLLL.LLLL.LLLLLL
LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLLLL.LL.L.LLLLLLLLLLLLLLLL.LLLL.LLLLLLLLL.LLLLLL
.L..L...L........L.......L.L.LL.LLLL...LLL.L.L..L.L....L.................L.L.L.L....L...L...L..
LLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLLLL.LLLL.LLLLLL.LLLLLLLLLLLLLL.LLLL.LLLL.LLLLLL
LLLLLLLLLL.LLLL.LLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLLLLLLL.LLLL.LLLL.LLLLLL
LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLL.LLLLLLL.LLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLLLLLLL.LLLL.LLLL.LLLLLL
LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLL.LLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLL.LLLL.LLLL.LLLLLL.LLLL
LLLLL.LL.LLLLLLL.LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLL.LLLL.LLLLLLLLLLLLLLLL.LLLL.LLLLLLLLL.LLLLLL
LLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLL.LLLLLLLLLLL.LLLLLLLLLLLLLL.LLLL.LLLL.LLLLLL
LLLL...LLL.......LL..L.L.L.L...L........LL..............L.L......L.......L..LL....L....LL...LL.
LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLLLL.LLLL.LLLLLL.L.LLLLLLL.LLLLLLLLL.LLLLLLLLLLL
LLLLL.LLLL.LLLL.LLLLLL..LLLLLLLL.LLLLLLLLLLLLLLLLLL.LLLL.LLLLLL.LLLLLLLLL.LLLLLLL.L.LLLL.LLLLLL
LLLLL.LLLL.LLLLLLLLLLL.LLLLLLLLL.LLLLLLL.LLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLLLLLLL.LLLL.LLLLLLLLLLL
LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLLLLLLLLLL.LLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLLLL.LLLLLL
LLLLLLLLLL.LLLL.LLLLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLLLL
LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLLLLLLLLLL.LLLLL.LLLL.L.LL.LLLLLL.LLLLLLLLLLL.LL.LLLLLLLLL.LLL.LL
LLLLL.LLLL.LLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLLLLLL.LLLLLLLLLLL.LLLLLLLLL.LLLL.LLLLLLLLLLLLLLLL
LLLLLLLLLL.LLLL.LLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLLLLLLLLL.LLLLLLLLL.LLLL.LLLL.LLLL.LLLLLL
LL.LL.LLLLLLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLL.LLLLLLLLL.LLLL.LLLLLL
LLLL.L...LL.......LL..L.....L.LL..L....L.L..L.......L..L......LLLLL..L.L..L......L...L.L...L.L.
LLLLL.LLL..LLLL.LLLLLL.LLLLL.LLL.LLLLL.L.LLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLLLLLLL.LLLLLLLL..LLLLLL
LLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLL.LLLLLLL.LLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLL.LLLLLLLLL.LLLL.LLLLLL
LLLLL.LLLL.LLLLLLLLLLL.LLLLLLLLL.LLLLLL.LLLLLL.LLLL.LLLLLLLLLLLLLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLL
LLLLLLLLLL.LLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLLLLLLL.LLLL.LLLL.LLLLLL
LLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLL.LLLLL.LLLL.LLLLLLLLLLLL.LLLLLLLL.LLLL.LLLL.LLLL.LLLLLL
L.L...............L..L.LLLL...L..L...LLL.......LLL.LL........L..LL..L..L...L.L.L.LL..LLLL.L.LL.
LLLLLLLL.L.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLL.LLLL.LLLLLLLLL.LLLLLL
LLLLL.LLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLLLL.LLLLLLLLLLL
LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLLL.LLLLLLL.LLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLL.LLLL.LLLLLLLLLLL
LLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLL.LLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLL.LLLLLL
LLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLL.LLLLLLL.LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLL.LLLL.LLLLLLLLLLLLLLLL
L.LLL.LLLL.LLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLL
LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLL.LLLLLLL.LLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLL.LLLLLL
LLLLL.LLLLLLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLL.LLLLLLL.LLLL.LLLL.LLLLLL
.L..LL.L.L..L...L....L......LLL......L.LL..L....L.LLLL.LL.....L.L.LL.L.....L......L.LL.........
LLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLL.LLLL..LLLL.LLLL.LLLLLL.LLLLLLLLL.LLLL.LLLLLLLLL.LLLLLL
LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLL.LLLLLLL.LLLLL.LLLL.LLLLLLLLLLL.LLLLLLLLLLLLLL.LLLLLLLLL.LLLLLL
LLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLL.LLLLLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLL
LLLLL.LLL..LLLL.LLLLLL.LLLLLLLLL.LLLLLLL.LLLLL.LLLL.LLLLLLLLLLL.LLLLLLLLL..LLL.LLLLLLLLLLLLLLLL
.LLL......L.L.L......L.....LL......L.LLL.LLL..LL...L.L.......L..L.......L....L.....L.......LL..
LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLL.LLLLLLL.LLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLL.LLLL.LLLLLLLLL.LLLLLL
LLLLL.LLLL.LLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLL.LLLL.LLLL.LLLL.LLLLLL
LLLLLLLLLL.LLLL.LLLLLL.LLLLLLLLL.LLLLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLLL.LLLL.LLLL.LLLL.LLLLLL
LLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLL.LLLLLLLLLLLLLL.LLLLLL
LLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLL.LLLL.LLLL.LLLL.LLLLLL
LL...L...........L...L..L......LL...........L...L.LL..LL....L....LLL.LLLL....LLL...LL..L..L...L
LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLL.LLLL.LLLL.LLLL.LLLLLL
LLLLL.LLLL.LLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLLLLLL.LLLL.LLLLLL.LLLLLLLLL.LLLL.LLLL.LLLLLLLLLLL
LLLLLLLLLL.LLLL.LLLLLL.LLL.LLLLLLLLLLLLL.LLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLL.LLLL.LLLL.LLLLLLLLLLL
.LLLL.LLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLL.LLLLLLLLLLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLLLL.LLLL.LLLLLL
LLLLL.LLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLL.LLLL.LLLL.LLLL.LLLLLL
LLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLL
LLLLL.LL.LLLLLLLLLLLLL.LLLLLLLLL.LLLLLLL.LLLLLLLLLL.LLLLLLLLLLL.LLLLLLLL..LLLL.LLL..LLLLLLLLLLL
.......L...LL.L.....L.L.....LLL.L.......L.....LL.......L..LLL.....L.LL.L..........LL...........
LLLLL.LLLLLLLLL.LLLLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLLLL.LL.LL.LLL.LLLLLLLLLLLLLL.LLLL.LLLLLLLLLLL
LLLLLLLLLL.LLLL.LLLLLL.LLLLLLLLL.LLLLLLL.LLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLL.LLLL.LLLL.LLLL.LLLLLL
LLLLL.LLLL.LLLLLLLLLLL.LLLLLLLLLLLLLLLLL.LLLLLLLLLL.LLLL.LLLLLL.LLLLLLLL..LLLLLLLLL.LLLL.LLLLLL
LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLL.LLLLLLLLLLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLL.LLLL.LLLLLL
LLLLL.LLLL.LLLL.LLLLLL.LLLLLLLLL.LLLLLLL.LLLLL.LLLL.LLLL.LLLLLLLLLLLLLLLL.LLLL.LLLL.LLLL.LLLLLL
LLLLL.LLLLLLLLLLLLLLLL.LLLLLLLLL.LLLLLLL.LLLLL.LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL.LLLLLLLLL.LLLLLL
""".trimIndent()

import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.abs
import kotlin.system.measureNanoTime

private var runs = 0

@Deprecated("Use aoc() instead")
fun <R : Any> aocRun(input: String, function: (String) -> R) {
	lateinit var result: R
	val time = measureNanoTime { result = function(input) }.formatDurationNanos()
	println(
		"""
            >-------------------------------------------------------
            >Part ${++runs}
            >Result: $result
            >Time taken: $time
            >-------------------------------------------------------
            """.trimMargin(">")
	)
}

fun <T> parseInput(
	regex: Pattern,
	input: String,
	inputDelimiter: String = System.lineSeparator(),
	converter: (Matcher) -> T
): List<T> = input.split(inputDelimiter).map {
	val matcher = regex.matcher(it)
	if (!matcher.find())
		throw RuntimeException("Not a valid input! -> '$input'")
	return@map converter(matcher)
}

/**
 * Gets an [IntProgression] from [int1] to [int2] regardless of which is larger than the other
 */
fun range(int1: Int, int2: Int) = if (int1 == int2)
	IntRange.EMPTY
else
	IntProgression.fromClosedRange(int1, int2, if (int1 < int2) 1 else -1)

/**
 * Gets an [IntProgression] of the integers between [int1] and [int2] regardless of which is larger than the other
 */
fun between(int1: Int, int2: Int) = if (abs(int1 - int2) < 2)
	IntRange.EMPTY
else
	IntProgression.fromClosedRange(
		if (int1 < int2) int1 + 1 else int1 - 1,
		if (int2 < int1) int2 + 1 else int2 - 1,
		if (int1 < int2) 1 else -1
	)

import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.stream.Collectors
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureNanoTime

private var runs = 0

fun <I : Any, R : Any> aocRun(input: I, function: (I) -> R) {
    lateinit var result: R
    val time = measureNanoTime { result = function(input) }.formatDurationNanos()
    println("""
        >-------------------------------------------------------
        >Part ${++runs}
        >Result: $result
        >Time taken: $time
        >-------------------------------------------------------
    """.trimMargin(">"))
}

fun <T> parseInput(regex: Pattern, input: String, inputDelimiter: String = "\n", converter: (Matcher) -> T): List<T> =
    input.split(inputDelimiter).stream()
        .map {
            val matcher = regex.matcher(it)
            if (!matcher.find())
                throw RuntimeException("Not a valid input! -> '$input'")
            return@map converter(matcher)
        }
        .collect(Collectors.toList())

fun range(int1: Int, int2: Int) = if (int1 == int2)
    IntRange.EMPTY
else
    IntProgression.fromClosedRange(int1, int2, if (int1 < int2) 1 else -1)

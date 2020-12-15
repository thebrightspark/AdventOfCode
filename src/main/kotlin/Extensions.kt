import java.awt.Rectangle
import java.text.NumberFormat
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern

private val numberFormat = NumberFormat.getNumberInstance()

fun Number.format(): String = numberFormat.format(this)

fun String.splitInputToInt(delimiter: String = "\n"): List<Int> = this.split(delimiter).map { it.toInt() }

fun String.splitInputToIntMut(delimiter: String = "\n"): MutableList<Int> =
	this.split(delimiter).mapTo(mutableListOf()) { it.toInt() }

fun String.splitInputToLong(delimiter: String = "\n"): List<Long> = this.split(delimiter).map { it.toLong() }

fun <T> String.mapRegex(regex: Pattern, delimiter: String = ",", converter: (Matcher) -> T): List<T> =
	this.split(delimiter).map {
		val matcher = regex.matcher(it)
		if (matcher.matches())
			throw RuntimeException("Input '$it' doesn't match regex '${regex.pattern()}'")
		return@map converter(matcher)
	}

/**
 * Runs the [consumer] for each unique comparison of values in the list.
 * For example, for a list consisting of the integer values 1, 2, 3, and 4, the [consumer] will be invoked for the
 * following pairings:
 *  1, 2
 *  1, 3
 *  1, 4
 *  2, 3
 *  2, 4
 *  3, 4
 *
 *  If the [consumer] returns true then it'll break the loop for value1
 */
fun <T> List<T>.forEachComparison(consumer: (value1: T, value2: T) -> Boolean) {
    this.subList(0, size - 1).forEachIndexed { index1, value1 ->
        this.subList(index1 + 1, size).forEach { value2 ->
            if (consumer(value1, value2))
                return@forEachIndexed
        }
    }
}

fun <T> List<T>.anyComparison(consumer: (value1: T, value2: T) -> Boolean): Boolean {
    this.subList(0, size - 1).forEachIndexed { index1, value1 ->
        this.subList(index1 + 1, size).forEach { value2 ->
            if (consumer(value1, value2))
                return true
        }
    }
    return false
}

fun Rectangle.forEachPoint(consumer: (x: Int, y: Int) -> Unit) {
    (this.x..this.x + this.width).forEach { x ->
        (this.y..this.y + this.height).forEach { y ->
            consumer(x, y)
        }
    }
}

private fun appendTime(sb: StringBuilder, time: Long, unit: String) {
    if (time <= 0) return
    if (sb.isNotEmpty()) sb.append(" ")
    sb.append(time).append(unit)
}

/**
 * Converts this [Long] duration in nanoseconds to a human readable duration.
 * e.g. 2h 30m 59s
 */
fun Long.formatDurationNanos(): String {
    var millis = TimeUnit.NANOSECONDS.toMillis(this)
    if (millis == 0L) return "${this}ns"

    val sb = StringBuilder()
    val hours = TimeUnit.MILLISECONDS.toHours(millis)
    appendTime(sb, hours, "h")
    millis -= TimeUnit.HOURS.toMillis(hours)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
    appendTime(sb, minutes, "m")
    millis -= TimeUnit.MINUTES.toMillis(minutes)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis)
    appendTime(sb, seconds, "s")
    millis -= TimeUnit.SECONDS.toMillis(seconds)
    appendTime(sb, millis, "ms")

    return sb.toString()
}

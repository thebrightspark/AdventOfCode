import kotlin.system.measureNanoTime

class Aoc(private val year: Int, private val day: Int) {
	private var runs = 0
	private val fileInput: String by lazy {
		val name = "/_$year/day$day.txt"
		return@lazy this.javaClass.getResource(name)?.readText() ?: run { error("Can't find resource '$name'") }
	}

	fun <R : Any> aocRun(input: String = fileInput, function: (String) -> R) {
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
}

fun aoc(year: Int, day: Int, aoc: Aoc.() -> Unit) = aoc(Aoc(year, day))

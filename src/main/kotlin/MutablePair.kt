data class MutablePair<A, B>(
	var first: A,
	var second: B
) {
	fun set(first: A, second: B) {
		this.first = first
		this.second = second
	}

	override fun toString(): String = "($first, $second)"
}

infix fun <A, B> A.toMut(that: B): MutablePair<A, B> = MutablePair(this, that)

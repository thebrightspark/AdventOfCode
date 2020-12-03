data class MutablePair<A, B>(
	var first: A,
	var second: B
) {
	override fun toString(): String = "($first, $second)"
}

infix fun <A, B> A.toMut(that: B): MutablePair<A, B> = MutablePair(this, that)

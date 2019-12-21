package _2019

import aocRun
import java.util.regex.Pattern
import kotlin.math.abs

private val pattern = Pattern.compile("<x=(?<x>-?\\d+), y=(?<y>-?\\d+), z=(?<z>-?\\d+)>")

fun main() {
    aocRun(puzzleInput) { input ->
        val moons = parseMoons(input)
        repeat(1000) {
            moveMoons(moons)
//            if ((it + 1) % 10 == 0)
//                println("I: ${(it + 1)}\n${moonsToString(moons)}")
        }
        return@aocRun calcTotalEnergy(moons)
    }

    aocRun(puzzleInput) { input ->
        val moons = parseMoons(input)
        val initialState = moons.map { Moon(it.pos.copy(), it.vel.copy()) }
        var steps = 0
        val loopLength = MutableTriple(0, 0, 0)
        do {
            moveMoons(moons)
            steps++
            if (loopLength.x == 0 && moonAxisMatches(initialState, moons) { it.x }) {
                loopLength.x = steps
                println("Found X loop length: $steps")
            }
            if (loopLength.y == 0 && moonAxisMatches(initialState, moons) { it.y }) {
                loopLength.y = steps
                println("Found Y loop length: $steps")
            }
            if (loopLength.z == 0 && moonAxisMatches(initialState, moons) { it.z }) {
                loopLength.z = steps
                println("Found Z loop length: $steps")
            }
            if (loopLength.x != 0 && loopLength.y != 0 && loopLength.z != 0)
                break
        } while (initialState != moons)
        val lcm = lcm(loopLength.x, loopLength.y, loopLength.z)
        return@aocRun "$loopLength -> $lcm"
    }
}

private fun moonsToString(moons: List<Moon>) = moons.joinToString("\n")

private fun parseMoons(input: String): List<Moon> = input.split("\n").map { posString ->
    val matcher = pattern.matcher(posString)
    if (!matcher.matches())
        throw RuntimeException("Invalid position input: $posString")
    return@map Moon(MutableTriple(matcher.group("x").toInt(), matcher.group("y").toInt(), matcher.group("z").toInt()))
}

private fun moveMoons(moons: List<Moon>) {
    val oldMoons = moons.toList()
    moons.forEachIndexed new@{ i, moon ->
        oldMoons.forEachIndexed old@{ oldI, oldMoon ->
            if (i == oldI) return@old
            moon.applyGravity(oldMoon)
        }
    }
    moons.forEach { it.move() }
}

private fun calcTotalEnergy(moons: List<Moon>): Int = moons.sumBy {
    val potential = it.pos.run { abs(x) + abs(y) + abs(z) }
    val kinetic = it.vel.run { abs(x) + abs(y) + abs(z) }
    val total = potential * kinetic
//    println("$it\nPot: $potential\tKin: $kinetic\tTotal: $total")
    return@sumBy total
}

private fun moonAxisMatches(
    initialState: List<Moon>,
    currentState: List<Moon>,
    axisSelector: (MutableTriple) -> Int
): Boolean {
    currentState.forEachIndexed { index, moon ->
        val initialMoon = initialState[index]
        if (axisSelector(moon.pos) != axisSelector(initialMoon.pos) || axisSelector(moon.vel) != axisSelector(
                initialMoon.vel
            )
        )
            return false
    }
    return true
}

private fun lcm(vararg ints: Int): Long = lcmInternal(num = ints.max()!!.toLong(), ints = ints.map { it.toLong() })

private tailrec fun lcmInternal(num: Long, increment: Long = num, ints: List<Long>): Long =
    if (ints.all { num % it == 0.toLong() }) num else lcmInternal(num + increment, increment, ints)

private data class MutableTriple(var x: Int, var y: Int, var z: Int)

private data class Moon(val pos: MutableTriple, val vel: MutableTriple = MutableTriple(0, 0, 0)) {
    fun applyGravity(other: Moon) {
        vel.x += other.pos.x.compareTo(pos.x)
        vel.y += other.pos.y.compareTo(pos.y)
        vel.z += other.pos.z.compareTo(pos.z)
    }

    fun move() {
        pos.x += vel.x
        pos.y += vel.y
        pos.z += vel.z
    }
}

private val testInput1 = """
<x=-1, y=0, z=2>
<x=2, y=-10, z=-7>
<x=4, y=-8, z=8>
<x=3, y=5, z=-1>
""".trimIndent()

private val testInput2 = """
<x=-8, y=-10, z=0>
<x=5, y=5, z=10>
<x=2, y=-7, z=3>
<x=9, y=-8, z=-3>
""".trimIndent()

private val puzzleInput = """
<x=4, y=12, z=13>
<x=-9, y=14, z=-3>
<x=-7, y=-1, z=2>
<x=-11, y=17, z=-1>
""".trimMargin()
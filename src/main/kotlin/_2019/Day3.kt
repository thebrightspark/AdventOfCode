package _2019

import aocRun
import range
import splitInput
import kotlin.math.abs

fun main() {
    aocRun(puzzleInput) { input ->
        val wiresRaw = input.splitInput()
        val wire1 = parseWire(wiresRaw[0])
        val wire2 = parseWire(wiresRaw[1])
        val intersections = intersections(wire1, wire2)
        return@aocRun intersections.map { Pair(it, it.dist()) }.minBy { it.second }!!
    }

    aocRun(puzzleInput) { input ->
        val wiresRaw = input.splitInput()
        val wire1 = parseWire(wiresRaw[0])
        val wire2 = parseWire(wiresRaw[1])
        val intersections = intersections(wire1, wire2)
        return@aocRun intersections.map {
//            println("1")
            val steps1 = steps(wire1, it)
//            println("2")
            val steps2 = steps(wire2, it)
            val total = steps1 + steps2
//            println("$it -> $steps1 + $steps2 = $total")
            Pair(it, total)
        }.minBy { it.second }!!
    }
}

private fun steps(wire: List<Line>, pos: Pos): Int {
    var steps = 0
    wire.forEach { line ->
        line.positions.apply {
            val index = indexOf(pos)
            val s = if (index < 0) size - 1 else index
//            println("$line -> $s")
            steps += s
            if (index >= 0)
                return steps
        }
    }
    return steps
}

private fun intersections(wire1: List<Line>, wire2: List<Line>): List<Pos> = mutableListOf<Pos>().apply {
    wire1.forEach { line1 ->
        wire2.forEach { line2 ->
            line1.intersection(line2)?.let { add(it) }
        }
    }
}

private fun parseWire(inputWire: String): List<Line> {
    var lastPos = Pos.ORIGIN
    return inputWire.split(',').map {
        val dir = Dir.valueOf(it[0].toString())
        val num = it.substring(1).toInt()
        val pos = lastPos.offset(dir, num)
        val line = Line(lastPos, pos)
        lastPos = pos
        return@map line
    }
}

private enum class Dir(val x: Int, val y: Int) {
    U(0, 1),
    D(0, -1),
    L(-1, 0),
    R(1, 0)
}

private data class Pos(val x: Int, val y: Int) {
    companion object {
        val ORIGIN = Pos(0, 0)
    }

    fun offset(dir: Dir, num: Int) = Pos(x + (dir.x * num), y + (dir.y * num))

    fun dist(pos: Pos = ORIGIN) = abs(x - pos.x) + abs(y - pos.y)
}

private data class Line(val pos1: Pos, val pos2: Pos) {
    init {
        if (pos1 == pos2 || (pos1.x != pos2.x && pos1.y != pos2.y))
            throw RuntimeException("The two positions $pos1 and $pos2 do not form a horizontal or vertical line!")
    }

    val positions by lazy {
        if (pos1.x == pos2.x)
            range(pos1.y, pos2.y).map { Pos(pos1.x, it) }
        else
            range(pos1.x, pos2.x).map { Pos(it, pos1.y) }
    }

    fun intersection(line: Line): Pos? {
        positions.forEach { pos ->
            if (pos != Pos.ORIGIN)
                line.positions.find { it == pos }?.let { return it }
        }
        return null
    }
}

private const val testInput1 = "R75,D30,R83,U83,L12,D49,R71,U7,L72\nU62,R66,U55,R34,D71,R55,D58,R83"
private const val testInput2 = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\nU98,R91,D20,R16,D67,R40,U7,R15,U6,R7"
private const val testInput3 = "R8,U5,L5,D3\nU7,R6,D4,L4"

private const val puzzleInput = "R991,U557,R554,U998,L861,D301,L891,U180,L280,D103,R828,D58,R373,D278,L352,D583,L465,D301,R384,D638,L648,D413,L511,U596,L701,U463,L664,U905,L374,D372,L269,U868,R494,U294,R661,U604,L629,U763,R771,U96,R222,U227,L97,D793,L924,U781,L295,D427,R205,D387,L455,D904,R254,D34,R341,U268,L344,D656,L715,U439,R158,U237,R199,U729,L428,D125,R487,D506,R486,D496,R932,D918,R603,U836,R258,U15,L120,U528,L102,D42,R385,U905,L472,D351,R506,U860,L331,D415,R963,D733,R108,D527,L634,U502,L553,D623,R973,U209,L632,D588,R264,U553,L768,D689,L708,D432,R247,U993,L146,U656,R710,U47,R783,U643,R954,U888,L84,U202,R495,U66,R414,U993,R100,D557,L326,D645,R975,U266,R143,U730,L491,D96,L161,U165,R97,D379,R930,D613,R178,D635,R192,U957,L450,U149,R911,U220,L914,U659,L67,D825,L904,U137,L392,U333,L317,U310,R298,D240,R646,U588,R746,U861,L958,D892,L200,U463,R246,D870,R687,U815,R969,U864,L972,U254,L120,D418,L567,D128,R934,D217,R764,U128,R146,U467,R690,U166,R996,D603,R144,D362,R885,D118,L882,U612,R270,U917,L599,D66,L749,D498,L346,D920,L222,U439,R822,U891,R458,U15,R831,U92,L164,D615,L439,U178,R409,D463,L452,U633,L683,U186,R402,D609,L38,D699,L679,D74,R125,D145,R424,U961,L353,U43,R794,D519,L359,D494,R812,D770,L657,U154,L137,U549,L193,D816,R333,U650,R49,D459,R414,U72,R313,U231,R370,U680,L27,D221,L355,U342,L597,U748,R821,D280,L307,U505,L160,U982,L527,D516,L245,U158,R565,D797,R99,D695,L712,U155,L23,U964,L266,U623,L317,U445,R689,U150,L41,U536,R638,D200,R763,D260,L234,U217,L881,D576,L223,U39,L808,D125,R950,U341,L405\n" +
        "L993,D508,R356,U210,R42,D68,R827,D513,L564,D407,L945,U757,L517,D253,R614,U824,R174,D536,R906,D291,R70,D295,R916,D754,L892,D736,L528,D399,R76,D588,R12,U617,R173,D625,L533,D355,R178,D706,R139,D419,R460,U976,L781,U973,L931,D254,R195,U42,R555,D151,R226,U713,L755,U398,L933,U264,R352,U461,L472,D810,L257,U901,R429,U848,L181,D362,R404,D234,L985,D392,R341,U608,L518,D59,L804,D219,L366,D28,L238,D491,R265,U131,L727,D504,R122,U461,R732,D411,L910,D884,R954,U341,L619,D949,L570,D823,R646,D226,R197,U892,L691,D294,L955,D303,R490,D469,L503,D482,R390,D741,L715,D187,R378,U853,L70,D903,L589,D481,L589,U911,R45,U348,R214,D10,R737,D305,R458,D291,R637,D721,R440,U573,R442,D407,L63,U569,L903,D936,R518,U859,L370,D888,R498,D759,R283,U469,R548,D185,R808,D81,L629,D761,R807,D878,R712,D183,R382,D484,L791,D371,L188,D397,R645,U679,R415,D446,L695,U174,R707,D36,R483,U877,L819,D538,L277,D2,R200,D838,R837,U347,L865,D945,R958,U575,L924,D351,L881,U961,R899,U845,R816,U866,R203,D380,R766,D97,R38,U148,L999,D332,R543,U10,R351,U281,L460,U309,L543,U795,L639,D556,L882,D513,R722,U314,R531,D604,L418,U840,R864,D694,L530,U862,R559,D639,R689,D201,L439,D697,R441,U175,R558,D585,R92,D191,L533,D788,R154,D528,R341,D908,R811,U750,R172,D742,R113,U56,L517,D826,L250,D269,L278,U74,R285,U904,L221,U270,R296,U671,L535,U340,L206,U603,L852,D60,R648,D313,L282,D685,R482,U10,R829,U14,L12,U365,R996,D10,R104,U654,R346,D458,R219,U247,L841,D731,R115,U400,L731,D904,L487,U430,R612,U437,L865,D618,R747,U522,R309,U302,R9,U609,L201"
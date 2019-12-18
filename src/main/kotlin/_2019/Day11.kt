package _2019

import aocRun

fun main() {
    aocRun(puzzleInput) { input -> paint(input, PaintColour.BLACK).size }

    aocRun(puzzleInput) { input ->
        val panels = paint(input, PaintColour.WHITE)
        val min = RobotPos(Int.MAX_VALUE, Int.MAX_VALUE)
        val max = RobotPos(Int.MIN_VALUE, Int.MIN_VALUE)
        panels.keys.forEach {
            if (it.x < min.x) min.x = it.x
            if (it.x > max.x) max.x = it.x
            if (it.y < min.y) min.y = it.y
            if (it.y > max.y) max.y = it.y
        }
        val pos = RobotPos(0, 0)
        val sb = StringBuilder()
        (max.y downTo min.y).forEach { y ->
            (min.x..max.x).forEach {
                sb.append(
                    panels[pos.apply {
                        this.x = it
                        this.y = y
                    }]?.char ?: PaintColour.BLACK.char
                ).append(" ")
            }
            sb.append("\n")
        }
        println(sb.toString())
        return@aocRun
    }
}

private fun paint(input: String, startPanel: PaintColour): Map<RobotPos, PaintColour> {
    val panels = mutableMapOf<RobotPos, PaintColour>()
    var facing = Facing.UP
    val pos = RobotPos(0, 0)
    var lastPainted: PaintColour? = null
    panels[pos.copy()] = startPanel
    IntcodeComputer(input, startPanel.ordinal.toLong()).apply {
        printOut = false
        while (!isFinished()) {
            executeStep()
            if (lastOutput.isNotBlank()) {
                if (lastPainted == null) {
                    lastPainted = PaintColour.values()[lastOutput.toInt()]
                    panels[pos.copy()] = lastPainted!!
//                    println("Painted $lastPainted at $pos")
                    clearLastOutput()
                } else {
                    val outputInt = lastOutput.toInt()
                    facing = when (outputInt) {
                        0 -> facing.turnLeft()
                        1 -> facing.turnRight()
                        else -> throw RuntimeException("Wtf is this output? -> $lastOutput")
                    }
                    pos.x += facing.x
                    pos.y += facing.y
//                    println("Turned ${if (outputInt == 0) "left" else "right"} and moved forward to $pos")
                    clearLastOutput()
                    lastPainted = null
                    addInput(panels.getOrDefault(pos, PaintColour.BLACK).ordinal.toLong())
                }
            }
        }
    }
    return panels
}

private data class RobotPos(var x: Int, var y: Int)

private enum class PaintColour(val char: Char) {
    BLACK('.'),
    WHITE('#');
}

private enum class Facing(val x: Int, val y: Int) {
    UP(0, 1),
    RIGHT(1, 0),
    DOWN(0, -1),
    LEFT(-1, 0);

    private fun get(i: Int) = values()[when {
        i < 0 -> 3
        i > 3 -> 0
        else -> i
    }]

    fun turnLeft() = get(ordinal - 1)

    fun turnRight() = get(ordinal + 1)
}

private const val puzzleInput =
    "3,8,1005,8,290,1106,0,11,0,0,0,104,1,104,0,3,8,1002,8,-1,10,1001,10,1,10,4,10,108,1,8,10,4,10,1002,8,1,28,1006,0,59,3,8,1002,8,-1,10,101,1,10,10,4,10,108,0,8,10,4,10,101,0,8,53,3,8,1002,8,-1,10,101,1,10,10,4,10,1008,8,0,10,4,10,101,0,8,76,1006,0,81,1,1005,2,10,3,8,102,-1,8,10,1001,10,1,10,4,10,1008,8,1,10,4,10,1002,8,1,105,3,8,102,-1,8,10,1001,10,1,10,4,10,108,1,8,10,4,10,1001,8,0,126,3,8,1002,8,-1,10,1001,10,1,10,4,10,108,1,8,10,4,10,1002,8,1,148,3,8,102,-1,8,10,101,1,10,10,4,10,1008,8,1,10,4,10,1001,8,0,171,3,8,1002,8,-1,10,1001,10,1,10,4,10,1008,8,0,10,4,10,101,0,8,193,1,1008,8,10,1,106,3,10,1006,0,18,3,8,1002,8,-1,10,1001,10,1,10,4,10,108,0,8,10,4,10,1001,8,0,225,1,1009,9,10,1006,0,92,3,8,1002,8,-1,10,1001,10,1,10,4,10,108,0,8,10,4,10,1001,8,0,254,2,1001,8,10,1,106,11,10,2,102,13,10,1006,0,78,101,1,9,9,1007,9,987,10,1005,10,15,99,109,612,104,0,104,1,21102,1,825594852136,1,21101,0,307,0,1106,0,411,21101,0,825326580628,1,21101,0,318,0,1105,1,411,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,21102,179557207043,1,1,21101,0,365,0,1106,0,411,21101,0,46213012483,1,21102,376,1,0,1106,0,411,3,10,104,0,104,0,3,10,104,0,104,0,21101,988648727316,0,1,21102,399,1,0,1105,1,411,21102,988224959252,1,1,21101,0,410,0,1106,0,411,99,109,2,21201,-1,0,1,21101,0,40,2,21102,1,442,3,21101,432,0,0,1105,1,475,109,-2,2105,1,0,0,1,0,0,1,109,2,3,10,204,-1,1001,437,438,453,4,0,1001,437,1,437,108,4,437,10,1006,10,469,1102,0,1,437,109,-2,2105,1,0,0,109,4,2102,1,-1,474,1207,-3,0,10,1006,10,492,21101,0,0,-3,21202,-3,1,1,22102,1,-2,2,21101,0,1,3,21102,511,1,0,1105,1,516,109,-4,2105,1,0,109,5,1207,-3,1,10,1006,10,539,2207,-4,-2,10,1006,10,539,21201,-4,0,-4,1106,0,607,21202,-4,1,1,21201,-3,-1,2,21202,-2,2,3,21101,558,0,0,1106,0,516,22101,0,1,-4,21101,1,0,-1,2207,-4,-2,10,1006,10,577,21102,1,0,-1,22202,-2,-1,-2,2107,0,-3,10,1006,10,599,21201,-1,0,1,21101,0,599,0,105,1,474,21202,-2,-1,-2,22201,-4,-2,-4,109,-5,2106,0,0"
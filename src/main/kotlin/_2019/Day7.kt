package _2019

import aocRun

fun main() {
    aocRun(puzzleInput) { run(it, 0, 1, 2, 3, 4) }

    aocRun("3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5") {
        run(it, 5, 6, 7, 8, 9)
    }
}

private fun run(codeString: String, vararg phaseSettings: Long): String {
    val code = IntcodeComputer.parseCode(codeString)
    val computer = IntcodeComputer()
    computer.printOut = false
    var max = Long.MIN_VALUE
    phaseSettings.forEach { a ->
        val ampA = runAmplifier(computer, code, a)
        phaseSettings.forEach b@{ b ->
            if (b == a) return@b
            val ampB = runAmplifier(computer, code, b, ampA)
            phaseSettings.forEach c@{ c ->
                if (c == a || c == b) return@c
                val ampC = runAmplifier(computer, code, c, ampB)
                phaseSettings.forEach d@{ d ->
                    if (d == a || d == b || d == c) return@d
                    val ampD = runAmplifier(computer, code, d, ampC)
                    phaseSettings.forEach e@{ e ->
                        if (e == a || e == b || e == c || e == d) return@e
                        val ampE = runAmplifier(computer, code, e, ampD)
                        println("$a$b$c$d$e -> $ampE")
                        if (ampE > max)
                            max = ampE
                    }
                }
            }
        }
    }
    return computer.lastOutput
}

private fun runAmplifier(computer: IntcodeComputer, code: List<Long>, phaseSetting: Long, inputSignal: Long = 0): Long =
    computer.init(code.toMutableList(), phaseSetting, inputSignal).execute().lastOutput.toLong()

private const val puzzleInput = "3,8,1001,8,10,8,105,1,0,0,21,30,47,60,81,102,183,264,345,426,99999,3,9,1002,9,5,9,4,9,99,3,9,1002,9,5,9,1001,9,4,9,1002,9,4,9,4,9,99,3,9,101,2,9,9,1002,9,4,9,4,9,99,3,9,1001,9,3,9,1002,9,2,9,101,5,9,9,1002,9,2,9,4,9,99,3,9,102,4,9,9,101,4,9,9,1002,9,3,9,101,2,9,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,101,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,99"
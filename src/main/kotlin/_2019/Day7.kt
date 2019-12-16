package _2019

import aocRun

fun main() {
    aocRun(puzzleInput) { input ->
        val code = IntcodeComputer.parseCode(input)
        val computer = IntcodeComputer()
        var max = Long.MIN_VALUE
        phaseSettingValues.forEach { a ->
            val ampA = runAmplifier(computer, code, "A", a)
            phaseSettingValues.forEach b@{ b ->
                if (b == a) return@b
                val ampB = runAmplifier(computer, code, "B", b, ampA)
                phaseSettingValues.forEach c@{ c ->
                    if (c == a || c == b) return@c
                    val ampC = runAmplifier(computer, code, "C", c, ampB)
                    phaseSettingValues.forEach d@{ d ->
                        if (d == a || d == b || d == c) return@d
                        val ampD = runAmplifier(computer, code, "D", d, ampC)
                        phaseSettingValues.forEach e@{ e ->
                            if (e == a || e == b || e == c || e == d) return@e
                            val ampE = runAmplifier(computer, code, "E", e, ampD)
                            println("$a$b$c$d$e -> $ampE")
                            if (ampE > max)
                                max = ampE
                        }
                    }
                }
            }
        }
        return@aocRun max
    }
}

private val phaseSettingValues = Array(5) { it.toLong() }

private fun runAmplifier(computer: IntcodeComputer, code: List<Long>, amp: String, phaseSetting: Long, inputSignal: Long = 0): Long {
    println("Executing amp $amp with inputs: $phaseSetting and $inputSignal")
    return computer.init(code.toMutableList(), phaseSetting, inputSignal).execute().lastOutput.toLong()
}

private const val puzzleInput = "3,8,1001,8,10,8,105,1,0,0,21,30,47,60,81,102,183,264,345,426,99999,3,9,1002,9,5,9,4,9,99,3,9,1002,9,5,9,1001,9,4,9,1002,9,4,9,4,9,99,3,9,101,2,9,9,1002,9,4,9,4,9,99,3,9,1001,9,3,9,1002,9,2,9,101,5,9,9,1002,9,2,9,4,9,99,3,9,102,4,9,9,101,4,9,9,1002,9,3,9,101,2,9,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,101,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,99"
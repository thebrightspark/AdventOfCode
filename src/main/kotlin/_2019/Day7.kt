package _2019

import aocRun

fun main() {
    aocRun(testInput1) { input ->
        // TESTING

        IntcodeComputer.debug = true
        val code = IntcodeComputer.parseCode(input)
        println("Amp A")
        IntcodeComputer.execute(code.toMutableList(), 4, 0)
        println("Amp B")
        IntcodeComputer.execute(code.toMutableList(), 3, IntcodeComputer.lastOutput.toInt())
        println("Amp C")
        IntcodeComputer.execute(code.toMutableList(), 2, IntcodeComputer.lastOutput.toInt())
        println("Amp D")
        IntcodeComputer.execute(code.toMutableList(), 1, IntcodeComputer.lastOutput.toInt())
        println("Amp E")
        IntcodeComputer.execute(code.toMutableList(), 0, IntcodeComputer.lastOutput.toInt())
    }

    aocRun(testInput1) { input ->
//        IntcodeComputer.printOut = false
        val code = IntcodeComputer.parseCode(input)
        var max = Int.MIN_VALUE
        phaseSettingValues.forEach { a ->
            val ampA = runAmplifier(code, "A", a)
            phaseSettingValues.forEach b@{ b ->
                if (b == a) return@b
                val ampB = runAmplifier(code, "B", a, ampA)
                phaseSettingValues.forEach c@{ c ->
                    if (c == a || c == b) return@c
                    val ampC = runAmplifier(code, "C", a, ampB)
                    phaseSettingValues.forEach d@{ d ->
                        if (d == a || d == b || d == c) return@d
                        val ampD = runAmplifier(code, "D", a, ampC)
                        phaseSettingValues.forEach e@{ e ->
                            if (e == a || e == b || e == c || e == d) return@e
                            val ampE = runAmplifier(code, "E", a, ampD)
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

private val phaseSettingValues = Array(5) { it }

private fun runAmplifier(code: List<Int>, amp: String, phaseSetting: Int, inputSignal: Int = 0): Int {
    println("Executing amp $amp with inputs: $phaseSetting and $inputSignal")
    IntcodeComputer.execute(code.toMutableList(), phaseSetting, inputSignal)
    return IntcodeComputer.lastOutput.toInt()
}

private const val testInput1 = "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0"

private const val puzzleInput = "3,8,1001,8,10,8,105,1,0,0,21,30,47,60,81,102,183,264,345,426,99999,3,9,1002,9,5,9,4,9,99,3,9,1002,9,5,9,1001,9,4,9,1002,9,4,9,4,9,99,3,9,101,2,9,9,1002,9,4,9,4,9,99,3,9,1001,9,3,9,1002,9,2,9,101,5,9,9,1002,9,2,9,4,9,99,3,9,102,4,9,9,101,4,9,9,1002,9,3,9,101,2,9,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,101,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,99"
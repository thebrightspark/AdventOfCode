package _2019

import aocRun

fun main() {
    aocRun(puzzleInput) { input ->
        val code = IntcodeComputer.parseCode(input).apply {
            set(1, 12)
            set(2, 2)
        }
        return@aocRun IntcodeComputer(code).execute().code[0]
        /*return@aocRun parseInput(input).apply {
            set(1, 12)
            set(2, 2)
            process(this)
        }*/
    }

    aocRun(puzzleInput) { input ->
        val baseCode = IntcodeComputer.parseCode(input)
        (0.toLong()..99.toLong()).forEach { noun ->
            (0.toLong()..99.toLong()).forEach { verb ->
                val code = baseCode.toMutableList().apply {
                    set(1, noun)
                    set(2, verb)
                }
                if (IntcodeComputer(code).execute().code[0] == 19690720.toLong())
                    return@aocRun 100 * noun + verb
            }
        }
        /*val baseCode = parseInput(input)
        (0..99).forEach { noun ->
            (0..99).forEach { verb ->
                val val0 = baseCode.toMutableList().apply {
                    set(1, noun)
                    set(2, verb)
                    process(this)
                }[0]
                if (val0 == 19690720)
                    return@aocRun 100 * noun + verb
            }
        }*/
    }
}

/*private fun parseInput(input: String) = input.split(',').map { it.toInt() }.toMutableList()

private fun process(code: MutableList<Int>) {
    for (i in 0 until code.size step 4)
        when (val op = code[i]) {
            // Add
            1 -> code[code[i + 3]] = code[code[i + 1]] + code[code[i + 2]]
            // Mult
            2 -> code[code[i + 3]] = code[code[i + 1]] * code[code[i + 2]]
            // End
            99 -> return
            // WTF?
            else -> throw RuntimeException("Uh, what? -> $op")
        }
}*/

private const val puzzleInput = "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,10,1,19,1,5,19,23,1,23,5,27,2,27,10,31,1,5,31,35,2,35,6,39,1,6,39,43,2,13,43,47,2,9,47,51,1,6,51,55,1,55,9,59,2,6,59,63,1,5,63,67,2,67,13,71,1,9,71,75,1,75,9,79,2,79,10,83,1,6,83,87,1,5,87,91,1,6,91,95,1,95,13,99,1,10,99,103,2,6,103,107,1,107,5,111,1,111,13,115,1,115,13,119,1,13,119,123,2,123,13,127,1,127,6,131,1,131,9,135,1,5,135,139,2,139,6,143,2,6,143,147,1,5,147,151,1,151,2,155,1,9,155,0,99,2,14,0,0"
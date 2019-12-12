package _2019

object IntcodeComputer {
    var debug = false
    var lastCode: MutableList<Int> = mutableListOf()
        private set
    var lastOutput: String = ""
        private set

    fun parseCode(code: String) = code.split(',').map { it.toInt() }.toMutableList()

    fun execute(code: String, input: Int = 0) = execute(parseCode(code), input)

    fun execute(code: MutableList<Int>, input: Int = 0) {
        lastCode = code
        LocalContext(code, input).apply {
            var lastPointer = pointer
            while (true) {
                init()
                if (debug)
                    println("Processing index $pointer -> $opCode: ${getParamInfo(this)}")
                if (opCode.isEnd())
                    return
                execute()
                if (lastPointer == pointer)
                    throw RuntimeException("Infinite loop detected!")
                lastPointer = pointer
            }
        }
    }

    private fun getParamInfo(context: LocalContext): String = Array(context.opCode.numParams) {
        val value = context.code[context.pointer + it + 1]
        val mode = context.paramModes[it]
        return@Array "($value${if (mode == ParamMode.POSITION) "->${context.code[value]}" else ""})"
    }.joinToString()

    private class LocalContext(val code: MutableList<Int>, val input: Int) {
        var pointer: Int = 0
        lateinit var opCode: OpCode
        val paramModes = arrayOf(ParamMode.POSITION, ParamMode.POSITION, ParamMode.POSITION)

        fun init() {
            paramModes.fill(ParamMode.POSITION)
            val instruction = code[pointer].toString()
            val length = instruction.length
            if (length <= 2) {
                opCode = OpCode.fromCode(instruction.toInt())
            } else {
                opCode = OpCode.fromCode(instruction.substring(length - 2).toInt())
                instruction.substring(0, length - 2).reversed().forEachIndexed { index, c ->
                    paramModes[index] = ParamMode.values()[c.toString().toInt()]
                }
            }
        }

        fun execute() = opCode.execute(this)

        /**
         * Gets the param at the local instruction [index]
         */
        fun get(index: Int) = paramModes[index].get(code, code[pointer + index + 1])

        /**
         * Sets the [value] to the position defined by the local instruction [index]
         */
        fun set(index: Int, value: Int) {
            code[code[pointer + index + 1]] = value
        }
    }

    @Suppress("unused")
    private enum class OpCode(val code: Int, val numParams: Int) {
        ADD(1, 3) {
            override fun execute(context: LocalContext) {
                context.set(2, context.get(0) + context.get(1))
                super.execute(context)
            }
        },
        MULT(2, 3) {
            override fun execute(context: LocalContext) {
                context.set(2, context.get(0) * context.get(1))
                super.execute(context)
            }
        },
        IN(3, 1) {
            override fun execute(context: LocalContext) {
                context.set(0, context.input)
                super.execute(context)
            }
        },
        OUT(4, 1) {
            override fun execute(context: LocalContext) {
                lastOutput = context.get(0).toString()
                println("Output (${context.pointer}): $lastOutput")
                super.execute(context)
            }
        },
        JUMP_TRUE(5, 2) {
            override fun execute(context: LocalContext) {
                if (context.get(0) != 0)
                    context.pointer = context.get(1)
                else
                    super.execute(context)
            }
        },
        JUMP_FALSE(6, 2) {
            override fun execute(context: LocalContext) {
                if (context.get(0) == 0)
                    context.pointer = context.get(1)
                else
                    super.execute(context)
            }
        },
        LESS_THAN(7, 3) {
            override fun execute(context: LocalContext) {
                context.set(2, if (context.get(0) < context.get(1)) 1 else 0)
                super.execute(context)
            }
        },
        EQUALS(8, 3) {
            override fun execute(context: LocalContext) {
                context.set(2, if (context.get(0) == context.get(1)) 1 else 0)
                super.execute(context)
            }
        },
        END(99, 0);

        fun isEnd() = this == END

        fun shouldSkip() = this != JUMP_TRUE && this != JUMP_FALSE

        open fun execute(context: LocalContext) {
            context.pointer += numParams + 1
        }

        companion object {
            private val VALUES_MAP = values().associateBy { it.code }

            fun fromCode(opCode: Int) = VALUES_MAP[opCode] ?: throw RuntimeException("OpCode doesn't exist for code $opCode")
        }
    }

    private enum class ParamMode {
        POSITION {
            override fun get(code: MutableList<Int>, value: Int): Int = code[value]
        },
        IMMEDIATE {
            override fun get(code: MutableList<Int>, value: Int): Int = value
        };

        abstract fun get(code: MutableList<Int>, value: Int): Int
    }
}

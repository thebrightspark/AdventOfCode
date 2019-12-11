package _2019

object IntcodeComputer {
    var debug = false

    fun parseCode(code: String) = code.split(',').map { it.toInt() }.toMutableList()

    fun execute(code: String, input: Int = 0) = execute(parseCode(code), input)

    fun execute(code: MutableList<Int>, input: Int = 0): MutableList<Int> {
        val context = LocalContext(code, input)
        var index = 0
        while (true) {
            context.apply {
                init(index)
                if (debug)
                    println("Processing index $index -> $opCode: ${getParamInfo(context, index)}")
                if (isEnd())
                    return code
                execute()
                index += opCode.numParams + 1
            }
        }
    }

    private fun getParamInfo(context: LocalContext, index: Int): String = listOf(0, 1, 2)
        .map {
            val value = context.code[index + it + 1]
            val mode = context.paramModes[it]
            return@map "($value${if (mode == ParamMode.POSITION) "->${context.code[value]}" else ""})"
        }
        .joinToString()

    private class LocalContext(val code: MutableList<Int>, val input: Int) {
        var opIndex: Int = 0
        lateinit var opCode: OpCode
        val paramModes = arrayOf(ParamMode.POSITION, ParamMode.POSITION, ParamMode.POSITION)

        fun init(opIndex: Int) {
            this.opIndex = opIndex
            val instruction = code[opIndex].toString()
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

        fun isEnd() = opCode.isEnd()

        fun execute() = opCode.execute(this)

        /**
         * Gets the param at the local instruction [index]
         */
        fun get(index: Int) = paramModes[index].get(code, code[opIndex + index + 1])

        /**
         * Sets the [value] to the position defined by the local instruction [index]
         */
        fun set(index: Int, value: Int) {
            code[code[opIndex + index + 1]] = value
        }
    }

    @Suppress("unused")
    private enum class OpCode(val code: Int, val numParams: Int) {
        ADD(1, 3) {
            override fun execute(context: LocalContext) =
                context.set(2, context.get(0) + context.get(1))
        },
        MULT(2, 3) {
            override fun execute(context: LocalContext) =
                context.set(2, context.get(0) * context.get(1))
        },
        IN(3, 1) {
            override fun execute(context: LocalContext) = context.set(0, context.input)
        },
        OUT(4, 1) {
            override fun execute(context: LocalContext) = println("Output (${context.opIndex}): ${context.get(0)}")
        },
        END(99, 0);

        fun isEnd() = this == END

        open fun execute(context: LocalContext) = Unit

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

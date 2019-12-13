package _2019

class IntcodeComputer {
    var debug = false
    var printOut = true
    var lastOutput: String = ""
        private set

    lateinit var code: MutableList<Int>
        private set
    var pointer = 0
        private set
    private var lastPointer = 0
    lateinit var input: IntArray
        private set
    var nextInput = 0
        private set
    private var opCode: OpCode? = null
    private val paramModes = arrayOf(ParamMode.POSITION, ParamMode.POSITION, ParamMode.POSITION)

    companion object {
        fun parseCode(code: String) = code.split(',').map { it.toInt() }.toMutableList()
    }

    constructor() {
        code = mutableListOf()
        input = intArrayOf()
    }

    constructor(code: String, input: Int = 0): this(parseCode(code), input)

    constructor(code: MutableList<Int>, vararg input: Int = intArrayOf(0)) {
        init(code, *input)
    }

    fun isFinished() = opCode != null && opCode!!.isEnd()

    fun init(code: MutableList<Int>, vararg input: Int = intArrayOf(0)): IntcodeComputer = apply {
        this.code = code
        this.input = input
        pointer = 0
        lastPointer = 0
        nextInput = 0
        opCode = null
    }

    private fun canExecute() = code.isEmpty() || isFinished()

    fun execute(): IntcodeComputer {
        if (canExecute())
            return this
        while (!isFinished())
            executeStep()
        return this
    }

    fun executeStep(): IntcodeComputer {
        if (canExecute())
            return this
        paramModes.fill(ParamMode.POSITION)
        val instruction = code[pointer].toString()
        val length = instruction.length
        if (length <= 2)
            opCode = OpCode.fromCode(instruction.toInt())
        else {
            opCode = OpCode.fromCode(instruction.substring(length - 2).toInt())
            instruction.substring(0, length - 2).reversed().forEachIndexed { index, c ->
                paramModes[index] = ParamMode.values()[c.toString().toInt()]
            }
        }
        if (debug)
            println("Processing index $pointer -> $opCode: ${getParamInfo()}")
        if (opCode!!.isEnd())
            return this
        opCode!!.execute(this)
        if (lastPointer == pointer)
            throw RuntimeException("Infinite loop detected!")
        lastPointer = pointer
        return this
    }

    /**
     * Gets the param at the local instruction [index]
     */
    fun get(index: Int) = paramModes[index].get(code, code[pointer + index + 1])

    /**
     * Sets the [value] to the position defined by the local instruction [index]
     */
    private fun set(index: Int, value: Int) {
        code[code[pointer + index + 1]] = value
    }

    private fun getInput(): Int {
        val i = input[nextInput++]
        println("Got input: $i")
        return i
    }

    private fun getParamInfo(): String = Array(opCode!!.numParams) {
        val value = code[pointer + it + 1]
        val mode = paramModes[it]
        return@Array "($value${if (mode == ParamMode.POSITION) "->${code[value]}" else ""})"
    }.joinToString()

    @Suppress("unused")
    private enum class OpCode(val code: Int, val numParams: Int) {
        ADD(1, 3) {
            override fun execute(computer: IntcodeComputer) = computer.run {
                set(2, get(0) + get(1))
                super.execute(this)
            }
        },
        MULT(2, 3) {
            override fun execute(computer: IntcodeComputer) = computer.run {
                set(2, get(0) * get(1))
                super.execute(this)
            }
        },
        IN(3, 1) {
            override fun execute(computer: IntcodeComputer) = computer.run {
                set(0, getInput())
                super.execute(this)
            }
        },
        OUT(4, 1) {
            override fun execute(computer: IntcodeComputer) = computer.run {
                lastOutput = get(0).toString()
                if (printOut)
                    println("Output ($pointer): $lastOutput")
                super.execute(this)
            }
        },
        JUMP_TRUE(5, 2) {
            override fun execute(computer: IntcodeComputer) = computer.run {
                if (get(0) != 0)
                    pointer = get(1)
                else
                    super.execute(this)
            }
        },
        JUMP_FALSE(6, 2) {
            override fun execute(computer: IntcodeComputer) = computer.run {
                if (get(0) == 0)
                    pointer = get(1)
                else
                    super.execute(this)
            }
        },
        LESS_THAN(7, 3) {
            override fun execute(computer: IntcodeComputer) = computer.run {
                set(2, if (get(0) < get(1)) 1 else 0)
                super.execute(this)
            }
        },
        EQUALS(8, 3) {
            override fun execute(computer: IntcodeComputer) = computer.run {
                set(2, if (get(0) == get(1)) 1 else 0)
                super.execute(this)
            }
        },
        END(99, 0);

        fun isEnd() = this == END

        open fun execute(computer: IntcodeComputer) {
            computer.pointer += numParams + 1
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

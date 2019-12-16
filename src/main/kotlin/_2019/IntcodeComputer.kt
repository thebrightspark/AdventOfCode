package _2019

class IntcodeComputer {
    var debug = false
    var printOut = true
    var lastOutput: String = ""
        private set

    lateinit var code: MutableList<Long>
        private set
    var pointer = 0
        private set
    private var lastPointer = 0
    lateinit var input: LongArray
        private set
    var nextInput = 0
        private set
    private var opCode: OpCode? = null
    private val paramModes = arrayOf(ParamMode.POSITION, ParamMode.POSITION, ParamMode.POSITION)
    private var relativeBase: Long = 0

    companion object {
        fun parseCode(code: String) = code.split(',').map { it.toLong() }.toMutableList()
    }

    constructor() {
        code = mutableListOf()
        input = longArrayOf()
    }

    constructor(code: String, vararg input: Long = longArrayOf(0)): this(parseCode(code), *input)

    constructor(code: MutableList<Long>, vararg input: Long = longArrayOf(0)) {
        init(code, *input)
    }

    fun isFinished() = opCode != null && opCode!!.isEnd()

    fun init(code: MutableList<Long>, vararg input: Long = longArrayOf(0)): IntcodeComputer = apply {
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
            println("Processing index $pointer -> $instruction/$opCode: ${getParamInfo()}")
        if (opCode!!.isEnd())
            return this
        opCode!!.execute(this)
        if (lastPointer == pointer)
            throw RuntimeException("Infinite loop detected!")
        lastPointer = pointer
        return this
    }

    fun getDirect(index: Int) = code.getOrElse(index) { 0 }

    /**
     * Gets the param at the local instruction [index]
     */
    fun get(index: Int) = paramModes[index].getValue(this, getDirect(pointer + index + 1))

    /**
     * Sets the [value] to the position defined by the local instruction [index]
     */
    private fun set(index: Int, value: Long) {
        val mode = paramModes[index]
        val indexToSetAt = mode.getIndex(this, pointer + index + 1)
        if (code.size <= indexToSetAt)
            repeat(indexToSetAt - code.size + 1) { code.add(0) }
        code[indexToSetAt] = value
    }

    private fun getInput(): Long {
        val i = input[nextInput++]
        println("Got input: $i")
        return i
    }

    private fun getParamInfo(): String = Array(opCode!!.numParams) {
        val value = getDirect(pointer + it + 1)
        return@Array when (paramModes[it]) {
            ParamMode.POSITION -> "($value-P>${getDirect(value.toInt())})"
            ParamMode.IMMEDIATE -> "($value)"
            ParamMode.RELATIVE -> "($value-R>${getDirect((relativeBase + value).toInt())})"
        }
    }.joinToString()

    @Suppress("unused")
    private enum class OpCode(val code: Int, val numParams: Int) {
        ADD(1, 3) {
            override fun execute(computer: IntcodeComputer) = computer.run {
                val v0 = get(0)
                val v1 = get(1)
                val r = v0 + v1
                set(2, r)
                if (debug)
                    println("ADD: $v0 + $v1 = $r")
                super.execute(this)
            }
        },
        MULT(2, 3) {
            override fun execute(computer: IntcodeComputer) = computer.run {
                val v0 = get(0)
                val v1 = get(1)
                val r = v0 * v1
                set(2, r)
                if (debug)
                    println("MULT: $v0 * $v1 = $r")
                super.execute(this)
            }
        },
        IN(3, 1) {
            override fun execute(computer: IntcodeComputer) = computer.run {
                set(0, getInput())
                if (debug)
                    println("IN: ${get(0)}")
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
        JUMP_IF_NOT_0(5, 2) {
            override fun execute(computer: IntcodeComputer) = computer.run {
                if (get(0) != 0.toLong()) {
                    pointer = get(1).toInt()
                    if (debug)
                        println("JUMP_IF_NOT_0: $pointer")
                }
                else
                    super.execute(this)
            }
        },
        JUMP_IF_0(6, 2) {
            override fun execute(computer: IntcodeComputer) = computer.run {
                if (get(0) == 0.toLong()) {
                    pointer = get(1).toInt()
                    if (debug)
                        println("JUMP_IF_0: $pointer")
                } else
                    super.execute(this)
            }
        },
        LESS_THAN(7, 3) {
            override fun execute(computer: IntcodeComputer) = computer.run {
                val v0 = get(0)
                val v1 = get(1)
                val r: Long = if (get(0) < get(1)) 1 else 0
                set(2, r)
                if (debug)
                    println("LESS_THAN: $v0 < $v1 = $r")
                super.execute(this)
            }
        },
        EQUALS(8, 3) {
            override fun execute(computer: IntcodeComputer) = computer.run {
                val v0 = get(0)
                val v1 = get(1)
                val r: Long = if (get(0) == get(1)) 1 else 0
                set(2, r)
                if (debug)
                    println("EQUALS: $v0 == $v1 = $r")
                super.execute(this)
            }
        },
        RELATIVE_BASE_OFFSET(9, 1) {
            override fun execute(computer: IntcodeComputer) = computer.run {
                val v0 = get(0)
                val before = relativeBase
                relativeBase += v0
                if (debug)
                    println("RELATIVE_BASE_OFFSET: $before + $v0 = $relativeBase")
                super.execute(computer)
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
            override fun getValue(computer: IntcodeComputer, value: Long): Long = computer.getDirect(value.toInt())
            override fun getIndex(computer: IntcodeComputer, index: Int): Int = computer.getDirect(index).toInt()
        },
        IMMEDIATE {
            override fun getValue(computer: IntcodeComputer, value: Long): Long = value
            override fun getIndex(computer: IntcodeComputer, index: Int): Int = computer.run {
                throw RuntimeException("Can't use IMMEDIATE mode for set operation at $pointer:$opCode -> ${getParamInfo()}")
            }
        },
        RELATIVE {
            override fun getValue(computer: IntcodeComputer, value: Long): Long =
                computer.run { getDirect((relativeBase + value).toInt()) }
            override fun getIndex(computer: IntcodeComputer, index: Int): Int =
                (computer.relativeBase + computer.getDirect(index)).toInt()
        };

        abstract fun getValue(computer: IntcodeComputer, value: Long): Long

        abstract fun getIndex(computer: IntcodeComputer, index: Int): Int
    }
}

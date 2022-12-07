package _2022

import PATTERN_LINE_SEPARATOR
import REGEX_LINE_SEPARATOR
import aoc
import java.util.regex.Pattern

private val EMPTY_LINE_REGEX = Pattern.compile("^${PATTERN_LINE_SEPARATOR}", Pattern.MULTILINE)
private val STACK_REGEX = Pattern.compile("\\[(\\w)]")
private val INSTRUCTION_REGEX = Pattern.compile("move (?<amount>\\d+) from (?<from>\\d+) to (?<to>\\d+)")

fun main() {
    aoc(2022, 5) {
        aocRun { input ->
            process(input, Instruction::moveEach)
        }
        aocRun { input ->
            process(input, Instruction::moveAll)
        }
    }
}

private fun process(input: String, moveFunction: Instruction.(List<ArrayDeque<Char>>) -> Unit): String {
    val (stacksString, instructionsString) = input.split(EMPTY_LINE_REGEX).map { it.trimEnd() }
    val stacks = parseStacks(stacksString)
    val instructions = parseInstructions(instructionsString)

    instructions.forEach { moveFunction(it, stacks) }

    return stacks.joinToString(separator = "") { it.lastOrNull()?.toString() ?: "" }
}

private fun parseStacks(stacksString: String): List<ArrayDeque<Char>> {
    val stacks = buildList<ArrayDeque<Char>> {
        repeat(stacksString.takeLast(1).toInt()) { add(ArrayDeque()) }
    }
    stacksString.split(REGEX_LINE_SEPARATOR).reversed().drop(1).forEach { line ->
        STACK_REGEX.matcher(line).results().filter { it.group(1) != null }.forEach { match ->
            val char = match.group(1)[0]
            val stackIndex = (match.start(1) - 1) / 4
            stacks[stackIndex].add(char)
        }
    }
    return stacks
}

private fun parseInstructions(instructionsString: String): List<Instruction> = buildList {
    INSTRUCTION_REGEX.matcher(instructionsString).run {
        while (find()) {
            add(Instruction(group("amount").toInt(), group("from").toInt() - 1, group("to").toInt() - 1))
        }
    }
}

private data class Instruction(private val amount: Int, private val stackFrom: Int, private val stackTo: Int) {
    fun moveEach(stacks: List<ArrayDeque<Char>>) {
        val fromStack = stacks[stackFrom]
        val toStack = stacks[stackTo]
        repeat(amount) { toStack.add(fromStack.removeLast()) }
    }

    fun moveAll(stacks: List<ArrayDeque<Char>>) {
        val fromStack = stacks[stackFrom]
        val toStack = stacks[stackTo]
        val toMove = ArrayDeque<Char>()
        repeat(amount) { toMove.add(fromStack.removeLast()) }
        repeat(amount) { toStack.add(toMove.removeLast()) }
    }
}

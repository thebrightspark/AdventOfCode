#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end
#parse("File Header.java")

import aoc

fun main() {
    aoc(${YEAR}, ${DAY}) {
        aocRun(testInput) { input ->
        
        }
//        aocRun(testInput) { input ->
//        }
    }
}

private val testInput = """

""".trimIndent()

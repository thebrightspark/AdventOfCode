package _2018

import aocRun
import java.util.regex.Pattern
import kotlin.math.min

private val pattern = Pattern.compile("Step (?<step1>.) must be finished before step (?<step2>.) can begin")

fun main() {
    aocRun(puzzleInput) { input ->
        val steps = processSteps(input)
        //println("Steps:\n$steps")

        val allSteps = allSteps(steps)
        //println("All steps:\n$allSteps")

        val groupedSteps = steps.groupByTo(mutableMapOf(), { it.second }, { it.first })

        val stepOrder = StringBuilder()
        val stepsWithoutRequirements = allSteps.filter { !groupedSteps.keys.contains(it) }.sorted().toMutableList()
        do {
            //println("\nSteps grouped:\n$groupedSteps")
            println("Steps without requirements:\n$stepsWithoutRequirements")
            val next = stepsWithoutRequirements[0]
            println("Adding next step: $next")
            stepOrder.append(next)
            groupedSteps.remove(next)
            stepsWithoutRequirements.remove(next)

            groupedSteps.forEach { (step, req) ->
                if (req.remove(next))
                    println("Removed requirement $next from step $step")
                if (req.isEmpty() && !stepsWithoutRequirements.contains(step))
                    stepsWithoutRequirements += step
            }

            stepsWithoutRequirements.sort()
        } while (stepsWithoutRequirements.isNotEmpty())

        return@aocRun stepOrder.toString()
    }

    aocRun(puzzleInput) { input ->
        val steps = processSteps(input)
        println("Steps:\n$steps")
        val allSteps = allSteps(steps)
        val durations = allSteps.associateWith { it.toInt() - 4 }
        println(durations)

        val groupedSteps = steps.groupByTo(mutableMapOf(), { it.second }, { it.first })
        val stepOrder = StringBuilder()
        val stepsProcessing = mutableMapOf<Char, Int>()
        val stepsWithoutRequirements = allSteps.filter { !groupedSteps.keys.contains(it) }.sorted().toMutableList()
        val numWorkers = 5
        var curTime = 0

        do {
            println("\nTime: $curTime")
            println("Processing:\n$stepsProcessing")

            // Update steps being processed
            val finished = mutableListOf<Char>()
            val processingIter = stepsProcessing.iterator()
            while (processingIter.hasNext()) {
                val entry = processingIter.next()
                entry.setValue(entry.value - 1)
                if (entry.value <= 0) {
                    val c = entry.key
                    println("Step $c finished")
                    finished += c
                    processingIter.remove()
                    groupedSteps.remove(c)
                    groupedSteps.forEach { (step, req) ->
                        if (req.remove(c)) {
                            println("Removed requirement $c from step $step")
                            if (req.isEmpty() && !stepsWithoutRequirements.contains(step))
                                stepsWithoutRequirements += step
                        }
                    }
                }
            }

            // Add finished steps to stepOrder
            if (finished.isNotEmpty()) {
                finished.sort()
                finished.forEach { stepOrder.append(it) }
            }

            // Try give workers more steps to process
            stepsWithoutRequirements.sort()
            val numWorking = stepsProcessing.size
            if (numWorking < numWorkers) {
                repeat(min(numWorkers - numWorking, stepsWithoutRequirements.size)) {
                    println("Steps without requirements: $stepsWithoutRequirements")
                    val next = stepsWithoutRequirements[0]
                    println("Adding next step: $next")
                    stepsProcessing[next] = durations.getValue(next)
                    stepsWithoutRequirements.remove(next)
                }
            }

            curTime++
        } while (stepsWithoutRequirements.isNotEmpty() || stepsProcessing.isNotEmpty())

        println("\nOrder: $stepOrder")
        return@aocRun curTime - 1
    }
}

private fun processSteps(input: String): List<Pair<Char, Char>> = input.split("\n").map {
    val matcher = pattern.matcher(it)
    if (!matcher.find())
        throw RuntimeException("Not a valid input! -> '$it'")
    return@map Pair(matcher.group("step1")[0], matcher.group("step2")[0])
}

private fun allSteps(input: List<Pair<Char, Char>>): MutableList<Char> {
    val all = mutableListOf<Char>()
    input.forEach {
        if (!all.contains(it.first))
            all += it.first
        if (!all.contains(it.second))
            all += it.second
    }
    all.sort()
    return all
}

private val testInput = """
Step C must be finished before step A can begin.
Step C must be finished before step F can begin.
Step A must be finished before step B can begin.
Step A must be finished before step D can begin.
Step B must be finished before step E can begin.
Step D must be finished before step E can begin.
Step F must be finished before step E can begin.
""".trimIndent()

private val puzzleInput = """
Step I must be finished before step G can begin.
Step J must be finished before step A can begin.
Step L must be finished before step D can begin.
Step V must be finished before step S can begin.
Step U must be finished before step T can begin.
Step F must be finished before step Z can begin.
Step D must be finished before step A can begin.
Step E must be finished before step Z can begin.
Step C must be finished before step Q can begin.
Step H must be finished before step X can begin.
Step A must be finished before step Z can begin.
Step Z must be finished before step M can begin.
Step P must be finished before step Y can begin.
Step N must be finished before step K can begin.
Step R must be finished before step W can begin.
Step K must be finished before step O can begin.
Step W must be finished before step S can begin.
Step G must be finished before step Q can begin.
Step Q must be finished before step B can begin.
Step S must be finished before step T can begin.
Step B must be finished before step M can begin.
Step T must be finished before step Y can begin.
Step M must be finished before step O can begin.
Step X must be finished before step O can begin.
Step O must be finished before step Y can begin.
Step C must be finished before step O can begin.
Step B must be finished before step O can begin.
Step T must be finished before step O can begin.
Step S must be finished before step X can begin.
Step E must be finished before step K can begin.
Step Q must be finished before step M can begin.
Step E must be finished before step P can begin.
Step Q must be finished before step S can begin.
Step E must be finished before step O can begin.
Step D must be finished before step P can begin.
Step X must be finished before step Y can begin.
Step I must be finished before step U can begin.
Step B must be finished before step X can begin.
Step F must be finished before step T can begin.
Step B must be finished before step T can begin.
Step V must be finished before step R can begin.
Step I must be finished before step Q can begin.
Step I must be finished before step A can begin.
Step M must be finished before step X can begin.
Step Z must be finished before step S can begin.
Step C must be finished before step S can begin.
Step T must be finished before step M can begin.
Step K must be finished before step X can begin.
Step Z must be finished before step P can begin.
Step V must be finished before step H can begin.
Step Z must be finished before step B can begin.
Step M must be finished before step Y can begin.
Step C must be finished before step K can begin.
Step W must be finished before step Y can begin.
Step J must be finished before step Z can begin.
Step Q must be finished before step O can begin.
Step T must be finished before step X can begin.
Step P must be finished before step Q can begin.
Step P must be finished before step K can begin.
Step D must be finished before step M can begin.
Step P must be finished before step N can begin.
Step S must be finished before step B can begin.
Step H must be finished before step Y can begin.
Step R must be finished before step K can begin.
Step G must be finished before step S can begin.
Step P must be finished before step S can begin.
Step C must be finished before step Z can begin.
Step Q must be finished before step Y can begin.
Step F must be finished before step R can begin.
Step N must be finished before step B can begin.
Step G must be finished before step M can begin.
Step E must be finished before step X can begin.
Step D must be finished before step E can begin.
Step D must be finished before step C can begin.
Step U must be finished before step O can begin.
Step H must be finished before step Z can begin.
Step L must be finished before step C can begin.
Step L must be finished before step F can begin.
Step V must be finished before step D can begin.
Step F must be finished before step X can begin.
Step V must be finished before step W can begin.
Step S must be finished before step Y can begin.
Step K must be finished before step T can begin.
Step D must be finished before step Z can begin.
Step C must be finished before step W can begin.
Step V must be finished before step M can begin.
Step F must be finished before step H can begin.
Step A must be finished before step M can begin.
Step G must be finished before step Y can begin.
Step H must be finished before step M can begin.
Step N must be finished before step W can begin.
Step J must be finished before step K can begin.
Step C must be finished before step B can begin.
Step Z must be finished before step Y can begin.
Step L must be finished before step E can begin.
Step G must be finished before step B can begin.
Step Q must be finished before step T can begin.
Step D must be finished before step W can begin.
Step H must be finished before step G can begin.
Step L must be finished before step O can begin.
Step N must be finished before step O can begin.
""".trimIndent()

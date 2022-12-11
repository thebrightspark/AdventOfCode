package _2022

import REGEX_LINE_SEPARATOR
import REGEX_WHITESPACE
import aoc
import format

fun main() {
	aoc(2022, 7) {
		aocRun { input ->
			process(input).values.filter { it.size <= 100000 }.sumOf { it.size }
		}
        aocRun { input ->
			val dirs = process(input)
			val totalSize = dirs["/"]!!.size
			val spaceNeeded = 30000000 - (70000000 - totalSize)
			println("Total: ${totalSize.format()}, Need: ${spaceNeeded.format()}")

			return@aocRun dirs.values.sortedBy { it.size }.first { it.size >= spaceNeeded }.size
        }
	}
}

private fun process(input: String): Map<String, Dir> {
	val path = mutableListOf("/")
	fun pathToString() = path[0] + path.asSequence().drop(1).joinToString(separator = "/")

	val dirs = mutableMapOf<String, Dir>()
	dirs["/"] = Dir("/", null)
	fun getDir(): Dir {
		val pathString = pathToString()
		return dirs.getOrPut(pathString) {
			val parent = pathString.substringBeforeLast('/').let { if (it.isEmpty()) dirs["/"] else dirs[it] }
			Dir(pathString, parent)
		}
	}

	input.splitToSequence(REGEX_LINE_SEPARATOR).map { it.split(REGEX_WHITESPACE) }.forEach { line ->
		when (line[0]) {
			"$" -> {
				when (line[1]) {
					"cd" -> when (line[2]) {
						"/" -> path.apply {
							clear()
							add("/")
						}
						".." -> path.removeLast()
						else -> path.add(line[2])
					}
					"ls" -> Unit
					else -> error("Unknown command: $line")
				}
			}
			"dir" -> getDir().subDirs += line[1]
			else -> {
				val fileSize = line[0].toLong()
				var dir = getDir()
				dir.files += (line[1] to fileSize)
				dir.size += fileSize
				while (dir.parentDir != null) {
					dir = dir.parentDir!!
					dir.size += fileSize
				}
			}
		}
	}

//	dirs.values.sortedBy { it.path }.forEach { println("${it.path} -> ${it.size}") }

	return dirs
}

private data class Dir(
	val path: String,
	val parentDir: Dir?,
	var size: Long = 0,
	val subDirs: MutableList<String> = mutableListOf(),
	val files: MutableList<Pair<String, Long>> = mutableListOf()
)

private val testInput = """
$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k
""".trimIndent()

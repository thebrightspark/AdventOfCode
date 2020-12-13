package _2020

import aocRun
import splitInputToInt
import kotlin.math.pow

fun main() {
	aocRun(testInput1) { input ->
		val adapters = createSortedAdapterList(input)
		val differenceMap = (0 until adapters.size - 1)
			.map { adapters[it + 1] - adapters[it] }
			.groupBy { it }
			.mapValues { it.value.size }
		println(differenceMap)
		return@aocRun differenceMap[1]!! * differenceMap[3]!!
	}
	aocRun(testInput1) { input ->
		val adapters = createSortedAdapterList(input)
		val differences = (0 until adapters.size - 1).map { adapters[it + 1] - adapters[it] }
		println(differences)
		var count = 1
		var i = 0
		while (i < differences.size) {
			val curI = i
			i++
			val diff = differences[curI]
			// We're only paying attention to differences of 1
			if (diff != 1)
				continue
			// Count the size of this group of 1s
			var last = curI + 1
			while (differences[last] == 1)
				last++
			var range = last - curI
			// If group is only a single 1, then ignore
			if (range <= 1)
				continue
			if (range == 2) {
				// If group of 2, then only 2 permutations available
				println("$curI-$last = 2")
				count *= 2
			} else {
				// Remove 1 from the range, as we need to always keep the last adapter before the next 3 jolt gap
				range -= 1
				// Calculate number of permutations for range
				val permutations = 2F.pow(range).toInt() - 2F.pow(range - 2).toInt()
				println("$curI-$last = 2^$range - 2^${range - 2} = $permutations")
				count *= permutations
			}
			// Skip forward to
			i = last + 1
		}
		return@aocRun count
	}
}

private fun createSortedAdapterList(input: String): List<Int> = input.splitInputToInt().toMutableList().apply {
	add(0)
	add(maxOrNull()!! + 3)
	sort()
	println(this)
}

private val testInput1 = """
16
10
15
5
1
11
7
19
6
12
4
""".trimIndent()

private val testInput2 = """
28
33
18
42
31
14
46
20
48
47
24
23
49
45
19
38
39
11
1
32
25
35
8
17
7
9
4
2
34
10
3
""".trimIndent()

private val puzzleInput = """
99
128
154
160
61
107
75
38
15
11
129
94
157
84
121
14
119
48
30
10
55
108
74
104
91
45
134
109
164
66
146
44
116
89
79
32
149
1
136
58
96
7
60
23
31
3
65
110
90
37
43
115
122
52
113
123
161
50
95
150
120
101
126
151
114
127
73
82
162
140
51
144
36
4
163
85
42
59
67
64
86
49
2
145
135
22
24
33
137
16
27
70
133
130
20
21
83
143
100
41
76
17
""".trimIndent()

package _2019

import aocRun
import splitInputToInt
import kotlin.math.floor

fun main() {
    aocRun(puzzleInput) { input ->
        return@aocRun input.splitInputToInt().stream().mapToInt { calcFuel(it) }.sum()
    }

    aocRun(puzzleInput) { input ->
        return@aocRun input.splitInputToInt().stream().mapToInt {
            val fuel = calcFuel(it)
            return@mapToInt fuel + calcExtraFuel(fuel)
        }.sum()
    }
}

private fun calcFuel(input: Int) = floor(input.toDouble() / 3).toInt() - 2

private fun calcExtraFuel(fuel: Int): Int {
    var lastFuel = calcFuel(fuel)
    var extra = lastFuel
    while (lastFuel > 0) {
        lastFuel = calcFuel(lastFuel)
        if (lastFuel > 0)
            extra += lastFuel
    }
    return extra
}

private val puzzleInput = """
92903
97793
95910
104540
122569
60424
145155
90081
81893
140366
77358
122977
114868
135274
80770
104328
87475
135948
128585
71353
93571
69870
137262
142606
95397
62517
87269
73336
118502
82894
125097
102311
134164
119828
116181
99303
88937
63418
145060
91014
136031
106000
105084
139280
99775
94626
99081
119824
58232
54759
93633
142621
63718
106439
62425
119965
73033
130019
93223
118848
122769
130704
63418
87205
137230
147960
51051
65279
82183
57705
102519
144031
94413
98485
130646
111400
100503
95963
143785
97857
146611
67684
79662
147350
60427
118683
128729
65014
55844
120846
117969
134494
127003
139614
95021
124970
100680
91622
106898
79702
""".trimIndent()

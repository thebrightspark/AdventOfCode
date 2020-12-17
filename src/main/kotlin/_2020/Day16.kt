package _2020

import aocRun
import splitToInts

private val RULE_REGEX = Regex("^([\\w\\s]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)$")

fun main() {
	aocRun(puzzleInput) { input ->
		val parts = input.split("\n\n")
		val rules = parseRules(parts[0])
		val tickets = parseTickets(parts[2])
		var invalidTotal = 0
		tickets.forEach { ticket ->
			ticket.forEach { num ->
				if (!rules.any { rule -> rule.second.any { it.contains(num) } }) {
					invalidTotal += num
				}
			}
		}
		return@aocRun invalidTotal
	}
	aocRun(testInput) { input ->
		val parts = input.split("\n\n")
		val rules = parseRules(parts[0])
		val myTicket = parseMyTicket(parts[1])
		val tickets = parseTickets(parts[2])
		var invalidTotal = 0
		val validatedTickets = tickets.filter { ticket ->
			var valid = true
			ticket.forEach { num ->
				if (!rules.any { rule -> rule.second.any { it.contains(num) } }) {
					valid = false
					invalidTotal += num
				}
			}
			return@filter valid
		}

		// TODO: Need to use a process of elimination to work out field mappings
		val fieldSize = rules.size
		val fieldMappings = mutableMapOf<String, Int>()
		for (ticket in validatedTickets) {
			println("\nTicket $ticket")
			for ((i, num) in ticket.withIndex()) {
				println("Num $i: $num")
				if (!fieldMappings.containsValue(i)) {
					val matchingRules = rules.filter { rule -> rule.second.any { it.contains(num) } }
					println("Matching rules: ${matchingRules.size}")
					if (matchingRules.size == 1) {
						val matchingRule = matchingRules.single()
						println("Match: $matchingRule")
						fieldMappings[matchingRule.first] = i
					}
				}
			}

			if (fieldMappings.size == fieldSize)
				break
		}
		return@aocRun fieldMappings
	}
}

private fun parseRules(rules: String): List<Pair<String, Array<IntRange>>> = rules.split("\n").map {
	RULE_REGEX.matchEntire(it)!!.run {
		groupValues[1] to arrayOf(groupValues[2].toInt()..groupValues[3].toInt(), groupValues[4].toInt()..groupValues[5].toInt())
	}
}

private fun parseMyTicket(ticket: String): List<Int> = ticket.split("\n")[1].splitToInts(",")

private fun parseTickets(tickets: String): List<List<Int>> =
	tickets.split("\n").run { subList(1, size) }.map { it.splitToInts(",") }

private val testInput = """
class: 0-1 or 4-19
row: 0-5 or 8-19
seat: 0-13 or 16-19

your ticket:
11,12,13

nearby tickets:
3,9,18
15,1,5
5,14,9
""".trimIndent()

private val puzzleInput = """
departure location: 27-672 or 680-954
departure station: 25-430 or 439-966
departure platform: 31-293 or 299-953
departure track: 29-749 or 775-955
departure date: 43-93 or 107-953
departure time: 50-916 or 941-963
arrival location: 31-682 or 702-954
arrival station: 38-663 or 672-960
arrival platform: 31-629 or 635-969
arrival track: 42-365 or 371-967
class: 30-147 or 167-966
duration: 39-525 or 545-967
price: 30-803 or 822-950
route: 39-235 or 257-957
row: 33-206 or 231-971
seat: 29-784 or 798-951
train: 38-302 or 316-957
type: 50-635 or 642-966
wagon: 25-502 or 510-973
zone: 42-843 or 861-965

your ticket:
73,53,173,107,113,89,59,167,137,139,71,179,131,181,67,83,109,127,61,79

nearby tickets:
401,502,276,495,9,177,627,330,621,478,589,620,657,138,563,260,167,837,351,943
492,714,451,146,914,565,290,882,280,405,626,666,597,349,461,340,342,348,482,885
904,625,470,94,269,863,710,747,284,429,371,866,401,351,259,377,69,189,801,723
64,238,70,81,478,280,426,609,460,603,137,647,589,76,140,588,472,891,66,376
458,425,56,320,347,742,887,648,3,389,257,143,646,362,268,195,500,586,895,730
802,50,181,740,908,732,416,423,895,128,445,905,117,462,881,206,833,86,725,369
475,107,203,404,627,493,478,870,346,778,383,604,737,668,721,331,454,780,912,784
464,906,405,206,882,195,2,196,888,344,497,552,841,624,263,417,404,839,561,78
340,167,568,390,78,397,123,366,828,652,495,445,570,275,144,948,76,893,838,328
72,173,645,443,610,941,131,335,400,500,379,804,887,824,479,831,582,916,360,884
469,352,705,914,714,367,520,603,681,137,184,329,702,76,779,524,72,318,167,650
351,512,110,422,904,832,345,101,55,388,196,547,401,354,57,524,875,386,570,269
170,489,130,251,188,262,784,517,941,883,875,332,484,82,556,653,656,495,282,611
408,545,180,349,425,72,139,707,415,722,723,140,350,426,301,831,435,521,897,358
574,292,491,708,620,895,332,836,599,590,891,589,324,455,734,333,652,61,626,312
360,595,843,933,289,475,523,441,730,327,473,876,708,599,550,521,554,594,83,909
467,737,707,717,177,572,191,404,681,704,748,580,418,550,644,58,987,520,599,784
471,325,736,520,458,720,200,867,469,65,655,81,77,148,654,83,888,711,439,717
609,283,945,141,465,352,453,833,561,192,582,452,447,400,724,724,897,483,504,588
404,382,124,337,583,356,897,614,728,346,479,296,337,57,90,626,490,202,401,402
75,658,402,884,350,615,548,836,171,384,829,910,332,636,660,885,826,875,426,548
354,890,779,407,823,883,249,360,142,486,316,194,911,722,865,550,867,907,406,883
132,373,490,886,494,273,518,803,285,609,872,60,54,654,144,324,519,95,802,343
379,263,524,643,359,726,865,140,712,993,261,178,718,661,338,454,144,880,511,600
615,703,836,181,202,356,888,132,409,330,505,234,74,656,373,491,350,580,330,316
356,837,297,137,451,417,736,586,202,470,77,181,742,139,780,144,349,276,202,347
442,466,547,872,463,327,625,616,979,616,119,650,293,342,577,870,182,145,175,338
873,201,360,639,116,839,354,828,905,584,912,781,660,647,893,266,334,69,711,549
231,281,153,616,482,380,874,622,477,490,646,708,902,316,413,93,83,269,574,174
204,52,875,619,870,385,450,573,173,739,54,150,568,357,741,71,557,907,579,471
268,615,726,825,360,887,377,523,993,495,268,501,175,204,489,619,643,871,863,52
718,609,204,353,178,349,592,407,734,721,420,894,459,300,742,712,60,366,729,189
899,657,635,126,590,581,658,418,945,485,570,901,471,644,175,131,196,157,186,83
325,762,316,476,92,783,894,374,635,713,473,324,353,749,912,456,401,480,658,915
865,109,504,724,133,663,261,417,342,907,71,180,191,587,269,578,93,257,62,397
884,833,331,589,830,911,902,653,469,501,624,311,453,613,946,779,403,719,749,554
827,165,592,727,572,714,452,948,83,682,178,627,85,442,719,193,741,475,358,322
391,331,352,725,809,389,173,139,523,713,546,80,635,870,187,801,390,197,803,708
714,474,276,434,336,520,171,407,338,262,798,657,189,270,327,324,524,287,862,944
889,580,829,634,439,517,550,87,202,485,486,738,681,866,292,136,802,90,291,180
650,379,359,364,887,192,275,386,430,742,629,903,364,920,235,336,345,703,647,475
151,583,835,92,578,823,425,126,199,502,731,193,680,732,800,493,893,885,521,378
707,324,59,108,448,202,107,565,525,912,371,89,262,487,247,825,124,780,320,620
377,656,80,310,864,115,778,906,611,55,142,512,408,949,381,561,489,132,470,712
867,870,800,175,825,401,262,155,280,782,904,399,393,302,586,599,59,375,274,799
897,603,262,590,580,281,645,656,549,800,232,902,425,473,449,423,174,611,998,175
199,414,891,445,114,314,833,412,910,522,723,262,573,419,776,914,724,612,502,330
934,478,891,193,75,130,55,739,738,875,122,81,838,611,115,871,833,448,646,568
508,394,595,271,360,375,587,329,568,625,706,629,602,864,332,392,136,570,620,448
413,381,427,264,288,373,8,583,834,56,451,361,340,195,462,183,448,672,905,351
442,601,304,139,134,449,557,65,884,145,117,610,781,517,423,945,574,458,373,263
111,188,743,495,611,390,183,141,875,587,837,282,293,595,185,475,492,376,92,255
186,642,70,523,140,302,193,732,63,429,149,461,168,742,280,233,234,418,204,500
357,92,567,440,572,565,233,465,293,810,894,870,405,592,131,704,729,645,338,780
604,187,339,173,886,944,716,192,622,658,279,949,603,885,883,568,0,748,424,362
728,873,904,405,482,656,590,270,587,52,619,300,987,487,871,942,421,800,200,910
315,577,351,831,63,80,62,417,119,167,268,131,328,492,471,363,409,184,578,800
356,405,565,624,137,147,341,617,748,174,466,62,181,269,277,824,674,279,395,411
716,801,190,615,174,554,588,885,181,715,672,256,420,745,657,776,169,352,193,722
497,107,680,901,731,137,115,195,353,110,861,401,836,836,663,509,415,515,418,895
276,471,565,606,704,116,468,351,593,385,284,180,110,145,362,167,671,646,741,647
440,364,476,72,72,192,127,293,283,593,502,544,620,548,644,718,447,914,335,643
96,822,317,364,874,571,270,288,398,124,67,107,652,453,783,915,127,501,385,138
287,423,398,295,548,553,878,349,582,319,583,462,605,906,598,190,398,732,882,286
197,723,281,488,325,194,128,885,798,717,3,232,93,127,890,494,205,72,409,197
136,674,393,277,329,189,398,711,60,589,454,389,873,199,278,380,888,590,280,338
779,391,186,825,390,733,122,54,726,87,946,658,976,628,91,291,899,125,839,346
403,353,392,123,583,730,416,556,458,577,133,425,709,234,834,944,582,455,611,161
945,946,912,138,429,443,118,311,465,942,451,838,525,720,886,561,654,498,740,454
501,747,396,191,997,441,206,496,555,885,455,418,614,72,404,722,479,880,569,498
440,398,206,623,801,124,459,825,282,985,458,91,266,681,863,717,409,744,182,835
710,867,259,618,574,882,628,61,825,251,450,275,717,412,607,576,452,606,426,406
896,276,397,343,385,383,894,383,133,703,748,577,180,624,710,718,506,589,737,603
706,724,181,841,452,832,475,861,23,167,316,258,782,465,350,445,185,203,682,62
634,339,347,874,393,266,50,146,643,382,879,454,189,682,301,518,423,838,829,600
652,748,834,559,70,390,162,545,322,775,577,644,206,429,176,472,495,183,525,658
325,499,239,135,280,516,316,781,776,204,547,647,400,51,726,822,325,514,522,71
375,290,900,293,902,643,271,783,383,605,232,780,325,348,608,815,905,909,562,402
862,428,119,82,360,191,292,281,192,496,462,113,57,761,555,400,51,610,54,456
460,800,263,170,498,617,14,519,365,479,511,660,144,423,413,454,557,863,300,257
351,887,478,147,336,627,110,559,721,260,325,141,468,132,276,366,277,468,61,290
451,317,194,726,948,380,416,841,834,414,478,75,628,547,14,573,377,905,783,328
405,894,575,300,512,409,645,302,392,347,352,20,487,622,324,682,483,663,465,864
122,804,320,393,477,672,835,547,711,471,126,882,125,72,88,495,117,130,50,580
442,779,406,601,912,406,572,435,134,129,439,50,588,110,569,546,779,177,113,745
838,428,583,730,281,196,782,832,334,643,865,631,565,202,364,398,299,825,404,583
945,681,484,347,548,430,369,489,174,485,864,874,112,201,501,357,328,781,142,735
287,702,320,157,592,879,578,597,490,206,77,900,830,77,560,909,710,862,899,349
448,335,560,908,185,742,134,398,566,51,278,390,521,916,285,236,123,784,262,360
76,318,938,557,79,171,914,481,231,261,829,896,657,706,491,331,824,362,822,861
433,803,577,326,267,841,138,131,338,199,191,842,464,193,167,457,888,62,524,460
332,941,363,481,179,361,118,915,737,131,559,690,621,199,69,359,731,714,906,801
825,779,681,367,90,877,143,273,184,317,77,473,650,564,361,910,885,568,575,722
618,109,174,826,552,73,511,373,557,75,91,391,656,259,407,741,300,826,159,179
283,458,901,466,609,976,473,415,79,67,566,387,635,882,203,649,799,736,603,116
415,447,681,710,550,727,289,284,54,287,945,385,416,646,280,581,766,498,598,293
447,407,890,482,803,405,300,588,571,203,112,231,874,266,832,338,340,438,406,710
624,85,467,487,443,404,779,76,455,353,1,628,288,557,356,601,749,129,136,340
173,548,413,709,185,349,470,635,328,279,741,833,412,632,125,488,374,284,133,578
613,525,725,898,399,660,644,201,114,607,455,350,186,833,123,289,632,426,647,64
358,56,5,778,587,710,479,184,408,286,743,270,711,468,397,112,622,181,385,80
889,340,168,496,76,579,990,56,395,446,607,280,512,359,604,282,384,647,486,584
481,456,735,418,585,723,926,129,627,553,495,778,140,51,624,628,826,453,655,885
778,602,446,726,396,629,632,200,465,517,382,476,285,259,708,123,88,404,425,620
233,895,802,878,585,117,82,508,408,445,177,87,91,585,399,567,407,385,108,274
475,892,584,905,748,359,182,280,484,293,620,734,729,503,601,275,799,418,373,378
266,552,908,591,720,814,170,593,714,357,116,550,177,441,189,134,720,705,494,169
112,945,714,391,377,81,467,389,279,299,361,916,195,101,111,110,449,517,86,719
242,449,456,272,783,338,396,353,715,183,571,643,882,417,385,861,78,141,177,741
300,196,359,119,604,836,490,646,622,662,501,655,778,190,578,733,343,128,491,503
496,596,711,621,720,320,333,823,183,562,468,291,421,315,338,739,292,745,54,81
488,370,830,185,458,386,628,272,143,513,373,654,802,359,477,316,629,362,655,422
944,538,628,514,710,110,714,721,122,654,411,491,626,602,387,206,57,397,653,404
709,288,22,706,783,278,388,329,174,445,452,144,194,79,598,300,895,523,386,202
381,294,863,58,833,80,707,643,329,875,834,827,479,838,128,384,467,454,204,834
284,575,596,774,502,593,704,442,136,58,741,71,513,378,391,489,194,302,289,831
395,69,127,705,52,743,376,629,609,442,334,193,142,869,137,320,629,246,202,186
410,364,261,489,182,139,350,702,340,132,140,489,299,480,81,280,821,871,944,733
551,454,592,440,727,552,617,829,413,738,183,928,480,717,116,346,562,283,661,598
943,911,119,589,593,568,462,514,287,486,278,577,646,382,653,79,705,561,808,394
800,847,269,839,299,259,468,358,77,428,712,382,272,120,721,624,136,625,546,462
68,711,275,415,132,605,405,137,394,577,558,553,572,501,778,272,298,489,144,680
259,601,62,593,615,187,739,889,52,77,622,871,413,555,975,128,948,740,391,829
732,662,446,829,245,454,567,439,496,378,135,84,57,422,628,352,282,834,263,269
476,741,105,386,470,87,512,829,378,574,588,126,374,573,865,388,943,603,467,276
913,488,336,326,293,122,947,658,745,649,623,321,736,113,393,365,920,286,263,902
295,595,645,943,607,600,478,173,425,422,478,344,498,490,377,374,343,281,864,564
391,312,629,68,178,486,713,546,551,650,609,498,681,266,235,167,643,651,301,323
624,446,600,626,180,345,588,648,642,723,910,916,442,182,947,358,778,506,487,826
134,723,775,275,416,576,231,327,238,285,396,378,872,579,402,299,591,511,266,721
554,91,724,615,193,866,430,495,655,64,872,485,620,596,11,133,410,721,456,646
88,824,438,841,144,121,394,525,81,514,383,496,202,482,86,469,489,487,726,203
676,943,862,578,610,887,460,703,716,707,881,731,338,351,61,895,648,831,570,799
578,715,191,189,379,910,406,941,182,801,369,607,291,353,886,741,420,653,331,469
809,883,898,581,125,825,130,658,495,393,400,355,588,405,458,604,781,424,426,841
59,137,289,736,783,459,191,561,391,908,410,501,467,583,360,377,155,110,829,109
422,115,474,885,571,447,324,926,390,591,823,583,205,138,778,510,876,723,269,839
511,578,197,947,878,350,302,133,83,597,645,182,65,337,798,832,725,772,115,861
622,863,748,200,832,329,729,70,651,565,51,289,381,322,329,188,399,309,414,519
405,496,355,511,428,318,664,134,864,316,520,829,660,112,598,564,660,290,549,381
742,885,285,649,338,413,826,758,178,890,133,342,472,653,201,946,279,64,727,205
190,841,429,456,400,575,58,553,510,384,416,596,916,941,418,115,235,175,246,617
331,300,206,393,442,352,579,510,383,519,450,4,376,704,65,555,442,730,802,626
723,652,418,896,193,508,82,392,185,448,494,608,120,172,300,863,887,199,266,477
334,922,655,477,175,720,132,454,598,647,205,777,346,320,646,388,569,596,745,642
779,576,493,613,517,361,375,783,984,421,176,231,627,364,823,468,381,901,450,585
85,658,745,209,142,734,415,281,731,405,189,720,877,559,579,188,492,941,356,88
733,400,387,583,331,192,813,593,659,471,171,545,317,649,834,399,611,876,643,126
717,634,289,134,283,174,823,445,612,201,520,266,130,361,129,267,337,424,661,570
380,841,472,905,881,85,295,825,317,351,122,291,355,195,776,143,360,321,749,175
947,430,888,146,479,186,515,747,622,374,873,658,611,990,78,346,128,269,947,352
898,876,628,916,142,250,357,908,327,745,167,897,119,182,780,872,321,393,608,867
406,777,656,77,367,288,575,525,463,338,333,729,196,568,92,581,358,362,459,888
563,66,412,571,442,62,293,296,344,73,147,56,316,569,488,733,84,59,145,112
741,802,514,546,81,730,434,868,827,463,341,706,184,622,203,863,117,522,143,356
732,729,129,728,775,619,371,653,279,568,252,408,567,192,837,872,645,908,565,284
563,492,176,660,872,562,675,623,714,196,727,393,484,405,269,393,721,904,279,602
366,802,487,949,562,825,69,202,877,442,288,866,593,443,114,868,343,131,517,379
329,659,744,59,599,445,576,410,6,907,288,524,839,91,55,185,523,179,287,703
82,104,135,292,425,662,881,659,458,580,453,199,722,747,394,680,552,281,880,595
374,667,424,461,269,473,355,129,867,354,63,629,469,332,829,602,900,869,706,480
743,375,370,881,442,274,886,408,418,444,878,646,268,482,133,134,661,561,397,396
864,656,82,531,625,448,181,714,481,494,899,913,475,275,489,742,359,474,385,474
579,604,591,343,266,576,20,107,357,905,833,115,397,378,781,829,464,943,562,345
704,115,902,331,988,269,91,628,459,474,563,902,893,620,610,869,422,711,418,355
428,208,877,613,744,739,91,197,177,108,874,872,493,54,407,338,521,887,284,288
259,334,652,261,502,405,411,563,88,891,421,67,739,623,832,388,987,607,339,121
781,456,376,384,625,109,636,744,446,76,361,124,324,196,589,736,197,703,647,480
479,572,387,869,579,90,336,584,602,419,600,572,180,394,234,313,127,289,112,716
359,234,264,720,735,446,742,111,74,728,905,340,83,73,725,412,401,606,865,436
141,580,365,194,654,739,736,65,870,301,944,136,715,484,609,342,579,810,139,390
82,285,364,421,393,831,315,411,611,913,460,126,388,479,413,886,82,888,895,730
57,830,835,472,457,411,430,826,419,383,362,856,61,737,831,831,112,206,232,179
265,882,90,677,617,548,89,76,133,571,704,472,54,107,554,497,323,713,452,187
662,124,452,727,553,588,20,723,655,884,429,713,862,843,552,626,417,870,395,549
329,126,807,876,738,441,231,292,580,388,892,729,141,322,416,365,573,379,619,407
875,461,170,559,548,874,175,516,146,337,661,511,83,621,189,497,398,654,947,809
186,902,183,376,800,490,911,424,64,486,356,390,623,679,450,907,829,738,280,361
839,185,394,196,575,831,716,824,146,51,383,561,283,916,778,913,836,316,366,400
422,653,551,608,629,893,547,460,894,480,705,367,190,609,68,324,947,172,82,59
656,169,864,565,80,496,358,493,841,483,904,439,199,448,645,942,248,523,231,282
450,880,91,176,51,893,770,832,68,115,866,282,662,784,55,875,376,522,416,732
623,705,284,602,178,881,467,832,318,548,391,407,486,363,598,613,103,552,521,803
81,431,462,201,649,189,911,621,582,183,523,290,576,749,399,735,914,823,621,725
828,347,460,672,289,603,719,325,728,873,517,889,575,612,159,726,405,799,748,79
257,125,381,593,891,416,444,126,898,199,877,775,67,862,174,574,303,327,469,911
682,410,705,623,803,111,862,447,417,271,70,678,475,548,383,861,907,447,561,658
19,556,408,589,713,261,126,335,74,894,595,611,169,419,280,592,872,586,561,329
599,264,647,350,85,780,550,360,197,552,494,874,351,816,302,320,468,744,282,729
195,564,173,604,451,71,87,746,89,151,446,144,126,391,863,352,361,121,483,335
725,574,448,439,426,268,128,243,879,823,651,877,481,551,267,601,949,801,358,456
904,121,525,710,282,191,508,331,586,451,65,387,718,824,107,132,491,274,500,373
734,459,837,417,826,660,522,108,175,748,733,947,446,826,867,166,830,266,586,613
784,989,708,574,733,629,602,117,863,518,388,524,361,560,205,607,257,582,713,81
545,565,59,302,621,616,722,945,584,516,267,882,987,601,283,656,604,563,113,777
899,349,742,800,299,449,381,490,583,343,571,195,324,245,178,341,189,730,722,578
182,257,862,659,729,87,511,409,130,836,811,398,648,259,397,877,455,717,487,462
168,383,179,429,587,406,555,392,177,522,341,436,331,477,142,869,194,347,147,642
613,174,609,327,284,949,604,647,457,484,742,870,137,4,616,440,590,129,909,550
499,556,21,292,334,569,379,723,414,746,287,916,420,573,382,682,871,280,727,494
358,878,717,185,318,60,54,61,264,658,59,717,192,210,615,627,69,453,451,837
109,50,775,548,901,604,129,556,493,189,734,638,552,85,878,916,878,881,282,863
375,469,743,865,481,383,893,675,329,571,502,576,365,451,616,648,330,177,912,908
753,464,867,445,444,468,616,618,894,916,563,355,602,910,832,493,191,363,865,442
112,363,913,141,71,740,442,327,274,617,377,635,353,301,605,107,596,932,775,146
591,191,322,484,616,338,380,812,127,137,257,657,884,301,600,423,112,581,415,877
501,639,324,648,344,499,377,58,901,577,359,493,187,112,69,477,146,720,292,609
835,513,398,171,622,659,359,325,654,419,432,869,61,405,398,478,654,659,943,600
82,681,575,594,116,484,890,383,76,655,705,446,454,261,562,396,729,741,343,919
884,889,268,833,360,870,722,579,52,389,402,582,414,370,783,623,379,635,721,889
491,466,577,475,841,428,800,843,365,116,307,90,605,720,877,658,682,866,882,234
349,877,754,346,409,93,176,736,387,377,168,782,205,409,460,867,896,430,826,749
647,281,574,718,86,928,340,843,708,55,365,470,704,55,725,343,378,822,520,347
780,826,470,873,557,709,110,557,476,191,83,129,281,297,911,570,128,645,823,376
339,916,477,68,278,233,945,492,620,74,933,736,617,182,881,337,836,393,204,901
660,891,563,403,152,888,170,615,333,362,591,672,861,69,324,599,714,410,173,882
354,864,359,642,613,643,447,709,731,170,502,909,592,800,52,144,914,554,8,944
59,445,392,509,624,374,586,61,274,396,556,117,128,601,601,123,653,264,270,611
468,470,350,520,483,739,735,743,550,578,911,984,351,896,523,58,335,708,91,460
271,372,185,593,650,331,291,598,663,414,591,508,359,779,325,143,520,942,470,740
582,138,194,373,734,192,139,188,392,520,477,167,648,433,174,878,74,913,515,840
627,186,325,233,372,152,911,425,781,395,841,561,554,413,739,418,454,398,316,291
712,728,712,53,746,897,513,453,142,865,2,167,717,133,656,560,354,650,203,661
905,606,60,183,653,874,325,944,494,576,728,565,331,907,111,708,274,410,122,102
301,124,711,749,394,709,799,656,50,167,574,76,113,87,735,627,408,592,15,495
387,712,356,783,498,982,363,644,335,781,395,514,183,458,884,894,194,493,842,74
204,600,428,458,351,512,113,119,258,653,472,226,422,608,430,87,595,456,879,356
281,206,895,408,50,610,98,882,443,898,608,737,842,405,580,481,279,746,178,731
546,10,262,270,333,910,466,337,567,258,729,178,743,379,86,525,460,92,652,799
901,487,336,821,77,330,889,601,302,421,121,289,879,680,719,496,83,409,613,480
89,124,616,668,613,414,708,842,323,458,602,826,472,373,599,278,268,120,615,864
724,506,350,371,870,417,588,473,342,896,59,576,515,680,945,573,397,716,182,494
61,888,277,259,88,523,57,652,170,175,607,757,91,276,732,427,361,593,781,169
452,517,739,180,646,471,269,290,610,335,579,984,443,429,704,597,475,525,581,293
363,672,456,887,898,586,572,142,861,604,457,94,131,77,175,343,884,464,274,396
581,63,232,130,589,80,775,499,910,603,618,571,880,280,455,491,370,118,283,611
""".trimIndent()

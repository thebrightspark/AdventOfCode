package _2018

import aocRun
import forEachComparison
import splitInput
import java.awt.Point
import java.awt.Rectangle
import java.util.regex.Pattern

private val pattern = Pattern.compile("#(?<i>\\d+) @ (?<x>\\d+),(?<y>\\d+): (?<w>\\d+)x(?<h>\\d+)")

fun main() {
    aocRun(puzzleInput) { input ->
        val rectangles = input.splitInput().map { stringToRectangle(it) }
        val intersections = mutableSetOf<Rectangle>()
        rectangles.forEachComparison { r1, r2 ->
            val intersection = r1.intersection(r2)
            if (!intersection.isEmpty)
                intersections += intersection
            return@forEachComparison false
        }
        val intersectedPoints = HashSet<Point>()
        intersections.forEach {
            (it.x until it.x + it.width).forEach { x ->
                (it.y until it.y + it.height).forEach { y ->
                    intersectedPoints += Point(x, y)
                }
            }
        }
        return@aocRun intersectedPoints.size
    }

    aocRun(puzzleInput) { input ->
        val rectangles = input.splitInput().map { stringToRectangle(it) }
        val cleanRectangles = HashSet(rectangles)
        rectangles.forEachIndexed outerLoop@{ index1, rect1 ->
            rectangles.forEachIndexed innerLoop@{ index2, rect2 ->
                if (index1 != index2 && rect1.intersects(rect2)) {
                    cleanRectangles -= rect1
                    return@outerLoop
                }
            }
        }
        return@aocRun if (cleanRectangles.size != 1)
            "Not 1 result! -> ${cleanRectangles.size}"
        else
            cleanRectangles.first()
    }
}

private fun stringToRectangle(input: String): Rect {
    val matcher = pattern.matcher(input)
    if (!matcher.find())
        throw RuntimeException("Doesn't match the pattern! -> '$input'")
    return Rect(
        matcher.group("i").toInt(),
        matcher.group("x").toInt(),
        matcher.group("y").toInt(),
        matcher.group("w").toInt(),
        matcher.group("h").toInt())
}

private class Rect(val id: Int, x: Int, y: Int, w: Int, h: Int): Rectangle(x, y, w, h) {
    override fun equals(other: Any?): Boolean = other is Rect && other.id == id

    override fun hashCode(): Int = id

    override fun toString(): String = "${this::class.simpleName}[id=$id, x=$x, y=$y, w=$width, h=$height]"
}

private val puzzleInput = """
#1 @ 306,433: 16x11
#2 @ 715,698: 18x29
#3 @ 955,34: 21x24
#4 @ 226,621: 16x23
#5 @ 528,35: 10x17
#6 @ 396,408: 13x24
#7 @ 144,524: 23x29
#8 @ 746,103: 24x15
#9 @ 818,839: 13x23
#10 @ 800,915: 20x14
#11 @ 801,660: 21x19
#12 @ 926,42: 20x10
#13 @ 124,229: 22x13
#14 @ 834,869: 15x26
#15 @ 152,128: 18x19
#16 @ 712,519: 14x26
#17 @ 588,602: 12x17
#18 @ 165,921: 22x27
#19 @ 648,842: 25x14
#20 @ 707,710: 15x20
#21 @ 641,116: 25x18
#22 @ 357,6: 21x12
#23 @ 670,927: 13x14
#24 @ 217,430: 6x6
#25 @ 573,283: 22x11
#26 @ 329,438: 13x16
#27 @ 562,894: 12x23
#28 @ 496,2: 26x29
#29 @ 406,346: 25x19
#30 @ 930,393: 16x16
#31 @ 530,830: 13x20
#32 @ 569,314: 11x18
#33 @ 225,621: 10x13
#34 @ 925,591: 29x15
#35 @ 543,331: 26x26
#36 @ 225,619: 12x22
#37 @ 168,437: 24x28
#38 @ 449,11: 15x15
#39 @ 665,949: 28x24
#40 @ 794,927: 5x4
#41 @ 70,333: 10x25
#42 @ 131,744: 18x16
#43 @ 668,276: 25x23
#44 @ 912,559: 25x14
#45 @ 72,416: 18x22
#46 @ 798,341: 13x13
#47 @ 231,104: 24x19
#48 @ 498,965: 24x13
#49 @ 684,424: 18x27
#50 @ 550,669: 11x13
#51 @ 169,964: 29x27
#52 @ 155,655: 22x12
#53 @ 256,981: 12x14
#54 @ 588,972: 17x27
#55 @ 57,851: 16x15
#56 @ 762,508: 26x28
#57 @ 407,887: 20x27
#58 @ 308,845: 19x23
#59 @ 93,594: 27x25
#60 @ 902,489: 19x16
#61 @ 151,454: 26x15
#62 @ 202,76: 20x14
#63 @ 402,981: 26x18
#64 @ 795,859: 13x21
#65 @ 725,643: 29x22
#66 @ 561,344: 29x20
#67 @ 938,178: 15x18
#68 @ 63,905: 27x13
#69 @ 920,703: 28x11
#70 @ 839,762: 24x22
#71 @ 277,626: 11x21
#72 @ 123,869: 25x25
#73 @ 343,66: 17x15
#74 @ 239,364: 25x10
#75 @ 785,758: 17x26
#76 @ 23,683: 26x28
#77 @ 32,855: 29x13
#78 @ 820,967: 11x27
#79 @ 353,9: 28x18
#80 @ 890,412: 24x25
#81 @ 395,894: 26x16
#82 @ 893,370: 11x26
#83 @ 96,30: 24x16
#84 @ 644,345: 17x29
#85 @ 884,19: 16x19
#86 @ 322,948: 14x12
#87 @ 602,415: 13x23
#88 @ 277,890: 28x16
#89 @ 173,916: 23x20
#90 @ 668,818: 21x15
#91 @ 569,66: 20x17
#92 @ 745,803: 12x14
#93 @ 114,610: 26x19
#94 @ 75,249: 15x11
#95 @ 746,481: 16x14
#96 @ 484,727: 29x24
#97 @ 759,476: 18x11
#98 @ 891,805: 25x19
#99 @ 80,8: 22x11
#100 @ 748,813: 11x29
#101 @ 508,370: 26x10
#102 @ 766,195: 11x22
#103 @ 331,83: 21x19
#104 @ 304,908: 10x21
#105 @ 950,925: 29x10
#106 @ 251,190: 18x23
#107 @ 773,884: 19x27
#108 @ 139,595: 12x29
#109 @ 683,721: 25x14
#110 @ 689,690: 28x12
#111 @ 555,890: 15x14
#112 @ 940,365: 25x20
#113 @ 220,299: 16x17
#114 @ 244,290: 27x17
#115 @ 510,653: 10x29
#116 @ 702,843: 20x22
#117 @ 467,603: 14x17
#118 @ 306,964: 28x23
#119 @ 265,848: 19x17
#120 @ 458,941: 11x19
#121 @ 914,875: 22x27
#122 @ 590,979: 10x3
#123 @ 940,243: 17x20
#124 @ 406,9: 14x27
#125 @ 929,88: 27x18
#126 @ 782,750: 28x24
#127 @ 510,804: 19x20
#128 @ 272,487: 22x11
#129 @ 215,116: 16x22
#130 @ 965,108: 29x20
#131 @ 894,931: 22x24
#132 @ 458,123: 20x18
#133 @ 230,544: 14x4
#134 @ 327,455: 21x13
#135 @ 423,950: 27x19
#136 @ 452,700: 26x24
#137 @ 931,597: 15x4
#138 @ 64,400: 27x17
#139 @ 144,59: 17x22
#140 @ 789,888: 29x22
#141 @ 628,288: 25x24
#142 @ 224,602: 10x24
#143 @ 684,966: 12x21
#144 @ 941,35: 28x19
#145 @ 73,605: 22x11
#146 @ 462,635: 10x22
#147 @ 894,50: 18x28
#148 @ 123,956: 17x16
#149 @ 677,510: 11x22
#150 @ 294,366: 28x21
#151 @ 829,626: 29x19
#152 @ 49,100: 17x20
#153 @ 368,380: 10x16
#154 @ 820,376: 19x18
#155 @ 829,0: 15x13
#156 @ 950,848: 29x13
#157 @ 64,510: 19x29
#158 @ 216,374: 14x18
#159 @ 409,26: 16x10
#160 @ 314,698: 18x18
#161 @ 163,928: 20x16
#162 @ 729,402: 22x17
#163 @ 424,725: 18x13
#164 @ 173,34: 14x17
#165 @ 458,898: 22x20
#166 @ 105,285: 29x15
#167 @ 74,822: 24x23
#168 @ 641,872: 5x3
#169 @ 202,907: 24x23
#170 @ 535,63: 21x13
#171 @ 267,384: 25x29
#172 @ 601,785: 19x27
#173 @ 960,848: 19x16
#174 @ 514,971: 24x29
#175 @ 606,9: 10x15
#176 @ 263,859: 28x17
#177 @ 120,623: 15x18
#178 @ 594,375: 13x26
#179 @ 406,310: 20x14
#180 @ 221,678: 24x21
#181 @ 926,211: 12x13
#182 @ 681,342: 12x27
#183 @ 335,231: 27x21
#184 @ 144,186: 11x10
#185 @ 550,716: 10x10
#186 @ 640,803: 29x20
#187 @ 859,856: 29x25
#188 @ 892,551: 10x25
#189 @ 852,768: 11x20
#190 @ 488,513: 18x26
#191 @ 766,142: 12x14
#192 @ 780,891: 24x24
#193 @ 321,800: 21x28
#194 @ 165,29: 11x16
#195 @ 156,489: 14x14
#196 @ 187,538: 23x29
#197 @ 206,556: 19x10
#198 @ 207,297: 29x16
#199 @ 769,103: 15x22
#200 @ 664,559: 19x10
#201 @ 425,650: 21x15
#202 @ 152,230: 12x26
#203 @ 185,219: 10x13
#204 @ 438,153: 11x20
#205 @ 385,532: 11x10
#206 @ 911,900: 15x24
#207 @ 469,19: 16x19
#208 @ 775,874: 28x16
#209 @ 756,432: 20x21
#210 @ 587,950: 26x21
#211 @ 931,541: 17x15
#212 @ 795,982: 26x13
#213 @ 582,774: 20x13
#214 @ 160,48: 14x24
#215 @ 332,253: 4x23
#216 @ 596,355: 13x19
#217 @ 713,472: 28x17
#218 @ 787,784: 23x13
#219 @ 427,448: 28x11
#220 @ 622,650: 12x19
#221 @ 243,238: 26x11
#222 @ 359,844: 13x13
#223 @ 831,883: 29x19
#224 @ 935,864: 21x25
#225 @ 39,580: 21x20
#226 @ 440,489: 28x21
#227 @ 28,963: 14x17
#228 @ 655,613: 11x29
#229 @ 537,940: 19x28
#230 @ 518,523: 16x29
#231 @ 942,81: 11x25
#232 @ 835,584: 19x11
#233 @ 398,271: 16x13
#234 @ 457,103: 24x24
#235 @ 293,482: 26x14
#236 @ 838,366: 11x16
#237 @ 770,521: 12x22
#238 @ 948,375: 25x28
#239 @ 611,479: 19x18
#240 @ 279,613: 23x21
#241 @ 407,715: 24x25
#242 @ 848,190: 15x18
#243 @ 260,111: 15x11
#244 @ 384,898: 11x27
#245 @ 593,960: 16x19
#246 @ 298,127: 11x29
#247 @ 436,903: 10x26
#248 @ 865,562: 27x29
#249 @ 594,723: 10x24
#250 @ 106,113: 29x12
#251 @ 981,752: 14x12
#252 @ 856,371: 18x15
#253 @ 568,163: 19x10
#254 @ 973,966: 22x22
#255 @ 270,541: 29x21
#256 @ 899,494: 24x28
#257 @ 620,623: 15x27
#258 @ 665,379: 28x10
#259 @ 35,241: 19x22
#260 @ 347,751: 11x25
#261 @ 96,554: 23x13
#262 @ 668,223: 21x19
#263 @ 15,976: 26x16
#264 @ 793,338: 24x29
#265 @ 159,651: 20x16
#266 @ 456,785: 13x26
#267 @ 838,394: 11x20
#268 @ 504,505: 11x17
#269 @ 655,29: 29x18
#270 @ 791,808: 18x17
#271 @ 72,435: 16x18
#272 @ 56,907: 17x23
#273 @ 908,558: 20x29
#274 @ 839,655: 25x20
#275 @ 658,316: 28x27
#276 @ 304,574: 10x12
#277 @ 699,205: 12x17
#278 @ 172,920: 18x24
#279 @ 240,590: 11x28
#280 @ 456,447: 16x18
#281 @ 6,272: 17x26
#282 @ 885,516: 25x28
#283 @ 904,39: 11x17
#284 @ 559,700: 23x29
#285 @ 124,215: 29x17
#286 @ 646,721: 25x17
#287 @ 69,801: 18x16
#288 @ 374,383: 17x15
#289 @ 883,794: 11x22
#290 @ 612,484: 12x12
#291 @ 445,912: 24x21
#292 @ 449,956: 12x20
#293 @ 164,268: 29x27
#294 @ 758,473: 12x16
#295 @ 326,64: 20x23
#296 @ 745,345: 12x13
#297 @ 941,487: 11x29
#298 @ 766,739: 22x25
#299 @ 142,639: 19x23
#300 @ 582,193: 17x19
#301 @ 633,870: 19x13
#302 @ 532,236: 22x29
#303 @ 502,331: 21x19
#304 @ 595,788: 25x28
#305 @ 340,547: 17x29
#306 @ 971,675: 24x21
#307 @ 841,220: 27x15
#308 @ 44,599: 12x16
#309 @ 950,202: 25x12
#310 @ 180,422: 17x27
#311 @ 159,748: 26x25
#312 @ 431,871: 29x13
#313 @ 400,827: 14x22
#314 @ 530,541: 24x11
#315 @ 516,818: 14x20
#316 @ 117,320: 20x11
#317 @ 350,407: 20x12
#318 @ 660,340: 18x11
#319 @ 583,596: 7x10
#320 @ 27,560: 28x24
#321 @ 885,935: 6x9
#322 @ 391,581: 29x24
#323 @ 791,578: 27x12
#324 @ 757,675: 16x25
#325 @ 895,805: 15x15
#326 @ 110,212: 20x27
#327 @ 883,930: 11x19
#328 @ 404,699: 17x11
#329 @ 616,370: 18x28
#330 @ 810,410: 29x23
#331 @ 343,336: 20x16
#332 @ 825,695: 26x22
#333 @ 393,230: 17x12
#334 @ 438,11: 12x14
#335 @ 940,272: 20x12
#336 @ 473,759: 20x21
#337 @ 223,101: 11x21
#338 @ 690,429: 11x11
#339 @ 496,177: 16x21
#340 @ 236,18: 15x17
#341 @ 105,710: 12x24
#342 @ 406,403: 20x22
#343 @ 390,151: 17x29
#344 @ 532,80: 26x16
#345 @ 164,534: 18x26
#346 @ 833,30: 17x22
#347 @ 23,234: 18x15
#348 @ 355,734: 22x12
#349 @ 456,486: 27x11
#350 @ 93,200: 26x28
#351 @ 29,695: 20x27
#352 @ 93,972: 25x26
#353 @ 422,905: 18x19
#354 @ 104,862: 12x17
#355 @ 419,320: 18x27
#356 @ 379,366: 28x19
#357 @ 399,275: 22x24
#358 @ 338,249: 18x26
#359 @ 550,181: 24x22
#360 @ 3,789: 19x29
#361 @ 467,889: 16x23
#362 @ 238,83: 10x10
#363 @ 258,371: 10x20
#364 @ 73,622: 24x17
#365 @ 343,0: 13x25
#366 @ 523,909: 14x27
#367 @ 500,938: 18x13
#368 @ 16,113: 12x15
#369 @ 208,22: 15x18
#370 @ 813,957: 25x19
#371 @ 641,657: 23x26
#372 @ 629,522: 22x19
#373 @ 645,349: 23x12
#374 @ 248,340: 12x24
#375 @ 455,920: 19x29
#376 @ 974,872: 12x21
#377 @ 816,527: 29x26
#378 @ 264,519: 22x22
#379 @ 514,924: 27x15
#380 @ 155,378: 27x21
#381 @ 768,447: 17x26
#382 @ 873,54: 27x24
#383 @ 299,297: 18x21
#384 @ 231,28: 18x15
#385 @ 721,416: 20x19
#386 @ 532,536: 29x13
#387 @ 357,579: 29x28
#388 @ 536,833: 19x14
#389 @ 658,21: 27x20
#390 @ 807,721: 23x20
#391 @ 234,669: 17x29
#392 @ 0,376: 25x19
#393 @ 221,695: 15x21
#394 @ 407,418: 28x16
#395 @ 928,791: 21x17
#396 @ 144,472: 26x26
#397 @ 614,538: 27x19
#398 @ 506,320: 19x19
#399 @ 699,674: 14x14
#400 @ 741,832: 29x26
#401 @ 671,952: 27x10
#402 @ 368,426: 26x17
#403 @ 620,361: 21x18
#404 @ 945,76: 24x14
#405 @ 147,878: 10x11
#406 @ 805,658: 17x11
#407 @ 504,755: 28x24
#408 @ 231,85: 15x21
#409 @ 882,960: 22x10
#410 @ 118,222: 15x12
#411 @ 261,400: 13x25
#412 @ 213,602: 18x26
#413 @ 309,823: 27x25
#414 @ 967,897: 15x17
#415 @ 810,580: 25x26
#416 @ 477,728: 16x28
#417 @ 899,591: 13x14
#418 @ 764,81: 13x20
#419 @ 482,484: 27x10
#420 @ 651,853: 22x11
#421 @ 569,665: 11x22
#422 @ 715,324: 14x18
#423 @ 735,410: 17x11
#424 @ 90,67: 21x13
#425 @ 26,521: 19x16
#426 @ 600,434: 23x13
#427 @ 496,328: 29x21
#428 @ 880,554: 26x29
#429 @ 754,603: 21x18
#430 @ 299,695: 12x23
#431 @ 507,429: 10x14
#432 @ 254,40: 22x12
#433 @ 647,613: 14x11
#434 @ 134,924: 22x22
#435 @ 983,700: 12x24
#436 @ 516,923: 23x19
#437 @ 161,953: 19x19
#438 @ 966,733: 12x18
#439 @ 461,714: 29x21
#440 @ 141,639: 28x12
#441 @ 854,37: 19x24
#442 @ 66,444: 18x16
#443 @ 11,261: 10x20
#444 @ 154,475: 8x16
#445 @ 631,291: 15x6
#446 @ 616,2: 29x26
#447 @ 888,192: 19x28
#448 @ 426,969: 15x19
#449 @ 729,80: 22x12
#450 @ 732,433: 12x19
#451 @ 21,371: 18x12
#452 @ 542,46: 3x10
#453 @ 522,251: 11x26
#454 @ 522,119: 14x10
#455 @ 922,152: 28x16
#456 @ 970,711: 18x29
#457 @ 331,768: 20x16
#458 @ 731,591: 16x26
#459 @ 222,122: 10x20
#460 @ 889,360: 13x14
#461 @ 609,78: 13x27
#462 @ 75,527: 28x13
#463 @ 240,740: 29x29
#464 @ 6,402: 14x15
#465 @ 92,546: 17x15
#466 @ 945,264: 17x12
#467 @ 663,269: 13x11
#468 @ 470,445: 28x11
#469 @ 242,407: 14x17
#470 @ 319,432: 10x14
#471 @ 688,363: 14x19
#472 @ 754,60: 22x10
#473 @ 355,576: 21x15
#474 @ 936,512: 22x28
#475 @ 14,961: 16x21
#476 @ 792,698: 28x27
#477 @ 975,779: 12x15
#478 @ 16,99: 22x16
#479 @ 668,345: 12x21
#480 @ 803,285: 22x27
#481 @ 64,198: 25x15
#482 @ 677,394: 23x10
#483 @ 928,947: 23x26
#484 @ 728,947: 15x13
#485 @ 825,232: 15x23
#486 @ 160,418: 15x26
#487 @ 317,772: 21x29
#488 @ 944,838: 25x25
#489 @ 595,13: 15x22
#490 @ 957,64: 25x20
#491 @ 512,517: 21x27
#492 @ 425,883: 19x15
#493 @ 664,342: 20x14
#494 @ 446,558: 29x15
#495 @ 56,373: 18x12
#496 @ 22,950: 25x23
#497 @ 517,950: 12x18
#498 @ 554,920: 23x27
#499 @ 249,530: 22x17
#500 @ 940,167: 22x13
#501 @ 278,520: 26x15
#502 @ 419,965: 11x23
#503 @ 786,766: 14x25
#504 @ 214,681: 26x22
#505 @ 588,904: 20x13
#506 @ 937,970: 28x25
#507 @ 890,739: 20x11
#508 @ 656,379: 20x20
#509 @ 821,47: 22x22
#510 @ 927,666: 20x27
#511 @ 131,79: 20x28
#512 @ 810,55: 14x18
#513 @ 416,317: 10x22
#514 @ 2,959: 16x25
#515 @ 674,561: 4x5
#516 @ 199,107: 13x16
#517 @ 834,403: 27x25
#518 @ 206,337: 11x24
#519 @ 422,974: 22x11
#520 @ 120,930: 22x10
#521 @ 97,488: 3x11
#522 @ 276,222: 15x20
#523 @ 181,405: 21x22
#524 @ 622,690: 29x29
#525 @ 584,148: 20x12
#526 @ 428,971: 13x22
#527 @ 694,836: 23x26
#528 @ 611,90: 25x16
#529 @ 539,44: 11x20
#530 @ 145,374: 16x28
#531 @ 907,429: 14x21
#532 @ 214,368: 11x24
#533 @ 756,801: 10x26
#534 @ 175,731: 10x29
#535 @ 404,8: 15x20
#536 @ 808,645: 21x14
#537 @ 816,227: 27x29
#538 @ 665,816: 15x26
#539 @ 950,498: 16x27
#540 @ 596,212: 25x22
#541 @ 161,869: 24x23
#542 @ 946,18: 16x19
#543 @ 765,881: 17x14
#544 @ 354,909: 28x15
#545 @ 490,892: 12x10
#546 @ 322,251: 19x29
#547 @ 649,940: 7x6
#548 @ 65,646: 12x18
#549 @ 660,779: 10x11
#550 @ 693,377: 28x19
#551 @ 341,288: 29x17
#552 @ 28,317: 10x24
#553 @ 181,108: 29x17
#554 @ 445,602: 28x12
#555 @ 328,546: 21x21
#556 @ 973,193: 21x25
#557 @ 464,107: 12x24
#558 @ 196,586: 22x23
#559 @ 647,935: 12x17
#560 @ 973,250: 14x29
#561 @ 936,105: 14x12
#562 @ 550,726: 13x13
#563 @ 628,606: 18x23
#564 @ 926,696: 15x29
#565 @ 538,901: 18x28
#566 @ 869,126: 20x25
#567 @ 945,696: 22x10
#568 @ 340,816: 10x21
#569 @ 903,793: 27x28
#570 @ 139,307: 12x13
#571 @ 722,910: 14x23
#572 @ 90,952: 22x26
#573 @ 287,342: 10x28
#574 @ 539,59: 14x21
#575 @ 761,537: 27x23
#576 @ 670,683: 17x17
#577 @ 554,903: 28x28
#578 @ 571,799: 23x12
#579 @ 185,413: 16x12
#580 @ 277,828: 18x29
#581 @ 367,384: 19x22
#582 @ 63,264: 15x12
#583 @ 703,321: 21x29
#584 @ 725,331: 21x27
#585 @ 215,428: 11x14
#586 @ 560,678: 25x22
#587 @ 370,923: 27x21
#588 @ 304,943: 23x15
#589 @ 906,29: 25x18
#590 @ 325,547: 26x12
#591 @ 917,683: 14x25
#592 @ 650,75: 14x15
#593 @ 765,581: 27x19
#594 @ 332,345: 25x26
#595 @ 900,747: 20x27
#596 @ 393,888: 14x29
#597 @ 0,376: 14x20
#598 @ 782,607: 28x26
#599 @ 248,868: 13x13
#600 @ 841,842: 22x21
#601 @ 544,141: 13x17
#602 @ 418,413: 19x20
#603 @ 899,215: 28x21
#604 @ 100,136: 27x20
#605 @ 971,346: 11x26
#606 @ 147,466: 19x26
#607 @ 935,817: 12x15
#608 @ 341,593: 25x17
#609 @ 701,454: 24x22
#610 @ 546,903: 29x26
#611 @ 230,85: 15x22
#612 @ 442,155: 20x11
#613 @ 417,331: 13x11
#614 @ 150,495: 12x23
#615 @ 537,662: 17x25
#616 @ 57,249: 28x17
#617 @ 811,631: 17x11
#618 @ 176,933: 21x10
#619 @ 968,595: 29x28
#620 @ 206,563: 18x13
#621 @ 140,236: 21x10
#622 @ 393,352: 12x25
#623 @ 494,312: 22x25
#624 @ 607,576: 20x5
#625 @ 389,912: 27x19
#626 @ 389,29: 28x23
#627 @ 863,48: 23x16
#628 @ 972,198: 13x25
#629 @ 276,869: 15x26
#630 @ 553,930: 16x20
#631 @ 260,618: 12x19
#632 @ 465,805: 17x24
#633 @ 767,841: 12x12
#634 @ 791,925: 23x18
#635 @ 840,891: 16x6
#636 @ 856,399: 20x23
#637 @ 316,704: 14x19
#638 @ 50,429: 16x29
#639 @ 763,684: 15x28
#640 @ 243,397: 11x22
#641 @ 14,388: 20x26
#642 @ 245,778: 12x23
#643 @ 242,162: 17x29
#644 @ 56,573: 10x27
#645 @ 621,214: 14x23
#646 @ 109,328: 20x12
#647 @ 357,787: 22x11
#648 @ 285,542: 24x18
#649 @ 821,701: 29x14
#650 @ 938,535: 11x13
#651 @ 545,529: 11x25
#652 @ 487,479: 25x28
#653 @ 540,149: 14x19
#654 @ 683,305: 14x21
#655 @ 714,899: 17x14
#656 @ 514,131: 18x21
#657 @ 208,63: 13x28
#658 @ 695,948: 9x4
#659 @ 497,883: 13x13
#660 @ 259,647: 10x24
#661 @ 762,618: 16x18
#662 @ 890,590: 10x18
#663 @ 78,831: 10x26
#664 @ 963,246: 18x14
#665 @ 934,804: 13x20
#666 @ 193,787: 13x12
#667 @ 432,580: 6x15
#668 @ 352,412: 11x3
#669 @ 205,10: 21x29
#670 @ 574,331: 12x29
#671 @ 248,787: 3x5
#672 @ 395,383: 12x17
#673 @ 798,218: 19x22
#674 @ 500,665: 16x26
#675 @ 26,812: 10x14
#676 @ 456,794: 25x16
#677 @ 840,9: 20x12
#678 @ 657,859: 20x18
#679 @ 546,349: 24x10
#680 @ 682,764: 23x14
#681 @ 291,758: 14x26
#682 @ 734,98: 23x25
#683 @ 109,393: 19x28
#684 @ 398,336: 26x22
#685 @ 122,298: 18x26
#686 @ 54,539: 25x13
#687 @ 1,378: 24x23
#688 @ 512,587: 28x16
#689 @ 371,638: 20x14
#690 @ 745,526: 25x23
#691 @ 833,817: 11x9
#692 @ 318,692: 20x14
#693 @ 801,207: 22x24
#694 @ 134,117: 26x11
#695 @ 712,298: 23x29
#696 @ 554,141: 28x28
#697 @ 776,835: 24x24
#698 @ 76,388: 17x21
#699 @ 490,544: 27x13
#700 @ 91,493: 22x25
#701 @ 78,984: 22x12
#702 @ 328,602: 17x27
#703 @ 240,781: 29x29
#704 @ 818,642: 17x20
#705 @ 376,813: 21x28
#706 @ 86,598: 24x21
#707 @ 507,105: 23x23
#708 @ 828,736: 16x23
#709 @ 621,665: 27x17
#710 @ 99,250: 26x14
#711 @ 520,192: 25x26
#712 @ 94,479: 10x28
#713 @ 257,121: 10x22
#714 @ 72,143: 21x22
#715 @ 107,953: 28x29
#716 @ 635,808: 12x22
#717 @ 605,574: 29x11
#718 @ 676,217: 10x12
#719 @ 458,345: 25x25
#720 @ 462,552: 28x23
#721 @ 255,381: 28x13
#722 @ 609,975: 10x12
#723 @ 816,522: 20x12
#724 @ 742,644: 16x15
#725 @ 628,708: 11x16
#726 @ 123,759: 25x23
#727 @ 551,915: 16x14
#728 @ 851,119: 28x10
#729 @ 294,698: 19x19
#730 @ 679,511: 23x22
#731 @ 526,49: 29x15
#732 @ 802,716: 13x24
#733 @ 342,777: 20x11
#734 @ 470,97: 20x15
#735 @ 377,347: 21x23
#736 @ 408,291: 25x10
#737 @ 819,109: 21x14
#738 @ 247,657: 16x19
#739 @ 58,94: 18x11
#740 @ 636,701: 21x29
#741 @ 712,73: 28x18
#742 @ 924,532: 22x16
#743 @ 17,804: 24x10
#744 @ 177,244: 18x15
#745 @ 503,5: 27x26
#746 @ 393,783: 22x27
#747 @ 585,381: 23x10
#748 @ 807,634: 27x21
#749 @ 196,438: 15x20
#750 @ 52,203: 16x18
#751 @ 975,885: 21x15
#752 @ 914,232: 28x21
#753 @ 818,919: 10x10
#754 @ 867,141: 24x24
#755 @ 49,536: 28x23
#756 @ 937,817: 15x11
#757 @ 384,423: 20x15
#758 @ 816,498: 20x14
#759 @ 671,583: 23x13
#760 @ 511,582: 18x15
#761 @ 426,694: 26x23
#762 @ 70,610: 12x26
#763 @ 798,115: 26x22
#764 @ 693,384: 13x19
#765 @ 754,523: 15x13
#766 @ 12,110: 24x17
#767 @ 498,144: 24x27
#768 @ 106,222: 22x16
#769 @ 98,730: 29x20
#770 @ 849,846: 5x11
#771 @ 64,591: 21x10
#772 @ 218,476: 25x27
#773 @ 63,659: 22x24
#774 @ 177,798: 21x29
#775 @ 2,574: 10x18
#776 @ 340,568: 21x17
#777 @ 813,848: 11x22
#778 @ 796,625: 24x16
#779 @ 569,343: 14x22
#780 @ 289,925: 21x20
#781 @ 64,123: 26x29
#782 @ 201,554: 16x11
#783 @ 629,723: 28x20
#784 @ 972,748: 11x20
#785 @ 712,677: 27x16
#786 @ 718,73: 17x19
#787 @ 186,79: 17x23
#788 @ 844,627: 13x20
#789 @ 425,259: 12x23
#790 @ 399,805: 27x20
#791 @ 208,889: 14x29
#792 @ 274,24: 25x27
#793 @ 493,962: 21x20
#794 @ 337,547: 24x12
#795 @ 694,768: 7x6
#796 @ 766,927: 14x24
#797 @ 724,197: 23x10
#798 @ 525,163: 29x21
#799 @ 507,99: 25x18
#800 @ 565,726: 23x27
#801 @ 950,510: 28x21
#802 @ 86,593: 20x29
#803 @ 900,401: 25x26
#804 @ 478,528: 26x21
#805 @ 131,873: 27x21
#806 @ 636,685: 12x28
#807 @ 481,607: 18x27
#808 @ 817,899: 11x25
#809 @ 630,508: 20x21
#810 @ 540,145: 29x18
#811 @ 857,155: 18x17
#812 @ 448,213: 16x14
#813 @ 560,275: 23x24
#814 @ 580,591: 14x19
#815 @ 145,290: 20x25
#816 @ 894,544: 22x23
#817 @ 317,476: 18x14
#818 @ 247,863: 14x25
#819 @ 596,378: 26x25
#820 @ 633,824: 17x10
#821 @ 602,537: 17x21
#822 @ 8,134: 28x25
#823 @ 580,44: 20x27
#824 @ 394,541: 15x15
#825 @ 985,116: 13x24
#826 @ 423,416: 10x19
#827 @ 631,776: 27x15
#828 @ 866,358: 20x18
#829 @ 658,939: 19x12
#830 @ 522,799: 25x21
#831 @ 11,655: 12x19
#832 @ 266,624: 29x23
#833 @ 389,26: 21x12
#834 @ 480,174: 25x25
#835 @ 268,14: 16x15
#836 @ 906,838: 10x10
#837 @ 51,433: 28x21
#838 @ 556,351: 14x21
#839 @ 636,355: 22x20
#840 @ 593,596: 28x26
#841 @ 164,696: 24x13
#842 @ 723,685: 25x17
#843 @ 309,480: 11x14
#844 @ 566,911: 10x18
#845 @ 869,153: 23x12
#846 @ 391,46: 20x25
#847 @ 932,36: 25x15
#848 @ 426,915: 18x23
#849 @ 176,914: 10x27
#850 @ 203,331: 22x16
#851 @ 618,364: 20x12
#852 @ 918,550: 29x10
#853 @ 155,284: 14x12
#854 @ 616,601: 24x28
#855 @ 500,541: 14x14
#856 @ 838,205: 12x23
#857 @ 369,704: 26x28
#858 @ 199,706: 17x19
#859 @ 775,818: 26x11
#860 @ 197,702: 29x22
#861 @ 980,360: 14x16
#862 @ 180,877: 28x12
#863 @ 70,7: 28x13
#864 @ 379,372: 18x23
#865 @ 791,905: 20x20
#866 @ 356,618: 13x22
#867 @ 579,195: 14x18
#868 @ 221,610: 26x19
#869 @ 157,133: 28x21
#870 @ 929,292: 29x18
#871 @ 746,517: 16x12
#872 @ 606,351: 13x13
#873 @ 592,169: 28x29
#874 @ 456,511: 26x10
#875 @ 304,568: 16x22
#876 @ 802,596: 26x23
#877 @ 602,100: 16x12
#878 @ 434,891: 21x16
#879 @ 146,738: 25x25
#880 @ 876,603: 15x24
#881 @ 920,745: 13x23
#882 @ 521,3: 10x10
#883 @ 724,707: 18x22
#884 @ 90,28: 15x23
#885 @ 258,302: 10x12
#886 @ 38,410: 23x27
#887 @ 371,905: 17x21
#888 @ 323,79: 23x10
#889 @ 60,353: 20x21
#890 @ 576,776: 22x12
#891 @ 34,765: 15x11
#892 @ 663,599: 13x13
#893 @ 153,497: 4x16
#894 @ 737,199: 18x11
#895 @ 66,512: 13x24
#896 @ 614,57: 19x21
#897 @ 294,474: 12x21
#898 @ 176,171: 18x14
#899 @ 910,859: 29x28
#900 @ 949,399: 15x13
#901 @ 955,980: 10x11
#902 @ 513,151: 26x13
#903 @ 309,602: 18x21
#904 @ 893,860: 23x19
#905 @ 790,959: 14x10
#906 @ 965,117: 13x11
#907 @ 904,751: 12x26
#908 @ 148,390: 13x27
#909 @ 740,933: 19x27
#910 @ 908,745: 13x25
#911 @ 787,962: 12x25
#912 @ 752,842: 21x20
#913 @ 255,597: 12x22
#914 @ 92,40: 12x10
#915 @ 527,893: 18x13
#916 @ 913,447: 24x24
#917 @ 962,130: 26x13
#918 @ 879,858: 13x17
#919 @ 406,33: 21x20
#920 @ 688,66: 14x12
#921 @ 520,82: 28x21
#922 @ 29,532: 25x27
#923 @ 446,804: 27x14
#924 @ 391,716: 16x15
#925 @ 188,239: 28x14
#926 @ 327,802: 3x11
#927 @ 67,525: 16x14
#928 @ 952,96: 14x27
#929 @ 763,874: 19x15
#930 @ 246,111: 20x18
#931 @ 875,599: 20x15
#932 @ 388,799: 16x15
#933 @ 812,703: 12x11
#934 @ 803,704: 16x15
#935 @ 82,421: 23x26
#936 @ 832,322: 24x25
#937 @ 801,852: 18x13
#938 @ 101,869: 16x12
#939 @ 905,757: 14x28
#940 @ 94,616: 23x25
#941 @ 340,369: 12x16
#942 @ 745,49: 25x27
#943 @ 729,539: 26x15
#944 @ 762,148: 15x24
#945 @ 392,773: 17x16
#946 @ 495,224: 13x10
#947 @ 418,322: 3x13
#948 @ 63,836: 29x27
#949 @ 923,721: 18x29
#950 @ 107,325: 4x17
#951 @ 239,246: 14x10
#952 @ 20,60: 14x12
#953 @ 196,211: 26x15
#954 @ 209,217: 19x21
#955 @ 188,80: 26x29
#956 @ 427,208: 27x21
#957 @ 266,240: 20x22
#958 @ 693,940: 15x21
#959 @ 225,900: 11x12
#960 @ 783,847: 21x29
#961 @ 309,705: 17x15
#962 @ 715,322: 27x14
#963 @ 658,639: 17x17
#964 @ 303,591: 23x29
#965 @ 322,681: 19x24
#966 @ 959,212: 28x13
#967 @ 463,919: 11x13
#968 @ 871,197: 28x22
#969 @ 920,273: 23x23
#970 @ 541,829: 21x15
#971 @ 212,496: 13x16
#972 @ 945,19: 26x23
#973 @ 482,24: 20x23
#974 @ 749,518: 20x27
#975 @ 347,207: 27x10
#976 @ 460,345: 19x28
#977 @ 71,63: 17x26
#978 @ 508,968: 22x15
#979 @ 481,227: 20x21
#980 @ 902,390: 29x28
#981 @ 200,643: 13x29
#982 @ 283,514: 27x26
#983 @ 675,609: 24x23
#984 @ 599,95: 27x26
#985 @ 262,369: 10x17
#986 @ 113,252: 25x26
#987 @ 54,432: 12x26
#988 @ 669,845: 19x21
#989 @ 68,623: 24x25
#990 @ 965,122: 13x23
#991 @ 909,954: 11x21
#992 @ 441,807: 28x24
#993 @ 811,921: 10x13
#994 @ 891,494: 10x23
#995 @ 579,154: 26x18
#996 @ 148,411: 26x28
#997 @ 654,130: 25x11
#998 @ 233,577: 21x23
#999 @ 810,493: 15x15
#1000 @ 466,766: 24x15
#1001 @ 133,180: 16x16
#1002 @ 963,960: 26x19
#1003 @ 170,780: 14x29
#1004 @ 322,960: 21x22
#1005 @ 300,509: 23x17
#1006 @ 20,305: 26x23
#1007 @ 679,358: 21x26
#1008 @ 982,661: 14x21
#1009 @ 31,241: 18x28
#1010 @ 629,771: 23x20
#1011 @ 241,596: 11x16
#1012 @ 982,730: 11x25
#1013 @ 228,536: 20x22
#1014 @ 327,715: 15x11
#1015 @ 609,214: 19x21
#1016 @ 403,297: 10x15
#1017 @ 697,552: 27x10
#1018 @ 416,584: 23x13
#1019 @ 721,83: 25x18
#1020 @ 703,539: 27x17
#1021 @ 113,101: 28x21
#1022 @ 537,730: 18x19
#1023 @ 63,796: 22x14
#1024 @ 843,203: 13x22
#1025 @ 724,81: 20x23
#1026 @ 308,315: 21x17
#1027 @ 212,547: 19x25
#1028 @ 860,670: 12x20
#1029 @ 101,114: 17x25
#1030 @ 258,534: 11x22
#1031 @ 478,756: 18x12
#1032 @ 909,840: 3x5
#1033 @ 403,717: 22x21
#1034 @ 675,692: 26x22
#1035 @ 16,660: 28x16
#1036 @ 300,780: 18x29
#1037 @ 401,808: 27x25
#1038 @ 809,896: 20x28
#1039 @ 265,522: 19x11
#1040 @ 91,134: 26x13
#1041 @ 580,163: 19x20
#1042 @ 455,780: 10x20
#1043 @ 891,145: 20x21
#1044 @ 323,464: 18x26
#1045 @ 655,830: 13x24
#1046 @ 605,76: 13x18
#1047 @ 256,516: 15x23
#1048 @ 910,735: 12x19
#1049 @ 331,6: 28x14
#1050 @ 82,73: 22x10
#1051 @ 230,110: 13x24
#1052 @ 853,83: 15x12
#1053 @ 729,723: 17x22
#1054 @ 611,193: 11x11
#1055 @ 716,854: 27x24
#1056 @ 132,627: 28x21
#1057 @ 401,230: 26x20
#1058 @ 176,220: 13x10
#1059 @ 643,357: 11x28
#1060 @ 334,213: 18x12
#1061 @ 395,694: 15x13
#1062 @ 19,370: 17x25
#1063 @ 9,77: 13x25
#1064 @ 720,337: 16x21
#1065 @ 669,595: 24x12
#1066 @ 456,911: 11x10
#1067 @ 459,519: 14x23
#1068 @ 353,539: 28x10
#1069 @ 703,304: 16x29
#1070 @ 885,621: 11x11
#1071 @ 184,615: 20x20
#1072 @ 524,380: 19x15
#1073 @ 208,661: 19x15
#1074 @ 815,916: 18x28
#1075 @ 26,180: 10x10
#1076 @ 897,734: 27x19
#1077 @ 468,605: 27x13
#1078 @ 671,361: 14x27
#1079 @ 387,383: 12x10
#1080 @ 410,21: 13x15
#1081 @ 899,39: 26x26
#1082 @ 69,356: 13x26
#1083 @ 484,547: 24x20
#1084 @ 127,735: 24x18
#1085 @ 949,971: 23x25
#1086 @ 618,532: 29x10
#1087 @ 973,776: 26x17
#1088 @ 752,724: 20x16
#1089 @ 88,323: 29x22
#1090 @ 588,78: 24x24
#1091 @ 813,263: 11x28
#1092 @ 94,496: 8x13
#1093 @ 482,157: 20x16
#1094 @ 638,793: 14x13
#1095 @ 388,160: 12x13
#1096 @ 911,907: 24x23
#1097 @ 38,222: 27x23
#1098 @ 770,502: 23x10
#1099 @ 889,953: 20x24
#1100 @ 954,918: 15x12
#1101 @ 746,605: 15x18
#1102 @ 120,750: 19x19
#1103 @ 322,379: 22x11
#1104 @ 542,65: 13x12
#1105 @ 832,431: 10x23
#1106 @ 892,27: 13x29
#1107 @ 434,248: 20x17
#1108 @ 661,943: 11x26
#1109 @ 487,728: 29x15
#1110 @ 879,615: 27x13
#1111 @ 371,421: 11x24
#1112 @ 304,875: 16x17
#1113 @ 235,562: 10x28
#1114 @ 678,331: 17x18
#1115 @ 30,762: 24x11
#1116 @ 439,928: 25x28
#1117 @ 528,365: 12x25
#1118 @ 232,116: 15x15
#1119 @ 329,599: 22x17
#1120 @ 9,65: 28x25
#1121 @ 287,467: 27x27
#1122 @ 371,732: 27x12
#1123 @ 942,848: 12x17
#1124 @ 167,876: 23x19
#1125 @ 531,18: 28x18
#1126 @ 662,861: 10x12
#1127 @ 700,197: 28x18
#1128 @ 314,786: 13x13
#1129 @ 942,296: 15x15
#1130 @ 259,118: 15x25
#1131 @ 306,144: 12x22
#1132 @ 78,537: 15x23
#1133 @ 348,606: 14x23
#1134 @ 648,772: 13x10
#1135 @ 500,246: 18x15
#1136 @ 754,150: 20x15
#1137 @ 974,586: 10x29
#1138 @ 348,610: 16x18
#1139 @ 336,300: 11x12
#1140 @ 200,430: 11x15
#1141 @ 505,825: 16x22
#1142 @ 870,581: 19x15
#1143 @ 686,77: 25x29
#1144 @ 794,889: 23x11
#1145 @ 564,615: 27x10
#1146 @ 312,440: 22x19
#1147 @ 234,636: 13x16
#1148 @ 532,75: 19x26
#1149 @ 188,544: 29x12
#1150 @ 892,72: 17x13
#1151 @ 830,815: 23x15
#1152 @ 942,260: 23x10
#1153 @ 544,200: 16x14
#1154 @ 795,961: 22x12
#1155 @ 88,48: 26x15
#1156 @ 726,86: 13x21
#1157 @ 507,432: 22x26
#1158 @ 253,757: 28x11
#1159 @ 111,848: 19x26
#1160 @ 500,766: 22x21
#1161 @ 322,685: 15x24
#1162 @ 703,532: 25x11
#1163 @ 185,414: 28x12
#1164 @ 377,763: 25x15
#1165 @ 87,66: 17x13
#1166 @ 125,950: 17x10
#1167 @ 601,549: 24x20
#1168 @ 763,497: 28x13
#1169 @ 162,312: 20x18
#1170 @ 201,112: 7x5
#1171 @ 111,743: 25x13
#1172 @ 576,911: 22x11
#1173 @ 257,42: 13x6
#1174 @ 404,373: 20x25
#1175 @ 745,503: 21x24
#1176 @ 593,520: 23x24
#1177 @ 14,969: 19x18
#1178 @ 115,958: 19x13
#1179 @ 50,542: 10x24
#1180 @ 846,26: 11x14
#1181 @ 231,124: 13x10
#1182 @ 981,58: 15x17
#1183 @ 619,199: 12x20
#1184 @ 608,360: 19x14
#1185 @ 601,977: 15x10
#1186 @ 581,736: 21x19
#1187 @ 402,338: 22x27
#1188 @ 189,723: 14x10
#1189 @ 255,329: 13x25
#1190 @ 169,156: 15x20
#1191 @ 611,793: 16x21
#1192 @ 110,715: 16x25
#1193 @ 516,11: 22x24
#1194 @ 576,793: 19x11
#1195 @ 317,440: 10x24
#1196 @ 761,482: 18x12
#1197 @ 213,703: 16x29
#1198 @ 846,884: 27x22
#1199 @ 430,578: 13x27
#1200 @ 470,777: 15x27
#1201 @ 29,846: 18x24
#1202 @ 668,627: 27x13
#1203 @ 9,398: 10x27
#1204 @ 656,674: 22x27
#1205 @ 746,203: 19x13
#1206 @ 924,141: 18x15
#1207 @ 240,785: 16x19
#1208 @ 702,819: 12x19
#1209 @ 145,890: 24x24
#1210 @ 349,607: 14x21
#1211 @ 580,229: 20x15
#1212 @ 846,79: 10x19
#1213 @ 132,919: 22x23
#1214 @ 767,215: 27x15
#1215 @ 301,705: 27x24
#1216 @ 713,178: 26x24
#1217 @ 270,635: 12x10
#1218 @ 813,882: 25x13
#1219 @ 5,580: 24x24
#1220 @ 771,100: 11x14
#1221 @ 329,836: 28x28
#1222 @ 553,524: 18x19
#1223 @ 87,576: 14x20
#1224 @ 768,931: 9x14
#1225 @ 550,165: 14x14
#1226 @ 191,892: 28x17
#1227 @ 542,916: 12x16
#1228 @ 648,679: 27x13
#1229 @ 459,943: 25x19
#1230 @ 104,377: 18x25
#1231 @ 439,708: 14x10
#1232 @ 400,652: 27x27
#1233 @ 825,902: 23x17
#1234 @ 592,85: 28x19
#1235 @ 361,669: 19x16
#1236 @ 771,583: 10x20
#1237 @ 4,810: 22x10
#1238 @ 665,353: 28x21
#1239 @ 79,958: 12x17
#1240 @ 171,386: 15x29
#1241 @ 515,371: 21x10
#1242 @ 19,171: 16x22
#1243 @ 246,990: 12x10
#1244 @ 652,51: 11x29
#1245 @ 938,354: 26x21
#1246 @ 102,293: 19x18
#1247 @ 163,693: 16x26
#1248 @ 198,611: 18x19
#1249 @ 597,719: 14x24
#1250 @ 671,353: 16x20
#1251 @ 770,486: 26x15
#1252 @ 723,58: 15x20
#1253 @ 706,307: 11x18
#1254 @ 474,10: 16x15
#1255 @ 536,321: 15x14
#1256 @ 569,326: 23x18
#1257 @ 346,643: 29x27
#1258 @ 137,609: 25x13
#1259 @ 165,804: 12x19
#1260 @ 613,783: 17x22
#1261 @ 918,531: 20x16
#1262 @ 632,21: 17x24
#1263 @ 188,93: 18x17
#1264 @ 884,153: 15x25
#1265 @ 383,645: 16x24
#1266 @ 107,768: 20x10
#1267 @ 610,527: 11x17
#1268 @ 25,118: 15x17
#1269 @ 70,605: 19x27
#1270 @ 60,268: 14x12
#1271 @ 527,837: 27x17
#1272 @ 821,313: 19x14
#1273 @ 474,8: 21x12
#1274 @ 971,883: 26x16
#1275 @ 716,292: 20x25
#1276 @ 73,134: 20x15
#1277 @ 160,621: 18x16
#1278 @ 370,542: 14x14
#1279 @ 244,929: 29x23
#1280 @ 554,299: 29x28
#1281 @ 551,8: 12x22
#1282 @ 885,147: 13x27
#1283 @ 512,120: 29x14
#1284 @ 845,570: 18x19
#1285 @ 534,918: 17x24
#1286 @ 512,253: 17x10
#1287 @ 320,426: 21x21
#1288 @ 66,530: 24x12
#1289 @ 742,155: 29x13
#1290 @ 113,935: 27x21
#1291 @ 822,405: 23x14
#1292 @ 469,635: 27x22
#1293 @ 428,454: 29x11
#1294 @ 770,573: 27x20
#1295 @ 240,934: 29x28
""".trimIndent()

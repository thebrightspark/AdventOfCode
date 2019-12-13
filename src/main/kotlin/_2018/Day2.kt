package _2018

import aocRun

fun main() {
    aocRun(puzzleInput) { input ->
        var twice = 0
        var thrice = 0
        input.split("\n").map { id ->
            val counts = mutableMapOf<Char, Int>()
            id.toCharArray().forEach { char -> counts.compute(char) { _, count -> (count ?: 0) + 1 } }
            if (counts.count { it.value == 2 } >= 1)
                twice++
            if (counts.count { it.value == 3 } >= 1)
                thrice++
        }
        return@aocRun twice * thrice
    }

    aocRun(puzzleInput) { input ->
        val inputList = input.split("\n")
        val size = inputList.size
        inputList.subList(0, size - 1).forEachIndexed { index1, id1 ->
            inputList.subList(index1 + 1, size).forEach { id2 ->
                val index = compareIds(id1, id2)
                if (index >= 0)
                    return@aocRun """
                        |
                        |   ID 1: $id1
                        |   ID 2: $id2
                        |   Same: ${id1.removeRange(index, index + 1)}
                    """.trimMargin()
            }
        }
    }
}

/**
 * Compares the two strings similarity.
 * If they are the exact same or more than two characters different, returns -1.
 * Otherwise, returns the index of the differing character.
 */
private fun compareIds(id1: String, id2: String): Int {
    if (id1 == id2)
        return -1
    var diffChar = -1
    val chars2 = id2.toCharArray()
    id1.toCharArray().forEachIndexed { index, c ->
        if (c != chars2[index]) {
            if (diffChar >= 0)
                return -1
            diffChar = index
        }
    }
    return diffChar
}

private val puzzleInput = """
cnjxpritdzhubeseewfmqagkul
cwyxpgitdzhvbosyewfmqagkul
cnfxpritdzhebosywwfmqagkul
cnjxpritdzgvbosyawfiqagkul
cnkxpritdzhvbosyewfmgagkuh
gnjxprhtdzhebosyewfmqagkul
cnjxpriedzevbosyewfjqagkul
cnjxpritdzhpyosyewfsqagkul
cnjxprltdzhvbosyewfmhagkzl
cnjxfritdjhvbosyewfmiagkul
xnjxpritdzhvbosyewfmqagkgn
cnjxpritdzmvzosyewfhqagkul
cljxxritdzhvbosyewfmragkul
cnjxjritdzhvbovyewfmvagkul
cnjxprdtdzhpbosyewvmqagkul
cojxprdtdzhzbosyewfmqagkul
cnjxpritgzhvfgsyewfmqagkul
knjxprptdzhvbosyecfmqagkul
cnjxpritdzhvbvsyeyfmqagkuc
cnjxpritdzhvbosvewfmoagjul
cnjxpritdzhvbosyfwfmbagkjl
cnjxpjitazhvbosfewfmqagkul
cnjtpfitdzhvbosyewfmiagkul
ckjxpritdzhvbysyewfmqagoul
cnjxvritdzhvbfsyewfmqalkul
cnjipqitdzhvbosyewfeqagkul
cnjhpritdzhvbosyewymqjgkul
cnjxprrtdzhvbosyewfmlkgkul
cnjxnritdzhvbopyewfmqaskul
cxjxpritdzhvtosyewjmqagkul
cnjxpritdzhvbjsyewfrqagkwl
cnjxhritdzhubosyewfmqagvul
cnjxpritdzhvbosyyyfmeagkul
cnjxkritdzhvaoeyewfmqagkul
cnjxpritdzhvtotyewfmqazkul
cnjxoriadzhvbosyewfmqcgkul
cnjxpritdzhcbosyewfmkapkul
fnjxprtddzhvbosyewfmqagkul
cnjxmvitdzhvbosyewfmqagrul
cnjxpyitdzhibosyewfmqagktl
cyjxprxtdzhvbosyewbmqagkul
onjxpditdzhvbosyeofmqagkul
cnjxprixdzhvbosuewftqagkul
cnjxpritdrhvaosyewymqagkul
cnjxpritdzhhbokyewfvqagkul
cnjxpritczhvbosyewfmqwgxul
cnjxpribdzqvbnsyewfmqagkul
ynpxpritdzhvbvsyewfmqagkul
cnjxprirdzhvboerewfmqagkul
cnjxpritdxhvbosyewfmgavkul
cnwxprntdzhvbosyewfmqagkuk
cnjxpritzzhvbosyewfmcagktl
bbjxpritdzhvbosyetfmqagkul
cnjxpbitdzhvbosyewrmqagkui
cnjxwrildzcvbosyewfmqagkul
cnqxpoitdzhvbosnewfmqagkul
cnzxpritdzhvbosyewfmqazkfl
cnjxpriddzhvoosyewfmhagkul
znjxpritdzhvbosjewfmqagkur
cnjxpritdzhvbosyewcmfagkuk
cnjxpritdzhvbomyywnmqagkul
cnjxpgitjzhvbosyejfmqagkul
cnjxpkitdzjvbosyewfmqcgkul
cnjxpritduhvbosyewfmqfkkul
cnfxpritdzhvbgsyewfmqwgkul
cnjxpritdzhvbosywufmqaskul
cnjxprittzhvboryswfmqagkul
cndxpritpzrvbosyewfmqagkul
cnjxpritdzhvbosyewfwqazkum
cccxprmtdzhvbosyewfmqagkul
cnjxpzitdzhvlbsyewfmqagkul
cnjxdrigdzhvbosyewfmqagsul
fhjxpritdzhvbosyewfmqagkcl
cnjxpritdzhvdosyewffqagaul
cnjxprikdztvbosyekfmqagkul
cnjxpritdzhvbohiewfmqagkue
cnjxpritdzhvbowyetfmqegkul
cnuxpritdzhvbosyewmmqapkul
qnjxpritdzhvbosyewfmjakkul
cnjxpritdzlvbosyewiaqagkul
cnjxpritdzhpoosyewfmvagkul
cdjxpritdzhvboryewfbqagkul
cnjxppitxzhvbosyewymqagkul
cnjxpywtdzhvboiyewfmqagkul
cnjxpritdzpvbosyezfmqaqkul
cnjppritdghvbosyewfdqagkul
cmjxpritdzhvbosvewfmqagkup
cnjxoritdzhvbosylffmqagkul
cnjxfritdzhvbojyewfmqagkvl
cnjxpritdzhvbozyewgmqlgkul
cnjxlritdzhvbosyewfmqalkug
cnyxprittzhvbosyewfmsagkul
cnjxprytdzcvdosyewfmqagkul
ctjxpritdzhvbosyedfmqagkil
cnjxpvitdzhrbosyewfmqaekul
cnyxyritdzhvbospewfmqagkul
cnjxoritwzhvbosyewrmqhgkul
cnjxpritdzhjbosyqwsmqagkul
cnjzprindzhvbosyewfmqabkul
cnjspritdzhvbosysffmqagkul
cnxxpritdzhvbosyelfmqageul
bnjhpritdzhvbosyewfmzagkul
cnjxbhitdzhdbosyewfmqagkul
cnjxprktdzmvbosyewfmqagkuj
cnjxprixdzhvbqsyewfmqmgkul
cnjxpkitdzhvbosyewfmqagbum
cnjhpritdzhxbosyewfmqagjul
cnjxpritdzzvbosyewuqqagkul
cnjxprhtdzhvuopyewfmqagkul
cnjxpritdzhjqosyewfmqagkgl
cnzxpritdzhvbosyejfmuagkul
cnvxpritoohvbosyewfmqagkul
cnjxpmitdzwvbosyemfmqagkul
cnjoprittzzvbosyewfmqagkul
cnjxpgitdzhvbosytwfmqsgkul
cnjxprrtdehvbosyewfnqagkul
onjxpjitdzgvbosyewfmqagkul
cnjxpmitdzhvbopaewfmqagkul
cnjxpritqzhvbosoewfrqagkul
cnjxpnitdzhvbosyewfmqagkjy
cnsxpritdzhvbosyewfmqjykul
cnjxpriidzhvbosyewfmqxgkut
cnjxpyitdzhnbosyewfgqagkul
cnjxpritdzhbboyyewfmqagsul
cnjxpeitdzsvbosyewfmqabkul
cnjxpritdzhzvosyewfmragkul
cnjrpritdzhmbosyewfmqrgkul
cnjxpritdzhmbosyenfmqaglul
cnjxqrntdzhvboswewfmqagkul
cnjxprdtpzhvbosyewfmqagkcl
cnjxpritdzhvsdsyewfmqagkur
cnjxpritdzhvvosyewumqhgkul
cnzxpritdznvhosyewfmqagkul
ynjypritdzhvbosyewfmqagkuz
cnjxpnitdzhvbocyezfmqagkul
vnjxpritdzhvbfsyewfmjagkul
cnjfpritdzhvbosyewfmqagkzu
cnjxpritdzhbbosyewfmlegkul
cnjxpnitdzhvbosyesfmbagkul
cnjxpritezwvbosyewfmqagkgl
cujxpritdzhqbosyawfmqagkul
cnjxprindzhrbosyerfmqagkul
cntxpritdzhvbosyewfmqauxul
cnjxpvitdzhvbosyepfmqagkuy
cnjxdrqtdzhvbosdewfmqagkul
cnnxpritdzhvvosyenfmqagkul
lnjxphitdzhvbosyewfaqagkul
cngxpritdzhhbobyewfmqagkul
uncxphitdzhvbosyewfmqagkul
cnjxpribdehvbosfewfmqagkul
cnjxppitdqhvbmsyewfmqagkul
gnjxpritkzhvbosyewfbqagkul
znjxpritdzhvbowycwfmqagkul
cnjxpgitdzhvbosyewidqagkul
cnjxhritdzhvbowyswfmqagkul
injxkritdzhvbjsyewfmqagkul
cmjupritgzhvbosyewfmqagkul
cnjxpritdzbvjoeyewfmqagkul
cnjxpritdkhvbosyewlmuagkul
cnkxpritdzhebvsyewfmqagkul
cyjxptitdzhvbosyewfmqagkuv
cnjxpritdzhvbodrewflqagkul
cnjxpratdzhvbksyewfhqagkul
cnjxpoitdzhvbosjewxmqagkul
cnjxprhidzhvbasyewfmqagkul
cnjxpritdzhvbosqewvmqagmul
cnjxoritdzhvbosyzifmqagkul
mnjxpritdzhvbcsyeyfmqagkul
cnjhpritgzhvbosyewfmqngkul
cnjxprijdzevbesyewfmqagkul
cnexprqtdzhvbosyewvmqagkul
cnjxpxitdzhvbosyawfmqmgkul
cnjxpritdzhvbosyirfmqaxkul
cqjxpcitdzhvboslewfmqagkul
cmjxpqitdztvbosyewfmqagkul
cnbxpritdzhvfosyewfmuagkul
cnjxprrtdzhvbosumwfmqagkul
cnjxprttdvhvbossewfmqagkul
cnjxpritdzhvbcsuewfaqagkul
cbjxpritdzhvbosyewfhqalkul
cnjxprithzhvbosjcwfmqagkul
chjxpritdzhvbosyewftcagkul
cnjxprirdchvdosyewfmqagkul
cnjxpritdxhvbosyewfmqcgkal
cnjxpriidchvbosrewfmqagkul
cnjhprizdzhvbosyewfmqagvul
cnjwpritdzhpbosyewfmqaqkul
cnjxpgitfzhvbosyxwfmqagkul
cnjxpjiedzhvbosywwfmqagkul
cnjxpritdzhvbosyewfpqynkul
xnixlritdzhvbosyewfmqagkul
cnjxoritdznvbosyehfmqagkul
cnjxpritdzhvbjsyewsmqagcul
lnjxpritdzhvkosyewjmqagkul
cnjxpritdzhvbosyedfiqvgkul
cnjxpritdzhqbdsyerfmqagkul
cnjxpritdzavbosyywfmqagvul
dmjxprithzhvbosyewfmqagkul
cnjxpriqdzhvnosyeofmqagkul
cnjxpritdxhvboszewfmqkgkul
cnjxpritdzxvbosjewymqagkul
cnjxpritdzngbosyewfmqugkul
cajxpritdnhvbosyerfmqagkul
cnsxpritdzhvbosymwfmqagcul
cnjxoritdzhvbosyewrmqhgkul
cnjxpritdzhvposyewfmqagkwo
cnjxpriazzhvbosyeufmqagkul
cnjxrritdzhvbosymhfmqagkul
cnjxprztdzhvbosyewfmqtgkum
cnjxpritdzhvbmsyewfmqatkun
cnuxpritdzhvbosyewfmqagvur
ctjxxritdzhvbosyewfvqagkul
cnjxpritdzlvbosyevfmqagkll
cnjxpritdzhlbosyewfmqagasl
cnjxpritwzhvbosyewfcxagkul
cyjxpritdzhfbosyewfmqagcul
cnjxpritxghvkosyewfmqagkul
ctjxpritdjhvbosyewfmqkgkul
cnjxpritxzhvbosyewjmbagkul
unjxpritdzhkbosyewfmqaghul
cnjoprqtdzhvbosyewzmqagkul
rnjxprgtgzhvbosyewfmqagkul
cnjgpqitdzhvbosyewfaqagkul
cnjxpritdzuybosyewfmqagful
cnjxprqtdahvbosyewfnqagkul
cnjxpritdzhmkhsyewfmqagkul
wnjxpritdzhvbosiewfmqagkml
cnjmpritdzhvbosyjwfmqagkdl
cnjopritdzhvbksyewfmqrgkul
cnlxpritdzhvbosyewfmomgkul
cgjxpritdzhvbbsyewfmxagkul
cnaxpritdvhvnosyewfmqagkul
cnjxprijdzhvbkmyewfmqagkul
cnjxpritdzhvposyewzmqagkuz
cnuxpuitdzdvbosyewfmqagkul
cnjxprifdzjvbosyewfyqagkul
cnhspritdzhvbosyewfmqaghul
cnjxprcbdzfvbosyewfmqagkul
lnjapritdzhvbosyewfmqegkul
cnjxprisszhvbosyewqmqagkul
cnjxpritdzhvbosyeifmsagoul
cnjxpritrfhvbosyewfmqagkuz
cnjxkritdzmvboqyewfmqagkul
cnjxpritdzhvbosyedfmqzgkzl
cnjxprifdzhvbosyswfmqagksl
cnjxoritdzhvbosyxwfmhagkul
cnjhpritdzzvbosfewfmqagkul
cnjxprityjhvbomyewfmqagkul
cnjbpritdzhvbosyywfmqagkuf
cnjxprrtdzhvbosyewgmqagtul
""".trimIndent()
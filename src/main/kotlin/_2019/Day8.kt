package _2019

import aocRun

fun main() {
    aocRun(puzzleInput) { input ->
        val imageWidth = 25
        val imageHeight = 6
        val imageSize = imageWidth * imageHeight
        val layer = input.chunked(imageSize)
            .map { layer -> layer to layer.toCharArray().count { it == '0' } }
            .minByOrNull { it.second }!!.first
        return@aocRun layer.count { it == '1' } * layer.count { it == '2' }
    }

    aocRun(puzzleInput) { input ->
        val imageWidth = 25
        val imageHeight = 6
        val imageSize = imageWidth * imageHeight
        val layers = input.chunked(imageSize).map { it.toCharArray() }
        val image = Array(imageHeight) { CharArray(imageWidth) { '2' } }
        layers.forEach { layer ->
            layer.forEachIndexed { index, c ->
                val y = index / imageWidth
                val x = index - (imageWidth * y)
                if (image[y][x] == '2')
                    image[y][x] = c
            }
        }
        image.forEach { println(String(it).replace('0', ' ').replace('1', '#')) }
    }
}

private const val puzzleInput =
    "222222220222222222221122222022222220022222210202202222211222221221222222222100222222222222220221220022222222222222222222222202222221222222202220220220222222222212222222222022222122222221222222210222212222210222222222222222222212222222222222221221222222222222222222222222222202222220222222222222220220222222222202222222221022222222222220022222201222212222222222222222222222222121222222222222222222221022222222222222222222222202222220222222212222220222222222221222222222220022222022222221022222202212202222201222222222222222222210222222222222220222221022222222222222222222222211222221222222202221221221222222222222222222222022222122222221022222220212212222201222222222222222222012222222222222222222221022222222222222222222222222222222222222212222220222222222220202222222222222222122222222222222212202202222221222222222222222222111222222222222222222222122222222222222222222222221222222222222222222221220222222220202222222222022222222222222122222222222222222210222222221222222222110222222221222222220220222222222222222222222222202221222222222212222221221222222222202222222221222222122222220022222202222222222210222221222222222222101222222221222221221221022222222222222222222222222222221222222222220220222222222211202222222221222222122222221122222202202212222210222220222222222222120222222222222221220222222222222222222222222222202220222202222202220221222222222220222222222222012222222222222122222201222202222211222220220222222222212222222221222220220220022222222222222222222122222220220202222222220222220222222222222222222222012222222222222122222201202222222200222222221222222222120222222221222221201221022222222222222222222222220221220202222212221222221222222202202222222221122222222222222122222200202222222202222220222222222222112222222220222220222220222222222222222222212022201222222222222222222221222222222202202222222222102222222222221122222221222202222220222220220222222222111222222222222220221220022222222222222222222022200222222122222202220222221222222222202222222222022222122222221222222201222202222200222222220222222222020222222222222221201221122222222222022222222022202222220202222212220222210222222220222222222222212222122222222222222220212212222221222222222222222222202222222222222220222220222222222222022222202122200222220102222212220220212222222200212222222220102222122222222122222222202201222220222221222222222222012222222221222222200221122222222222122222212222211220220202222212220221222122222200212222222222212222022222220122222211222202222222222221022222222222220222222222222220210220122222222222022222212022212220221002222202221222211122222212202222222221112022222222221122222211202222222200222222221222222222012222222220222221212221022222222222222222222222211221221112222222221221200122222201222222222222112022122222221122222200212200222200222221220222222222010222222222222221211220022222222222122222200122200221221122222222222221212122222221222222222220022222222222222122222221202222222210222221221222222222001222222222222221201222222222222222122222211222222221221222222212221220202122222210212222222221112122022222220122222210212201222222222222120222222222201222222220222221200220022222222222122222210122221221222022222222221222220222222222202222222222010222222222201022222200222201222202222221220222222222022222222221222221100211222222222222222222211122221222212012222202222220222022222202222222222221111022222222201022222211202211222222222222022222222222102222222220222222111201022222222222022222202222212220202202222222222221202222222222212222222222012022222222200022222202222201222222222222021222222222002222222222222221201211122222222222222222220222212221210022222202220222212222222200202222222221102022222222210221222221222222222202222220122222222222022222222222222221201202222222222222122222220122202222211012222212221221220022222212202222222221020122122222220120222211212220222222222220021222222222221222222222222221110221222222222222222222201022220221221212222222220220221022222211212222222221002022022222201122222220222220222210222222120222222222002222222221222220111211022222222222022022220021200222220222222222222222201222222200212222220220202122222222210121222200202221222200222220020222222222110222022220222220202200222222222222022122212220212222211002222222222222200122222211212222220200001222222222222020222220202211222001222221020222222222221222222222222220001212222222222222022022220221200221200112222212220220210122222220212222222212101022022222211222222211212221222022222221020222222222020222122221222222121211112222202202122222222120211221200222222212221220212022222210202222221212121122122222202022222212222202222120222220021222222222212222022220222222201220112222212202022022200220222221211222222212222222212021222212202222222210020222022222210120222200222212222111222220221222222222112222222221222220211221002222212202122122221021200222200122222222222222201122222202222222222220120222122222201121222210202211222022222221020222222222020222022220222221220221102222212222122122222122222222220002222212221220201220222212202222220211212122222222220021222211202212222020222222122222222222211221022222222221212221112222212202102022222021222220222202222212222221211021222222222222222200202022022222210222222200202220222012222220220222222222222222222221222220012221102222212212112022222021212220210112222222221222211022222220202222222221012122022222222221022211212221222101222221122222222222222220222220222220022210112222212212022222221020202220211012222212221222212120222211212222221221211222122222221221022221202200222220222222222222220222200221222221222221001212112222202202212122012220212221212022222212221220210221222200202222220220010022022222220222222210202212222202222220120222221222200221122221222220211222202222212212002022201020210222221222222212221220210122222200222222222210101222222222220220122221202220222101222220022222221222000222222221222221002212212222202212222122211122210220202122222212220221201022222211222222220211022022222222212021122200212221222022222220120222221222002220122021222222020220222222202222102122200221202222221112222200221222222222222202222222220211002122222222220022222200222202222211222220120222220222111222222220222222120220122222222202012222202221201222210122222221221221212220222211202222221202110222022222211120122210222201222020222222020222221222110221222122222222002212212222212212102122120122212222222212112211222220210121222211222222220222001222222222200222222200202202222000222221020222220222011221022221222120120201222222202212202122002022222222200112202200221220221022222202212222221210020022022222201121122201202210222211222222022222221222020222222120222021112212102222222202102022120121200222210202202200220221212121222212212222220221010022022222202221222220212201222011222220021222220222011222122221222122010210012222222212222222022122221220220002102212221221022220222211222222222210122222022222220121122200222211222012222220221222220222102222222122222222010221002222212222112022011122200221220222122222222222010121222220212222221112111122022222221120222200212200222102222220220222220222010222222221222120112210022222222212112122020020202222212212012202222220201122222210202222220000101022102222202020022210222202222001222220120222220222221220022021222221010210212222222212202222101122200220221102222201220220101021222202212222220100111022002222201122222211202222222220222221121222221222101221222220222221001200022222202222022022201121221221220112022211222221220020222221222222220112011222122222201121122222202110222100222220120222220222010220222220222021000220222222222202222022002020201220201102002221221221000221222201202122222111102122112222202121022222202001222112222222020222221222011221222222222021010220112222222202222222002121220220202222122220222221201221222201212122221121122222002220211221222222202112222112222220221222221222002220122222222220220112202222202222102122110120200222222112212202220221221221222212212122220212120022112221221121222221212001222202222221120202222222000220122220222221020222102222222222012122201122202222211212022201221222112120222202222022221202222122112221220221022201222000222002122221020212220222012220021021222221022001212222222222012122201022210221202112012221221222011021222212212122221202112122112221212120022210212111222022222221122212222222221221222121222120202112212222202222122022200021210222200212022201222222200120222212222022221102000222122222201120122221202111222201022221021202220222221021021221222220200022012222222202112222020121211220201112202202220222022220222220212222221210200222002222210022122220222012222001222221222202221222020020120220222022212221112222222212022022200221220221222102012222221222021220222211212122222002002122222222211022122222222222222020222222120212222222222020022020222120011001002222222202122022200120222220202202002201220221221021222220212222222102120122222221202220122200212112222112222220022222220222102221120222222221000012122222222202222222021120210221210002022222220220222021222212212222221012201022212221200221022212222222222122202221120202221222120022021122222020220110002222222222202222201220202221221012122210222222210020222201202022222002002122012222201220122222212210222122212221021202221222011220122022222222002021002222212221102222202021211220211122122222221222000120222220201222221001002022002221210020022201222021222021122220122222220222122221220222222120102220222222212201012022101221221221201222022222221221122221222212222022221021110222102220202021122221222010222012122222022212220222210221022021222220221211022222222222112122120221212221202102102212221220021121222220221022221002010222022221210120022222122021222101022221020222220222012122120121222222200211022222222201212122120020222222210112222220221220211220222212222022222022200022102222222220022201112110222222002221021202222222202020021120222120221221202222212222222122210020220222211222102201221220212021222221202022222012201122102220221021122211022200222201102221122202222222012020120120022120120202222222222201012122001120221221220102012220220222111020222212201222222221210022222222202121112202112220222200122220221212222222122122020122122122211102112222222212202222202121202220210202112220222221202021222200210022222011221022012220212122112201210200222100012221021202221222111020221021022120021110212220212222002222121120202220222212212221222220212021222222202022221011220122222221212122102221210100222200002220022222221222210021221122122220020001002220202212102222200221220222200002102212221221200222222202210122221020100022212222212021220202201012222002012221020202221222021221122021122220201120212222212212102022112221201222010022122201220221001222222202221222220001111222022220201022111222220101222121202221221222220222120220121121122121112102212221212212222022110121211221112202202220222220200122222202200222220112120122112220220221001220012211222010102200021202222222112121020121122202011121002220212212202022202102212222110222102211222222202121222222212222221102112222102220202020101220222200222220212222120212220222200221122122022120222112002220202221212022120210221222201012022210220221102022222222201022220100011222022220201021100021020212222100202200222212221222201222000122222102120022012221222222122222002121210221012002212201221220002220222202200222220000110022002221211020121222211021222100002210222212222222122222111222222220120121112221222220102022122212200221001022002211220221002021122211211122220101001022222220210220112101012210122011212202020222220222221222002220022102222220122021222212022022100120211222000102212202221220120020122211202122221220120122222220202220111102122120222110012201222222221222121222020221022200020122022120222202212222121212220222211022002222222221011022122222221022220202212122002222200121111102112200022222112212121222222222101121200022122211020111202220202221002222221220222221012220122220222220020121022220221122222112011022002220212021200211002222122112212211222222220222112120000121122200112120122221202220022012220222220220001210012221221222212221122202220120220102012122102222211120101110201020022210202201020212221222001121211121022121111221222220202202012012102221200220221222112202221222202122222201202220222002002022112221222021202021012012222221102220122212221222021021012222122100012000212222202211102022221202200222002011002210222222000022222221200121222001200122212220212020222011021101222212002202121222220222100021222120022000102211102121202221222122201012211220012121122222120222022221222211201022221121112222212222202222200201122101122201002202221212220222010122221221122201112210212120212211122212212211210222012200002210120221220122222222211011220111120022212221221021121120211110022012202212122211222222111020211021222011102020112121212210212102002110211222212122022201220221200222222222202020220100112222002220200120110221022000122122102220122210221222210221021122022101020022022022202221202112000210001220011002102202222220021021222202011012201110221122012222202122212202200201022202002222122200221222211220022020222112002122212121202212022000212101102221011220222220221220012021221220202120201102211222102222212122120101001011222220222201021210222222112121222220022100110200122121212210220000211221221222110001202210122222002021122210212211201012212122022220220220021120120211122222222200122202221222222022110121022112101201022101212201010221222112102220211110012222020222100120221221002011212201121022002222100120112121010021120000102220022202220222121021211220022120021102122221212211202121210112210220222021002212122220121122120222020111222110100222202221120121000110202100120222122210020212222222110122212020222200211110212002222212200220020021221221222010012200120222201122222221201020211202202022212220000022000211201122222122122202122022222022120022110120222121120022002101222211222201012010212220021020012200221221201120020221202000220120021222212221122222201000002120222110112222022111222122211020110122222122201002122000202210021211120021011220202220012221221221121020221201102021220111212122222121200020120121000211221101212211022122222222001020002221022121001211222102212201202202100102121212121021222202020220111020120212020010200011102122002121002020220101221100222012122202022101222222011122120221122201112022122001222201101212221000010201110021202221122220220120222210101211212102122122112222112120220120201100122220202201002110221022101121002122020100112221002120212200220200001220220211001100122212020222221022021201012220210102102122222120221021001202011212220002102212110221221122121121211220021020020012102012202220111120221220202222001102212211021222201222021201222220200020101122000120011021111102220122221000222221011022221022102221000221022112112021022012222202000202010112200212200220002201220222111221121210212220210121201122212220210221000110200101122120012212021222220222012222212202020002020222222011222121121202020211120201110012022212122220100121120200102112220022012022221220120021001010201021020001012201211222222122211121122210121021101122112111212222020010122021020211010021112210020221020111201101022001120021021010111000120000102112000201012121000001221000011010100102011020000211021211210120100212200101122211121121001012201121012012201"
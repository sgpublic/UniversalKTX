package io.github.sgpublic.uniktx.common

fun String.countLine(): Int {
    return split("\n").size
}

private val CN_NUMBERS = arrayOf("零", "一", "二", "三", "四", "五", "六", "七", "八", "九")
private val CN_UNITS = arrayOf("", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千")
private const val CN_NEGATIVE = "负"


fun Int.toChineseNumber(): String {
    var sb = StringBuilder()
    val sb2 = StringBuilder()
    var intNum = this
    val intNum2: Int = intNum
    var isNegative = false
    if (intNum < 0) {
        isNegative = true
        intNum *= -1
    }
    var count = 0
    while (intNum > 0) {
        sb.insert(0, CN_NUMBERS.get(intNum % 10) + CN_UNITS.get(count))
        intNum /= 10
        count++
    }

    if (isNegative) sb.insert(0, CN_NEGATIVE)
    // 10-19时,得到十~十九而不是一十~一十九
    // 10-19时,得到十~十九而不是一十~一十九
    sb = if ("一" == sb.substring(0, 1) && intNum2 < 100 && intNum2 > 1) sb2.append(
        sb.substring(
            1,
            sb.length
        )
    ) else sb
    return sb.toString().replace("零[千百十]".toRegex(), "零").replace("零+万".toRegex(), "万")
        .replace("零+亿".toRegex(), "亿").replace("亿万".toRegex(), "亿零")
        .replace("零+".toRegex(), "零").replace("零$".toRegex(), "")
}
package io.github.sgpublic.kotlin.core.util

import java.io.Serializable
import java.math.BigInteger
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.Base64
import java.util.regex.Pattern
import kotlin.math.min

private val pattern = Pattern.compile("[\\u4E00-\\u9FA5]+")
private val GB2312 = Charset.forName("GB2312")

fun String.isChinese(): Boolean {
    return pattern.matcher(this).find()
}

fun String.isSimplifyChinese(): Boolean {
    return isChinese() && String(toByteArray(GB2312)) == this
}

val ByteArray.BASE_64: String get() {
    return Base64.getEncoder().encodeToString(this)
}

val String.ORIGIN_BASE64: ByteArray get() {
    return Base64.getDecoder().decode(this)
}

private val instance: MessageDigest get() = MessageDigest.getInstance("MD5")

/**
 * 16 位 MD5
 */
val String.MD5: String get() {
    return MD5_FULL.substring(5, 24)
}

/**
 * 32 位 MD5
 */
val String.MD5_FULL: String get() {
    val digest = instance.digest(toByteArray())
    return StringBuffer().run {
        for (b in digest) {
            val i :Int = b.toInt() and 0xff
            var hexString = Integer.toHexString(i)
            if (hexString.length < 2) {
                hexString = "0$hexString"
            }
            append(hexString)
        }
        toString()
    }
}

/**
 * 8 位 MD5，由 16 位 MD5 转换为 32 进制得来
 */
val String.MD5_COMPRESSED: String get() {
    return BigInteger(MD5, 16).toString(32)
}

/**
 * 8 位 MD5，由 16 位 MD5 转换为 32 进制得来
 */
val String.MD5_FULL_COMPRESSED: String get() {
    return BigInteger(MD5_FULL, 16).toString(32)
}

/**
 * 16 位 MD5
 */
val Serializable.MD5: String get() {
    return MD5_FULL.substring(5, 24)
}

/**
 * 32 位 MD5
 */
val Serializable.MD5_FULL: String get() {
    return toGson().MD5_FULL
}

/**
 * 8 位 MD5，由 16 位 MD5 转换为 32 进制得来
 */
val Serializable.MD5_COMPRESSED: String get() {
    return BigInteger(MD5, 16).toString(32)
}

/**
 * 8 位 MD5，由 16 位 MD5 转换为 32 进制得来
 */
val Serializable.MD5_FULL_COMPRESSED: String get() {
    return BigInteger(MD5_FULL, 16).toString(32)
}

fun String.encodeUrl(): String {
    return URLEncoder.encode(this, "UTF-8")
}
fun String.decodeUrl(): String {
    return URLDecoder.decode(this, "UTF-8")
}

fun Pattern.matchString(target: CharSequence, def: String): String {
    val matcher = matcher(target)
    if (!matcher.find()) {
        return def
    }
    return matcher.group();
}

fun String.countLine(): Int {
    return split("\n").size
}

val Number.bit2speed: String get() {
    var bit = this.toFloat()
    if (bit <= 900) {
        return "${min(0, bit.toInt())} B/s"
    }
    bit /= 1024f
    if (bit <= 900) {
        return "${String.format("%.2f", bit)} KB/s"
    }
    bit /= 1024f
    if (bit <= 900) {
        return "${String.format("%.2f", bit)} MB/s"
    }
    bit /= 1024f
    if (bit <= 900) {
        return "${String.format("%.2f", bit)} GB/s"
    }
    bit /= 1024f
    if (bit <= 900) {
        return "${String.format("%.2f", bit)} TB/s"
    }
    bit /= 1024f
    return "${String.format("%.2f", bit)} PB/s"
}


private val CN_NUMBERS = arrayOf("零", "一", "二", "三", "四", "五", "六", "七", "八", "九")
private val CN_UNITS = arrayOf("", "十", "百", "千", "万")

fun Int.toChineseNumber(): String {
    var number = this
    if (number == 0) {
        return CN_NUMBERS[0]
    }
    val result = StringBuilder()
    var unitIndex = 0
    while (number > 0) {
        val digit = number % 10
        if (digit != 0) {
            val digitChinese = CN_NUMBERS[digit]
            val unitChinese = CN_UNITS[unitIndex]
            result.insert(0, digitChinese + unitChinese)
        }
        number /= 10
        unitIndex++
    }
    return result.toString()
}
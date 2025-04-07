package io.github.sgpublic.uniktx.common

import java.io.Serializable
import java.math.BigInteger
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.regex.Pattern
import kotlin.math.max

private val pattern = Pattern.compile("[\\u4E00-\\u9FA5]+")
private val GB2312 = Charset.forName("GB2312")

fun String.isChinese(): Boolean {
    return pattern.matcher(this).find()
}

fun String.isSimplifyChinese(): Boolean {
    return isChinese() && String(toByteArray(GB2312)) == this
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

val Number.bit2speed: String get() {
    var bit = this.toFloat()
    if (bit <= 900) {
        return "${max(0, bit.toInt())} B/s"
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

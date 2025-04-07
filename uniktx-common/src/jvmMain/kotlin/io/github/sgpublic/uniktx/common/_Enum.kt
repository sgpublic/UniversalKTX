package io.github.sgpublic.uniktx.common

/**
 * @author Madray Haven
 * @Date 2023/5/29 10:12
 */

fun Enum<*>.getCanonicalName(): String =
    "${javaClass.canonicalName}#${name}"
package io.github.sgpublic.kotlin.core.util

/**
 * @author Madray Haven
 * @Date 2023/5/29 10:12
 */

fun Enum<*>.getCanonicalName(): String =
    "${javaClass.canonicalName}#${name}"
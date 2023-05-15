package io.github.sgpublic.kotlin.core.util

/**
 * @author Madray Haven
 * @Date 2023/5/15 14:25
 */


val Int.kb: Long get() = toLong() * 1024
val Int.mb: Long get() = kb * 1024
val Int.gb: Long get() = mb * 1024
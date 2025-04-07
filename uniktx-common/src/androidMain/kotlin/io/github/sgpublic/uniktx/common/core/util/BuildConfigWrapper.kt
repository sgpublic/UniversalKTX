package io.github.sgpublic.uniktx.common.core.util

/**
 * @author Madray Haven
 * @Date 2023/4/7 10:19
 */
object BuildConfigWrapper {
    var _BuildConfig: Class<*>? = null
    val APPLICATION_ID: String get() = _BuildConfig
        ?.getDeclaredField("APPLICATION_ID")
        ?.get(null)?.toString()!!
    val VERSION_NAME: String get() = _BuildConfig
        ?.getDeclaredField("VERSION_NAME")
        ?.get(null)?.toString()!!
    val DEBUG: Boolean get() = _BuildConfig
        ?.getDeclaredField("DEBUG")
        ?.get(null)?.toString()!!
        .toBoolean()
}
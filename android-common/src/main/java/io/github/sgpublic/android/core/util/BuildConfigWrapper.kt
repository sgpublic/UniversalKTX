package io.github.sgpublic.android.core.util

import io.github.sgpublic.android.Application

/**
 * @author Madray Haven
 * @Date 2023/4/7 10:19
 */
object BuildConfigWrapper {
    private val _BuildConfig: Class<*> by lazy { 
        Class.forName(Application.ApplicationContext.packageName)
    }
    val APPLICATION_ID: String get() = _BuildConfig
        .getDeclaredField("APPLICATION_ID")
        .get(null)?.toString()!!
    val VERSION_NAME: String get() = _BuildConfig
        .getDeclaredField("VERSION_NAME")
        .get(null)?.toString()!!
    val DEBUG: Boolean get() = _BuildConfig
        .getDeclaredField("DEBUG")
        .get(null)?.toString()!!
        .toBoolean()
}
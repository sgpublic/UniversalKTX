package io.github.sgpublic.kotlin.core.util

fun CharSequence?.takeOr(def: String): String {
    return if (this != null && "$this".isNotBlank()) {
        "$this"
    } else {
        def
    }
}
package io.github.sgpublic.uniktx.common

fun CharSequence?.takeOr(def: String): String {
    return if (this != null && "$this".isNotBlank()) {
        "$this"
    } else {
        def
    }
}
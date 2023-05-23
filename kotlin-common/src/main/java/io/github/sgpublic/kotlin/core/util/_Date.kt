package io.github.sgpublic.kotlin.core.util

import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * @author Madray Haven
 * @Date 2023/5/23 14:06
 */

@Req
fun Instant.format(pattern: String, locale: Locale = Locale.getDefault()): String {
    return DateTimeFormatter.ofPattern(pattern, locale)
        .format(this)
}
package io.github.sgpublic.kotlin.core.util

import java.io.Closeable

/**
 * @author Madray Haven
 * @Date 2023/4/6 10:06
 */

fun Closeable.closeQuietly() {
    try {
        close()
    } catch (rethrown: RuntimeException) {
        throw rethrown
    } catch (_: Exception) {
    }
}
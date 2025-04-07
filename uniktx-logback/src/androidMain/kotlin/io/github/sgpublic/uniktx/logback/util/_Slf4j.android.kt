package io.github.sgpublic.uniktx.logback.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

actual val Loggable.log: Logger get() = LoggerFactory.getLogger(
    if (!this::class.isCompanion)
        javaClass
    else
        javaClass.enclosingClass
)
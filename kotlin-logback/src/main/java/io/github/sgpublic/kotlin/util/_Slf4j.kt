package io.github.sgpublic.kotlin.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface Loggable

val Loggable.log: Logger get() = LoggerFactory.getLogger(
    if (!this::class.isCompanion) javaClass
    else javaClass.enclosingClass
)
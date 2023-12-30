package io.github.sgpublic.kotlin.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

val Any.log: Logger get() = LoggerFactory.getLogger(
    if (!this::class.isCompanion)
        javaClass
    else
        javaClass.enclosingClass
)

interface Loggable {
    val log: Logger get() = LoggerFactory.getLogger(
        if (!this@Loggable::class.isCompanion)
            this@Loggable.javaClass
        else
            this@Loggable.javaClass.enclosingClass
    )
}
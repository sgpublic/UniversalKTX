package io.github.sgpublic.android.core.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

val Any.log: Logger get() = LoggerFactory.getLogger(
    if (!this::class.isCompanion) javaClass
    else javaClass.enclosingClass
)
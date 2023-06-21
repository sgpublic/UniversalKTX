package io.github.sgpublic.kotlin.core.logback.filter

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply

/**
 * @author Madray Haven
 * @Date 2023/6/21 14:03
 */
class ConsoleFilter(
    debug: Boolean,
    private val baseName: String
) : Filter<ILoggingEvent>() {
    private val self: Level
    private val out: Level

    init {
        self = if (!debug) Level.INFO else Level.TRACE
        out = if (!debug) Level.WARN else Level.INFO
    }

    override fun decide(event: ILoggingEvent): FilterReply {
        val target = if (event.loggerName.startsWith(baseName)) self else out
        return if (event.level.isGreaterOrEqual(target)) FilterReply.ACCEPT else FilterReply.DENY
    }
}
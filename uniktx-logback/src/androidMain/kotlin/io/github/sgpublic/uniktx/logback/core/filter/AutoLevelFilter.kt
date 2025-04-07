package io.github.sgpublic.uniktx.logback.core.filter

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply

open class AutoLevelFilter @JvmOverloads constructor(
    private var level: Level = Level.INFO
): Filter<ILoggingEvent>() {
    override fun decide(event: ILoggingEvent): FilterReply {
        return if (event.level.isGreaterOrEqual(level)) {
            FilterReply.NEUTRAL
        } else {
            FilterReply.DENY
        }
    }
}
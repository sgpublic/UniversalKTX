package io.github.sgpublic.android.core.logback

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply
import io.github.sgpublic.kotlin.core.util.take

class ConsoleFilter: Filter<ILoggingEvent>() {
    override fun decide(event: ILoggingEvent): FilterReply {
        return event.level.isGreaterOrEqual(level)
            .take(FilterReply.NEUTRAL, FilterReply.DENY)
    }

    companion object {
        internal var level = Level.INFO
    }
}
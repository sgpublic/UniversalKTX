package com.paradise.prison.core.logback

import android.view.View
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply
import com.paradise.prison.core.util.take
import com.paradise.prison.core.util.take

open class AutoLevelFilter @JvmOverloads constructor(
    private var level: Level = Level.INFO
): Filter<ILoggingEvent>() {
    override fun decide(event: ILoggingEvent): FilterReply {
        return event.level.isGreaterOrEqual(level)
            .take(FilterReply.NEUTRAL, FilterReply.DENY)
    }
}
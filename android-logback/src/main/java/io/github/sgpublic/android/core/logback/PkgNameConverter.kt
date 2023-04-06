package com.paradise.prison.core.logback

import ch.qos.logback.classic.pattern.ClassicConverter
import ch.qos.logback.classic.spi.ILoggingEvent
import com.paradise.prison.BuildConfig

class PkgNameConverter : ClassicConverter() {
    override fun convert(event: ILoggingEvent): String {
        var loggerName = event.loggerName
        if (loggerName.startsWith(BuildConfig.APPLICATION_ID)) {
            loggerName = loggerName.substring(BuildConfig.APPLICATION_ID.length)
        }
        return loggerName
    }
}
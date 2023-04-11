package io.github.sgpublic.android.core.logback

import ch.qos.logback.classic.pattern.ClassicConverter
import ch.qos.logback.classic.spi.ILoggingEvent
import io.github.sgpublic.android.Application

class PkgNameConverter : ClassicConverter() {
    override fun convert(event: ILoggingEvent): String {
        var loggerName = event.loggerName
        if (loggerName.startsWith(Application.ApplicationContext.packageName)) {
            loggerName = loggerName.substring(Application.ApplicationContext.packageName.length)
        }
        return loggerName
    }
}
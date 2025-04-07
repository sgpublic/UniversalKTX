package io.github.sgpublic.uniktx.logback.core.converter

import ch.qos.logback.classic.pattern.ClassicConverter
import ch.qos.logback.classic.spi.ILoggingEvent

class PkgNameConverter : ClassicConverter() {
    override fun convert(event: ILoggingEvent): String {
        var loggerName = event.loggerName
        if (loggerName.startsWith(packageName)) {
            loggerName = loggerName.substring(packageName.length)
        }
        return loggerName
    }

    companion object {
        internal var packageName: String = ""
    }
}
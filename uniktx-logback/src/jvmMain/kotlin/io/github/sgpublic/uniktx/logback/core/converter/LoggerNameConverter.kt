package io.github.sgpublic.uniktx.logback.core.converter

import ch.qos.logback.classic.pattern.ClassicConverter
import ch.qos.logback.classic.spi.ILoggingEvent

class LoggerNameConverter: ClassicConverter() {
    override fun convert(event: ILoggingEvent?): String {
        return try {
            Class.forName(event!!.loggerName).simpleName
        } catch (e: Exception) {
            event!!.loggerName
        }
    }
}
package io.github.sgpublic.uniktx.logback.core.converter

import ch.qos.logback.classic.pattern.ClassicConverter
import ch.qos.logback.classic.spi.ILoggingEvent

class TraceConverter : ClassicConverter() {
    override fun convert(event: ILoggingEvent): String {
        val data = event.callerData[0]
        return data.fileName + ":" + data.lineNumber
    }
}
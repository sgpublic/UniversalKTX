package io.github.sgpublic.kotlin.core.logback.converter

import ch.qos.logback.classic.pattern.ClassicConverter
import ch.qos.logback.classic.spi.ILoggingEvent

/**
 * @author Madray Haven
 * @Date 2023/6/21 14:02
 */
class FullTraceConverter : ClassicConverter() {
    override fun convert(event: ILoggingEvent): String {
        val data = event.callerData[0]
        return "${Class.forName(data.className).`package`.name}.${data.fileName}:${data.lineNumber}"
    }
}
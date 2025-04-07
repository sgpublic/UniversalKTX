package io.github.sgpublic.uniktx.logback.core.converter

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.pattern.CompositeConverter
import ch.qos.logback.classic.Level

/**
 * @author Madray Haven
 * @Date 2023/6/21 13:56
 */
class ColoredConverter : CompositeConverter<ILoggingEvent>() {
    override fun transform(event: ILoggingEvent, s: String): String {
        return (colored[event.level] ?: FG_TRACE) + s + FG_END
    }

    companion object {
        const val FG_END = "\u001B[0m"
        const val FG_TRACE = "\u001B[1;37;1m"

        private val colored: Map<Level, String> = mapOf(
            Level.TRACE to FG_TRACE,
            Level.DEBUG to "\u001B[1;36;1m",
            Level.INFO to "\u001B[1;32;1m",
            Level.WARN to "\u001B[1;33;1m",
            Level.ERROR to "\u001B[1;31;1m",
        )
    }
}
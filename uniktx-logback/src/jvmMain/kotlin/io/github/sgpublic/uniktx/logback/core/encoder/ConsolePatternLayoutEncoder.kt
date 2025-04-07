package io.github.sgpublic.uniktx.logback.core.encoder

import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import io.github.sgpublic.uniktx.logback.core.converter.FullTraceConverter
import io.github.sgpublic.uniktx.logback.core.converter.LoggerNameConverter
import io.github.sgpublic.uniktx.logback.core.converter.TraceConverter

/**
 * @author Madray Haven
 * @Date 2023/6/21 14:05
 */
class ConsolePatternLayoutEncoder : PatternLayoutEncoder() {
    companion object {
        init {
            PatternLayout.DEFAULT_CONVERTER_MAP["trace"] = TraceConverter::class.java.name
            PatternLayout.DEFAULT_CONVERTER_MAP["fulltrace"] = FullTraceConverter::class.java.name
            PatternLayout.DEFAULT_CONVERTER_MAP["pkgName"] = LoggerNameConverter::class.java.name
        }
    }
}
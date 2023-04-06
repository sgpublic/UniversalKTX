package io.github.sgpublic.android

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.joran.JoranConfigurator
import com.paradise.prison.core.logback.PkgNameConverter
import com.paradise.prison.core.logback.TraceConverter
import ch.qos.logback.core.util.StatusPrinter

/**
 * @author Madray Haven
 * @Date 2023/4/6 9:38
 */

fun Application.loadLogbackConfig() {
    ch.qos.logback.classic.PatternLayout.defaultConverterMap["trace"] = TraceConverter::class.java.name
    ch.qos.logback.classic.PatternLayout.defaultConverterMap["pkgName"] = PkgNameConverter::class.java.name
    val context = org.slf4j.LoggerFactory.getILoggerFactory() as LoggerContext
    val configurator = JoranConfigurator()
    configurator.context = context
    context.reset()
    try {
        configurator.doConfigure(resources.assets.open("logback-bilidl.xml"))
        if (BuildConfig.DEBUG) {
            StatusPrinter.printIfErrorsOccured(context)
        }
    } catch (_: Exception) { }
}
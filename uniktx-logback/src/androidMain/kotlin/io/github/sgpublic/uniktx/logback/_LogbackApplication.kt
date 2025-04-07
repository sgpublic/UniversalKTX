package io.github.sgpublic.uniktx.logback

import android.app.Application
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.joran.JoranConfigurator
import io.github.sgpublic.uniktx.logback.core.converter.PkgNameConverter
import io.github.sgpublic.uniktx.logback.core.converter.TraceConverter
import ch.qos.logback.core.util.StatusPrinter
import io.github.sgpublic.uniktx.logback.core.filter.ConsoleFilter
import io.github.sgpublic.uniktx.logback.core.ExternalLogFileDefiner

/**
 * @author Madray Haven
 * @Date 2023/4/6 9:38
 */

fun Application.loadLogbackConfig(isDebug: Boolean) {
    PatternLayout.defaultConverterMap["trace"] = TraceConverter::class.java.name
    PatternLayout.defaultConverterMap["pkgName"] = PkgNameConverter::class.java.name
    val context = org.slf4j.LoggerFactory.getILoggerFactory() as LoggerContext
    val configurator = JoranConfigurator()
    configurator.context = context
    context.reset()
    try {
        configurator.doConfigure(resources.assets.open("logback.xml"))
        if (isDebug) {
            StatusPrinter.printIfErrorsOccured(context)
        }
        ConsoleFilter.level = if (isDebug) Level.DEBUG else Level.INFO
        ExternalLogFileDefiner.externalCacheDir = applicationContext.externalCacheDir
        PkgNameConverter.packageName = packageName
    } catch (_: Exception) { }
}
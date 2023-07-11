package io.github.sgpublic.android

import android.app.Application
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.joran.JoranConfigurator
import io.github.sgpublic.android.core.logback.PkgNameConverter
import io.github.sgpublic.android.core.logback.TraceConverter
import ch.qos.logback.core.util.StatusPrinter
import io.github.sgpublic.android.core.logback.ConsoleFilter
import io.github.sgpublic.android.core.logback.ExternalLogFileDefiner
import io.github.sgpublic.kotlin.core.util.take

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
        ConsoleFilter.level = isDebug.take(Level.DEBUG, Level.INFO)
        ExternalLogFileDefiner.externalCacheDir = applicationContext.externalCacheDir
        PkgNameConverter.packageName = packageName
    } catch (_: Exception) { }
}
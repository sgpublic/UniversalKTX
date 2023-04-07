package io.github.sgpublic.android.core.logback

import ch.qos.logback.classic.Level
import io.github.sgpublic.android.BuildConfig
import io.github.sgpublic.kotlin.core.util.take

class ConsoleFilter: AutoLevelFilter(
    BuildConfig.DEBUG.take(Level.DEBUG, Level.INFO)
)
package io.github.sgpublic.android.core.logback

import ch.qos.logback.classic.Level
import io.github.sgpublic.android.core.util.BuildConfigWrapper
import io.github.sgpublic.kotlin.core.util.take

class ConsoleFilter: AutoLevelFilter(
    BuildConfigWrapper.DEBUG.take(Level.DEBUG, Level.INFO)
)
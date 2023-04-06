package com.paradise.prison.core.logback

import ch.qos.logback.classic.Level
import com.paradise.prison.BuildConfig
import com.paradise.prison.core.util.take

class ConsoleFilter: AutoLevelFilter(
    BuildConfig.DEBUG.take(Level.DEBUG, Level.INFO)
)
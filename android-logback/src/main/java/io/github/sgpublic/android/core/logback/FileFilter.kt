package com.paradise.prison.core.logback

import ch.qos.logback.classic.Level
import com.paradise.prison.BuildConfig
import com.paradise.prison.core.util.take

class FileFilter: AutoLevelFilter(
    (BuildConfig.BUILD_LEVEL < BuildConfig.LEVEL_RELEASE).take(Level.DEBUG, Level.INFO)
)
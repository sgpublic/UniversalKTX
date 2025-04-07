package io.github.sgpublic.uniktx.logback.core

import ch.qos.logback.core.PropertyDefinerBase
import java.io.File
import java.io.IOException

class ExternalLogFileDefiner: PropertyDefinerBase() {
    override fun getPropertyValue(): String {
        val dir = externalCacheDir ?: throw IllegalArgumentException("未初始化")
        val logDir = File(dir, "log")
        if (!logDir.exists() && !logDir.mkdirs()) {
            throw RuntimeException("日志目录创建失败")
        }
        return try {
            logDir.canonicalPath
        } catch (e: IOException) {
            logDir.path
        }
    }

    companion object {
        internal var externalCacheDir: File? = null
    }
}
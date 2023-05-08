package io.github.sgpublic.android.core.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Madray Haven
 * @Date 2023/5/8 15:21
 */


suspend fun CoroutineScope.sleep(millis: Long) = withContext(Dispatchers.IO) {
    Thread.sleep(millis)
}
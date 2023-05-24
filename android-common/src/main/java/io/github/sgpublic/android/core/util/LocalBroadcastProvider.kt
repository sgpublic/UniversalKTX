package io.github.sgpublic.android.core.util

import android.content.Context
import androidx.localbroadcastmanager.content.LocalBroadcastManager

/**
 * @author Madray Haven
 * @Date 2023/5/24 16:36
 */

interface LocalBroadcastProvider {
    fun getContext(): Context

    fun getLocalBroadcastManager(): LocalBroadcastManager {
        return LocalBroadcastManager.getInstance(getContext())
    }
}
package io.github.sgpublic.android.core.util

import android.app.ActivityManager
import android.content.Context
import android.net.ConnectivityManager

/**
 * @author Madray Haven
 * @Date 2023/6/27 14:02
 */


val Context.sysConnectivityManager: ConnectivityManager get() {
    return getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager
}

val Context.sysActivityManager: ActivityManager get() {
    return getSystemService(Context.ACTIVITY_SERVICE)
            as ActivityManager
}
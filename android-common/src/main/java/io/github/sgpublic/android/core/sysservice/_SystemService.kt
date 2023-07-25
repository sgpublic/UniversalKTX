package io.github.sgpublic.android.core.sysservice

import android.app.ActivityManager
import android.content.Context
import android.media.AudioManager
import android.net.ConnectivityManager
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

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

val Context.sysInputMethodManager: InputMethodManager get() {
    return getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
}

val Context.sysWindowManager: WindowManager get() {
    return getSystemService(Context.WINDOW_SERVICE)
            as WindowManager
}

val Context.sysAudioManager: AudioManager get() {
    return getSystemService(Context.AUDIO_SERVICE)
            as AudioManager
}
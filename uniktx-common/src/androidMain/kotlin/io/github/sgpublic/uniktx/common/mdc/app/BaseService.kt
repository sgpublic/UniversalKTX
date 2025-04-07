package io.github.sgpublic.uniktx.common.mdc.app

import android.app.Service
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import androidx.annotation.CallSuper
import io.github.sgpublic.uniktx.common.core.util.register
import io.github.sgpublic.uniktx.common.core.util.unregister

/**
 *
 * @author Madray Haven
 * @date 2022/11/3 17:17
 */
abstract class BaseService: Service() {
    @CallSuper
    override fun onCreate() {
        register()
    }

    @CallSuper
    override fun onDestroy() {
        unregister()
    }
}

interface ServiceConnectionX<T>: ServiceConnection {
    fun onConnected(name: ComponentName, service: T)
    fun onDisconnected(name: ComponentName)
    override fun onServiceConnected(name: ComponentName, service: IBinder) {
        @Suppress("UNCHECKED_CAST")
        onConnected(name, service as T)
    }
    override fun onServiceDisconnected(name: ComponentName) {
        onDisconnected(name)
    }
}
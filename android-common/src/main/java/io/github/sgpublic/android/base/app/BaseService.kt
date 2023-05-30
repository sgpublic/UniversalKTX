package io.github.sgpublic.android.base.app

import android.app.Service
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import io.github.sgpublic.android.core.util.register
import io.github.sgpublic.android.core.util.unregister
import io.github.sgpublic.kotlin.core.util.closeQuietly
import io.github.sgpublic.kotlin.util.Loggable
import java.io.Closeable

/**
 *
 * @author Madray Haven
 * @date 2022/11/3 17:17
 */
abstract class BaseService: Service(), Loggable {
    @CallSuper
    override fun onCreate() {
        register()
    }

    @CallSuper
    override fun onDestroy() {
        unregister()
    }
}

open class BinderX(
    private val activity: AppCompatActivity,
    private val connection: ServiceConnection,
): Binder(), Closeable {
    private val Observer = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            closeQuietly()
        }
    }
    init {
        activity.lifecycle.addObserver(Observer)
    }

    @CallSuper
    override fun close() {
        activity.lifecycle.removeObserver(Observer)
        activity.unbindService(connection)
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
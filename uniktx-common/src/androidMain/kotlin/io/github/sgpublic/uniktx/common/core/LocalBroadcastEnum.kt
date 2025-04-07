package io.github.sgpublic.uniktx.common.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Parcelable
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import io.github.sgpublic.uniktx.common.Application
import io.github.sgpublic.uniktx.common.util.Loggable
import io.github.sgpublic.uniktx.common.util.log
import java.lang.IllegalArgumentException

/**
 * @author Madray Haven
 * @Date 2023/5/15 10:35
 */


private lateinit var EnumClass: Class<*>
private var _LocalBroadcastManager: LocalBroadcastManager? = null
private var mLocalBroadcastManager: LocalBroadcastManager
    get() = _LocalBroadcastManager ?: throw NullPointerException(
        "mLocalBroadcastManager is not init, did you call Application#initLocalBroadcast() ?"
    )
    set(value) { _LocalBroadcastManager = value }

fun <T: LocalBroadcastEnum> Application.initLocalBroadcast(clazz: Class<T>) {
    EnumClass = clazz
    _LocalBroadcastManager = LocalBroadcastManager.getInstance(this)
}

interface LocalBroadcastEnum {
    val name: String

    fun send() {
        mLocalBroadcastManager.sendBroadcast(Intent().also {
            it.action = name
        })
    }

    fun sendSync() {
        mLocalBroadcastManager.sendBroadcastSync(Intent().also {
            it.action = name
        })
    }

    fun <T: Parcelable> send(data: T) {
        mLocalBroadcastManager.sendBroadcast(Intent().also {
            it.action = name
            it.putExtra("data", data)
        })
    }

    fun <T: Parcelable> sendSync(data: T) {
        mLocalBroadcastManager.sendBroadcastSync(Intent().also {
            it.action = name
            it.putExtra("data", data)
        })
    }
}

abstract class LocalBroadcastReceiver<T: LocalBroadcastEnum>: BroadcastReceiver(), Loggable {
    fun unregister() {
        mLocalBroadcastManager.unregisterReceiver(this)
    }

    fun register(vararg actions: LocalBroadcastEnum) {
        if (actions.isEmpty()) {
            throw IllegalArgumentException("Please put at least one action")
        }
        mLocalBroadcastManager.registerReceiver(this, IntentFilter().also {
            for (action in actions) {
                it.addAction(action.name)
            }
        })
    }

    final override fun onReceive(context: Context, intent: Intent) {
        log.debug("accept broadcast: ${intent.action}")
        try {
            val action = intent.action!!
            val invoke = EnumClass.getMethod("valueOf", String::class.java)
                .invoke(null, action) as T
            onReceive(context, invoke, intent.getParcelableExtra("data"))
        } catch (e: Exception) {
            log.warn("Intent action failed to parse", e)
        }
    }

    abstract fun onReceive(context: Context, action: T, data: Parcelable?)
}
package io.github.sgpublic.uniktx.common

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.Looper
import androidx.annotation.CallSuper
import io.github.sgpublic.uniktx.common.core.realInitNotify
import io.github.sgpublic.uniktx.common.core.util.BuildConfigWrapper
import io.github.sgpublic.uniktx.common.core.util.ContextResource
import io.github.sgpublic.uniktx.common.util.Loggable
import org.slf4j.LoggerFactory
import java.lang.ref.WeakReference

abstract class Application : Application(), Loggable {
    @CallSuper
    override fun onCreate() {
        super.onCreate()
        mContext = WeakReference(this)
        BuildConfigWrapper._BuildConfig = buildConfig
        log.info("APP启动：${BuildConfigWrapper.VERSION_NAME}")
    }

    override fun onTerminate() {
        log.info("APP结束：${BuildConfigWrapper.VERSION_NAME}")
        super.onTerminate()
    }

    protected abstract val buildConfig: Class<*>

    companion object: ContextResource {
        private var mContext: WeakReference<Application>? = null
        override fun getContext(): Context {
            return mContext?.get()!!
        }
        val ApplicationContext: Context get() = mContext?.get()!!

        fun callTerminate() {
            mContext?.get()?.onTerminate()
        }
    }
}

fun <T: Application>  T.listenException() {
    Handler(mainLooper).post {
        while (true) {
            try {
                Looper.loop()
            } catch (e: Throwable){
                LoggerFactory.getLogger(javaClass)
                    .error("应用意外退出", e)
                throw e
            }
        }
    }
}

/**
 * 初始化通知频道，系统语言更改时自动重新初始化，Android 8.0 以下没有通知频道功能不需要初始化
 */
fun <T: Enum<T>> io.github.sgpublic.uniktx.common.Application.Companion.initNotifyChannel(enum: Class<T>) {
    realInitNotify(enum)
    getContext().registerReceiver(object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            realInitNotify(enum)
        }
    }, IntentFilter(Intent.ACTION_LOCALE_CHANGED))
}
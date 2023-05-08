package io.github.sgpublic.android

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import androidx.annotation.CallSuper
import io.github.sgpublic.android.core.NotifyChannelEnum
import io.github.sgpublic.android.core.util.BuildConfigWrapper
import io.github.sgpublic.android.core.util.ContextResource
import io.github.sgpublic.kotlin.util.Loggable
import io.github.sgpublic.kotlin.util.log
import org.slf4j.LoggerFactory
import java.lang.ref.WeakReference

abstract class Application : Application(), Loggable {
    @CallSuper
    override fun onCreate() {
        super.onCreate()
        context = WeakReference(this)
        BuildConfigWrapper._BuildConfig = buildConfig
        log.info("APP启动：${BuildConfigWrapper.VERSION_NAME}")
    }

    override fun onTerminate() {
        log.info("APP结束：${BuildConfigWrapper.VERSION_NAME}")
        super.onTerminate()
    }

    protected abstract val buildConfig: Class<*>

    companion object: ContextResource {
        private var context: WeakReference<Application>? = null
        override fun getContext(): Context = ApplicationContext
        val ApplicationContext: Context get() = this.context?.get()!!

        fun callTerminate() {
            this.context?.get()?.onTerminate()
        }

        val SelectableItemBackgroundBorderless: Drawable
            get() = getDrawableAttr(android.R.attr.selectableItemBackgroundBorderless)
        val SelectableItemBackground: Drawable
            get() = getDrawableAttr(android.R.attr.selectableItemBackground)
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
fun <T: Enum<T>> io.github.sgpublic.android.Application.initNotifyChannel(enum: Class<T>) {
    NotifyChannelEnum.realInit(Application, enum)
    io.github.sgpublic.android.Application.ApplicationContext
        .registerReceiver(object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            NotifyChannelEnum.realInit(Application, enum)
        }
    }, IntentFilter(Intent.ACTION_LOCALE_CHANGED))
}
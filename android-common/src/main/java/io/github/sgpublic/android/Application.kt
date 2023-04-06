package io.github.sgpublic.android

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import io.github.sgpublic.android.core.util.ContextResource
import java.lang.ref.WeakReference

public class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        context = WeakReference(this)
        startListenException()
    }

    private fun startListenException() {
        Handler(mainLooper).post {
            while (true) {
                try {
                    Looper.loop()
                } catch (e: Throwable){
                    throw e
                }
            }
        }
    }


    companion object: ContextResource {
        private val mHandler: Handler by lazy {
            Handler(Looper.getMainLooper())
        }
        @JvmStatic
        fun Post(runnable: () -> Unit) = mHandler.post(runnable)
        @JvmStatic
        fun PostDelayed(runnable: () -> Unit, delayMillis: Long) =
            mHandler.postDelayed(runnable, delayMillis)

        private var context: WeakReference<Application>? = null
        override fun getContext(): Context = ApplicationContext
        @JvmStatic
        val ApplicationContext: Context get() = this.context?.get()!!

//        private val Database: AppDatabase by lazy {
//            Room.databaseBuilder(this.ApplicationContext, RealAppDatabase::class.java, BuildConfig.PROJECT_NAME)
//                .fallbackToDestructiveMigration()
//                .allowMainThreadQueries()
//                .build()
//        }

        fun callTerminate() {
            this.context?.get()?.onTerminate()
        }

        val SelectableItemBackgroundBorderless: Drawable
            get() = getDrawableAttr(android.R.attr.selectableItemBackgroundBorderless)
        val SelectableItemBackground: Drawable
            get() = getDrawableAttr(android.R.attr.selectableItemBackground)
    }
}

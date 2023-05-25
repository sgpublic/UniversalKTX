package io.github.sgpublic.android.base.app

import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.yanzhenjie.sofia.Sofia
import io.github.sgpublic.android.common.R
import io.github.sgpublic.android.core.util.BackPressedDispatcherProvider
import io.github.sgpublic.android.core.util.LayoutInflaterProvider
import io.github.sgpublic.android.core.util.Toast
import io.github.sgpublic.android.core.util.finishAllActivity
import io.github.sgpublic.android.core.util.register
import io.github.sgpublic.android.core.util.unregister
import io.github.sgpublic.kotlin.util.Loggable

/**
 * @author Madray Haven
 * @Date 2023/4/4 14:27
 */
abstract class BaseCompatActivity: AppCompatActivity(),
    LayoutInflaterProvider,
    BackPressedDispatcherProvider,
    Loggable {
    @Suppress("PropertyName")
    protected val STATE: Bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        register()
        beforeCreate()
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = BaseFragment.Factory(this)
        if (savedInstanceState != null) {
            STATE.putAll(savedInstanceState)
        }
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(isActivityAtBottom) {
            private var last: Long = -1
            override fun handleOnBackPressed() {
                val now = System.currentTimeMillis()
                if (last < 0) {
                    Toast(R.string.text_back_exit)
                    last = now
                    return
                }
                if (now - last < 2000) {
                    finishAllActivity()
                    return
                }
                last = now
                Toast(R.string.text_back_exit_notice)
            }
        })
    }

    protected open fun beforeCreate() { }

    override fun onSaveInstanceState(outState: Bundle) {
        STATE.takeIf { !STATE.isEmpty }?.let {
            outState.putAll(STATE)
        }
        super.onSaveInstanceState(outState)
    }

    protected open val isActivityAtBottom: Boolean = false

    override fun onDestroy() {
        STATE.clear()
        super.onDestroy()
        unregister()
    }
}

interface IntentBeanAddonComponent<T: Parcelable> {
    fun startActivity(origin: BaseCompatActivity, target: Class<out IntentBeanAddon<T>>, data: T) {
        origin.startActivity(Intent(origin, target).putExtra("bean", data))
    }
}

@Suppress("unused")
interface IntentBeanAddon<T: Parcelable> {
    fun getIntent(): Intent?
}

inline fun <reified T: Parcelable> IntentBeanAddon<T>.getIntentBean(): T {
    return if (VERSION.SDK_INT >= VERSION_CODES.TIRAMISU) {
        T::class.javaObjectType
        getIntent()?.getParcelableExtra("bean", T::class.java)!!
    } else {
        @Suppress("DEPRECATION")
        getIntent()?.getParcelableExtra("bean")!!
    }
}

internal fun AppCompatActivity.applySofia() {
    Sofia.with(this)
        .statusBarBackgroundAlpha(0)
        .navigationBarBackgroundAlpha(0)
        .invasionNavigationBar()
        .invasionStatusBar()
        .statusBarDarkFont()
}

fun AppCompatActivity.applyFragmentManager() {
    supportFragmentManager.fragmentFactory = BaseFragment.Factory(this)
}
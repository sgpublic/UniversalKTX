package io.github.sgpublic.android.base.app

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.yanzhenjie.sofia.Sofia
import io.github.sgpublic.android.Application
import io.github.sgpublic.android.common.R
import io.github.sgpublic.android.core.util.LayoutInflaterProvider
import io.github.sgpublic.android.core.util.Toast
import io.github.sgpublic.android.core.util.finishAll
import io.github.sgpublic.android.core.util.register
import io.github.sgpublic.android.core.util.unregister
import io.github.sgpublic.kotlin.util.Loggable

/**
 * @author Madray Haven
 * @Date 2023/4/4 14:27
 */
abstract class BaseCompatActivity: AppCompatActivity(), LayoutInflaterProvider, Loggable {
    @Suppress("PropertyName")
    protected val STATE: Bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        register()
        beforeCreate()
        super.onCreate(savedInstanceState, persistentState)
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
                    Application.finishAll()
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

    protected fun setupToolbar(toolbar: MaterialToolbar) {
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    protected val isActivityAtBottom: Boolean = false

    override fun onDestroy() {
        STATE.clear()
        super.onDestroy()
        unregister()
    }
}

fun AppCompatActivity.applySofia() {
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
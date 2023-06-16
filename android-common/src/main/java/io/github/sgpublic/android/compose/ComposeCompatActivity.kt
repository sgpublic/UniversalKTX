package io.github.sgpublic.android.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import io.github.sgpublic.android.common.R
import io.github.sgpublic.android.core.util.Toast
import io.github.sgpublic.android.core.util.finishAllActivity
import io.github.sgpublic.android.core.util.register
import io.github.sgpublic.android.core.util.unregister
import io.github.sgpublic.kotlin.util.Loggable

/**
 * @author Madray Haven
 * @Date 2023/6/15 9:08
 */
abstract class ComposeCompatActivity: ComponentActivity(), Loggable {
    final override fun onCreate(savedInstanceState: Bundle?) {
        register()
        beforeCreate()
        super.onCreate(savedInstanceState)
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
        setContent {
            Content()
        }
        onActivityCreated(savedInstanceState)
    }

    protected abstract fun onActivityCreated(savedInstanceState: Bundle?)

    @Composable
    abstract fun Content()

    protected open fun beforeCreate() { }

    protected open val isActivityAtBottom: Boolean = false

    override fun onDestroy() {
        super.onDestroy()
        unregister()
    }
}
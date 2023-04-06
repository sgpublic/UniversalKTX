package io.github.sgpublic.android.core.util

import android.content.Context
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Lifecycle
import io.github.sgpublic.android.Application

fun Context.Toast(content: String) {
    Application.Post {
        android.widget.Toast.makeText(
            this, content,
            if (content.length < 20) android.widget.Toast.LENGTH_SHORT
            else android.widget.Toast.LENGTH_LONG
        ).show()
    }
}

fun Context.Toast(@StringRes id: Int) {
    Toast(Application.getStringRes(id))
}

fun Context.Toast(@StringRes id: Int, message: String) {
    Toast("${getString(id)}，$message")
}

fun Context.Toast(@StringRes id: Int, code: Int) {
    Toast("${getString(id)}($code)")
}

fun Context.Toast(@StringRes id: Int, message: String, code: Int) {
    Toast("${getString(id)}，${message}($code)")
}

private val contexts = LinkedHashSet<Context>()

fun AppCompatActivity.register() {
    (this as Context).register()
}

fun AppCompatActivity.unregister() {
    (this as Context).unregister()
}

fun Context.register() {
    contexts.add(this)
}

fun Context.unregister() {
    contexts.remove(this)
    if (contexts.isEmpty()) {
        Application.callTerminate()
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}

fun Application.Companion.finishAll() {
    val tmp = ArrayList(contexts)
    for (context in tmp){
        if (context !is AppCompatActivity) {
            continue
        }
        if (context.lifecycle.currentState != Lifecycle.State.DESTROYED){
            context.finish()
        }
    }
    tmp.clear()
}

val Context.isNightMode: Boolean get() = resources.configuration.uiMode and
        Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES


interface ContextResource {
    fun getContext(): Context

    @ColorInt
    fun getColorRes(@ColorRes id: Int): Int {
        return ResourcesCompat.getColor(getContext().resources, id, getContext().theme)
    }

    fun getStringRes(@StringRes id: Int, vararg args: Any): String {
        return getContext().getString(id, *args)
    }

    fun getDrawableRes(@DrawableRes id: Int): Drawable? {
        return ResourcesCompat.getDrawable(getContext().resources, id, getContext().theme)
    }

    fun getDrawableAttr(@AttrRes id: Int): Drawable {
        getContext().obtainStyledAttributes(intArrayOf(id)).use {
            return it.getDrawable(0)!!
        }
    }
}

inline fun <reified T: Any> ContextResource.getSysService(): T {
    return getContext().getSystemService()!!
}
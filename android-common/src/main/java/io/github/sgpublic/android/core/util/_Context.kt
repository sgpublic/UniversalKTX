package io.github.sgpublic.android.core.util

import android.R
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources.Theme
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Lifecycle
import io.github.sgpublic.android.Application

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

fun Context.finishAllActivity() {
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

fun Context.finishOtherActivity() {
    val tmp = ArrayList(contexts)
    for (context in tmp) {
        if (context !is AppCompatActivity || context == this) {
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


interface ContextResource: LocalBroadcastProvider {
    override fun getContext(): Context

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

    @ColorInt
    fun getColorAttr(@AttrRes id: Int) = TypedValue().also {
        getTheme().resolveAttribute(id, it, true)
    }.data

    fun getTheme(): Theme {
        return getContext().theme
    }

    val Int.dp: Int get() = toFloat().dp.toInt()
    val Float.dp: Float get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this, getContext().resources.displayMetrics)

    val Int.sp: Int get() = toFloat().sp.toInt()
    val Float.sp: Float get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this, getContext().resources.displayMetrics)
}

inline fun <reified T: Any> ContextResource.getSysService(): T {
    return getContext().getSystemService()!!
}

val ContextResource.selectableItemBackgroundBorderless: Drawable get() = getDrawableAttr(
    R.attr.selectableItemBackgroundBorderless
)
val ContextResource.selectableItemBackground: Drawable get() = getDrawableAttr(
    R.attr.selectableItemBackground
)
package io.github.sgpublic.uniktx.common.core.util

import android.Manifest
import android.R
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.content.res.Resources.Theme
import android.graphics.drawable.Drawable
import android.net.NetworkCapabilities
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresPermission
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import io.github.sgpublic.uniktx.common.Application
import io.github.sgpublic.uniktx.common.core.sysservice.sysConnectivityManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val contexts = LinkedHashSet<Context>()

fun Activity.register() {
    (this as Context).register()
}

fun Activity.unregister() {
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
        if (context !is Activity) {
            continue
        }

        if (!context.isDestroyed){
            context.finish()
        }
    }
    tmp.clear()
}

fun Context.finishOtherActivity() {
    val tmp = ArrayList(contexts)
    for (context in tmp) {
        if (context !is Activity || context == this) {
            continue
        }
        if (!context.isDestroyed){
            context.finish()
        }
    }
    tmp.clear()
}

val Context.isNightMode: Boolean get() = resources.configuration.uiMode and
        Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES


interface ContextResource {
    fun getContext(): Context

    val Int.dp: Int get() = toFloat().dp.toInt()
    val Float.dp: Float get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this, getContext().resources.displayMetrics)

    val Int.sp: Int get() = toFloat().sp.toInt()
    val Float.sp: Float get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this, getContext().resources.displayMetrics)

    fun LifecycleOwner.Toast(content: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            android.widget.Toast.makeText(
                getContext(), content,
                if (content.length > 10) android.widget.Toast.LENGTH_LONG else android.widget.Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun LifecycleOwner.Toast(@StringRes content: Int, message: String? = null, code: Int? = null) {
        Toast(getContext().getString(content)
                + ("".takeIf { message == null } ?: "，$message")
                + ("".takeIf { code == null } ?: " (${code})"))
    }
}

fun ContextResource.getLocalBroadcastManager(): LocalBroadcastManager {
    return LocalBroadcastManager.getInstance(getContext())
}

fun ContextResource.getTheme(): Theme {
    return getContext().theme
}
fun ContextResource.getResource(): Resources {
    return getContext().resources
}


@ColorInt
fun ContextResource.getColorRes(@ColorRes id: Int): Int {
    return getContext().resources.getColor(id, getContext().theme)
}

fun ContextResource.getStringRes(@StringRes id: Int, vararg args: Any): String {
    return getContext().getString(id, *args)
}

fun ContextResource.getDrawableRes(@DrawableRes id: Int): Drawable? {
    return ResourcesCompat.getDrawable(getContext().resources, id, getTheme())
}

fun ContextResource.getDrawableAttr(@AttrRes id: Int): Drawable {
    getContext().obtainStyledAttributes(intArrayOf(id)).use {
        return it.getDrawable(0)!!
    }
}

@ColorInt
fun ContextResource.getColorAttr(@AttrRes id: Int) = TypedValue().also {
    getTheme().resolveAttribute(id, it, true)
}.data

fun ContextResource.getDimenRes(@DimenRes id: Int): Float {
    return getContext().resources.getDimension(id)
}

fun ContextResource.getDimenResInt(@DimenRes id: Int): Int {
    return getDimenRes(id).toInt()
}


inline fun <reified T: Any> ContextResource.getSysService(): T {
    return getContext().getSystemService(T::class.java)
}

val ContextResource.selectableItemBackgroundBorderless: Drawable get() = getDrawableAttr(
    R.attr.selectableItemBackgroundBorderless
)
val ContextResource.selectableItemBackground: Drawable get() = getDrawableAttr(
    R.attr.selectableItemBackground
)

enum class NetworkType {
    WIFI, CELLULAR, ETHERNET, BLUETOOTH, UNKNOWN
}

@get:RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
val Context.currentNetworkType: Set<NetworkType> get() {
    val actNw = sysConnectivityManager.getNetworkCapabilities(
        sysConnectivityManager.activeNetwork
    ) ?: return emptySet()
    val result = mutableSetOf<NetworkType>()
    when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> result.add(NetworkType.WIFI)
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> result.add(NetworkType.CELLULAR)
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> result.add(NetworkType.ETHERNET)
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> result.add(NetworkType.BLUETOOTH)
    }
    return result
}

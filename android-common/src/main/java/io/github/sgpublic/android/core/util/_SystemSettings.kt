package io.github.sgpublic.android.core.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.provider.Settings
import androidx.annotation.RequiresPermission
import io.github.sgpublic.kotlin.util.log

/**
 * @author Madray Haven
 * @Date 2023/7/25 9:37
 */

@set:RequiresPermission(Manifest.permission.WRITE_SETTINGS)
var Context.Settings_ScreenBrightness: Int
    get() {
        return try {
            Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: Exception) {
            log.warn("get Settings.System.SCREEN_BRIGHTNESS", e)
            0
        }
    }
    set(value) {
        if (value < 0) {
            Settings.System.putInt(
                contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
            )
            return
        }
        val realValue = when {
            value >= 255 -> 255
            else -> value
        }
        try {
            Settings.System.putInt(
                contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
            )
            Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, realValue)
        } catch (e: Exception) {
            log.warn("set Settings.System.SCREEN_BRIGHTNESS", e)
        }
    }

var Activity.Window_ScreenBrightness: Int
    get() = (window.attributes.screenBrightness * 255).toInt()
    set(value) {
        window.attributes = window.attributes.also {
            it.screenBrightness = when {
                value <= 0 -> 0
                value >= 255 -> 255
                else -> value
            } / 255f
        }
    }



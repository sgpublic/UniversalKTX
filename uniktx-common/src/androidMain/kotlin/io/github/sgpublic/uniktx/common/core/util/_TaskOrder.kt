package io.github.sgpublic.uniktx.common.core.util

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import io.github.sgpublic.uniktx.common.core.sysservice.sysActivityManager

/**
 * @author Madray Haven
 * @Date 2023/6/26 15:59
 */


fun Context.isRunningForeground(): Boolean {
    for (process in sysActivityManager.runningAppProcesses) {
        if (process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
            if (process.processName == applicationInfo.processName) {
                return true
            }
        }
    }
    return false
}

@RequiresPermission(android.Manifest.permission.REORDER_TASKS)
fun Context.moveSelfToTop(taskId: Int) {
    for (process in sysActivityManager.runningAppProcesses) {
        if (process.processName != applicationInfo.processName) {
            continue
        }
        if (process.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
            sysActivityManager.moveTaskToFront(taskId, ActivityManager.MOVE_TASK_WITH_HOME)
        }
        break
    }
}

fun Context.killOther(packageName: String) {
    ActivityManager::class.java.getDeclaredMethod("forceStopPackage", String::class.java)
        .invoke(sysActivityManager, packageName)
}


fun Context.startOtherPackage(packageName: String, vararg fallback: String) {
    try {
        val intent = packageManager
            .getLaunchIntentForPackage(packageName)
            ?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)!!
        startActivity(intent)
    } catch (e: Exception) {
        if (fallback.isNotEmpty()) {
            startOtherPackage(fallback[0], *(fallback.toMutableList().also {
                if (it.isNotEmpty()) {
                    it.removeFirst()
                }
            }).toTypedArray())
        }
    }
}
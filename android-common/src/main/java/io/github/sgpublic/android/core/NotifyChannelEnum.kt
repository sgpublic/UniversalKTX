package io.github.sgpublic.android.core

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.RemoteViews
import androidx.annotation.RequiresPermission
import androidx.annotation.StringRes
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.LocusIdCompat
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.graphics.drawable.IconCompat
import io.github.sgpublic.android.Application
import io.github.sgpublic.android.core.util.ContextResource

/**
 * 通知频道封装，利用枚举更优雅的实现通知管理，若需添加新的通知频道，在此添加即可。
 * @author Madray Haven
 * @date 2022/10/10 11:44
 * @param channelName 频道名称，请传入字符串资源
 * @param channelDesc 频道介绍，请传入字符串资源
 * @param importance 优先级
 * @param enableLights 是否显示通知灯
 * @param showBadge 是否显示桌面图标角标
 */
interface NotifyChannelEnum {
    @get:StringRes
    val channelName: Int
    @get:StringRes
    val channelDesc: Int
    val importance: Int get() = NotificationManager.IMPORTANCE_DEFAULT
    val enableLights: Boolean get() = false
    val showBadge: Boolean get() = false

    fun getName(): String

    /**
     * 在当前通知频道配置下创建一个通知构建器
     * @param context 上下文
     * @return 通知构建器
     */
    fun newBuilder(context: Context): Builder {
        return Builder(context, getName())
    }

    /**
     * Notification 封装，支持仅传入 id 发送通知，需通过 NotifyChannel.Builder 创建
     * @see NotifyChannelEnum.Builder
     *
     * @see android.app.Notification
     */
    class Notification internal constructor(
        private val context: Context,
        private val notification: android.app.Notification
    ) : android.app.Notification() {
        /**
         * 发送通知，使用已有 id 会更新现有通知
         * @param id 通知 id
         */
        @RequiresPermission("android.permission.POST_NOTIFICATIONS")
        fun send(id: Int) {
            NotificationManagerCompat.from(context).notify(id, notification)
        }

        /**
         * 发送通知以启动前台服务，使用已有 id 会更新现有通知
         * @param id 通知 id
         */
        fun startForeground(id: Int) {
            if (context is Service) {
                context.startForeground(id, notification)
            } else {
                throw IllegalStateException("Current context is not a Service, cannot call startForeground()!")
            }
        }
    }

    /**
     * 通知构建器封装，构建时返回封装后的 Notification
     * @see NotifyChannel.newBuilder
     */
    class Builder internal constructor(
        private val context: Context, name: String
    ) : NotificationCompat.Builder(context, name) {
        override fun build(): Notification {
            return Notification(context, super.build())
        }

        override fun setAllowSystemGeneratedContextualActions(allowed: Boolean): Builder {
            super.setAllowSystemGeneratedContextualActions(allowed)
            return this
        }

        override fun setAutoCancel(autoCancel: Boolean): Builder {
            super.setAutoCancel(autoCancel)
            return this
        }

        override fun setBadgeIconType(icon: Int): Builder {
            super.setBadgeIconType(icon)
            return this
        }

        override fun setBubbleMetadata(data: NotificationCompat.BubbleMetadata?): Builder {
            super.setBubbleMetadata(data)
            return this
        }

        override fun setCategory(category: String?): Builder {
            super.setCategory(category)
            return this
        }

        override fun setChannelId(channelId: String): Builder {
            super.setChannelId(channelId)
            return this
        }

        override fun setChronometerCountDown(countsDown: Boolean): Builder {
            super.setChronometerCountDown(countsDown)
            return this
        }

        override fun setColor(argb: Int): Builder {
            super.setColor(argb)
            return this
        }

        override fun setColorized(colorize: Boolean): Builder {
            super.setColorized(colorize)
            return this
        }

        override fun setContent(views: RemoteViews?): Builder {
            super.setContent(views)
            return this
        }

        override fun setContentInfo(info: CharSequence?): Builder {
            super.setContentInfo(info)
            return this
        }

        override fun setContentIntent(intent: PendingIntent?): Builder {
            super.setContentIntent(intent)
            return this
        }

        override fun setContentText(text: CharSequence?): Builder {
            super.setContentText(text)
            return this
        }

        override fun setContentTitle(title: CharSequence?): Builder {
            super.setContentTitle(title)
            return this
        }

        override fun setCustomBigContentView(contentView: RemoteViews?): Builder {
            super.setCustomBigContentView(contentView)
            return this
        }

        override fun setCustomContentView(contentView: RemoteViews?): Builder {
            super.setCustomContentView(contentView)
            return this
        }

        override fun setCustomHeadsUpContentView(contentView: RemoteViews?): Builder {
            super.setCustomHeadsUpContentView(contentView)
            return this
        }

        override fun setDefaults(defaults: Int): Builder {
            super.setDefaults(defaults)
            return this
        }

        override fun setDeleteIntent(intent: PendingIntent?): Builder {
            super.setDeleteIntent(intent)
            return this
        }

        override fun setExtras(extras: Bundle?): Builder {
            super.setExtras(extras)
            return this
        }

        override fun setForegroundServiceBehavior(behavior: Int): Builder {
            super.setForegroundServiceBehavior(behavior)
            return this
        }

        override fun setFullScreenIntent(intent: PendingIntent?, highPriority: Boolean): Builder {
            super.setFullScreenIntent(intent, highPriority)
            return this
        }

        override fun setGroup(groupKey: String?): Builder {
            super.setGroup(groupKey)
            return this
        }

        override fun setGroupAlertBehavior(groupAlertBehavior: Int): Builder {
            super.setGroupAlertBehavior(groupAlertBehavior)
            return this
        }

        override fun setGroupSummary(isGroupSummary: Boolean): Builder {
            super.setGroupSummary(isGroupSummary)
            return this
        }

        override fun setLargeIcon(icon: Bitmap?): Builder {
            super.setLargeIcon(icon)
            return this
        }

        override fun setLights(argb: Int, onMs: Int, offMs: Int): Builder {
            super.setLights(argb, onMs, offMs)
            return this
        }

        override fun setLocalOnly(b: Boolean): Builder {
            super.setLocalOnly(b)
            return this
        }

        override fun setLocusId(locusId: LocusIdCompat?): Builder {
            super.setLocusId(locusId)
            return this
        }

        override fun setNumber(number: Int): Builder {
            super.setNumber(number)
            return this
        }

        override fun setOngoing(ongoing: Boolean): Builder {
            super.setOngoing(ongoing)
            return this
        }

        override fun setOnlyAlertOnce(onlyAlertOnce: Boolean): Builder {
            super.setOnlyAlertOnce(onlyAlertOnce)
            return this
        }

        override fun setPriority(pri: Int): Builder {
            super.setPriority(pri)
            return this
        }

        override fun setProgress(max: Int, progress: Int, indeterminate: Boolean): Builder {
            super.setProgress(max, progress, indeterminate)
            return this
        }

        override fun setPublicVersion(n: android.app.Notification?): Builder {
            super.setPublicVersion(n)
            return this
        }

        override fun setRemoteInputHistory(text: Array<CharSequence>?): Builder {
            super.setRemoteInputHistory(text)
            return this
        }

        override fun setSettingsText(text: CharSequence?): Builder {
            super.setSettingsText(text)
            return this
        }

        override fun setShortcutId(shortcutId: String?): Builder {
            super.setShortcutId(shortcutId)
            return this
        }

        override fun setShortcutInfo(shortcutInfo: ShortcutInfoCompat?): Builder {
            super.setShortcutInfo(shortcutInfo)
            return this
        }

        override fun setShowWhen(show: Boolean): Builder {
            super.setShowWhen(show)
            return this
        }

        override fun setSilent(silent: Boolean): Builder {
            super.setSilent(silent)
            return this
        }

        override fun setSmallIcon(icon: Int): Builder {
            super.setSmallIcon(icon)
            return this
        }

        override fun setSmallIcon(icon: IconCompat): Builder {
            super.setSmallIcon(icon)
            return this
        }

        override fun setSmallIcon(icon: Int, level: Int): Builder {
            super.setSmallIcon(icon, level)
            return this
        }

        override fun setSortKey(sortKey: String?): Builder {
            super.setSortKey(sortKey)
            return this
        }

        override fun setSound(sound: Uri?): Builder {
            super.setSound(sound)
            return this
        }

        override fun setSound(sound: Uri?, streamType: Int): Builder {
            super.setSound(sound, streamType)
            return this
        }

        override fun setStyle(style: NotificationCompat.Style?): Builder {
            super.setStyle(style)
            return this
        }

        override fun setSubText(text: CharSequence?): Builder {
            super.setSubText(text)
            return this
        }

        override fun setTicker(tickerText: CharSequence?): Builder {
            super.setTicker(tickerText)
            return this
        }

        override fun setTimeoutAfter(durationMs: Long): Builder {
            super.setTimeoutAfter(durationMs)
            return this
        }

        override fun setUsesChronometer(b: Boolean): Builder {
            super.setUsesChronometer(b)
            return this
        }

        override fun setVibrate(pattern: LongArray?): Builder {
            super.setVibrate(pattern)
            return this
        }

        override fun setVisibility(visibility: Int): Builder {
            super.setVisibility(visibility)
            return this
        }

        override fun setWhen(`when`: Long): Builder {
            super.setWhen(`when`)
            return this
        }

        @Deprecated("Deprecated in Java")
        override fun setNotificationSilent(): Builder {
            super.setNotificationSilent()
            return this
        }

        @Deprecated("Deprecated in Java")
        override fun setTicker(tickerText: CharSequence?, views: RemoteViews?): Builder {
            super.setTicker(tickerText, views)
            return this
        }
    }

    companion object {
        internal fun <T: Enum<T>> realInit(context: ContextResource, enum: Class<T>) {
            val manager: NotificationManagerCompat = NotificationManagerCompat.from(context.getContext())
            @Suppress("UNCHECKED_CAST")
            val invoke = enum.getMethod("values")
                .invoke(null, null) as Array<NotifyChannelEnum>
            for (value in invoke) {
                val channel: NotificationChannelCompat.Builder =
                    NotificationChannelCompat.Builder(value.getName(), value.importance)
                channel.setLightsEnabled(value.enableLights)
                channel.setShowBadge(value.showBadge)
                channel.setName(context.getStringRes(value.channelName))
                channel.setDescription(context.getStringRes(value.channelDesc))
                manager.createNotificationChannel(channel.build())
            }
        }
    }
}
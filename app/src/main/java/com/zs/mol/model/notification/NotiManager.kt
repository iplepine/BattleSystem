package com.zs.mol.model.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.zs.mol.MainActivity
import com.zs.mol.R

object NotiManager {
    const val CHANNEL_DEFAULT = "mol_channel_default"
    const val CHANNEL_REPORT = "mol_channel_report"
    const val CHANNEL_EVENT = "mol_channel_event"

    data class ChannelData(val id: String, val name: String, val description: String)

    val notificationIds = ArrayList<Int>()

    val channels = arrayOf(
        ChannelData(CHANNEL_DEFAULT, "기본 알림", "모든 기본 알림입니다"),
        ChannelData(CHANNEL_REPORT, "전투 보고", "전투 결과에 관한 알림입니다."),
        ChannelData(CHANNEL_EVENT, "이벤트", "게임 내 이벤트에 관한 알림입니다.")
    )

    fun createChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(NotificationManager::class.java)

            channels.forEach {
                val channel =
                    NotificationChannel(it.id, it.name, NotificationManager.IMPORTANCE_DEFAULT)
                channel.description = it.description
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    fun showNotification(context: Context?, channelId: String, title: String, content: String) {
        context?.apply {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val builder = NotificationCompat.Builder(context, channelId)
            builder.setSmallIcon(R.mipmap.ic_launcher_round)
            builder.setContentTitle(title)
            builder.setContentText(content)
            //builder.priority = NotificationCompat.PRIORITY_DEFAULT
            builder.setAutoCancel(true)
            builder.setContentIntent(pendingIntent)

            val style = NotificationCompat.BigTextStyle()
            style.bigText(content)
            builder.setStyle(style)

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(createNotificationId(), builder.build())
        }
    }

    private fun createNotificationId(): Int {
        return (Math.random() * 10000).toInt().apply {
            notificationIds.add(this)
        }
    }
}
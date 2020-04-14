package com.zs.mol.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import androidx.core.app.AlarmManagerCompat
import com.zs.mol.MainActivity
import com.zs.mol.R
import com.zs.mol.model.common.Logger
import com.zs.mol.model.db.PreferenceManager


class UnitManagementWidgetProvider : AppWidgetProvider() {

    companion object {
        const val ALARM_ID = 13939

        fun setAlarm(context: Context?) {
            context?.apply {
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                AlarmManagerCompat.setExact(
                    alarmManager,
                    AlarmManager.RTC,
                    System.currentTimeMillis() + WidgetData.ISSUE_TEXT_CHANGE_DELAY,
                    updatePendingIntent(context)
                )
                Logger.e("알람 추가")
            }
        }

        fun removeAlarm(context: Context?) {
            context?.apply {
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.cancel(updatePendingIntent(context))
                Logger.e("알람 제거")
            }
        }

        private fun updatePendingIntent(context: Context): PendingIntent {
            val intent = Intent(context, AlarmReceiver::class.java)
            return PendingIntent.getBroadcast(context, ALARM_ID, intent, 0)
        }

        fun updateViews(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val widget = ComponentName(context, UnitManagementWidgetProvider::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(widget)

            val widgetData = WidgetData.parse(
                PreferenceManager.getString(
                    context.applicationContext,
                    "widget_data",
                    ""
                )
            )

            appWidgetIds.forEach { appWidgetId ->
                val rv =
                    RemoteViews(context.packageName, R.layout.widget_unit_manage).apply {
                        val text = widgetData?.getCurrentKeyword() ?: "없음"
                        setTextViewText(R.id.time, text)
                        setOnClickPendingIntent(
                            R.id.time,
                            getClickPendingIntent(context, text)
                        )
                    }
                appWidgetManager.updateAppWidget(appWidgetId, rv)
            }

            widgetData?.apply {
                rankUp()
                PreferenceManager.setString(
                    context.applicationContext,
                    "widget_data",
                    toJson()
                )

                if (hasExpired()) {
                    WidgetUpdateService.enqueueWork(
                        context, Intent(
                            context,
                            WidgetUpdateService::class.java
                        )
                    )
                }
            }
        }

        private fun getClickPendingIntent(context: Context, text: String): PendingIntent {
            val url: String = "testtest:$text"
            val urlIntent = Intent(context, MainActivity::class.java)
            urlIntent.action = Intent.ACTION_VIEW
            urlIntent.data = Uri.parse(url)
            urlIntent.putExtra("widgetRequest", true)
            val pKeywordIntent = PendingIntent.getActivity(
                context,
                3141,
                urlIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            return pKeywordIntent
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        Logger.d("업데이트")
        updateViews(context)
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)

        context?.apply {
            setAlarm(context)
            WidgetUpdateService.enqueueWork(
                context, Intent(
                    context,
                    WidgetUpdateService::class.java
                )
            )
        }
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        removeAlarm(context)
    }
}
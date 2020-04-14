package com.zs.mol.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.zs.mol.model.common.Logger

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Logger.d("알람 리씨브")

        context?.also {
            updateWidget(it)
            reschedule(it)
        }
    }

    fun updateWidget(context: Context) {
        UnitManagementWidgetProvider.updateViews(context)
    }

    fun reschedule(context: Context) {
        UnitManagementWidgetProvider.setAlarm(context)
    }
}
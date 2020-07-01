package com.zs.mol.widget

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.zs.mol.model.common.Logger
import com.zs.mol.model.db.PreferenceManager
import org.json.JSONArray

class WidgetUpdateService : JobIntentService() {
    companion object {
        const val JOB_ID = 21994

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, WidgetUpdateService::class.java, JOB_ID, intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        Logger.d("위젯 업데이트 서비스 크리에이트")
    }

    override fun onHandleWork(intent: Intent) {
        val preferenceManager = PreferenceManager(applicationContext)
        Logger.d("위젯 업데이트 서비스 핸들워크")

        var widgetData =
            WidgetData.parse(preferenceManager.getString("widget_data", ""))

        if (widgetData == null) {
            widgetData = WidgetData()
            fetchIssueList(widgetData)
        } else if (widgetData.hasExpired()) {
            fetchIssueList(widgetData)
        }
    }

    private fun GET(url: String): String? {
        return try {
            val util = HttpUtil()
            util.setConnectionTimeout(10000)
            util.setSocketTimeout(10000)
            util.get(url, null, true)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun fetchIssueList(widgetData: WidgetData) {
        val result = GET("https://m.zum.com/api/search/issue")
        Logger.d("GET : $result")
        widgetData?.also {
            try {
                if (result == null) {
                    widgetData.status = WidgetData.STATUS_ERROR
                } else {
                    val jArray = JSONArray(result)
                    var i = 0
                    while (i < jArray.length() && i < 10) {
                        val cArray = jArray.getJSONArray(i)
                        val keyword = cArray.getString(0)
                        widgetData.issueList.add(keyword)
                        ++i
                    }
                    if (widgetData.issueList.isNotEmpty()) {
                        widgetData.status = (WidgetData.STATUS_DISPLAY)
                    }
                }

                val preferenceManager = PreferenceManager(applicationContext)
                preferenceManager.setString("widget_data", widgetData.toJson())
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                widgetData.status = (WidgetData.STATUS_ERROR)
            }
        }
    }
}
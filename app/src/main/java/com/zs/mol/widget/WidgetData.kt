package com.zs.mol.widget

import com.google.gson.Gson

class WidgetData {

    var lastUpdateTime: Long = 0
    var currentRank: Int = 0
    var status: Int = STATUS_LOADING
    var issueList: ArrayList<String> = ArrayList()

    companion object {
        const val STATUS_LOADING = 0
        const val STATUS_DISPLAY = 1
        const val STATUS_ERROR = 2

        const val ISSUE_RELOAD_DELAY = 1000 * 10L// * 10L
        const val ISSUE_TEXT_CHANGE_DELAY = 1000 * 5L   // 시스템 최소값 5초

        fun parse(json: String): WidgetData? {
            val gson = Gson()
            return try {
                gson.fromJson(json, WidgetData::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }

    fun toJson(): String {
        val gson = Gson()
        return gson.toJson(this)
    }

    fun hasExpired(): Boolean {
        return ISSUE_RELOAD_DELAY < System.currentTimeMillis() - lastUpdateTime
    }

    fun getCurrentKeyword(): String {
        if (issueList.size <= currentRank) {
            return ""
        }

        return issueList?.get(currentRank)
    }

    fun rankUp() {
        currentRank++
        if (issueList.size <= currentRank) {
            currentRank = 0
        }
    }
}

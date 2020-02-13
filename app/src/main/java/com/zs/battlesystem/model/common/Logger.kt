package com.zs.battlesystem.model.common

import android.util.Log

object Logger {
    var logLevel = LogLevel.ALL

    object LogLevel {
        const val ALL = 0
        const val DEBUG = 3
        const val INFO = 5
        const val ERROR = 10
    }

    private fun log(log: String) {
        Log.d("TTEST", getLogTagWithMethod())
        Log.d("TTEST", log)

        //println("$tag $log")
    }

    private fun getLogTagWithMethod(): String {
        val stack = Throwable().fillInStackTrace()
        val trace = stack.stackTrace[3]

        return trace.run {
            "$className.$methodName ($fileName:$lineNumber)"
        }
    }

    fun d(log: String) {
        if (logLevel <= LogLevel.DEBUG)
            log(log)
    }

    fun i(log: String) {
        if (logLevel <= LogLevel.INFO)
            log(log)
    }

    fun e(log: String) {
        if (logLevel <= LogLevel.ERROR)
            log(log)
    }
}
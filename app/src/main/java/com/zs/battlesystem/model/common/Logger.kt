package com.zs.battlesystem.model.common

object Logger {
    var logLevel = LogLevel.ALL

    object LogLevel {
        const val ALL = 0
        const val DEBUG = 3
        const val INFO = 5
        const val ERROR = 10
    }

    private fun log(log: String) {
        println(log)
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
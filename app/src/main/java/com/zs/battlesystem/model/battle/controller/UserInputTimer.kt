package com.zs.battlesystem.model.battle.controller

import com.zs.battlesystem.model.common.Logger

object UserInputTimer {
    const val MAX_WATING_TIME = 10 * 1000L    // 입력 대기 시간 10초

    var waitingTime = 0L

    fun clear() {
        waitingTime = 0
    }

    fun timePast(time: Long) {
        waitingTime += time
        log()
    }

    fun isTimeOver(): Boolean {
        return MAX_WATING_TIME < waitingTime
    }

    private fun log() {
        if (waitingTime % 1000 == 0L) {
            Logger.d("waiting input ... ${waitingTime / 1000}")
        }
    }
}
package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.common.Logger

object BattleSimulator {
    const val TIMEOUT = 1000 * 100

    fun simulate(battle: Battle): String {
        var totalTime = 0L
        var timeOver = false

        while (!battle.checkFinish()) {
            battle.updateTime(Battle.GAME_SPEED)

            totalTime += Battle.GAME_SPEED
            if (TIMEOUT < totalTime) {
                timeOver = true
                break
            }

            Logger.d("")
        }

        val message = when {
            timeOver -> "타임오버"
            battle.checkWin() -> "승리"
            else -> "패배"
        }

        Logger.d("전투종료 : $message")

        return message
    }
}
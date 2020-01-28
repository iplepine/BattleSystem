package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.common.Logger

object BattleSimulator {
    const val TIMEOUT = 1000 * 100

    fun simulate(battle: Battle): String {
        var totalTime = 0L
        var timeOver = false

        while (!battle.checkFinish()) {
            battle.updateTime(Battle.GAME_SPEED)

            if (battle.useRealTime) {
                Thread.sleep(Battle.GAME_SPEED)
            }

            totalTime += Battle.GAME_SPEED
            if (TIMEOUT < totalTime) {
                timeOver = true
                break
            }
        }

        val message = when {
            timeOver -> "Time Over"
            battle.checkWin() -> "Win"
            else -> "Lose"
        }

        Logger.d("finish a battle: $message")

        return message
    }
}
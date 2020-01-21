package com.zs.battlesystem.data.battle

object BattleSimulator {
    fun simulate(battle: Battle): String {
        var totalTime = 0L
        var timeOver = false

        while (!battle.isFinish()) {
            battle.update(Battle.GAME_SPEED)
            totalTime += Battle.GAME_SPEED

            if (1000 * 1000 * 1000 < totalTime) {
                timeOver = true
                break
            }
        }

        val message = when {
            timeOver -> "타임오버"
            battle.checkWin() -> "승리"
            else -> "패배"
        }

        return message
    }
}
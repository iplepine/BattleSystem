package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.battle.controller.UserInputTimer
import com.zs.battlesystem.data.battle.unit.BattleUnit
import com.zs.battlesystem.data.common.Logger
import com.zs.battlesystem.data.common.MonoBehaviour
import com.zs.battlesystem.data.user.User

class Battle : MonoBehaviour() {
    companion object {
        const val GAME_SPEED = 1000L// / 60L    // 1 프레임 당 시간 흐름
    }

    var useRealTime = false
    private var isRunning = true

    var battleState = BattleState.NONE

    var battleUnits = ArrayList<BattleUnit>()

    override fun updateTime(time: Long) {
        if (!isRunning) {
            return
        }

        when (battleState) {
            // 유저의 인풋 기다리는 중 이라 전투 시간 멈춤
            BattleState.INPUT -> {
                Logger.d("update time from battle : input")
                updateInputTime(time)
                Logger.d("")
            }

            // 유닛 행동 할 때까지 전투 시간 흐름
            BattleState.NONE -> {
                var readyUnits = getReadyUnits()
                if (readyUnits.isEmpty()) {
                    timePast(time)
                } else {
                    waitInput(readyUnits[0])
                }
            }
        }
    }

    private fun timePast(time: Long) {
        battleUnits.forEach { it.updateTime(time) }
    }

    private fun waitInput(unit: BattleUnit) {
        if (battleState != BattleState.NONE) {
            return
        }
        battleState = BattleState.INPUT

        unit.onTurn(this)
    }

    fun onFinishInput() {
        battleState = BattleState.NONE
        UserInputTimer.clear()
        //Logger.d("- finish input -\n")
    }

    private fun updateInputTime(time: Long) {
        UserInputTimer.timePast(time)
        if (UserInputTimer.isTimeOver()) {
            passTurn()
        }
    }

    private fun passTurn() {
        UserInputTimer.clear()
        battleState = BattleState.NONE
        Logger.d("pass turn")
    }

    fun findUnit(id: String): BattleUnit? {
        return battleUnits.find { it.id == id }
    }

    private fun getReadyUnits(): List<BattleUnit> {
        return battleUnits.filter {
            it.canAction()
        }.shuffled()
    }

    fun checkFinish(): Boolean {
        return checkWin() || checkLose()
    }

    /**
     * 내 유닛이 하나라도 살고, 적이 모두 죽었을 경우 승리
     */
    fun checkWin(): Boolean {
        var isDieAllMyUnits = true
        var isDieAllEnemies = true

        battleUnits.forEach {
            // 내 유닛일 때, 모두가 죽었는지 체크
            if (it.isMine(User.id) && !it.isDie()) {
                isDieAllMyUnits = false
            }

            // 내 유닛이 아닐 때, 하나라도 살았는지 체크
            if (it.isEnemy(User.id) && !it.isDie()) {
                return false
            }
        }

        return return !isDieAllMyUnits && isDieAllEnemies
    }

    /**
     * 내 유닛이 모두 죽었을 때 패배
     */
    fun checkLose(): Boolean {
        battleUnits.forEach {
            if (User.isMine(it.owner) && !it.isDie()) {
                return false
            }
        }

        return true
    }
}
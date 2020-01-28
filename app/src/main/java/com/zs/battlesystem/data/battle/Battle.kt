package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.battle.unit.BattleUnit
import com.zs.battlesystem.data.common.Logger
import com.zs.battlesystem.data.common.MonoBehaviour
import com.zs.battlesystem.data.user.User
import io.reactivex.disposables.CompositeDisposable

class Battle : MonoBehaviour() {
    companion object {
        const val MAX_TUREN = 100
        const val GAME_SPEED = 1000L// / 60L    // 1 프레임 당 시간 흐름
        const val MAX_WATING_TIME = 10 * 1000L    // 입력 대기 시간 10초
    }

    var useRealTime = false
    var isRunning = true

    var battleState = BattleState.NONE

    var waitingTime = 0L

    var battleUnits = ArrayList<BattleUnit>()
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

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
        waitingTime = 0
        Logger.d("- finish input -\n")
    }

    private fun updateInputTime(time: Long) {
        waitingTime += time
        if (MAX_WATING_TIME < waitingTime) {
            passTurn()
        }
        if (waitingTime % 1000 == 0L) {
            Logger.d("waiting input ... ${waitingTime / 1000}")
        }
    }

    private fun passTurn() {
        waitingTime = 0
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
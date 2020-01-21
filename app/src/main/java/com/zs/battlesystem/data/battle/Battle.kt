package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.battle.unit.BattleUnit
import com.zs.battlesystem.data.common.GameObject
import com.zs.battlesystem.data.event.BaseEvent
import com.zs.battlesystem.data.user.User
import io.reactivex.subjects.PublishSubject

class Battle : GameObject() {
    companion object {
        const val MAX_TUREN = 100
        const val GAME_SPEED = 1000 / 60L    // 1 프레임 당 시간 흐름
        const val MAX_WATING_TIME = 10 * 1000L    // 입력 대기 시간 10초
    }

    var battleState = BattleState.INPUT

    var battleTime = 0L
    var waitingTime = 0L

    var battleUnits = ArrayList<BattleUnit>()

    val eventSubject = PublishSubject.create<BaseEvent>()

    fun clear() {
        eventSubject.onComplete()
    }

    override fun updateTime(time: Long) {
        when (battleState) {
            // 유저의 인풋 기다리는 중 이라 전투 시간 멈춤
            BattleState.INPUT -> {
                updateInputTime(time)
            }

            // 애니메이션 진행 중이라 전투 시간 멈춤
            BattleState.ACTION -> {
            }

            // 유닛 행동 할 때까지 전투 시간 흐름
            BattleState.NONE -> {
                if (0 < getNextUnit().delay) {
                    timePast(time)
                } else {
                    battleState = BattleState.INPUT
                }
            }
        }
    }

    private fun timePast(time: Long) {
        waitingTime += time

        battleUnits.forEach { it.updateTime(time) }
    }

    private fun updateInputTime(time: Long) {
        if (MAX_WATING_TIME < waitingTime) {
            passTurn()
        } else {
            waitingTime += time
        }
    }

    private fun passTurn() {
        waitingTime = 0
        battleState = BattleState.NONE
    }

    fun getNextUnit(): BattleUnit {
        require(battleUnits.isNotEmpty())
        battleUnits.sortBy { it.delay }
        return battleUnits[0]
    }

    fun isFinish(): Boolean {
        return checkWin() || checkLose()
    }

    /**
     * 내 유닛이 하나라도 살고, 적이 모두 죽었을 경우 승리
     */
    fun checkWin(): Boolean {
        var isDieAllMyUnits = true
        var isDieAllEnemies = true

        battleUnits.forEach {
            // 내 유닛일 때
            if (User.isMine(it.owner) && !it.isDie()) {
                isDieAllMyUnits = false
            }

            // 내 유닛이 아닐 때
            if (!User.isMine(it.owner) && !it.isDie()) {
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
package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.battle.unit.BattleUnit
import com.zs.battlesystem.data.common.GameObject
import com.zs.battlesystem.data.common.Logger
import com.zs.battlesystem.data.event.BaseEvent
import com.zs.battlesystem.data.user.User
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class Battle : GameObject() {
    companion object {
        const val MAX_TUREN = 100
        const val GAME_SPEED = 1000L// / 60L    // 1 프레임 당 시간 흐름
        const val MAX_WATING_TIME = 10 * 1000L    // 입력 대기 시간 10초
    }

    var isRunning = true

    var battleState = BattleState.NONE

    var waitingTime = 0L

    var battleUnits = ArrayList<BattleUnit>()

    val eventSubject = PublishSubject.create<BaseEvent>()
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val messageSubject = PublishSubject.create<String>()

    fun init() {
        eventSubject.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onReceiveEvent(it)
            }, {
                onErrorEvent(it)
            }, {
                onCompleteEvent()
            })
    }

    private fun clear() {
        compositeDisposable.clear()
    }

    override fun updateTime(time: Long) {
        if (!isRunning) {
            return
        }
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
                val activeUnit = getActiveUnit()
                if (activeUnit == null) {
                    timePast(time)
                } else {
                    waitInput(activeUnit)
                }
            }
        }
    }

    private fun timePast(time: Long) {
        waitingTime += time
        battleUnits.forEach { it.updateTime(time) }
    }

    private fun waitInput(unit: BattleUnit) {
        battleState = BattleState.INPUT

        Completable.fromAction {
            unit.onTurn(this)
        }.subscribe {
            onReceiveInput()
        }
    }

    private fun onReceiveInput() {
        battleState = BattleState.NONE
    }

    private fun updateInputTime(time: Long) {
        if (waitingTime % 1000 == 0L) {
            Logger.d("waiting input ... ${waitingTime / 1000}")
        }

        if (MAX_WATING_TIME < waitingTime) {
            passTurn()
        } else {
            waitingTime += time
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

    fun getActiveUnit(): BattleUnit? {
        return battleUnits.filter {
            !it.isDie() && it.turnDelay <= 0
        }.shuffled()[0]
    }

    fun getNextUnit(): BattleUnit {
        return battleUnits.filter {
            !it.isDie()
        }.sortedBy {
            it.turnDelay
        }[0]
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

    private fun onReceiveEvent(event: BaseEvent) {
        if (battleState != BattleState.INPUT) {
            return
        }

        battleState = BattleState.ACTION

        when (event.type) {
            BaseEvent.CONTROL -> {
                // TODO 컨트롤에 대한 처리 해야하는데...
                return
            }
            BaseEvent.BATTLE ->
                onReceiveEvent(event)
        }
    }

    private fun onErrorEvent(error: Throwable) {
        Logger.e(error.toString())
    }

    private fun onCompleteEvent() {
        clear()
    }
}
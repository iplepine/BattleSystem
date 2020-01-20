package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.battle.unit.BattleUnit
import io.reactivex.subjects.PublishSubject

class Battle {
    var battleState = BattleState.READY
    var battleTime = 0L

    var battleUnits = ArrayList<BattleUnit>()

    val eventSubject = PublishSubject.create<BattleEvent>()

    fun init() {
    }

    fun clear() {
        eventSubject.onComplete()
    }

    fun update(time: Long) {
        battleTime += time
    }

    fun getNextUnit(): BattleUnit? {
        require(battleUnits.isNotEmpty())
        battleUnits.sortBy { it.delay }
        return battleUnits[0]
    }
}
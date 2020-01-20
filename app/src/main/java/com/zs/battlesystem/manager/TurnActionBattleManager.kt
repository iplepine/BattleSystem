package com.zs.battlesystem.manager

import com.zs.battlesystem.data.battle.unit.BattleUnit

class TurnActionBattleManager() {
    var battleState = BattleState.READY
    var battleTime = 0L

    var battleUnits = ArrayList<BattleUnit>()

    fun getNextUnit(): BattleUnit? {
        require(battleUnits.isNotEmpty())
        battleUnits.sortBy { it.delay }
        return battleUnits[0]
    }
}
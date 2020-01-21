package com.zs.battlesystem.data.battle.unit

import com.zs.battlesystem.data.battle.unit.stat.UnitState

class BattleUnit(val base: BaseUnit) {
    var owner = "enemy"
    var delay = 0L
    var state = UnitState.DELAY

    fun isDie(): Boolean {
        return state == UnitState.DIE
    }
}
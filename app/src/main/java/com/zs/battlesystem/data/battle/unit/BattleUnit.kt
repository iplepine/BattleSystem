package com.zs.battlesystem.data.battle.unit

import com.zs.battlesystem.data.battle.unit.stat.UnitState

class BattleUnit(val base: BaseUnit) {
    var owner = 0L
    var delay = 0L
    var state = UnitState.DELAY
}
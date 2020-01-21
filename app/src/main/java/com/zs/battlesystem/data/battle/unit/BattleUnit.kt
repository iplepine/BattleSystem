package com.zs.battlesystem.data.battle.unit

import com.zs.battlesystem.data.battle.unit.stat.UnitState
import com.zs.battlesystem.data.common.GameObject
import kotlin.math.max

class BattleUnit(val base: BaseUnit) : GameObject() {
    var owner = "enemy"
    var delay = 0L
    var state = UnitState.AFTER_DELAY

    fun isDie(): Boolean {
        return state == UnitState.DIE
    }

    override fun updateTime(time: Long) {
        delay = max(0L, delay - time)

        if (delay < 0) {
            when (state) {
                UnitState.READY -> return
                UnitState.CASTING -> return
                UnitState.EFFECT -> return
                UnitState.AFTER_DELAY -> return
                UnitState.STUN -> return
            }
            state = UnitState.READY
        }
    }
}
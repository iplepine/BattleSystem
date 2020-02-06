package com.zs.battlesystem.data.battle.skill.continuous

import com.zs.battlesystem.data.battle.unit.BattleUnit

abstract class ContinuousEffect(
    val duration: Long,
    val effectDelay: Long = duration
) {
    var remainingTime: Long = duration
    var delay: Long = 0L

    open fun onAdd(target: BattleUnit) {
        remainingTime = duration
        effect()
    }

    abstract fun onClear(targets: BattleUnit)

    open fun effect() {
        delay = effectDelay
    }

    fun isEnd(): Boolean {
        return remainingTime <= 0
    }

    open fun updateTime(time: Long) {
        remainingTime -= time

        delay -= time
        if (delay <= 0) {
            effect()
        }
    }
}
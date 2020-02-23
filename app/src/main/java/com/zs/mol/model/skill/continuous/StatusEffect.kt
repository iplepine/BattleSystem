package com.zs.mol.model.skill.continuous

import com.zs.mol.model.unit.BattleUnit

abstract class StatusEffect(
    val duration: Long,
    val effectDelay: Long = duration
) {
    var remainingTime: Long = duration
    var delay: Long = 0L
    var cancelable = true

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
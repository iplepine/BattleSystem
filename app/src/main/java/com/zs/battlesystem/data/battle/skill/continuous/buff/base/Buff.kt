package com.zs.battlesystem.data.battle.skill.continuous.buff.base

import com.zs.battlesystem.data.battle.skill.continuous.ContinuousEffect
import com.zs.battlesystem.data.battle.unit.BattleUnit

abstract class Buff(duration: Long, effectDelay: Long) : ContinuousEffect(
    duration,
    effectDelay
) {
    override fun onAdd(target: BattleUnit) {
        super.onAdd(target)
        target.addBuff(this)
    }

    override fun onClear(target: BattleUnit) {
        target.clearBuff(this)
    }
}

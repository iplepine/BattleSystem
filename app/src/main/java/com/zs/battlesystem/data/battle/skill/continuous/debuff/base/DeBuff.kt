package com.zs.battlesystem.data.battle.skill.continuous.debuff.base

import com.zs.battlesystem.data.battle.skill.continuous.ContinuousEffect
import com.zs.battlesystem.data.battle.unit.BattleUnit

abstract class DeBuff(duration: Long, effectDelay: Long) : ContinuousEffect(
    duration,
    effectDelay
) {
    override fun onAdd(target: BattleUnit) {
        super.onAdd(target)
        target.addDeBuff(this)
    }

    override fun onClear(target: BattleUnit) {
        target.clearDeBuff(this)
    }
}
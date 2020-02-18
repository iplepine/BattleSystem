package com.zs.mol.model.battle.skill.continuous.buff.base

import com.zs.mol.model.battle.skill.continuous.StatusEffect
import com.zs.mol.model.battle.unit.BattleUnit

abstract class Buff(duration: Long, effectDelay: Long) : StatusEffect(
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

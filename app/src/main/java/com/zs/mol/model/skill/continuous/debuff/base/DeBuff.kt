package com.zs.mol.model.skill.continuous.debuff.base

import com.zs.mol.model.skill.continuous.StatusEffect
import com.zs.mol.model.unit.BattleUnit

abstract class DeBuff(duration: Long, effectDelay: Long) : StatusEffect(
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
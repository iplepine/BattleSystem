package com.zs.mol.model.battle.skill.active.control

import com.zs.mol.model.battle.skill.Skill
import com.zs.mol.model.battle.skill.continuous.StatusEffect
import com.zs.mol.model.battle.unit.BattleUnit
import io.reactivex.subjects.PublishSubject

abstract class StateControlSkill(id: Int) : Skill(id) {
    val effects = ArrayList<StatusEffect>()

    init {
        initEffects()
    }

    protected abstract fun initEffects()

    protected fun addEffect(effect: StatusEffect) {
        effects.add(effect)
    }

    override fun onEffect(
        user: BattleUnit,
        target: BattleUnit,
        messageSubject: PublishSubject<String>?
    ) {
        effects.forEach {
            it.onAdd(target)
        }
    }
}
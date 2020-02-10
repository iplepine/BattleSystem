package com.zs.battlesystem.model.battle.skill.active.control

import com.zs.battlesystem.model.battle.skill.Skill
import com.zs.battlesystem.model.battle.skill.continuous.ContinuousEffect
import com.zs.battlesystem.model.battle.unit.BattleUnit
import io.reactivex.subjects.PublishSubject

abstract class StateControlSkill : Skill() {
    val effects = ArrayList<ContinuousEffect>()

    init {
        initEffects()
    }

    protected abstract fun initEffects()

    protected fun addEffect(effect: ContinuousEffect) {
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
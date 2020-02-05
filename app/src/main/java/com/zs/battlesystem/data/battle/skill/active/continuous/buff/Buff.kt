package com.zs.battlesystem.data.battle.skill.active.continuous.buff

import com.zs.battlesystem.data.battle.skill.active.continuous.ContinuousSkill
import com.zs.battlesystem.data.battle.unit.BattleUnit
import io.reactivex.subjects.PublishSubject

abstract class Buff : ContinuousSkill() {
    override fun onEffect(
        user: BattleUnit,
        target: BattleUnit,
        messageSubject: PublishSubject<String>?
    ) {
        super.onEffect(user, target, messageSubject)
        target.buffs.add(this)
    }

    override fun onClear(target: List<BattleUnit>) {
        target.forEach { it.removeBuff(this) }
    }
}
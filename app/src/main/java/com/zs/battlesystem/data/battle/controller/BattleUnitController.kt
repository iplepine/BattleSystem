package com.zs.battlesystem.data.battle.controller

import com.zs.battlesystem.data.battle.Battle
import com.zs.battlesystem.data.battle.skill.Skill
import com.zs.battlesystem.data.battle.unit.BattleUnit
import com.zs.battlesystem.data.event.BaseEvent
import io.reactivex.subjects.PublishSubject

object BattleUnitController {

    open fun onReceiveUnitControl(
        unit: BattleUnit,
        battle: Battle,
        controllEventSubject: PublishSubject<BaseEvent>
    ) {
        val availableSkills = unit.base.skills.filter { it.coolDown <= 0 }

        val skill = pickMostEffectiveSkill(availableSkills)
        skill?.onEffect(unit, battle.battleUnits, controllEventSubject)
    }

    private fun pickMostEffectiveSkill(skills: List<Skill>): Skill? {
        var bestEffect = 0.0
        var bestSkill: Skill? = null
        skills.forEach {
            if (bestEffect < it.getExpectEffect()) {
                bestEffect = it.getExpectEffect()
                bestSkill = it
            }
        }

        return bestSkill
    }
}
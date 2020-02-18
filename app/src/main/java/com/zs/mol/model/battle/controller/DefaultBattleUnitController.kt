package com.zs.mol.model.battle.controller

import com.zs.mol.model.battle.Battle
import com.zs.mol.model.battle.BattleFunction
import com.zs.mol.model.battle.skill.Skill
import com.zs.mol.model.battle.unit.BattleUnit
import com.zs.mol.model.common.Logger
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object DefaultBattleUnitController {

    open fun controlUnit(
        unit: BattleUnit,
        battle: Battle
    ) {
        Logger.d("(${this.javaClass.simpleName} is controlling this unit.)")

        if (battle.useRealTime) {
            GlobalScope.launch {
                delay(3900L)
                useSkill(unit, battle)
            }
        } else {
            useSkill(unit, battle)
        }
    }

    private fun useSkill(unit: BattleUnit, battle: Battle) {
        val availableSkills = unit.base.skills.filter { it.coolDown <= 0 }

        Logger.d("Available skill count : ${availableSkills.size}/${unit.base.skills.count()}")

        val skill = pickMostEffectiveSkill(availableSkills)
        skill?.also {
            val target = BattleFunction.findTarget(
                unit,
                battle.battleUnits,
                skill.targetType,
                skill.targetCount
            )
            unit.startCasting(skill, target)
        }

        battle.onFinishInput()
    }

    private fun pickMostEffectiveSkill(skills: List<Skill>): Skill? {
        return skills.sortedByDescending { it.getExpectEffect() }[0]
        //Logger.d("The most effective skill : ${bestSkill?.name ?: "NONE"}")
    }
}
package com.zs.mol.model.battle.controller

import com.zs.mol.model.battle.Battle
import com.zs.mol.model.battle.BattleFunction
import com.zs.mol.model.skill.UnitSkill
import com.zs.mol.model.unit.BattleUnit
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
        val availableSkills =
            unit.skills.filter { it.getSkill() != null && it.status.coolDown <= 0 }

        Logger.d("Available skill count : ${availableSkills.size}/${unit.skills.count()}")

        val skill = pickMostEffectiveSkill(availableSkills)
        skill?.also {
            val target = BattleFunction.findTarget(
                unit,
                battle.battleUnits,
                skill.getSkill().skillData.targetType,
                skill.getSkill().skillData.targetCount
            )
            unit.startCasting(skill, target)
        }

        battle.onFinishInput()
    }

    private fun pickMostEffectiveSkill(skills: List<UnitSkill>): UnitSkill? {
        return skills.sortedByDescending { it.getSkill().getExpectEffect() }[0]
        //Logger.d("The most effective skill : ${bestSkill?.name ?: "NONE"}")
    }
}
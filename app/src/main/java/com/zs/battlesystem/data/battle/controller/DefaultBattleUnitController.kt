package com.zs.battlesystem.data.battle.controller

import com.zs.battlesystem.data.battle.Battle
import com.zs.battlesystem.data.battle.skill.Skill
import com.zs.battlesystem.data.battle.unit.BattleUnit
import com.zs.battlesystem.data.common.Logger

object DefaultBattleUnitController {

    open fun onReceiveUnitControl(
        unit: BattleUnit,
        battle: Battle
    ) {
        Logger.d("(${this.javaClass.simpleName} 이 유닛을 조작합니다.)")
        val availableSkills = unit.base.skills.filter { it.coolDown <= 0 }
        Logger.d("사용 가능한 스킬 수 : ${availableSkills.size}")
        val skill = pickMostEffectiveSkill(availableSkills)
        skill?.also {
            unit.startCasting(skill, battle.battleUnits)
        }
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

        Logger.d("가장 효과적인 스킬 : ${bestSkill?.name ?: "없음"}")
        return bestSkill
    }
}
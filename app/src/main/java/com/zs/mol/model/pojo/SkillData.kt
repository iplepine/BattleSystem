package com.zs.mol.model.pojo

import com.zs.mol.model.battle.skill.Skill

data class SkillData(
    var name: String = "",
    var description: String = "",
    var type: String = "",
    var castingTime: Long = 0L,
    var effectTime: Long = 0L,
    var afterDelay: Long = 0L,
    var coolTime: Long = 1000L,
    var targetType: Skill.TargetType = Skill.TargetType.RANDOM,
    var targetCount: Int = 1
)
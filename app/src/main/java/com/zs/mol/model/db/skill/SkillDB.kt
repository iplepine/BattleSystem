package com.zs.mol.model.db.skill

import com.zs.mol.model.db.skill.SkillDB.Key.Bash
import com.zs.mol.model.db.skill.SkillDB.Key.EnhanceAttack
import com.zs.mol.model.db.skill.SkillDB.Key.NormalAttack
import com.zs.mol.model.pojo.SkillData
import com.zs.mol.model.skill.Skill

object SkillDB : HashMap<Int, SkillData>() {
    // attack skill 10000~19999
    // defence skill 20000~29999
    // buff skill 30000~39999

    object Key {
        const val NormalAttack = 10000
        const val Bash = 10001

        const val EnhanceAttack = 30000
    }

    init {
        this[NormalAttack] = SkillData(
            name = "Normal Attack",
            description = "",
            type = "active",
            castingTime = 0,
            effectTime = 0,
            afterDelay = 0,
            coolTime = 0,
            targetType = Skill.TargetType.ENEMY,
            targetCount = 1
        )

        this[Bash] = SkillData(
            name = "Bash",
            description = "",
            type = "active",
            castingTime = 0,
            effectTime = 0,
            afterDelay = 0,
            coolTime = 1000 * 10L,   // 쿨타임 10초
            targetType = Skill.TargetType.ENEMY,
            targetCount = 1
        )

        this[EnhanceAttack] = SkillData(
            name = "Enhance Attack",
            description = "",
            type = "active",
            castingTime = 0,
            effectTime = 0,
            afterDelay = 0,
            coolTime = 0,
            targetType = Skill.TargetType.ALLIES,
            targetCount = 1
        )
    }

    fun validId(id: Int): Boolean {
        return this.contains(id)
    }

    fun getSkillData(id: Int): SkillData {
        return get(id) ?: SkillData()
    }
}

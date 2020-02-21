package com.zs.mol.model.db

import com.zs.mol.model.battle.skill.Skill
import com.zs.mol.model.pojo.SkillData

object SkillDB {
    // attack skill 10000~19999
    // defence skill 20000~29999
    // buff skill 30000~39999

    const val NormalAttack = 10000
    const val Bash = 10001

    const val EnhanceAttack = 30000

    val db = HashMap<Int, SkillData>()

    init {
        db[NormalAttack] = SkillData(
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

        db[Bash] = SkillData(
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

        db[EnhanceAttack] = SkillData(
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
        return db.contains(id)
    }

    fun getSkillData(id: Int): SkillData {
        return db[id] ?: SkillData()
    }
}

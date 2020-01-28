package com.zs.battlesystem.data.manager

import com.zs.battlesystem.data.battle.skill.Skill
import com.zs.battlesystem.data.battle.skill.active.Bash
import com.zs.battlesystem.data.battle.skill.active.NormalAttack

object SkillManager {
    private val list = HashMap<String, Skill>()

    fun initSkills() {
        putSkill(NormalAttack())
        putSkill(Bash())
    }

    fun putSkill(skill: Skill) {
        list[skill.name] = skill
    }

    fun getSkill(name: String): Skill? {
        return list[name]
    }
}
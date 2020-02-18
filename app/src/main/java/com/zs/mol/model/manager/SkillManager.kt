package com.zs.mol.model.manager

import com.zs.mol.model.battle.skill.Skill
import com.zs.mol.model.battle.skill.active.Bash
import com.zs.mol.model.battle.skill.active.NormalAttack

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
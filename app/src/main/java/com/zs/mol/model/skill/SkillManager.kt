package com.zs.mol.model.skill

import com.zs.mol.model.skill.active.Bash
import com.zs.mol.model.skill.active.NormalAttack

object SkillManager {
    private val skills: HashMap<Int, Skill> = HashMap()

    fun getSkill(id: Int): Skill? {
        return skills[id]
    }

    init {
        skills[NormalAttack.id] = NormalAttack
        skills[Bash.id] = Bash
        skills[NormalAttack.id] = NormalAttack
    }

    fun putSkill(key: Int, skill: Skill) {
        skills[key] = skill
    }
}
package com.zs.mol.model.manager

import com.zs.mol.model.battle.skill.Skill
import com.zs.mol.model.battle.skill.active.Bash
import com.zs.mol.model.battle.skill.active.NormalAttack

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
}
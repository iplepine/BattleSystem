package com.zs.mol.model.skill.active.control

import com.zs.mol.model.skill.continuous.buff.base.StatBuff
import com.zs.mol.model.stat.SecondStat
import com.zs.mol.model.db.skill.SkillDB

class EnhanceAttack : StateControlSkill(SkillDB.Key.EnhanceAttack) {
    override fun initEffects() {
        addEffect(StatBuff(SecondStat.ATK, 30.0, false, 30 * 1000L))
    }
}
package com.zs.mol.model.battle.skill.active.control

import com.zs.mol.model.battle.skill.continuous.buff.base.StatBuff
import com.zs.mol.model.battle.stat.SecondStat
import com.zs.mol.model.db.SkillDB

class EnhanceAttack : StateControlSkill(SkillDB.EnhanceAttack) {
    override fun initEffects() {
        addEffect(StatBuff(SecondStat.ATK, 30.0, false, 30 * 1000L))
    }
}
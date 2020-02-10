package com.zs.battlesystem.model.battle.skill.active.control

import com.zs.battlesystem.model.battle.skill.continuous.buff.base.StatBuff
import com.zs.battlesystem.model.battle.stat.SecondStat

class EnhanceAttack : StateControlSkill() {
    init {
        targetType = TargetType.ALLIES
        targetCount = 1
    }

    override fun initEffects() {
        addEffect(StatBuff(SecondStat.ATK, 30.0, false, 30 * 1000L))
    }
}
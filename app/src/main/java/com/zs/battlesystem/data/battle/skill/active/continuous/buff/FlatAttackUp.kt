package com.zs.battlesystem.data.battle.skill.active.continuous.buff

import com.zs.battlesystem.data.battle.skill.active.continuous.buff.base.StatBuff
import com.zs.battlesystem.data.battle.stat.SecondStat.Companion.ATK

class FlatAttackUp : StatBuff() {
    init {
        name = "Attack Up"

        coolTime = 30L
        duration = 10L

        flatStat.secondStat.values[ATK] = 30.0
    }
}
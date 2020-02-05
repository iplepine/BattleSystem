package com.zs.battlesystem.data.battle.skill.buff

import com.zs.battlesystem.data.battle.skill.buff.base.StatBuff
import com.zs.battlesystem.data.battle.stat.SecondStat.Companion.ATK

class FlatAttackUp : StatBuff() {
    init {
        name = "Attack Up"

        coolTime = 3000L
        duration = 1000L

        flatStat.secondStat.values[ATK] = 30.0
    }
}
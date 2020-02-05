package com.zs.battlesystem.data.battle.skill.active.continuous.buff

import com.zs.battlesystem.data.battle.skill.active.continuous.buff.base.StatBuff
import com.zs.battlesystem.data.battle.stat.SecondStat.Companion.ATK

class PercentAttackUp : StatBuff() {
    init {
        name = "Attack Up"

        coolTime = 30L
        duration = 10L

        percentStat.secondStat.values[ATK] = 0.3
    }
}
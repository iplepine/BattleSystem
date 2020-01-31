package com.zs.battlesystem.data.battle.skill.buff

import com.zs.battlesystem.data.battle.skill.buff.base.StatBuff
import com.zs.battlesystem.data.battle.stat.SecondStat.Companion.DEF

class PercentDefenceUp() : StatBuff() {
    init {
        name = "Defence Up"

        coolTime = 3000L
        duration = 1000L

        percentStat.secondStat.values[DEF] = 0.3
    }
}
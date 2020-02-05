package com.zs.battlesystem.data.battle.skill.active.continuous.buff

import com.zs.battlesystem.data.battle.skill.active.continuous.buff.base.StatBuff
import com.zs.battlesystem.data.battle.stat.SecondStat.Companion.DEF

class PercentDefenceUp : StatBuff() {
    init {
        name = "Defence Up"

        coolTime = 60L
        duration = 30L

        percentStat.secondStat.values[DEF] = 0.3
    }
}
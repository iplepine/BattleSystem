package com.zs.battlesystem.data.battle.skill.active.continuous.buff.base

import com.zs.battlesystem.data.battle.skill.active.continuous.ContinuousSkill
import com.zs.battlesystem.data.battle.stat.Stat

abstract class StatBuff : ContinuousSkill() {
    val flatStat: Stat = Stat()
    val percentStat: Stat = Stat()
}
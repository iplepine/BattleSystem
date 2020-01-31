package com.zs.battlesystem.data.battle.skill.buff.base

import com.zs.battlesystem.data.battle.stat.Stat

abstract class StatBuff : Buff() {
    val flatStat: Stat = Stat()
    val percentStat: Stat = Stat()
}
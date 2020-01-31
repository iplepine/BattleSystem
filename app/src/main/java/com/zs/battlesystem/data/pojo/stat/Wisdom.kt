package com.zs.battlesystem.data.pojo.stat

import com.zs.battlesystem.data.battle.stat.BaseStat.Companion.WIS
import com.zs.battlesystem.data.pojo.Stat

object Wisdom : Stat() {
    init {
        key = WIS
        name = "지혜"
    }
}
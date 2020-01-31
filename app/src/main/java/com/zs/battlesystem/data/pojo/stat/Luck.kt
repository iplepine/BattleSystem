package com.zs.battlesystem.data.pojo.stat

import com.zs.battlesystem.data.battle.stat.BaseStat.Companion.LUCK
import com.zs.battlesystem.data.pojo.Stat

object Luck : Stat() {
    init {
        key = LUCK
        name = "운"
    }
}
package com.zs.battlesystem.data.pojo.stat

import com.zs.battlesystem.data.battle.stat.BaseStat.Companion.STR
import com.zs.battlesystem.data.pojo.Stat

object Strength : Stat() {
    init {
        key = STR
        name = "힘"
    }
}
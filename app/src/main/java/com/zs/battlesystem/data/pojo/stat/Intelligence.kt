package com.zs.battlesystem.data.pojo.stat

import com.zs.battlesystem.data.battle.stat.BaseStat.Companion.INT
import com.zs.battlesystem.data.pojo.Stat

object Intelligence : Stat() {
    init {
        key = INT
        name = "지능"
    }
}
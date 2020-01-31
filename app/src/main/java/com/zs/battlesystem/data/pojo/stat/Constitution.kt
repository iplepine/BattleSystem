package com.zs.battlesystem.data.pojo.stat

import com.zs.battlesystem.data.battle.stat.BaseStat.Companion.CON
import com.zs.battlesystem.data.pojo.Stat

object Constitution : Stat() {
    init {
        key = CON
        name = "체질"
    }
}
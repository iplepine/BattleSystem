package com.zs.battlesystem.data.pojo.stat

import com.zs.battlesystem.data.battle.stat.BaseStat.Companion.CHA
import com.zs.battlesystem.data.pojo.Stat

object Charisma : Stat() {
    init {
        key = CHA
        name = "매력"
    }
}
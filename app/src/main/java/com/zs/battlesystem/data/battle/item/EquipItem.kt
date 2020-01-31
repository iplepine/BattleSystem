package com.zs.battlesystem.data.battle.item

import com.zs.battlesystem.data.battle.stat.Stat

class EquipItem(name: String) : Item(name) {
    lateinit var equipType: String
    var flatStat: Stat = Stat()
    var percentStat: Stat = Stat()
}
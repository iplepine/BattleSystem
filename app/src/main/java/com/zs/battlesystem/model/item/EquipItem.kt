package com.zs.battlesystem.model.item

import com.zs.battlesystem.model.battle.stat.Stat

class EquipItem(name: String) : Item(name) {
    lateinit var equipType: String
    var flatStat: Stat = Stat()
    var percentStat: Stat = Stat()
}
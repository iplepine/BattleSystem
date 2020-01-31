package com.zs.battlesystem.data.battle.item

import com.zs.battlesystem.data.battle.stat.Stat

class EquipItem(name: String) : Item(name) {
    var flatStat: Stat = Stat()
    var percentStat: Stat = Stat()
}
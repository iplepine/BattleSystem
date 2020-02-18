package com.zs.mol.model.item

import com.zs.mol.model.battle.stat.Stat

class EquipItem(name: String) : Item(name) {
    lateinit var equipType: String
    var flatStat: Stat = Stat()
    var percentStat: Stat = Stat()
}
package com.zs.mol.model.item

import com.zs.mol.model.stat.Stat

class EquipItem(name: String) : InventoryItem(name) {
    lateinit var equipType: String
    var flatStat: Stat = Stat()
    var percentStat: Stat = Stat()
}
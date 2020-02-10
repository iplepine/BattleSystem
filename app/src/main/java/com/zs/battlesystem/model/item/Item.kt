package com.zs.battlesystem.model.item

import com.zs.battlesystem.model.battle.unit.BaseUnit

open class Item(val name: String, var amount: Long = 1) {
    fun addStatToUnit(unit: BaseUnit) {
    }

    class ItemStat() {
    }
}
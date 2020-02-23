package com.zs.mol.model.item

import com.zs.mol.model.unit.BaseUnit

open class Item(val name: String, var amount: Long = 1) {
    fun addStatToUnit(unit: BaseUnit) {
    }

    class ItemStat() {
    }
}
package com.zs.mol.model.item

import com.zs.mol.model.unit.BaseUnit

interface Equipable {
    fun equip(unit: BaseUnit)
}
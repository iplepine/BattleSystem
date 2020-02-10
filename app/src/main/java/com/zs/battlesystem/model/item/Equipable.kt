package com.zs.battlesystem.model.item

import com.zs.battlesystem.model.battle.unit.BaseUnit

interface Equipable {
    fun equip(unit: BaseUnit)
}
package com.zs.battlesystem.data.battle.item

import com.zs.battlesystem.data.battle.unit.BaseUnit

interface Equipable {
    fun equip(unit: BaseUnit)
}
package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.battle.unit.BaseUnit
import com.zs.battlesystem.data.battle.unit.BattleUnit

object BattleUnitFactory {

    fun createTestUnit(name: String): BattleUnit {
        val baseUnit = BaseUnit().apply {
            this.name = name
        }

        return BattleUnit(baseUnit)
    }
}
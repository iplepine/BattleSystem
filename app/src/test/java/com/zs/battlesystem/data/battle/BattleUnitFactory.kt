package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.battle.stat.Stat
import com.zs.battlesystem.data.battle.unit.BaseUnitFactory
import com.zs.battlesystem.data.battle.unit.BattleUnit

object BattleUnitFactory {

    fun createTestUnit(name: String): BattleUnit {
        val baseUnit = BaseUnitFactory.create(name)
        return BattleUnit(baseUnit)
    }

    fun createTestUnit(name: String, stat: Stat): BattleUnit {
        val baseUnit = BaseUnitFactory.create(name, stat)
        return BattleUnit(baseUnit)
    }
}

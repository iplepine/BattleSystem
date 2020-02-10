package com.zs.battlesystem.model.battle

import com.zs.battlesystem.model.battle.stat.Stat
import com.zs.battlesystem.model.battle.unit.BaseUnitFactory
import com.zs.battlesystem.model.battle.unit.BattleUnit

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

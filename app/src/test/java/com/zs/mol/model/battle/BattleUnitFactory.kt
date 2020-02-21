package com.zs.mol.model.battle

import com.zs.mol.model.battle.unit.BaseUnitFactory
import com.zs.mol.model.battle.unit.BattleUnit

object BattleUnitFactory {

    fun createTestUnit(name: String): BattleUnit {
        val baseUnit = BaseUnitFactory.create(name)
        return BattleUnit(baseUnit)
    }

}

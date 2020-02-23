package com.zs.mol.model.unit

object BattleUnitFactory {

    fun createTestUnit(name: String, owner: String = "enemy"): BattleUnit {
        val baseUnit = BaseUnitFactory.create(name).also {
            it.owner = owner
        }
        return BattleUnit(baseUnit)
    }

}

package com.zs.battlesystem.data.battle

import org.junit.Test

class BattleFunctionTest {
    private val unit1 = BattleUnitFactory.createTestUnit("유닛 1")
    private val unit2 = BattleUnitFactory.createTestUnit("유닛 2")

    @Test
    fun checkCritical() {
        unit1.base.stat.critical = 100
        unit2.base.stat.critical = 0

        assert(BattleFunction.checkCritical(unit1, unit2))
        assert(!BattleFunction.checkCritical(unit2, unit1))
    }

    @Test
    fun checkEvade() {
        unit1.base.stat.evade = 0
        unit2.base.stat.evade = 100

        assert(BattleFunction.checkEvade(unit1, unit2))
        assert(!BattleFunction.checkEvade(unit2, unit1))
    }

    @Test
    fun getDamageReduceRation() {
        assert(BattleFunction.getDamageReductionRatio(100, 0) == 0.0)
        assert(BattleFunction.getDamageReductionRatio(100, 100) == 0.0)
        assert(BattleFunction.getDamageReductionRatio(100, 500) >= 0.8)
        assert(BattleFunction.getDamageReductionRatio(100, 1000) >= 0.9)
    }
}
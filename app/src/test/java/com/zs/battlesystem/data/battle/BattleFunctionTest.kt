package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.battle.stat.SecondStat.Companion.CRI
import com.zs.battlesystem.data.battle.stat.SecondStat.Companion.EVADE
import org.junit.Test

class BattleFunctionTest {
    private val unit1 = BattleUnitFactory.createTestUnit("유닛 1")
    private val unit2 = BattleUnitFactory.createTestUnit("유닛 2")

    @Test
    fun checkCritical() {
        unit1.stat.secondStat.values[CRI] = 100.0
        unit2.stat.secondStat.values[CRI] = 0.0

        assert(BattleFunction.checkCritical(unit1, unit2))
        assert(!BattleFunction.checkCritical(unit2, unit1))
    }

    @Test
    fun checkEvade() {
        unit1.stat.secondStat.values[EVADE] = 0.0
        unit2.stat.secondStat.values[EVADE] = 100.0

        assert(BattleFunction.checkEvade(unit1, unit2))
        assert(!BattleFunction.checkEvade(unit2, unit1))
    }

    @Test
    fun getDamageReduceRation() {
        assert(BattleFunction.getDamageReductionRatio(100.0, 0.0) == 0.0)
        assert(BattleFunction.getDamageReductionRatio(100.0, 100.0) == 0.0)
        assert(BattleFunction.getDamageReductionRatio(100.0, 500.0) >= 0.8)
        assert(BattleFunction.getDamageReductionRatio(100.0, 1000.0) >= 0.9)
    }
}
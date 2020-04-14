package com.zs.mol.model.dungeon

import com.zs.mol.model.stat.BaseStat.Companion.INT
import com.zs.mol.model.unit.BattleUnitFactory
import org.junit.Test

class CheckEventTest {

    @Test
    fun check() {
        val testUnit = BattleUnitFactory.createUnit("", "test").apply {
            currentStat.baseStat[INT] = 18.0
        }

        val event = CheckEvent(INT, 6, 10)
        event.check(testUnit)
    }
}
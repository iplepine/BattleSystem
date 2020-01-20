package com.zs.battlesystem.manager

import com.zs.battlesystem.data.battle.unit.BaseUnit
import com.zs.battlesystem.data.battle.unit.BattleUnit
import org.junit.After
import org.junit.Before
import org.junit.Test

class TurnActionBattleManagerTest {
    @Before
    fun before() {

    }

    @After
    fun after() {

    }


    @Test
    fun getNextUnitTest() {
        val highDelay = BattleUnit(BaseUnit()).apply {
            delay = 1000
        }

        val lowDelay = BattleUnit(BaseUnit()).apply {
            delay = 500
        }

        var battleManager = TurnActionBattleManager().apply {
            battleUnits.add(highDelay)
            battleUnits.add(lowDelay)
        }

        assert(battleManager.getNextUnit() == lowDelay)
        print(battleManager.battleTime)
    }
}
package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.user.User
import org.junit.Test

class BattleSimulatorTest {

    private val myUnit1 = BattleUnitFactory.createTestUnit("유닛 1").apply { owner = User.id }
    private val myUnit2 = BattleUnitFactory.createTestUnit("유닛 2").apply { owner = User.id }

    private val enemyUnit1 = BattleUnitFactory.createTestUnit("적유닛 1")
    private val enemyUnit2 = BattleUnitFactory.createTestUnit("적유닛 2")

    private val battle = Battle().apply {
        battleUnits.add(myUnit1)
        battleUnits.add(myUnit2)
        battleUnits.add(enemyUnit1)
        battleUnits.add(enemyUnit2)
    }

    @Test
    fun simulateTest() {
        println("battle test : ${BattleSimulator.simulate(battle)}")
    }
}
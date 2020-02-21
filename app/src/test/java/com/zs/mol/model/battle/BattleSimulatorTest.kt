package com.zs.mol.model.battle

import com.zs.mol.model.user.UserManager
import org.junit.Test

class BattleSimulatorTest {

    private val myUnit1 = BattleUnitFactory.createTestUnit("Iplepine").apply {
        owner = UserManager.getUserId()
    }
    private val myUnit2 = BattleUnitFactory.createTestUnit("Seoty").apply {
        owner = UserManager.getUserId()
    }

    private val enemyUnit1 = BattleUnitFactory.createTestUnit("enemy1")
    private val enemyUnit2 = BattleUnitFactory.createTestUnit("enemy2")

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
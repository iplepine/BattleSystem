package com.zs.mol.model.battle

import com.zs.mol.model.unit.BattleUnitFactory
import com.zs.mol.model.user.UserManager
import org.junit.Test

class BattleSimulatorTest {

    private val myUnit1 = BattleUnitFactory.createMyUnit("Iplepine")
    private val myUnit2 = BattleUnitFactory.createMyUnit("Seoty")

    private val enemyUnit1 = BattleUnitFactory.createEnemy("enemy1")
    private val enemyUnit2 = BattleUnitFactory.createEnemy("enemy2")

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
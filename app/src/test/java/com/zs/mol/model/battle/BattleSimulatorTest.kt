package com.zs.mol.model.battle

import com.zs.mol.model.unit.BattleUnitFactory
import com.zs.mol.model.user.User
import org.junit.Test

class BattleSimulatorTest {

    private val battleUnitFactory = BattleUnitFactory()

    private val myUnit1 = battleUnitFactory.createUnit("guest", "Iplepine")
    private val myUnit2 = battleUnitFactory.createUnit("guest", "Seoty")

    private val enemyUnit1 = battleUnitFactory.createUnit("enemy", "enemy1")
    private val enemyUnit2 = battleUnitFactory.createUnit("enemy", "enemy2")

    /*private val battle = Battle().apply {
        battleUnits.add(myUnit1)
        battleUnits.add(myUnit2)
        battleUnits.add(enemyUnit1)
        battleUnits.add(enemyUnit2)
    }

    @Test
    fun simulateTest() {
        println("battle test : ${BattleSimulator.simulate(battle)}")
    }*/
}
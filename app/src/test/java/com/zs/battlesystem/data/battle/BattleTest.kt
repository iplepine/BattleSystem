package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.battle.unit.BaseUnit
import com.zs.battlesystem.data.battle.unit.BattleUnit
import com.zs.battlesystem.data.battle.unit.stat.UnitState
import com.zs.battlesystem.data.user.User
import org.junit.Test

class BattleTest {
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
    fun getNextUnitTest() {
        val highDelay = BattleUnit(BaseUnit()).apply {
            delay = 1000
        }

        val lowDelay = BattleUnit(BaseUnit()).apply {
            delay = 500
        }

        var battle = Battle().apply {
            battleUnits.add(highDelay)
            battleUnits.add(lowDelay)
        }

        assert(battle.getNextUnit() == lowDelay)
    }

    @Test
    fun checkWinOrLoseTest() {
        // 아군과 적군 둘 다 살아남았을 때
        assert(!battle.checkWin())
        assert(!battle.checkLose())

        // 아군 하나 살아남았을 때
        myUnit1.state = UnitState.DIE
        myUnit2.state = UnitState.STUN
        enemyUnit1.state = UnitState.DIE
        enemyUnit2.state = UnitState.DIE
        assert(battle.checkWin())
        assert(!battle.checkLose())

        // 적군 하나 살아남았을 때
        myUnit1.state = UnitState.DIE
        myUnit2.state = UnitState.DIE
        enemyUnit1.state = UnitState.READY
        enemyUnit2.state = UnitState.DIE
        assert(!battle.checkWin())
        assert(battle.checkLose())

        // 모두 죽었을 때
        myUnit1.state = UnitState.DIE
        myUnit2.state = UnitState.DIE
        enemyUnit1.state = UnitState.DIE
        enemyUnit2.state = UnitState.DIE
        assert(!battle.checkWin())
        assert(battle.checkLose())
    }
}
package com.zs.mol.model.battle

import com.zs.mol.model.db.skill.SkillDB
import com.zs.mol.model.skill.SkillManager
import com.zs.mol.model.skill.Skill
import com.zs.mol.model.skill.UnitSkill
import com.zs.mol.model.stat.SecondStat.Companion.ATK
import com.zs.mol.model.stat.SecondStat.Companion.DEF
import com.zs.mol.model.stat.SecondStat.Companion.EVADE
import com.zs.mol.model.stat.SecondStat.Companion.HP
import com.zs.mol.model.stat.UnitState
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.unit.BattleUnitFactory
import io.reactivex.subjects.PublishSubject
import org.junit.Test

class BattleTest {
    private val myUnit1 =
        BattleUnitFactory.createMyUnit("유닛 1")
    private val myUnit2 =
        BattleUnitFactory.createMyUnit("유닛 2")

    private val enemyUnit1 = BattleUnitFactory.createEnemy("적유닛 1")
    private val enemyUnit2 = BattleUnitFactory.createEnemy("적유닛 2")

    private val battle = Battle().apply {
        battleUnits.add(myUnit1)
        battleUnits.add(myUnit2)
        battleUnits.add(enemyUnit1)
        battleUnits.add(enemyUnit2)
    }

    @Test
    fun checkWinOrLoseTest() {
        // 아군과 적군 둘 다 살아남았을 때
        assert(!battle.checkWin())
        assert(!battle.checkLose())

        // 아군 하나 살아남았을 때
        myUnit1.changeState(UnitState.DIE)
        myUnit2.changeState(UnitState.STUN)
        enemyUnit1.changeState(UnitState.DIE)
        enemyUnit2.changeState(UnitState.DIE)
        assert(battle.checkWin())
        assert(!battle.checkLose())

        // 적군 하나 살아남았을 때
        myUnit1.changeState(UnitState.DIE)
        myUnit2.changeState(UnitState.DIE)
        enemyUnit1.changeState(UnitState.IDLE)
        enemyUnit2.changeState(UnitState.DIE)
        assert(!battle.checkWin())
        assert(battle.checkLose())

        // 모두 죽었을 때
        myUnit1.changeState(UnitState.DIE)
        myUnit2.changeState(UnitState.DIE)
        enemyUnit1.changeState(UnitState.DIE)
        enemyUnit2.changeState(UnitState.DIE)
        assert(!battle.checkWin())
        assert(battle.checkLose())
    }

    @Test
    fun dieTest() {
        val sucider = BattleUnitFactory.createEnemy("죽을 놈").apply {
            originalStat.secondStat[EVADE] = 0.0
            originalStat.secondStat[HP] = 1.0
            originalStat.secondStat[ATK] = 1000.0
            originalStat.secondStat[DEF] = 0.0
            updateStat()
        }

        sucider.useSkillImmediate(UnitSkill(SkillDB.Key.NormalAttack), arrayListOf(sucider))
        assert(sucider.isDie())
    }

    @Test
    fun usingSkillTest() {
        val testSkill = object : Skill(0) {
            init {
                skillData.apply {
                    name = "TestSkill"
                    coolTime = 5000L
                    castingTime = 1000L
                    effectTime = 1500L
                    afterDelay = 3000L
                }
            }

            override fun onEffect(
                user: BattleUnit,
                target: BattleUnit,
                messageSubject: PublishSubject<String>?
            ) {
            }
        }

        SkillManager.putSkill(testSkill.id, testSkill)
        val unitSkill = UnitSkill(testSkill.id)
        val unit = BattleUnitFactory.createMyUnit("user").apply {
            addSkill(unitSkill)
        }

        val enemy = BattleUnitFactory.createEnemy("enemy")

        unit.startCasting(unitSkill, arrayListOf(enemy))
        assert(unit.state.name == UnitState.CASTING)
        assert(unitSkill.getCoolDown() == 0L)

        unit.updateTime(testSkill.skillData.castingTime)
        assert(unit.state.name == UnitState.EFFECT)
        assert(unitSkill.getCoolDown() == 0L)

        unit.updateTime(testSkill.skillData.effectTime)
        assert(unit.state.name == UnitState.AFTER_DELAY)
        assert(unitSkill.getCoolDown() == 0L)

        unit.updateTime(testSkill.skillData.afterDelay)
        assert(unit.state.name == UnitState.IDLE)
        assert(unitSkill.getCoolDown() == testSkill.skillData.coolTime)

        unit.updateTime(testSkill.skillData.coolTime / 2)
        assert(unitSkill.getCoolDown() == testSkill.skillData.coolTime / 2)

        unit.updateTime(testSkill.skillData.coolTime / 2)
        assert(unitSkill.getCoolDown() == 0L)
    }
}
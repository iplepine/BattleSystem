package com.zs.mol.model.battle

import com.zs.mol.model.battle.skill.Skill
import com.zs.mol.model.battle.skill.UnitSkill
import com.zs.mol.model.battle.stat.SecondStat.Companion.ATK
import com.zs.mol.model.battle.stat.SecondStat.Companion.EVADE
import com.zs.mol.model.battle.stat.SecondStat.Companion.HP
import com.zs.mol.model.battle.stat.UnitState
import com.zs.mol.model.battle.unit.BattleUnit
import com.zs.mol.model.db.SkillDB
import com.zs.mol.model.user.UserManager
import io.reactivex.subjects.PublishSubject
import org.junit.Test

class BattleTest {
    private val myUnit1 =
        BattleUnitFactory.createTestUnit("유닛 1").apply { owner = UserManager.getUserId() }
    private val myUnit2 =
        BattleUnitFactory.createTestUnit("유닛 2").apply { owner = UserManager.getUserId() }

    private val enemyUnit1 = BattleUnitFactory.createTestUnit("적유닛 1")
    private val enemyUnit2 = BattleUnitFactory.createTestUnit("적유닛 2")

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
        val sucider = BattleUnitFactory.createTestUnit("죽을 놈").apply {
            stat.secondStat.values[EVADE] = 0.0
            stat.secondStat.values[HP] = 1.0
            stat.secondStat.values[ATK] = 10.0
        }

        sucider.useSkillImmediate(UnitSkill(SkillDB.NormalAttack), arrayListOf(sucider))
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

        val unit = BattleUnitFactory.createTestUnit("user").apply {
            addSkill(UnitSkill(testSkill))
        }

        val enemy = BattleUnitFactory.createTestUnit("enemy").apply {
        }

        unit.startCasting(UnitSkill(testSkill), arrayListOf(enemy))
        assert(unit.state.name == UnitState.CASTING)
        assert(unit.skills[0].getCoolDown() == 0L)

        unit.updateTime(testSkill.skillData.castingTime)
        assert(unit.state.name == UnitState.EFFECT)
        assert(unit.skills[0].getCoolDown() == 0L)

        unit.updateTime(testSkill.skillData.effectTime)
        assert(unit.state.name == UnitState.AFTER_DELAY)
        assert(unit.skills[0].getCoolDown() == 0L)

        unit.updateTime(testSkill.skillData.afterDelay)
        assert(unit.state.name == UnitState.IDLE)
        assert(unit.skills[0].getCoolDown() == testSkill.skillData.coolTime)

        unit.updateTime(testSkill.skillData.coolTime / 2)
        assert(unit.skills[0].getCoolDown() == testSkill.skillData.coolTime / 2)

        unit.updateTime(testSkill.skillData.coolTime / 2)
        assert(unit.skills[0].getCoolDown() == 0L)
    }
}
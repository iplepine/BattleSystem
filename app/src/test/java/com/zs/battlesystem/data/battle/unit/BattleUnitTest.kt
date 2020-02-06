package com.zs.battlesystem.data.battle.unit

import com.zs.battlesystem.data.battle.BattleUnitFactory
import com.zs.battlesystem.data.battle.skill.active.control.StateControlSkill
import com.zs.battlesystem.data.battle.skill.continuous.buff.base.StatBuff
import com.zs.battlesystem.data.battle.stat.SecondStat.Companion.ATK
import com.zs.battlesystem.data.common.Logger
import org.junit.Test
import kotlin.math.exp

class BattleUnitTest {

    @Test
    fun buffTest() {
        val unit = BattleUnitFactory.createTestUnit("BuffTester")
        val defaultStat = 100.0

        unit.base.totalStat.secondStat.values[ATK] = defaultStat

        val skill = object : StateControlSkill() {

            override fun initEffects() {
                addEffect(StatBuff(ATK, 0.2, false, 300))
                addEffect(StatBuff(ATK, 100.0, true, 500))
                addEffect(StatBuff(ATK, 1.0, false, 300))
            }
        }
        val target = arrayListOf(unit)

        var expect = (defaultStat + 100) * (1 + 1.2)

        unit.startCasting(skill, target)
        unit.calculateStat()
        assert(unit.stat.secondStat.get(ATK) == expect)

        expect = (defaultStat + 100)
        unit.updateTime(300L)
        unit.calculateStat()
        assert(unit.stat.secondStat.get(ATK) == expect)

        expect = defaultStat
        unit.updateTime(300L)
        unit.calculateStat()
        assert(unit.stat.secondStat.get(ATK) == expect)
    }
}
package com.zs.battlesystem.data.battle.unit

import com.zs.battlesystem.data.battle.BattleUnitFactory
import com.zs.battlesystem.data.battle.skill.active.continuous.buff.FlatAttackUp
import com.zs.battlesystem.data.battle.skill.active.continuous.buff.PercentAttackUp
import com.zs.battlesystem.data.battle.stat.SecondStat.Companion.ATK
import com.zs.battlesystem.data.common.Logger
import org.junit.Test

class BattleUnitTest {

    @Test
    fun buffTest() {
        val unit = BattleUnitFactory.createTestUnit("BuffTester")
        val defaultStat = 100.0

        unit.base.totalStat.secondStat.values[ATK] = defaultStat

        unit.startCasting(PercentAttackUp(), arrayListOf(unit))
        unit.startCasting(PercentAttackUp(), arrayListOf(unit))
        unit.startCasting(FlatAttackUp(), arrayListOf(unit))
        unit.calculateStat()

        Logger.d("" + unit.buffs.count())
        val expect =
            (defaultStat + FlatAttackUp().flatStat.secondStat.get(ATK)) *
                    (1 + PercentAttackUp().percentStat.secondStat.get(ATK) * 2)
        assert(unit.stat.secondStat.get(ATK) == expect)

        unit.updateTime(3000L)
        unit.calculateStat()
        assert(unit.stat.secondStat.get(ATK) == defaultStat)
    }
}
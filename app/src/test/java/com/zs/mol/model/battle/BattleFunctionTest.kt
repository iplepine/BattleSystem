package com.zs.mol.model.battle

import com.zs.mol.model.stat.SecondStat.Companion.CRI
import com.zs.mol.model.stat.SecondStat.Companion.EVADE
import com.zs.mol.model.common.Logger
import org.junit.Test

class BattleFunctionTest {
    private val unit1 = BattleUnitFactory.createTestUnit("유닛 1")
    private val unit2 = BattleUnitFactory.createTestUnit("유닛 2")

    @Test
    fun checkCritical() {
        unit1.stat.secondStat.values[CRI] = 100.0
        unit2.stat.secondStat.values[CRI] = 0.0

        assert(BattleFunction.checkCritical(unit1, unit2))
        assert(!BattleFunction.checkCritical(unit2, unit1))
    }

    @Test
    fun checkEvade() {
        unit1.stat.secondStat.values[EVADE] = 0.0
        unit2.stat.secondStat.values[EVADE] = 100.0

        assert(BattleFunction.checkEvade(unit1, unit2))
        assert(!BattleFunction.checkEvade(unit2, unit1))
    }

    @Test
    fun getDamageReduceRation() {
        assert(BattleFunction.getDamageReductionRatio(100.0, 0.0) == 0.0)
        assert(BattleFunction.getDamageReductionRatio(100.0, 100.0) == 0.0)
        assert(BattleFunction.getDamageReductionRatio(100.0, 500.0) >= 0.8)
        assert(BattleFunction.getDamageReductionRatio(100.0, 1000.0) >= 0.9)
    }

    @Test
    fun intLongTest() {
        val aaa = arrayOf(0, 1, 2, 3, 1, 2, 14, 14)
        var aa = 0
        var startTime = System.currentTimeMillis()
        for (i in 0 until 5000000) {
            for (j in aaa.indices) {
                aa += (aaa[j] * 0.01).toInt()
            }
        }
        Logger.d("int time : ${System.currentTimeMillis() - startTime}")

        val bbb = arrayOf(0L, 1L, 2L, 3L, 1L, 2L, 14L, 14L)
        var bb = 0L
        startTime = System.currentTimeMillis()
        for (i in 0 until 5000000) {
            for (j in aaa.indices) {
                bb += (bbb[j] * 0.01).toLong()
            }
        }
        Logger.d("long time : ${System.currentTimeMillis() - startTime}")


        val ccc = arrayOf(0.0f, 1.0f, 2.0f, 3.0f, 1.0f, 2.0f, 14.0f, 14.0f)
        var cc = 0.0f
        startTime = System.currentTimeMillis()
        for (i in 0 until 5000000) {
            for (j in aaa.indices) {
                cc += ccc[j] * 1.0f * 0.001f
            }
        }
        Logger.d("float time : ${System.currentTimeMillis() - startTime}")

        var ddd = arrayOf(0.0, 1.0, 2.0, 3.0, 1.0, 2.0, 14.0, 14.0)
        var dd = 0.0
        startTime = System.currentTimeMillis()
        for (i in 0 until 5000000) {
            for (j in aaa.indices) {
                dd += ddd[j] * 1.0 * 0.001
            }
        }
        Logger.d("double time : ${System.currentTimeMillis() - startTime}")
    }
}
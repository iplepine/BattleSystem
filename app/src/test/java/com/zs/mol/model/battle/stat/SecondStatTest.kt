package com.zs.mol.model.battle.stat

import com.zs.mol.model.common.Logger
import org.junit.Test

class SecondStatTest {

    @Test
    fun levelUpTest() {
        val baseStat = BaseStat()

        baseStat.values.forEach {
            baseStat.values[it.key] = 1.0
        }

        Logger.d(SecondStat.createFromBaseStat(baseStat, false).toString())
    }

    @Test
    fun copyTest() {
        val stat = Stat()
        val stat2 = stat.deepCopy()

        stat2.secondStat.values["test"] = 3.0

        Logger.d("" + stat.secondStat.values["test"])
        Logger.d("" + stat2.secondStat.values["test"])
    }
}
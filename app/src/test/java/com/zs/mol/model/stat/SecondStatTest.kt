package com.zs.mol.model.stat

import com.zs.mol.model.common.Logger
import org.junit.Test

class SecondStatTest {

    @Test
    fun levelUpTest() {
        val baseStat = BaseStat()

        baseStat.forEach {
            baseStat[it.key] = 1.0
        }

        Logger.d(SecondStat.createFromBaseStat(baseStat, false).toString())
    }

    @Test
    fun copyTest() {
        val stat = Stat()
        val stat2 = stat.deepCopy()

        stat2.secondStat["test"] = 3.0

        Logger.d("" + stat.secondStat["test"])
        Logger.d("" + stat2.secondStat["test"])
    }
}
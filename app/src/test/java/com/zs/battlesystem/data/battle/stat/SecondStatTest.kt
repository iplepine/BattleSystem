package com.zs.battlesystem.data.battle.stat

import com.zs.battlesystem.data.common.Logger
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
}
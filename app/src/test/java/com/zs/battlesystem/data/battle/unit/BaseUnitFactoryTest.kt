package com.zs.battlesystem.data.battle.unit

import com.zs.battlesystem.data.common.Logger
import org.junit.Test

class BaseUnitFactoryTest {
    @Test
    fun createUnitTest() {
        val unit = BaseUnitFactory.create()
        Logger.d(unit.toString())
        for (i in 1 until 10) {
            unit.levelUp()
            Logger.d(unit.toString())
        }
    }
}
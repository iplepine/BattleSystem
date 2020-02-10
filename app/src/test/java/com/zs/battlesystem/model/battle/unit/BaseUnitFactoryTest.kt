package com.zs.battlesystem.model.battle.unit

import com.zs.battlesystem.model.common.Logger
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
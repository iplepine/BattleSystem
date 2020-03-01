package com.zs.mol.model.unit

import com.zs.mol.model.common.Logger
import org.junit.Test

class BaseUnitFactoryTest {
    @Test
    fun createUnitTest() {
        val unit = BattleUnitFactory.createMyUnit("test")
        Logger.d(unit.toString())
        for (i in 1 until 10) {
            unit.levelUp()
            Logger.d(unit.toString())
        }
    }
}
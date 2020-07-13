package com.zs.mol.model.unit

import com.zs.mol.model.common.Logger
import org.junit.Test

class BattleUnitFactoryTest {
    @Test
    fun createUnitTest() {
        for (i in 1 until 10) {
            val unit = BattleUnitFactory().createUnit("guest","test")
            Logger.d(unit.toString())
        }
    }
}
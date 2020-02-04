package com.zs.battlesystem.data.battle.unit

import com.zs.battlesystem.data.common.Logger
import org.junit.Test

class BaseUnitFactoryTest {
    @Test
    fun createUnitTest() {
        for (i in 1 until 10) {
            Logger.d(BaseUnitFactory.create().toString())
        }
    }
}
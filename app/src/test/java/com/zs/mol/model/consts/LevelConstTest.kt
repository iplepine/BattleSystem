package com.zs.mol.model.consts

import org.junit.Test

class LevelConstTest {

    @Test
    fun testLevel() {
        assert(LevelConst.getLevel(0) == 1)
        assert(LevelConst.getLevel(1) == 1)
        assert(LevelConst.getLevel(2) == 1)
        assert(LevelConst.getLevel(3) == 2)
        assert(LevelConst.getLevel(200) == 8)
        assert(LevelConst.getLevel(Long.MAX_VALUE) == 20)
    }
}
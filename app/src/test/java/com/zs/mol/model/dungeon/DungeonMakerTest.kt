package com.zs.mol.model.dungeon

import com.zs.mol.model.dungeon.generator.CellularAutoMata
import org.junit.Test

class DungeonMakerTest {
    @Test
    fun createTest() {
        CellularAutoMata(30).createMap()
    }
}
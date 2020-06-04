package com.zs.mol.model.dungeon

import com.zs.mol.model.dungeon.generator.BSPMaker
import com.zs.mol.model.dungeon.generator.CellularAutoMata
import com.zs.mol.model.dungeon.generator.TileAndGraphBasedMaker
import org.junit.Test

class DungeonMakerTest {
    @Test
    fun countNeighborTest() {
        val array =
            arrayOf(BooleanArray(3) { true }, BooleanArray(3) { true }, BooleanArray(3) { true })

        println(CellularAutoMata(10).countNeighbor(array, 0, 0))
    }

    @Test
    fun createAutoMata() {
        CellularAutoMata(30).createMap()
    }

    @Test
    fun createBspTreeTest() {
        val maker = BSPMaker(42, 8)
        val map = maker.createMap()

        maker.printMap(map)
    }

    @Test
    fun createTiledMap() {
        val maker = TileAndGraphBasedMaker(5, 5)
        maker.makeRooms(10)

        val map = maker.createMap()
        maker.printMap(map)
        println()
    }
}
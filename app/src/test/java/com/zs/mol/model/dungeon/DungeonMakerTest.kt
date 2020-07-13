package com.zs.mol.model.dungeon

import com.zs.mol.model.dungeon.generator.BSPMaker
import com.zs.mol.model.dungeon.generator.CellularAutoMata
import com.zs.mol.model.dungeon.generator.MapGenerator
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

        MapGenerator.printMap(map)
    }

    @Test
    fun createTiledMap() {
        val map = TileAndGraphBasedMaker.Builder(10, 10)
            //.initRandomEntrance()
            .setEntrance(5, 5)
            .makeRooms(10, 2)
            //.addRoom(DungeonPlace.Direction.SOUTH)
            .build()

        MapGenerator.printMap(map)
    }
}
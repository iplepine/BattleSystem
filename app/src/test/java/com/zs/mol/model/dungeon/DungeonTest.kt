package com.zs.mol.model.dungeon

import com.zs.mol.model.dungeon.generator.TileAndGraphBasedMaker
import org.junit.Test

class DungeonTest {
    @Test
    fun moveTest() {
        val maker = TileAndGraphBasedMaker(10, 10)
        maker.makeRooms(10, 0)


        val navigator = Dungeon.DungeonNavigator(maker.dungeon, maker.map)
        val start = maker.dungeon.startPlace
        val end = maker.dungeon.places[maker.dungeon.places.keys.last()]!!

        navigator.findWay(start, end)
    }
}
package com.zs.mol.model.dungeon

import com.zs.mol.model.dungeon.Dungeon.DungeonMap.TileType
import com.zs.mol.model.dungeon.generator.TileAndGraphBasedMaker

class DungeonManager {
    val dungeons = ArrayList<Dungeon<DungeonPlace>>()

    lateinit var testDungeon: TileAndGraphBasedMaker.TiledDungeon

    fun createTestDungeon() {
        val startPlace = TileAndGraphBasedMaker.TiledPlace(0, 4, TileType.ENTRANCE)
        val dungeonMap = Dungeon.DungeonMap(10, 10)

        testDungeon = TileAndGraphBasedMaker.TiledDungeon(startPlace, dungeonMap)
    }
}
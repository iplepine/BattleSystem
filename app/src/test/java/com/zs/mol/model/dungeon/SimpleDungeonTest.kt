package com.zs.mol.model.dungeon

import com.zs.mol.model.dungeon.generator.MapGenerator
import com.zs.mol.model.dungeon.generator.TileAndGraphBasedMaker
import org.junit.Test
import kotlin.random.Random

class SimpleDungeonTest {
    @Test
    fun createMapTest() {
        val width = 5
        val height = 5

        val builder = SimpleDungeon.Builder()
            .setSize(width, height)
            .setEntrance(0, Random.nextInt(height))


        for (i in 0 until width * height) {
            builder.addRandomPlace()
        }

        val bossY = Random.nextInt(height)
        builder.addPlace(
            width - 1,
            bossY,
            TileAndGraphBasedMaker.TiledPlace(width - 1, bossY, DungeonPlace.PlaceType.BOSS)
        )

        val dungeon = builder.build()
        //MapGenerator.printMap(dungeon.map.tiles)
    }
}
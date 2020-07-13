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
            val place = createRandomPlace(width, height)
            builder.addPlace(place.position.x, place.position.y, place)
        }

        val bossY = Random.nextInt(height)
        builder.addPlace(
            width - 1,
            bossY,
            TileAndGraphBasedMaker.TiledPlace(width - 1, bossY, DungeonPlace.PlaceType.BOSS)
        )

        val dungeon = builder.build()
        MapGenerator.printMap(dungeon.map.tiles)
    }

    private fun createRandomPlace(width: Int, height: Int): TileAndGraphBasedMaker.TiledPlace {
        val x = Random.nextInt(width)
        val y = Random.nextInt(height)
        return TileAndGraphBasedMaker.TiledPlace(x, y).apply {
            type = DungeonPlace.PlaceType.values()
                .filter {
                    it == DungeonPlace.PlaceType.ITEM
                            || it == DungeonPlace.PlaceType.MONSTER
                            || it == DungeonPlace.PlaceType.TRAP
                            || it == DungeonPlace.PlaceType.GROUND
                }
                .random()
        }
    }
}
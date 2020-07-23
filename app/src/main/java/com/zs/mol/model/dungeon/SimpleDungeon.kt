package com.zs.mol.model.dungeon

import com.zs.mol.model.common.Position
import com.zs.mol.model.dungeon.generator.TileAndGraphBasedMaker
import kotlin.random.Random

class SimpleDungeon(
    private val width: Int,
    private val height: Int,
    map: DungeonMap<TileAndGraphBasedMaker.TiledPlace>,
    entrance: TileAndGraphBasedMaker.TiledPlace
) : Dungeon<TileAndGraphBasedMaker.TiledPlace>(map, entrance) {

    companion object {
        fun create(width: Int, height: Int): SimpleDungeon {

            val builder = Builder()
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

            return builder.build()
        }
    }

    class Builder {
        private var width = 0
        private var height = 0
        private var entrance =
            TileAndGraphBasedMaker.TiledPlace(0, 0, DungeonPlace.PlaceType.ENTRANCE)
        private var places = HashMap<String, TileAndGraphBasedMaker.TiledPlace>()

        fun build(): SimpleDungeon {
            val map = createMap()
            return SimpleDungeon(width, height, map, entrance)
        }

        private fun createMap(): DungeonMap<TileAndGraphBasedMaker.TiledPlace> {
            val map = DungeonMap<TileAndGraphBasedMaker.TiledPlace>(width, height)

            places.keys.forEach {
                places[it]?.apply {
                    map.set(position, this)
                }
            }

            map.set(entrance.position, entrance)

            return map
        }

        fun setSize(width: Int, height: Int): Builder {
            this.width = width
            this.height = height

            return this
        }

        fun setEntrance(x: Int, y: Int): Builder {
            entrance.position.x = x
            entrance.position.y = y
            return this
        }

        fun addPlace(x: Int, y: Int, place: TileAndGraphBasedMaker.TiledPlace): Builder {
            val key = Position(x, y).toString()
            places[key] = place
            return this
        }

        fun addRandomPlace(): Builder {
            val x = Random.nextInt(width)
            val y = Random.nextInt(height)
            val type = TileAndGraphBasedMaker.TiledPlace(x, y).apply {
                type = DungeonPlace.PlaceType.values()
                    .filter {
                        it == DungeonPlace.PlaceType.ITEM
                                || it == DungeonPlace.PlaceType.MONSTER
                                || it == DungeonPlace.PlaceType.TRAP
                                || it == DungeonPlace.PlaceType.GROUND
                    }
                    .random()
            }

            addPlace(x, y, type)
            return this
        }
    }

    fun getPlace(position: Position): TileAndGraphBasedMaker.TiledPlace? {
        return getPlace(position.toString())
    }
}
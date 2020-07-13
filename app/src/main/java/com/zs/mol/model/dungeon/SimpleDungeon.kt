package com.zs.mol.model.dungeon

import com.zs.mol.model.dungeon.generator.TileAndGraphBasedMaker

class SimpleDungeon(
    private val width: Int,
    private val height: Int,
    map: DungeonMap,
    entrance: TileAndGraphBasedMaker.TiledPlace
) :
    Dungeon<TileAndGraphBasedMaker.TiledPlace>(map, entrance) {

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

        private fun createMap(): DungeonMap {
            val map = DungeonMap(width, height)

            places.keys.forEach {
                places[it]?.apply {
                    map.tiles[position.x][position.y] = this
                }
            }

            map.tiles[entrance.position.x][entrance.position.y] = entrance

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
            places["$x,$y"] = place
            return this
        }
    }
}
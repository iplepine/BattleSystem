package com.zs.mol.model.dungeon

open class Dungeon<T : DungeonPlace>(start: T) {
    val places = HashMap<String, T>()
    val startPlace: T = start

    init {
        addPlace(startPlace)
    }

    fun getPlace(key: String): T? {
        return places[key]
    }

    fun addPlace(place: T) {
        places[place.getHashKey()] = place
    }

    fun connectPlace(
        place: T,
        direction: DungeonPlace.Direction,
        newPlace: T
    ) {
        addPlace(newPlace)
        place.connect(direction, newPlace)
    }

    class DungeonMap(val width: Int, val height: Int) {
        object TileType {
            const val ENTRANCE = 99
            const val GROUND = 0
            const val WALL = 1
            const val TRAP = 2
            const val MONSTER = 3
            const val BOSS = 4
            const val ITEM = 5
            const val VERTICAL_WAY = 100
            const val HORIZONTAL_WAY = 101
        }

        val tiles = Array(height) { IntArray(width) { TileType.WALL } }

        fun get(x: Int, y: Int): Int {
            return tiles[y][x]
        }

        fun set(x: Int, y: Int, type: Int) {
            if (x in 0 until width && y in 0 until height) {
                tiles[y][x] = type
            }
        }
    }
}
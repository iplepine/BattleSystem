package com.zs.mol.model.dungeon

import com.zs.mol.model.common.Logger
import com.zs.mol.model.common.Position
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

open class Dungeon<T : DungeonPlace>(start: T) {
    val places = HashMap<String, T>()
    val startPlace: T = start

    val mainStream = ArrayList<Position>()

    init {
        addPlace(startPlace)
    }

    fun getPlace(key: String): T? {
        return places[key]
    }

    fun addPlace(place: T) {
        places[place.getId()] = place
    }

    fun connectPlace(
        place: T,
        direction: DungeonPlace.Direction,
        newPlace: T
    ) {
        addPlace(newPlace)
        place.connect(direction, newPlace)
    }

    fun findEdge() {

    }

    class DungeonNavigator<T : DungeonPlace>(val dungeon: Dungeon<T>, val map: DungeonMap) {
        fun findWay(start: DungeonPlace, end: DungeonPlace): Way? {
            val visitMap = HashMap<String, Boolean>()

            val queue = LinkedList<DungeonPlace>()
            val history = Stack<DungeonPlace>()

            var currentPlace: DungeonPlace? = start
            queue.push(currentPlace)

            val way = Way()

            while (currentPlace == end) {
                if (queue.isEmpty()) {
                    Logger.e("not found")
                    return null
                }
                if (currentPlace == end) {
                    Logger.e("found!!")
                    return way
                }

                currentPlace = queue.pop()?.apply {
                    connects.forEach {
                        if (visitMap[it.value.getId()] == false) {
                            queue.push(it.value)
                        }
                    }
                }
            }

            return way
        }

        fun find(dungeon: Dungeon<T>, start: DungeonPlace) {
            val placeSize = dungeon.places.size
            val dist = Array(placeSize) { IntArray(placeSize) { Int.MAX_VALUE } }
            val visitMap = HashMap<String, Boolean>()
        }
    }

    class Way {
        lateinit var start: DungeonPlace
        lateinit var end: DungeonPlace
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
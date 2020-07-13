package com.zs.mol.model.dungeon

import com.zs.mol.model.common.Logger
import com.zs.mol.model.common.Position
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

open class Dungeon<T : DungeonPlace>(val map: DungeonMap, val entrance: T) {

    val places = HashMap<String, T>()
    val mainStream = ArrayList<Position>()

    init {
        addPlace(entrance)
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

    class DungeonMap(
        val width: Int,
        val height: Int,
        initialType: DungeonPlace.PlaceType = DungeonPlace.PlaceType.GROUND
    ) {
        val tiles = Array(width) { Array(height) { DungeonPlace(initialType) } }

        fun get(x: Int, y: Int): DungeonPlace {
            return tiles[x][y]
        }

        fun set(x: Int, y: Int, type: DungeonPlace.PlaceType) {
            if (x in 0 until width && y in 0 until height) {
                tiles[x][y].type = type
            }
        }
    }
}
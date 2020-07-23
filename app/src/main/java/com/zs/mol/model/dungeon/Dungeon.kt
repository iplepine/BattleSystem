package com.zs.mol.model.dungeon

import com.zs.mol.model.common.Logger
import com.zs.mol.model.common.Position
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

open class Dungeon<T : DungeonPlace>(val map: DungeonMap<T>, val entrance: T) {

    val places = HashMap<String, T>()       // 필요없어서 지워질듯
    val mainStream = ArrayList<Position>()

    init {
        addPlace(entrance)
    }

    fun getPlace(key: String): T? {
        return map.get(key)
    }

    fun addPlace(place: T) {
        map.set(place.getId(), place)
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

    class DungeonNavigator<T : DungeonPlace>(val dungeon: Dungeon<T>, val map: DungeonMap<T>) {
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

    class DungeonMap<T : DungeonPlace>(
        val width: Int,
        val height: Int
    ) {
        val tiles = HashMap<String, T>()

        fun get(position: Position): T? {
            return get(position.toString())
        }

        fun get(key: String): T? {
            return tiles[key]
        }

        fun set(key: String, place: T) {
            tiles[key] = place
        }

        fun set(position: Position, place: T) {
            if (position.x in 0 until width && position.y in 0 until height) {
                tiles[position.toString()] = place
            }
        }
    }
}
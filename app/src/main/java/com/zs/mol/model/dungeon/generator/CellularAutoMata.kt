package com.zs.mol.model.dungeon.generator

import com.zs.mol.model.dungeon.DungeonPlace
import kotlin.random.Random

class CellularAutoMata(private var mapSize: Int) : MapGenerator() {
    private val bigBangRatio = 0.55f
    private val aliveLimit = 5
    private val stepCount = 3

    override fun createMap(): Array<Array<DungeonPlace>> {
        return Array(mapSize) { Array(mapSize) { DungeonPlace() } }.also { map ->
            val initialWorld = firstBigBang(mapSize)
            var convertedMap = initialWorld.map {
                it.map { isGround ->
                    if (isGround) DungeonPlace(DungeonPlace.PlaceType.GROUND)
                    else DungeonPlace(DungeonPlace.PlaceType.WALL)
                }.toTypedArray()
            }.toTypedArray()
            printMap(convertedMap)
            println()

            for (i in 0 until stepCount) {
                stepOnce(initialWorld)
            }

            convertedMap = initialWorld.map {
                it.map { isGround ->
                    if (isGround) DungeonPlace(DungeonPlace.PlaceType.GROUND)
                    else DungeonPlace(DungeonPlace.PlaceType.WALL)
                }.toTypedArray()
            }.toTypedArray()
            printMap(convertedMap)
            println()
        }
    }

    private fun firstBigBang(mapSize: Int): Array<BooleanArray> {
        return Array(mapSize) { BooleanArray(mapSize) }.also { map ->
            for (i in map.indices) {
                for (j in map[i].indices) {
                    map[i][j] = Random.nextFloat() < bigBangRatio
                }
            }
        }
    }

    private fun checkAlive(cellMap: Array<BooleanArray>): Array<BooleanArray> {
        return Array(mapSize) { BooleanArray(mapSize) }.also { map ->
            for (i in map.indices) {
                for (j in map[i].indices) {
                    if (aliveLimit <= countNeighbor(cellMap, i, j)) {
                        map[i][j] = true
                    }
                }
            }
        }
    }

    fun countNeighbor(map: Array<BooleanArray>, x: Int, y: Int): Int {
        var count = 0
        val value = map[x][y]
        val range = arrayOf(-1, 0, 1)

        for (i in range.indices) {
            for (j in range.indices) {
                val x = x + range[i]
                val y = y + range[j]

                if (0 <= x && 0 <= y && x < mapSize && y < mapSize) {
                    if (value == map[x][y]) {
                        count++
                    }
                } else {
                    //count++
                }
            }
        }

        return count
    }

    fun stepOnce(cellMap: Array<BooleanArray>): Array<BooleanArray> {
        val aliveMap = checkAlive(cellMap)
        for (i in cellMap.indices) {
            for (j in cellMap[i].indices) {
                if (!aliveMap[i][j]) {
                    cellMap[i][j] = !cellMap[i][j]
                }
            }
        }
        return cellMap
    }
}
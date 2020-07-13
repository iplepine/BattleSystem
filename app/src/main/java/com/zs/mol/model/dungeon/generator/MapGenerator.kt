package com.zs.mol.model.dungeon.generator

import com.zs.mol.model.dungeon.DungeonPlace

abstract class MapGenerator {
    abstract fun createMap(): Array<Array<DungeonPlace>>

    companion object {
        fun printMap(map: Array<Array<DungeonPlace>>) {
            val width = map.size
            val height = map[0].size

            val convertMap = Array(map[0].size) { Array(map.size) { DungeonPlace() } }
            for (j in 0 until height) {
                for (i in 0 until width) {
                    convertMap[i][j] = map[j][i]
                }
            }

            convertMap.forEach { row ->
                row.forEach {
                    printField(it)
                }
                println()
            }
        }

        private fun printField(place: DungeonPlace) {
            val text = when (place.type) {
                DungeonPlace.PlaceType.VERTICAL_WAY -> "│"
                DungeonPlace.PlaceType.HORIZONTAL_WAY -> "─"
                DungeonPlace.PlaceType.ENTRANCE -> "▣"
                DungeonPlace.PlaceType.WALL -> "■"
                DungeonPlace.PlaceType.GROUND -> "□"
                DungeonPlace.PlaceType.BOSS -> "Β"
                DungeonPlace.PlaceType.ITEM -> "ⓘ"
                DungeonPlace.PlaceType.TRAP -> "Τ"
                DungeonPlace.PlaceType.MONSTER -> "ｍ"
            }
            print(text)
        }
    }
}
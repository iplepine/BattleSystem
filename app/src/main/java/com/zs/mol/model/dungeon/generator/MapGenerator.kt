package com.zs.mol.model.dungeon.generator

import com.zs.mol.model.dungeon.Dungeon.DungeonMap.TileType

abstract class MapGenerator {
    abstract fun createMap(): Array<IntArray>

    companion object {
        fun printMap(map: Array<IntArray>) {
            val width = map.size
            val height = map[0].size

            val convertMap = Array(map[0].size) { IntArray(map.size) }
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

        private fun printField(field: Int) {
            val text = when (field) {
                TileType.VERTICAL_WAY -> "│"
                TileType.HORIZONTAL_WAY -> "─"
                TileType.ENTRANCE -> "▣"
                TileType.WALL -> "■"
                TileType.GROUND -> "□"
                TileType.BOSS -> "Ｂ"
                else -> " "
            }
            print(text)
        }
    }
}
package com.zs.mol.model.dungeon.generator

import com.zs.mol.model.dungeon.Dungeon.DungeonMap.TileType

abstract class MapGenerator {
    abstract fun createMap(): Array<IntArray>

    fun printMap(map: Array<IntArray>) {
        map.forEach { row ->
            row.forEach {
                printField(it)
            }
            println()
        }
    }

    private fun printField(field: Int) {
        val text = when (field) {
            TileType.VERTICAL_WAY -> "─"
            TileType.HORIZONTAL_WAY -> "│"
            TileType.ENTRANCE -> "▣"
            TileType.WALL -> "■"
            TileType.GROUND -> "□"
            TileType.BOSS -> "Ｂ"
            else -> " "
        }
        print(text)
    }
}
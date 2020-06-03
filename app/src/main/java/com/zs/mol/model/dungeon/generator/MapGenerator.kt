package com.zs.mol.model.dungeon.generator

abstract class MapGenerator {
    object FieldType {
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
            FieldType.VERTICAL_WAY -> "│"
            FieldType.HORIZONTAL_WAY -> "─"
            FieldType.ENTRANCE -> "▣"
            FieldType.WALL -> "■"
            FieldType.GROUND -> "□"
            FieldType.BOSS -> "Ｂ"
            else -> " "
        }
        print(text)
    }
}
package com.zs.mol.model.dungeon.generator

abstract class MapGenerator {
    object FieldType {
        const val TYPE_COUNT = 3
        const val GROUND = 0
        const val WATER = 1
        const val HILL = 2
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
            FieldType.WATER -> "□"
            FieldType.GROUND -> "■"
            FieldType.HILL -> "♣"
            else -> " "
        }
        print(text)
    }
}
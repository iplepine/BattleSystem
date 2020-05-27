package com.zs.mol.model.dungeon.generator

abstract class MapGenerator {
    object FieldType {
        const val TYPE_COUNT = 3
        const val GROUND = 0
        const val WATER = 1
        const val HILL = 2
    }

    abstract fun createMap(): Array<IntArray>
}
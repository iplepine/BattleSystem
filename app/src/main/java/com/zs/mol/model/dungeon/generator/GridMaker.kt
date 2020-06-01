package com.zs.mol.model.dungeon.generator

import android.graphics.Point
import com.zs.mol.model.dungeon.DungeonPlace
import kotlin.random.Random

class GridMaker : MapGenerator() {
    val width = 10
    val height = 10

    lateinit var entrance: Point

    init {
        initEntrance()
    }

    private fun initEntrance() {
        entrance = when (DungeonPlace.Direction.values().random()) {
            DungeonPlace.Direction.NORTH -> Point(Random.nextInt(width), 0)
            DungeonPlace.Direction.EAST -> Point(width - 1, Random.nextInt(height))
            DungeonPlace.Direction.SOUTH -> Point(Random.nextInt(width), height - 1)
            DungeonPlace.Direction.WEST -> Point(0, Random.nextInt(height))
        }
    }

    override fun createMap(): Array<IntArray> {
        return Array(width) { IntArray(height) }
    }

    private fun makeBossRoom() {

    }
}
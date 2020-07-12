package com.zs.mol.model.common

import com.zs.mol.model.dungeon.DungeonPlace

class Position(var x: Int = 0, var y: Int = 0) {
    override fun toString(): String {
        return "$x,$y"
    }

    fun moveDirection(direction: DungeonPlace.Direction) {
        when (direction) {
            DungeonPlace.Direction.NORTH -> y -= 1
            DungeonPlace.Direction.EAST -> x += 1
            DungeonPlace.Direction.SOUTH -> y += 1
            DungeonPlace.Direction.WEST -> x -= 1
        }
    }
}
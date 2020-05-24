package com.zs.mol.model.dungeon_

abstract class DungeonPlace {
    enum class Direction { NORTH, EAST, SOUTH, WEST, NONE }

    //var event: DungeonEvent? = null
    var places = HashMap<Direction, DungeonPlace>()

    open fun onEnter() {
    }

    open fun onOut() {
    }
}
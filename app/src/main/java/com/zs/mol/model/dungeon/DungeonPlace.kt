package com.zs.mol.model.dungeon

abstract class DungeonPlace {
    enum class Direction { NORTH, EAST, SOUTH, WEST}

    enum class RoomType {
        NORMAL,
    }

    //var event: DungeonEvent? = null
    var connects = HashMap<Direction, DungeonPlace>()

    open fun onEnter() {
    }

    open fun onOut() {
    }
}
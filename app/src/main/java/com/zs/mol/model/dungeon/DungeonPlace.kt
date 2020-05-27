package com.zs.mol.model.dungeon

abstract class DungeonPlace {
    companion object {
        const val DIRECT_NORTH = 0
        const val DIRECT_EAST = 1
        const val DIRECT_SOUTH = 2
        const val DIRECT_WEST = 3
    }

    enum class Direction { NORTH, EAST, SOUTH, WEST, NONE }

    //var event: DungeonEvent? = null
    var connects = HashMap<Direction, DungeonPlace>()

    open fun onEnter() {
    }

    open fun onOut() {
    }
}
package com.zs.mol.model.dungeon

import com.zs.mol.model.user.User

abstract class DungeonPlace {
    enum class Direction { NONE, NORTH, EAST, SOUTH, WEST }

    enum class RoomType {
        EMPTY, ENTRANCE, MONSTER, TRAP, ITEM, BOSS
    }

    var event: DungeonEvent? = null

    open fun onEnter(user: User, dungeon: Dungeon) {

    }

    open fun onOut(user: User, dungeon: Dungeon) {

    }
}
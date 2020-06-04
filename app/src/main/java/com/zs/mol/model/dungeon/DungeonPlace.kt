package com.zs.mol.model.dungeon

import com.zs.mol.model.user.User

abstract class DungeonPlace {
    enum class Direction {
        NONE, NORTH, EAST, SOUTH, WEST;

        fun getOpposite(): Direction {
            return when (this) {
                NONE -> NONE
                NORTH -> SOUTH
                EAST -> WEST
                WEST -> EAST
                SOUTH -> NORTH
            }
        }
    }

    enum class PlaceType {
        EMPTY, ENTRANCE, MONSTER, TRAP, ITEM, BOSS
    }

    val connects = HashMap<Direction, DungeonPlace>()
    var events: DungeonEvent? = null

    fun connect(direction: Direction, place: DungeonPlace) {
        connects[direction] = place
        place.connects[direction.getOpposite()] = this
    }

    fun disconnect(direction: Direction) {
        connects.remove(direction)?.also {
            it.connects.remove(direction.getOpposite())
        }
    }

    override fun hashCode(): Int {
        return getHashKey().hashCode()
    }

    open fun getHashKey(): String {
        return toString()
    }

    open fun onEnter(user: User, dungeon: Dungeon<out DungeonPlace>) {

    }

    open fun onOut(user: User, dungeon: Dungeon<out DungeonPlace>) {

    }
}
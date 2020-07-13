package com.zs.mol.model.dungeon

import android.text.TextUtils

open class DungeonPlace(var type: PlaceType = PlaceType.GROUND) {
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
        ENTRANCE, MONSTER, TRAP, ITEM, BOSS, GROUND, WALL, VERTICAL_WAY, HORIZONTAL_WAY
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
        return if (TextUtils.isEmpty(getId())) {
            super.hashCode()
        } else {
            getId().hashCode()
        }
    }

    open fun getId(): String {
        return ""
    }
}
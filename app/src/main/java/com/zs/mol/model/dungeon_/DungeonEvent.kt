package com.zs.mol.model.dungeon_

class DungeonEvent(var type: EventType) {
    enum class EventType {
        WAY, MONSTER, TREASURE, TRAP
    }
}
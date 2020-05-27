package com.zs.mol.model.dungeon

import com.zs.mol.model.dungeon.event.DungeonEvent

class DungeonScenario {
    val events = ArrayList<DungeonEvent>()
    var currentEvent: DungeonEvent? = null
    var isStarted = false

    fun start() {
        isStarted = true
    }

    fun isFinish(): Boolean {
        return isStarted && currentEvent?.getNextEvent() == null
    }

    fun onNextEvent() {
        currentEvent = currentEvent?.getNextEvent()
    }
}
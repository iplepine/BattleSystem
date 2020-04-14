package com.zs.mol.model.dungeon

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
package com.zs.mol.model.dungeon.event

import kotlin.random.Random

abstract class DungeonEvent {
    var requireEvents = ArrayList<DungeonEvent>()
    var preEvents = ArrayList<DungeonEvent>()
    var nextEvents = ArrayList<DungeonEvent>()
    var state = State.NONE

    open fun onSuccess() {}
    open fun onFailed() {}
    open fun getNextEvent(): DungeonEvent? {
        val availableEvents = nextEvents.filter {
            // 선행 이벤트들이 모두 수행된 경우에만 다음 이벤트로 가져옴
            // 필수 선행 이벤트 중에 성공하지 않은 이벤트가 하나 없을 경우, true
            requireEvents.find { state != State.SUCCESS } == null
        }

        if (availableEvents.isEmpty()) {
            return null
        }

        val index = Random.nextInt(availableEvents.size)
        return availableEvents[index]
    }

    enum class State {
        NONE, SUCCESS, FAILED
    }
}
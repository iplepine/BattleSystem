package com.zs.mol.model.dungeon

import kotlin.random.Random

class SelectEvent : DungeonEvent() {
    val options = ArrayList<String>()

    override fun onSuccess() {}
    override fun onFailed() {}
    override fun getNextEvent(): DungeonEvent? {
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
}
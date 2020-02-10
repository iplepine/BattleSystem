package com.zs.battlesystem.model.event.processor

import com.zs.battlesystem.model.battle.Battle
import com.zs.battlesystem.model.event.BaseEvent
import com.zs.battlesystem.model.event.ControlEvent

object ControlEventProcessor : EventProcessor {
    override fun onReceiveEvent(event: BaseEvent, battle: Battle) {
        (event as? ControlEvent)?.apply {
        }
    }
}
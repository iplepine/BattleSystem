package com.zs.battlesystem.data.event.processor

import com.zs.battlesystem.data.battle.Battle
import com.zs.battlesystem.data.event.BaseEvent
import com.zs.battlesystem.data.event.ControlEvent

object ControlEventProcessor : EventProcessor {
    override fun onReceiveEvent(event: BaseEvent, battle: Battle) {
        (event as? ControlEvent)?.apply {
        }
    }
}
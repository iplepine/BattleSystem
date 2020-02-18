package com.zs.mol.model.event.processor

import com.zs.mol.model.battle.Battle
import com.zs.mol.model.event.BaseEvent
import com.zs.mol.model.event.ControlEvent

object ControlEventProcessor : EventProcessor {
    override fun onReceiveEvent(event: BaseEvent, battle: Battle) {
        (event as? ControlEvent)?.apply {
        }
    }
}
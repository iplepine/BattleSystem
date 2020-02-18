package com.zs.mol.model.event.processor

import com.zs.mol.model.battle.Battle
import com.zs.mol.model.event.BaseEvent

interface EventProcessor {
    open fun onReceiveEvent(event: BaseEvent, battle: Battle)
}
package com.zs.battlesystem.model.event.processor

import com.zs.battlesystem.model.battle.Battle
import com.zs.battlesystem.model.event.BaseEvent

interface EventProcessor {
    open fun onReceiveEvent(event: BaseEvent, battle: Battle)
}
package com.zs.battlesystem.data.event.processor

import com.zs.battlesystem.data.battle.Battle
import com.zs.battlesystem.data.event.BaseEvent

interface EventProcessor {
    open fun onReceiveEvent(event: BaseEvent, battle: Battle)
}
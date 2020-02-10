package com.zs.battlesystem.model.battle

import com.zs.battlesystem.model.event.BaseEvent

interface BattleEventListener {
    fun onHit(event: BaseEvent)
    fun onCritical(event: BaseEvent)
    fun onEvade(event: BaseEvent)
    fun onBlock(event: BaseEvent)
}

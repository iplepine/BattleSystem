package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.event.BaseEvent

interface BattleEventListener {
    fun onHit(event: BaseEvent)
    fun onCritical(event: BaseEvent)
    fun onEvade(event: BaseEvent)
    fun onBlock(event: BaseEvent)
}

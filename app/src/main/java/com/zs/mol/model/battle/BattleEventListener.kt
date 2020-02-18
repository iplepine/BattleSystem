package com.zs.mol.model.battle

import com.zs.mol.model.event.BaseEvent

interface BattleEventListener {
    fun onHit(event: BaseEvent)
    fun onCritical(event: BaseEvent)
    fun onEvade(event: BaseEvent)
    fun onBlock(event: BaseEvent)
}

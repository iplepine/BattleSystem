package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.event.BaseEvent

interface BattleEventListener {
    fun onBattleEvent(event : BaseEvent)
}

package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.battle.event.BattleEvent

interface BattleEventListener {
    fun onBattleEvent(event : BattleEvent)
}

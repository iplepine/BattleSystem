package com.zs.mol.model.event.processor

import com.zs.mol.model.battle.Battle
import com.zs.mol.model.event.BaseEvent
import com.zs.mol.model.event.BattleEvent

object BattleEventProcessor : EventProcessor {
    override fun onReceiveEvent(event: BaseEvent, battle: Battle) {
        (event as? BattleEvent)?.apply {
            when (event.result) {
                BattleEvent.HIT -> onHit(event, battle)
                BattleEvent.EVADE -> onEvade(event, battle)
                BattleEvent.CRITICAL -> onCritical(event, battle)
                BattleEvent.BLOCK -> onBlock(event, battle)
            }
        }
    }

    private fun onHit(event: BattleEvent, battle: Battle) {

    }

    private fun onEvade(event: BattleEvent, battle: Battle) {

    }

    private fun onCritical(event: BattleEvent, battle: Battle) {

    }

    private fun onBlock(event: BattleEvent, battle: Battle) {

    }
}
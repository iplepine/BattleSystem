package com.zs.battlesystem.data.event.processor

import com.zs.battlesystem.data.battle.Battle
import com.zs.battlesystem.data.event.BaseEvent
import com.zs.battlesystem.data.event.BattleEvent
import com.zs.battlesystem.data.manager.SkillManager

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
        val skill = SkillManager.getSkill(event.skillName)
        val user = battle.findUnit(event.userId)
        if (skill == null || user == null) {
            return
        }

    }

    private fun onEvade(event: BattleEvent, battle: Battle) {

    }

    private fun onCritical(event: BattleEvent, battle: Battle) {

    }

    private fun onBlock(event: BattleEvent, battle: Battle) {

    }
}
package com.zs.mol.model.quest

import com.zs.mol.model.quest.detail.reward.UnitReward
import com.zs.mol.model.unit.BattleUnit

class HireQuest : Quest(QuestType.HIRE) {

    override fun isValid(): Boolean {
        return rewards.isNotEmpty() && getUnit() != null
    }

    fun getUnit(): BattleUnit? {
        return if (rewards.isEmpty()) {
            null
        } else {
            (rewards[0] as? UnitReward)?.unit
        }
    }
}
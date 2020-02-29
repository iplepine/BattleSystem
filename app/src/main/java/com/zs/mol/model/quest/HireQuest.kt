package com.zs.mol.model.quest

import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.user.UserManager

class HireQuest : Quest(QuestType.HIRE) {

    override fun onSuccess() {
        super.onSuccess()
        rewards.find { it.key == RewardKey.UNIT }?.apply {
            (value as? BattleUnit)?.also {
                UserManager.addUnit(it)
            }
        }
    }
}
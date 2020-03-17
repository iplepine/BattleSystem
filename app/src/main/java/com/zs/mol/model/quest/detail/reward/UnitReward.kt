package com.zs.mol.model.quest.detail.reward

import com.zs.mol.model.item.ItemKey
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.user.UserManager

class UnitReward(value: BattleUnit) : QuestReward(ItemKey.UNIT, value) {
    val unit: BattleUnit = value

    override fun onSuccess() {
        UserManager.user.addUnit(unit)
    }

    override fun toDescription(): String {
        return unit.status.toLevelName()
    }
}
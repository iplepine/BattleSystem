package com.zs.mol.model.quest.detail.reward

import com.zs.mol.model.item.ItemKey
import com.zs.mol.model.user.UserManager

class GoldReward(value: Long) : QuestReward(ItemKey.GOLD, value) {
    val amount = value

    override fun onSuccess() {
        UserManager.gainGold(amount)
    }
}
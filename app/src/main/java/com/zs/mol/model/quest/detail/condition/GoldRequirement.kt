package com.zs.mol.model.quest.detail.condition

import com.zs.mol.model.item.ItemKey
import com.zs.mol.model.user.UserManager

class GoldRequirement(value: Long) : QuestRequirement(ItemKey.GOLD, value) {
    val amount = value

    override fun checkRequire(): Boolean {
        return amount <= UserManager.user.userStatus.gold
    }

    override fun onSuccess() {
        UserManager.user.userStatus.useGold(amount)
    }

    override fun toDescription(): String {
        return "Gold (${UserManager.user.userStatus.gold}/$amount)"
    }
}
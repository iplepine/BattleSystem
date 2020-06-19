package com.zs.mol.model.quest.detail.condition

import com.zs.mol.model.item.ItemKey
import com.zs.mol.model.user.User

class GoldRequirement(value: Long) : QuestRequirement(ItemKey.GOLD, value) {
    val amount = value

    override fun checkRequire(user: User): Boolean {
        return amount <= user.userStatus.gold
    }

    override fun onSuccess(user: User) {
        user.userStatus.useGold(amount)
    }

    override fun toDescription(): String {
        return "Gold $amount required"
    }
}
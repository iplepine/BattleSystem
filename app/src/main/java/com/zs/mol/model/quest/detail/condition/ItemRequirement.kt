package com.zs.mol.model.quest.detail.condition

import com.zs.mol.model.user.User

class ItemRequirement(key: String, value: Long) : QuestRequirement(key, value) {
    val amount = value

    override fun checkRequire(user: User): Boolean {
        return amount <= user.inventory.getAmount(key)
    }

    override fun onSuccess(user: User) {
        user.inventory.removeItem(key, amount)
    }
}
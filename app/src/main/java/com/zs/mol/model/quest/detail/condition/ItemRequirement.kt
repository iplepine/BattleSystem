package com.zs.mol.model.quest.detail.condition

import com.zs.mol.model.db.inventory.Inventory
import com.zs.mol.model.user.UserManager

class ItemRequirement(key: String, value: Long) : QuestRequirement(key, value) {
    val amount = value

    override fun checkRequire(): Boolean {
        return amount <= UserManager.user.inventory.getAmount(key)
    }

    override fun onSuccess() {
        UserManager.user.inventory.removeItem(key, amount)
    }
}
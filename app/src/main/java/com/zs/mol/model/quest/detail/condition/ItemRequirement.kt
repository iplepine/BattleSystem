package com.zs.mol.model.quest.detail.condition

import com.zs.mol.model.db.inventory.Inventory

class ItemRequirement(key: String, value: Long) : QuestRequirement(key, value) {
    val amount = value

    override fun checkRequire(): Boolean {
        return amount <= Inventory.getAmount(key)
    }

    override fun onSuccess() {
        Inventory.removeItem(key, amount)
    }
}
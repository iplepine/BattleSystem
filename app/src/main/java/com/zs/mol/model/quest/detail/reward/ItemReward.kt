package com.zs.mol.model.quest.detail.reward

import com.zs.mol.model.db.inventory.Inventory

class ItemReward(key: String, value: Long) : QuestReward(key, value) {
    override fun onSuccess() {
        Inventory.addItem(key, value as Long)
    }
}
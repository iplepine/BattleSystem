package com.zs.mol.model.quest.detail.reward

import com.zs.mol.model.user.UserManager

class ItemReward(key: String, value: Long) : QuestReward(key, value) {
    override fun onSuccess() {
        UserManager.user.inventory.addItem(key, value as Long)
    }
}
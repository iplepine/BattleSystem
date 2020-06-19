package com.zs.mol.model.quest.detail.reward

import com.zs.mol.model.user.User

class ItemReward(key: String, value: Long) : QuestReward(key, value) {
    override fun onSuccess(user: User) {
        user.inventory.addItem(key, value as Long)
    }
}
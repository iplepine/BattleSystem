package com.zs.mol.model.quest.detail.reward

import com.zs.mol.model.user.User
import io.reactivex.Single

class ItemReward(key: String, value: Long) : QuestReward(key, value) {
    override fun onSuccess(user: User): Single<Boolean> {
        return user.addItem(key, value as Long)
    }
}
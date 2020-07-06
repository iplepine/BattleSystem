package com.zs.mol.model.quest.detail.reward

import com.zs.mol.model.item.ItemKey
import com.zs.mol.model.user.User
import io.reactivex.Single

class GoldReward(value: Long) : QuestReward(ItemKey.GOLD, value) {
    val amount = value

    override fun onSuccess(user: User): Single<Boolean> {
        return user.addItem(key, amount)
    }
}
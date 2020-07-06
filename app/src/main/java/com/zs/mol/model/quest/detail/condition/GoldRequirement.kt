package com.zs.mol.model.quest.detail.condition

import com.zs.mol.model.item.ItemKey
import com.zs.mol.model.user.User
import io.reactivex.Single

class GoldRequirement(value: Long) : QuestRequirement(ItemKey.GOLD, value) {
    val amount = value

    override fun checkRequire(user: User): Boolean {
        return amount <= user.getItemAmount(key)
    }

    override fun onSuccess(user: User): Single<Boolean> {
        return user.useItem(key, amount)
    }

    override fun toDescription(): String {
        return "Gold $amount required"
    }
}
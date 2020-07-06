package com.zs.mol.model.quest.detail.condition

import com.zs.mol.model.user.User
import io.reactivex.Single

class ItemRequirement(key: String, value: Long) : QuestRequirement(key, value) {
    val amount = value

    override fun checkRequire(user: User): Boolean {
        return amount <= user.getItemAmount(key)
    }

    override fun onSuccess(user: User): Single<Boolean> {
        return user.useItem(key, amount)
    }
}
package com.zs.mol.model.quest.detail.penalty

import com.zs.mol.model.user.User
import io.reactivex.Single

class GoldPenalty(key: String, value: Long) : QuestPenalty(key, value) {
    val amount = value

    override fun onFailed(user: User): Single<Boolean> {
        user.useItem(key, amount)
        return Single.just(true)
    }
}
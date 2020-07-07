package com.zs.mol.model.quest.detail.penalty

import com.zs.mol.model.user.User
import io.reactivex.Single

class ExpPenalty(key: String, value: Long) : QuestPenalty(key, value) {
    val amount = value

    override fun onFailed(user: User): Single<Boolean> {
        user.userData.reduceExp(amount)
        return Single.just(true)
    }
}
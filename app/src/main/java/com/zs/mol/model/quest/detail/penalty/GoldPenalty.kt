package com.zs.mol.model.quest.detail.penalty

import com.zs.mol.model.user.User

class GoldPenalty(key: String, value: Long) : QuestPenalty(key, value) {
    val amount = value

    override fun onFailed(user: User) {
        user.userStatus.useGold(amount)
    }
}
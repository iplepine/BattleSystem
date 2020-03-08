package com.zs.mol.model.quest.detail.penalty

import com.zs.mol.model.user.UserManager

class ExpPenalty(key: String, value: Long) : QuestPenalty(key, value) {
    val amount = value

    override fun onFailed() {
        UserManager.user.userStatus.reduceExp(amount)
    }
}
package com.zs.mol.model.quest.detail.reward

import com.zs.mol.model.quest.detail.QuestDetailItem
import com.zs.mol.model.user.User

abstract class QuestReward(key: String, value: Any) : QuestDetailItem(key, value) {
    abstract fun onSuccess(user: User)
}
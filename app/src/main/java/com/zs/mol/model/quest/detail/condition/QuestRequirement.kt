package com.zs.mol.model.quest.detail.condition

import com.zs.mol.model.quest.detail.QuestDetailItem
import com.zs.mol.model.user.User

abstract class QuestRequirement(key: String, value: Any) : QuestDetailItem(key, value) {
    abstract fun checkRequire(user: User): Boolean
    abstract fun onSuccess(user: User)
}
package com.zs.mol.model.quest.detail.penalty

import com.zs.mol.model.quest.detail.QuestDetailItem
import com.zs.mol.model.user.User
import io.reactivex.Single

abstract class QuestPenalty(key: String, value: Any) : QuestDetailItem(key, value) {
    abstract fun onFailed(user: User): Single<Boolean>
}
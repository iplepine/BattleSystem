package com.zs.mol.model.quest.detail.reward

import com.zs.mol.model.quest.detail.QuestDetailItem
import com.zs.mol.model.user.User
import io.reactivex.Single

abstract class QuestReward(key: String, value: Any) : QuestDetailItem(key, value) {
    abstract fun onSuccess(user: User): Single<Boolean>
}
package com.zs.mol.model.quest.detail.reward

import com.zs.mol.model.quest.detail.QuestDetailItem

abstract class QuestReward(key: String, value: Any) : QuestDetailItem(key, value) {
    abstract fun onSuccess()
}
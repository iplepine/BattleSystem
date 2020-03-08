package com.zs.mol.model.quest.detail.penalty

import com.zs.mol.model.quest.detail.QuestDetailItem

abstract class QuestPenalty(key: String, value: Any) : QuestDetailItem(key, value) {
    abstract fun onFailed()
}
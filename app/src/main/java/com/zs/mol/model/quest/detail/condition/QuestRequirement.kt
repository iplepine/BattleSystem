package com.zs.mol.model.quest.detail.condition

import com.zs.mol.model.quest.detail.QuestDetailItem

abstract class QuestRequirement(key: String, value: Any) : QuestDetailItem(key, value) {
    abstract fun checkRequire(): Boolean
    abstract fun onSuccess()
}
package com.zs.mol.model.quest.detail

abstract class QuestDetailItem(protected var key: String, protected var value: Any) {
    open fun toDescription(): String {
        return "$key : $value"
    }
}

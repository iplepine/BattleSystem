package com.zs.mol.model.dungeon_

class DungeonEvent<T>(var type: EventType) {
    enum class EventType {
        MONSTER, TREASURE, TRAP
    }

    var isFinished = false

    var title = ""
    var description = ""

    fun onChoice(listener: EventChoiceListener<T>) {
        if (checkSuccess(listener)) {
            onSuccess()
        } else {
            onFailed()
        }
        finish()
    }

    open fun checkSuccess(choiceListener: EventChoiceListener<T>): Boolean {
        return true
    }

    open fun onSuccess() {
    }

    open fun onFailed() {
    }

    fun finish() {
        isFinished = true
    }

    interface EventChoiceListener<T> {
        fun onChoice(choice: T): Boolean
    }
}
package com.zs.mol.model.unit.action

import kotlin.math.max

open class UnitAction(time: Long) {
    enum class State {
        READY, PAUSE, RUNNING, SUCCESS, FAILED
    }

    enum class ActionType {
        IDLE, REST, DIE, WAITING, EXPEDITION, BATTLE
    }

    var state = State.READY
    var actionType = ActionType.IDLE
    var time = time

    fun updateTime(time: Long) {
        this.time = max(this.time - time, 0)
    }

    fun isRunning(): Boolean {
        return 0 < time
    }
}
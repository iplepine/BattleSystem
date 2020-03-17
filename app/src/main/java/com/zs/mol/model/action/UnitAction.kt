package com.zs.mol.model.action

import kotlin.math.max

abstract class UnitAction : Runnable {
    enum class State {
        READY, PAUSE, RUNNING, SUCCESS, FAILED
    }

    var time: Long = 0
    var state = State.READY

    fun update(time: Long) {
        this.time = max(this.time - time, 0)
    }
}
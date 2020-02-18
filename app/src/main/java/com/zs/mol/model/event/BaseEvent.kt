package com.zs.mol.model.event

open class BaseEvent(var type: Int = 0, var message: String = "") {
    companion object Type {
        const val CONTROL = 0
        const val BATTLE = 1
    }
}

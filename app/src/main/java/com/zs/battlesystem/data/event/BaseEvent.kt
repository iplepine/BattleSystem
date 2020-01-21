package com.zs.battlesystem.data.event

data class BaseEvent(var type: Int = 0, var message: String = "") {
    companion object Type {
        const val CONTROLL = 0
        const val BATTLE = 1
    }
}

package com.zs.battlesystem.data.event

import android.os.Bundle

data class ControllEvent(var type: Int = BaseEvent.CONTROLL, var message: String = "") {
    val data = Bundle()
}

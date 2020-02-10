package com.zs.battlesystem.model.event

import android.os.Bundle

data class ControlEvent(var type: Int = BaseEvent.CONTROL, var message: String = "") {
    val data = Bundle()

    companion object {
        const val KEY_SKILL = "skill"

        object Type {
            const val USE_SKILL = 0
        }
    }
}

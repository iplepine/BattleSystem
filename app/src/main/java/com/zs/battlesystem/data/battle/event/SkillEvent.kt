package com.zs.battlesystem.data.battle.event

import android.os.Bundle

data class SkillEvent(var type: Int = 0, var message: String = "") {
    val data = Bundle()

    object Result {
        const val HIT = "hit"
        const val EVADE = "evade"
        const val CRITICAL = "critical"
        const val BLOCK = "blcok"
    }
}

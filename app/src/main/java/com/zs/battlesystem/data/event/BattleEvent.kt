package com.zs.battlesystem.data.event

import android.os.Bundle

open class BattleEvent(type: Int = 0, message: String = "") : BaseEvent(type, message) {
    val data = Bundle()
    var result = Result.HIT

    var userId: String = ""
    var targetIds: List<String> = ArrayList()
    var skillName: String = ""

    companion object Result {
        const val HIT = "hit"
        const val EVADE = "evade"
        const val CRITICAL = "critical"
        const val BLOCK = "blcok"
    }
}

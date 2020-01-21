package com.zs.battlesystem.data.battle.event

data class BattleEvent(var type: Int = 0, var message: String = "") {
    object Type {
        const val SKILL = 0
    }
}

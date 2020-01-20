package com.zs.battlesystem.data.battle

data class BattleEvent(val type: Type, val message: String) {
    object Type {
        const val SKILL = 0
    }
}

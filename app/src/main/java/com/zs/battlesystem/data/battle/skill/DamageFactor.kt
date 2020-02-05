package com.zs.battlesystem.data.battle.skill

import com.zs.battlesystem.data.battle.unit.BattleUnit

data class DamageFactor(
    val who: BattleUnit,
    val key: String,
    val amount: Double,
    val isFlat: Boolean
)
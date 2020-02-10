package com.zs.battlesystem.model.battle.skill

import com.zs.battlesystem.model.battle.unit.BattleUnit

data class DamageFactor(
    val who: BattleUnit,
    val key: String,
    val amount: Double,
    val isFlat: Boolean
)
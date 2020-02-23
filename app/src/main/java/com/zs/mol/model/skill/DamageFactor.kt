package com.zs.mol.model.skill

import com.zs.mol.model.unit.BattleUnit

data class DamageFactor(
    val who: BattleUnit,
    val key: String,
    val amount: Double,
    val isFlat: Boolean
)